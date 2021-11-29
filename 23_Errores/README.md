# Manejo de errores

Este ejemplo y los siguientes nos va a permitir profundizar en un par de 
conceptos 
que están 
relacionados y que son necesarios para un buen desarrollo de nuestra API Rest desarrollada con Spring Boot.

## 1. Manejo de errores

Antes de Spring 3.2, los dos enfoques principales para manejar excepciones en una aplicación Spring MVC eran `HandlerExceptionResolver` o la anotación `@ExceptionHandler` . Ambos tienen algunas desventajas claras.

Desde la versión 3.2, hemos tenido la anotación `@ControllerAdvice` (y `@RestControllerAdvice`) para abordar las limitaciones de las dos soluciones anteriores y promover un manejo unificado de excepciones en toda la aplicación.

Ahora Spring 5 presenta la clase `ResponseStatusException`, una forma rápida para el manejo básico de errores en nuestras API REST.

Todos ellos tienen una cosa en común: abordan muy bien la separación de preocupaciones. La aplicación puede generar excepciones normalmente para indicar una fallo de algún tipo, que luego se manejará por separado.

> Todos estos enfoques parten de la base de definir excepciones propias para el manejo de situaciones de error.


### 1.1 Enfoque con `@ExceptionHandler` en el controlador

La primera solución funciona a nivel de `@Controller` o `@RestController`. 
Definiremos un 
método para manejar excepciones y lo anotaremos con `@ExceptionHandler`:

```java
public class FooController {
    
    //...
    @ExceptionHandler({CustomException1.class, CustomException2.class})
    public void handleException() {
        // devolver respuesta de error conveniente
    }
}
```

Este enfoque tiene el problema de que el tratamiento de errores que se realiza aplica solamente a este controlador. Si queremos que este tratamiento esté unificado en varios controladores, tendríamos que replicar el código en cada controlador, lo cual no es mantenible.

### 1.2 Enfoque con `@ControllerAdvice` o `@RestControllerAdvice`

Si queremos tener métodos anotados con `@ExceptionHandler` que nos permitan manejar errores de forma global a más de un controlador podemos definir estos métodos en una clase anotada con `@ControllerAdvice` (si se trata de una API Rest, con `@RestControllerAdvice`).

