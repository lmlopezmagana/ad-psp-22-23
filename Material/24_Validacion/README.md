# Validación

# 1. Validación con Spring 

La validación es un proceso mediante el cual podemos asegurar que los datos de entrada de una aplicación cumplen una serie de restricciones; y si no las cumplen, podemos informar al usuario (en nuestro caso, cliente de la API Rest) de los errores que ha cometido al proporcionarnos esos datos.

Spring Boot tiene como estándar de facto para validar a [Hibernate Validator](http://hibernate.org/validator/), la implementación de referencia de la especificación de [validación de beans](https://beanvalidation.org/).

En primer lugar, si queremos utilizar esta validación en nuestro proyecto, y estamos usando Spring Boot 2.3 o superior, tendremos que añadir la siguiente dependencia:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

Este mecanismo de valiación nos permite utilizar anotaciones sobre las propiedades de nuestras clases (sean entidades o no) para que después puedan ser validadas conforme a dichas restricciones. Por ejemplo:

```java
public class Producto {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar en blanco")
    private String nombre;

    @NotNull(message = "El producto debe tener un precio")
    @Min(value = 0, message = "El producto debe tener un precio mayor o igual a 0")
    private Double precio;

    @Lob
    private String descripcion;

    @URL
    private String imagen;     // URL de la imagen
    
}
```

Según la especificación de _Bean Validation_ para Java, tenemos disponibles algunas anotaciones como:

- `@NotNull`: el valor no puede ser nulo
- `@Min`, `@Max`: el valor debe ser como mínimo o máximo un valor especificado
- `@NotEmpty`: el valor no puede ser nulo y debe tener al menos un carácter que no sea un espacio en blanco.
- `@Past`: el valor debe ser una fecha anterior a la fecha actual
- ...

Puedes encontrar todas estas anotaciones en la documentación oficial: [https://beanvalidation.org/2.0/spec/#builtinconstraints](https://beanvalidation.org/2.0/spec/#builtinconstraints)

El mecanismo por defecto de validación de beans nos permite escribir un 
mensaje de _error de validación_ en la propia etiqueta, como hemos podido 
comprobar.

Sin embargo, esta estrategia es poco sostenible en el tiempo, y tampoco nos permite aplicar internacionalización a nuestro proyecto. Veremos más adelante como podemos definir los mensajes de error en un fichero externo.

Ahora, si queremos que los datos proporcionados por el usuario (cliente del api) sean válidos con respecto a estas restricciones, tenemos que utilizar la anotación `@Valid` junto al mecanismo mediante el cual estamos recibiendo estos datos (usualmente a través de `@RequestBody` en un API Rest):

```java
@RestController
@RequiredArgsConstructor
@RequestMapping("/producto")
public class ProductoControlador {
    
    // Resto del código

    public Producto crear(@Valid @RequestBody Producto producto) {
        return servicio.save(producto);
    }
    
}

```

# 2. Cómo informar de los errores de validación

En primer lugar, es interesante que podamos añadir a nuestros modelos de 
errores uno adicional, que extienda a `ApiSubError` y que nos permita 
mostrar convenientemente la información de validación. Ésta debería incluir:

- El objeto en el que sucede el error de validación.
- De todos los campos del objetivo, sobre el que sucede el error
- El valor rechazado
- Un mensaje conveniente.

Podemos crear entonces una clase así:

```java
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ApiValidationSubError extends ApiSubError {

    private String objeto, campo, mensaje;
    private Object valorRechazado;
    
}

```



Cuando Spring Boot encuentra un argumento anotado con `@Valid`, automáticamente arranca la implementación predeterminada de JSR 380 - Hibernate Validator - y valida el argumento. Cuando el argumento de destino no pasa la validación, Spring Boot lanza una excepción de tipo `MethodArgumentNotValidException`.

Precisamente, uno de los métodos que podemos sobrescribir en nuestra clase `GlobalRestControllerAdvice` que viene definido en la claes `ResponseEntityExceptionHandler` de la cual estamos heredando es `handleMethodArgumentNotValid`; es decir, este método nos permite manejar cómo informar de los errores de validación que se hayan producido.

La implementación de este método puede quedar así:

```kotlin
@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return buildApiError(ex, request, ex.getFieldErrors()
        .stream().map(error -> ApiValidationSubError.builder()
    .objeto(error.getObjectName())
    .campo(error.getField())
    .valorRechazado(error.getRejectedValue())
    .mensaje(error.getDefaultMessage())
    .build())
    .collect(Collectors.toList())
    );
}



    private ResponseEntity<Object> buildApiError(Exception ex, WebRequest request, List<ApiSubError> subErrores) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI(), subErrores));

}

    // Resto del código

}

```

El método `handleMethodArgumentNotValid` recibe como argumentos:

- La excepción de tipo `MethodArgumentNotValidException`.
- Los encabezados de la petición en una instancia de tipo `HttpHeaders`.
- El estado de la respuesta
- La petición en sí encapsulada en un objeto de tipo `WebRequest`.

El resultado de la petición con el siguiente `Request Body`:

```json
{
    "precio": -3,
    "imagen": "asdfg"
}
```

sería el siguiente:

```json
{
    "estado": "NOT_FOUND",
    "codigo": 404,
    "mensaje": "Errores varios en la validación",
    "ruta": "/producto/",
    "fecha": "29/11/2021 09:46:06",
    "subErrores": [
        {
            "objeto": "producto",
            "campo": "nombre",
            "mensaje": "El nombre del producto no puede estar en blanco",
            "valorRechazado": null
        },
        {
            "objeto": "producto",
            "campo": "precio",
            "mensaje": "El producto debe tener un precio mayor o igual a 0",
            "valorRechazado": -3.0
        },
        {
            "objeto": "producto",
            "campo": "imagen",
            "mensaje": "debe ser un URL válido",
            "valorRechazado": "asdfg"
        }
    ]
}
```

Si revisamos la documentación de `MethodArgumentNotValidException`, podemos encontrar que tiene un método, llamado `fieldErrors`, que es una lista de objetos `FieldError`. Cada uno de estos objetos encapsula un error de validación, permitiéndonos obtener sobre qué objeto y campo del mismo ha sucedido el error, cuál es el valor rechazado y qué mensaje para informar al usuario tenemos que proporcionar. De esta forma, podemos transformar el objeto de tipo `MethodArgumentNotValidException` con su lista de `FieldError` en un `ApiError` con una lista de `ApiValidationSubError`.

### 3 Cómo separar los mensajes de error en un fichero independiente

En el apartado anterior decíamos que era poco manejable asociar el mensaje de 
error a la anotación de una forma directa, porque esto era difícilmente mantenible, y no permite que podamos tener ese mismo mensaje de error en diferentes idiomas.

Spring nos proporciona un mecanismo mediante el cuál podemos separar los mensajes de error en un fichero (de properties) independiente. Para ello tenemos que indicar:

- Que cargue dicho fichero en un bean específico
- Asociar ese bean específico como fuente de mensajes al mecanismo de validación por defecto.

Todo ello lo hacemos a través de una clase de configuración con dos beans.

```java
@Configuration
public class ConfiguracionValidacion {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:errores");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

}

```

- El primer método, `messageSource`, indica que vamos a cargar un fichero, 
  que se llama `errores` (sin indicar la extensión del fichero), y que se 
  encuentra en algún sitio dentro del `classpath` (como por ejemplo puede ser la ruta `src/main/resources`), y que la codificación del contenido es UTF-8.
- El segundo método, `getValidator`, configura el validador por defecto asignándole como fuente de mensajes de validación (`setValidationMessageSource`) el bean anterior.

Podemos entonces crear un fichero, llamado `errores.properties`, que podría 
tener un contenido así:

```properties
producto.nombre.blank=El nombre del producto no puede quedar vacío
producto.precio.null=El precio del producto es un campo obligatorio
producto.precio.min=El precio del producto debe ser como mínimo 0
producto.imagen.url=La imagen de un producto debe ser una URL válida
```

En él podemos definir diferentes entradas, asociándole un mensaje de error. 
Una buena regla mnemotécnica para dar consistencia a los títulos de los 
mensajes de error puede ser `objeto.campo.restriccion`, como por ejemplo: 
`producto.nombre.blank` o `producto.precio.min`.

> Este mecanismo es más potente de lo que se presenta aquí, ya que estos mensajes pueden recibir argumentos desde fuera, o se puede internacionalizar creando diferentes ficheros con el nombre `messages-lang.properties` donde `lang` es el código ISO del idioma (`messages-en.properties`, `messsages-fr.properties`, ...). Puedes encontrar un ejemplo más completo en [https://www.javadevjournal.com/spring-boot/spring-custom-validation-message-source/](https://www.javadevjournal.com/spring-boot/spring-custom-validation-message-source/)

De esta forma, cuando definimos nuestras anotaciones de validación, en lugar 
de escribir un mensaje literal, podemos hacer referencia a alguna de las 
entradas escritas en `errores.properties`.

```java
public class Producto {

    @Id
    @GeneratedValue
    private Long id;

    //@NotBlank(message = "El nombre del producto no puede estar en blanco")
    @NotBlank(message = "{producto.nombre.blank}")
    private String nombre;

    //@NotNull(message = "El producto debe tener un precio")
    @NotNull(message="{producto.precio.null}")
    //@Min(value = 0, message = "El producto debe tener un precio mayor o igual a 0")
    @Min(value=0, message="{producto.precio.min}")
    private Double precio;

    @Lob
    private String descripcion;

    //@URL
    @URL(message = "{producto.imagen.url}")
    private String imagen;     // URL de la imagen



}

```

> Este ejemplo es bastante simple en cuanto al modelo utilizado, y por ello 
> no se han creado DTOs para, por ejemplo, crear objetos. En el caso de 
> utilizarse este patrón, muy posiblemente las anotaciones de validación 
> deberían estar colocadas en las clases DTO de creación o edición, ya que 
> es a través de ellas donde vamos a recibir los datos a validar.