![Arquitectura del manejo de errores en una aplicación Spring](https://uploads.toptal.io/blog/image/123908/toptal-blog-image-1503383110049-1cd3d10e7706d202ceb2a844d63f7351.png)

Entonces, al usar `@ExceptionHandler` y `@RestControllerAdvice`, podremos definir un punto central para tratar las excepciones y envolverlas en un objeto de error conveniente con mejor organización que el mecanismo predeterminado de manejo de errores de Spring Boot.

Si bien podemos definir una clase cualquiera como `@ControllerAdvice` o `@RestControllerAdvice`, es cierto que Spring Boot nos ofrece una clase conveniente que extender, que es `ResponseEntityExceptionHandler`. Esta clase es una clase base conveniente para que podamos personalizar el manejo de errores. Ofrece métodos que podemos sobrescribir para tratar algunos de los errores más comunes.

```java
@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler
{

}
```

Retomaremos este código más adelante, pero antes vamos a ver cuál va a ser la forma en que vamos a _envolver_ nuestros mensajes de error para que la respuesta sea más conveniente.

## 2. Mensajes de error

El módulo Spring Framework MVC viene con algunas características excelentes para ayudar con el manejo de errores. Pero queda en manos del desarrollador usar esas funciones para tratar las excepciones y devolver respuestas significativas al cliente API.

Un mensaje de error tipo si enviamos una petición POST incorrecta, por 
ejemplo, en el tipo de dato de uno de los atributos, sería este:

```json
{
 "timestamp": 1500597044204,
 "status": 400,
 "error": "Bad Request",
 "exception": "org.springframework.http.converter.HttpMessageNotReadableException",
 "message": "JSON parse error: Unrecognized token 'three': was expecting ('true', 'false' or 'null'); nested exception is com.fasterxml.jackson.core.JsonParseException: Unrecognized token 'aaa': was expecting ('true', 'false' or 'null')\n at [Source: java.io.PushbackInputStream@cba7ebc; line: 4, column: 17]",
 "path": "/birds"
}
```

Bueno ... el mensaje de respuesta tiene algunos campos buenos, pero se 
centra demasiado en cuál fue la excepción. Por cierto, esta es la clase 
`DefaultErrorAttributes` de Spring Boot. El campo `timestamp` es un número 
entero que ni siquiera contiene información de en qué unidad de medida se 
encuentra la marca de tiempo. El campo `exception` sólo es interesante para 
los desarrolladores de Java y el mensaje deja a los consumidores del API 
perdidos en todos los detalles de implementación que son irrelevantes para 
ellos. ¿Y si hubiera más detalles que pudiéramos extraer de la excepción de 
la que se originó el error? Vamos a tratar de crear una representación JSON 
más agradable par nuestros errores para así facilitar la vida de los 
clientes de la API REST.

```java


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {

    private HttpStatus estado;
    private String mensaje;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiSubError> subErrores;        
}
```

- El campo `estado` nos permitirá saber el estado de la respuesta (400 - BAD_REQUEST, por ejemplo)
- El campo `mensaje` puede contener un mensaje sencillo sobre el error.
- El campo `fecha` definirá una representación de la fecha más conveniente que el `toString` por defecto de `LocalDateTime`
- El campo `subErrores` contendrá una serie de errores secundarios que sucedieron. Un ejemplo sería los errores de validación, en el cual varios campos pueden no haber superado este proceso (por ejemplo, nos hemos equivocado dejando un campo en blanco, y un número no acotado dentro de un rango).

> Como no puede ser de otra manera, este modelo es una propuesta, y se 
> podrían añadir, modificar o suprimir los campos que se estimen 
> convenientes. Un ejemplo de añadir otro campo podría ser el _status code_ 
> con su valor numérico, por si es más fácil de comparar en el cliente.

Podemos completar este código con el siguiente:

```java


public abstract class ApiSubError {
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiValidationSubError extends ApiSubError {

    private String objeto;
    private String campo;
    private Object valorRechazado;
    private String mensaje;

}
```

De esta forma, `ApiValidationSubError` es una clase que extiende de `ApiSubError` y que nos permite encapsular un error sucedido durante una validación.

A continuación podemos ver cuál podría ser una respuesta más o menos completa a una petición REST que produce un error `400`:

```json
{
    "estado": "BAD_REQUEST",
    "mensaje": "Error de validación",
    "subErrores": [
        {
            "objeto": "editCategoriaDto",
            "campo": "urlImagen",
            "valorRechazado": "asdfg",
            "mensaje": "debe ser un URL válido"
        },
        {
            "objeto": "editCategoriaDto",
            "campo": "nombre",
            "valorRechazado": "",
            "mensaje": "El nombre de la categoría no puede quedar vacío"
        }
    ],
    "fecha": "19/11/2021 09:03:31"
}
```

Vamos a unificar lo que hemos trabajado hasta ahora para poder tener mensajes de error como este en un tratamiento de errores unificado para toda nuestra API Rest.

## 3. Manejo de errores global con una respuesta conveniente

En el punto 1.2 nos quedamos con este fragmento de clase

```java
@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler
{

}
```

Decíamos que `ResponseEntityExceptionHandler` es una clase conveniente para 
extender y crear un _ControllerAdvice_. Si revisamos su documentación, vemos 
que tiene muchos métodos que podemos extender. El más interesante es `public 
ResponseEntity<?> handleExceptionInternal(...) : `. De él, dice la 
documentación: _A single place to customize the response body of all exception types_, un único lugar para personalizar el cuerpo de las respuestas de error para todos los tipos de excepciones.

Por tanto, podemos sobrescribir este método para utilizar la clase `ApiError` para encapsular nuestros mensajes de error; y esto daría cobertura a todas las excepciones generadas en nuestra api:

```java
@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiError(ex, request);
    }


    private final ResponseEntity<Object> buildApiError(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()));

    }

}

```

De esta forma, la respuesta de cualquier excepción provocada en el sistema tendrá el JSON de respuesta que nosotros buscábamos.


### 3.1 Manejo de excepciones específicas

Este tratamiento global no quita que podamos definir algunas de un tipo específico. Supongamos que queremos dar un tratamiento específico a la situación de error de buscar una entidad o lista de entidades, y no encontrar nada en nuestro repositorio. Lo normal sería devolver una respuesta 404. Podemos manejar esto en nuestro controlador/servicio lanzando una excepción específica de entre las siguientes:

```java
public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String mensaje) {
        super(mensaje);
    }
    
}


public class SingleEntityNotFoundException extends EntityNotFoundException {
    
    public SingleEntityNotFoundException(String id, Class clazz) {
        super(String.format("No se puede encontrar una entidad del tipo %s con ID: %s", clazz.getName(), id));
    }
    
}

public class ListEntityNotFoundException extends EntityNotFoundException {

    public ListEntityNotFoundException(Class clazz) {
        super(String.format("No se pueden encontrar elementos del tipo %s ", clazz.getName()));
    }

}



```
Estas excepciones se podrían lanzar así:

```java
@Service
@RequiredArgsConstructor
public class ProductoServicio {

    private final ProductoRepositorio repositorio;


    public List<Producto> findAll() {
        List<Producto> result = repositorio.findAll();

        if (result.isEmpty()) {
            throw new ListEntityNotFoundException(Producto.class);
        } else {
            return result;
        }
    }


}

@RestController
@RequiredArgsConstructor
@RequestMapping("/producto")
public class ProductoControlador {

    private final ProductoServicio servicio;

    @GetMapping("/")
    public List<Producto> todos() {
        return servicio.findAll();
    }


}
```

Para captura este tipo de excepciones, y manejarlas, añadiríamos este método a nuestro `GlobalRestControllerAdvice`:

```java
@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return buildApiError(ex, request);
    }
    
}
```

Nótese que este método no extiende a ninguno definido por `ResponseEntityExceptionHandler`, sino que es un método propio; está anotado con `@ExceptionHandler`, que recibe como argumento un array con las excepciones que _escuchará_ y tratará, dando respuesta.

Lo cual nos podría producir mensajes como este:

```json
{
    "estado": "NOT_FOUND",
    "mensaje": "No se pueden encontrar elementos del tipo com.salesianostriana.dam.modelo.Producto para esa consulta",
    "fecha": "19/11/2021 09:13:26",
    "ruta": "/producto/"
}
```

El mensaje de error se podría modificar a nuestra conveniencia, si se entiende que este no es el más adecuado.

## 3.2 Unificar la respuesta con `DefaultErrorAttributes`

Hay otro tipo de errores que no son capturados por el mecanismo de nuestro 
_ControllerAdvice_, sino que siguen siendo manejados por Spring: pongamos 
por ejemplo que hacemos una petición a una ruta que no existe.

El modelo de respuesta a ese error, tanto en aplicaciones web con en APIs 
REST viene manejado por la clase `org.springframework.boot.web.servlet.error.
DefaultErrorAttributes`. Esta clase, que es la implementación por defecto de 
la interfaz `ErrorAttributes` nos presenta los siguientes datos (según su 
documentación):

- `timestamp`: El momento en el que han ocurrido los errores
- `status`: El código de estado (numérico)
- `error`: La razón del error
- `exception`: El nombre de la clase de excepción raíz (si está configurada)
- `message`: El mensaje de la excepción (si está configurado)
- `errors`: Algunos `ObjectError`s provenientes de una excepción de tipo 
  `BindingResult` (si está configurado)
- `trace`: La traza de la pila (si está configurada)
- `path`: La URL que provocó el lanzamiento de la excepción.

Como podemos observar, este modelo de error tampoco nos resulta del todo 
adecuado, y lo ideal sería unificarlo con la estructura de `ApiError`. Para 
ello podemos crear un bean, que extienda a  `DefaultErrorAttributes` y que 
exponga aquellos datos que creamos convenientes. Una propuesta podría ser esta:

```java
@Component
public class MiDefaultErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        Map<String,Object> result =  Map.of(
                "estado",errorAttributes.get("status"),
                "codigo", HttpStatus.valueOf((int) errorAttributes.get("status")).name(),
                "fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")),
                "ruta", errorAttributes.get("path")
        );

        if (errorAttributes.containsKey("message")) {
            result.put("mensaje", errorAttributes.get("message"));
        }

        if (errorAttributes.containsKey("errors")) {
            result.put("subErrores", errorAttributes.get("errors"));
        }

        return result;
    }
}

```