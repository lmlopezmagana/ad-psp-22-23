# Validación (cuarta parte)

# 1. Validador personalizado de más de un campo a la vez

Existen algunas ocasiones en las que no nos basta con comprobar determinadas 
reglas de negocio frente a un atributo, sino que queremos comprobarlas 
frente a varios atributos a la vez. Un ejemplo clásico es el alta de un 
usuario, al que se le solicita que introduzca la contraseña dos veces (esto 
se hace para garantizar que la constraseña ha sido bien escrita). La 
validación que queremos aplicar en este caso es que sendos campos contengan 
el mismo valor.

Spring nos ofrece la posibilidad de declarar anotaciones de validación a 
nivel de clase. De esta forma podremos aplicar una regla de validación a más 
de un campo a la vez.

Vamos a ver cómo hacer esto de una forma simple, o también múltiple.

# 2. Versión simple (versión 1)

En este caso, vamos a crear una anotación muy concreta, que nos servirá para 
verificar, a la hora de crear un usuario, que el campo `password` y 
`verifyPassword` tienen el mismo contenido. Al tratarse de una validación de 
dos campos a la vez, no podemos aplicar una anotación sobre uno u otro campo,
sino que tendrá que ser una anotación sobre la clase.

## 2.1 Anotación

El código de la nueva anotación quedaría así:

```java
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PasswordsMatch {

    String message() default "Las contraseñas introducidas no coinciden";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};

    String passwordField();
    String verifyPasswordField();
}
```

La mayoría del contenido de esta anotación no es nuevo, y lo hemos revisado 
en ejemplos anteriores.

Llaman la atención los métodos `passwordField` y `verifyPasswordField`. A 
través de ellos, vamos a indicar el nombre, dentro de nuestro DTO de usuario,
de los campos que vamos a utilizar para guardar la contraseña y su duplicado.
De esa forma, podemos utilizar dichos campos en el validador.

## 2.2 Validador

El código del validador quedaría así:

```java
public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

    private String passwordField;
    private String verifyPasswordField;

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        passwordField = constraintAnnotation.passwordField();
        verifyPasswordField = constraintAnnotation.verifyPasswordField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String password = (String) PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(passwordField);
        String verifyPassword = (String) PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(verifyPasswordField);

        return StringUtils.hasText(password) && password.contentEquals(verifyPassword);


    }
}
```

En este caso, sí que tenemos diferentes con el código de ejemplos anteriores:

1. Nuestro validador tendrá dos atributos, que son los nombres de los campos 
  que vamos a comparar.

2. El método `initialize` sí que tiene contenido. Si nos fijamos, este 
   método, que se invocará al inicializar el validador, recibe como 
   argumento la anotación a través de la cual ha sido invocado. Usando la 
   referencia a la anotación, podemos acceder a los métodos `passwordField()
   ` y `verifyPasswordField` que hemos definido antes, para inicializar las 
   propiedades de la clase que hemos declarado antes.

3. En la validación, utilizamos otro código que también es un poco 
   desconocido. El método `PropertyAccessorFactory.forBeanPropertyAccess
   (value)` nos permite acceder al objeto sobre el que se está aplicando la 
   validación (después veremos que es un DTO). Este método devuelve una 
   instancia de `BeanWrapperImpl`, que es una de las formas que nos ofrece 
   Spring para hacer ***introspección*** de objetos, es decir, de poder 
   _hurgar_ dentro de ellos. Si añadimos ahora la llamada al método 
   `getPropertyValue` de la clase `BeanWrapperImpl`, podemos acceder al 
   valor de una de sus propiedades. Como conocemos el nombre de los campos 
   que queremos consultar, la siguiente línea:

```java
PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(passwordField);
```

debería devolver el valor del campo `passwordField`. Lo devuelve como un 
`Object`, pero eso lo podemos solucionar fácilmente:

```java
String password = (String) PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(passwordField);
```

4. En el método `isValid`, ya solamente nos queda definir la condición que 
   se debe cumplir: que ambas cadenas sean iguales y no vacías. De ser así, 
   la validación es correcta.

## 2.3 Aplicación de la validación

Para aplicar la validación, podríamos usar el siguiente código:

```java
@Data
@NoArgsConstructor @AllArgsConstructor
@PasswordsMatch(passwordField = "password", verifyPasswordField = "verifyPassword", message = "{usuario.password.notmatch}")
public class CreateUsuarioDtoV1 {

    private String login;
    @Email
    private String email;
    @NotEmpty
    private String password;
    private String verifyPassword;

}


@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final UsuarioServicio servicio;

    @PostMapping("/v1")
    public ResponseEntity<Usuario> nuevoUsuario(@Valid @RequestBody CreateUsuarioDtoV1 newUser){
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.saveV1(newUser));
    }
    
}
```

## 2.4 Cambios en la configuración de gestión de errores

Si probamos este endpoint con el siguiente objeto:

```json
{
    "login": "lmlopez",
    "email": "lmlopez-salesianos",
    "password": "",
    "verifyPassword": "12345"
}
```

Podemos comprobar que la respuesta debería ser un error 400. **Pero, no trae 
los suberrores como esperamos. ¿Cuál es la razón?**

Hasta ahora, los errores de validación los hemos gestionado a través del 
método `handleMethodArgumentNotValid`. En el mismo, usábamos un código como 
este:

```java
return buildApiErrorWithSubError(HttpStatus.BAD_REQUEST, "Errores varios en la validación",
                request,
                ex.getFieldErrors()
                        .stream().map(error -> ApiValidationSubError.builder()
                                .objeto(error.getObjectName())
                                .campo(error.getField())
                                .valorRechazado(error.getRejectedValue())
                                .mensaje(error.getDefaultMessage())
                                .build())
                        .collect(Collectors.toList())
        );
```

Es decir, estamos suponiendo que todos los errores serán de tipo 
`FieldError`, es decir, un error de un campo. Sin embargo, la nueva 
anotación no produce un error de este tipo, ya que no es una anotación a 
nivel de miembro, sino a nivel de objeto. El tipo de error es `ObjectError`, 
y solamente los podemos obtener si invocamos al método `getAllErrors` de la 
excepción.

¿Cómo podemos gestionar entonces los errores que son a nivel de miembro, 
como los que ya teníamos, y también los que son a nivel de objeto? Con un 
código como el siguiente:

```java
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


        List<ApiSubError> subErrorList = new ArrayList<>();

        ex.getAllErrors().forEach(error -> {

            // Si el error de validación se ha producido a raíz de una anotación en un atributo...
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;

                subErrorList.add(
                        ApiValidationSubError.builder()
                                .objeto(fieldError.getObjectName())
                                .campo(fieldError.getField())
                                .valorRechazado(fieldError.getRejectedValue())
                                .mensaje(fieldError.getDefaultMessage())
                                .build()
                );
            }
            else // Si no, es que se ha producido en una anotación a nivel de clase
            {
                ObjectError objectError = (ObjectError) error;

                subErrorList.add(
                        ApiValidationSubError.builder()
                                .objeto(objectError.getObjectName())
                                .mensaje(objectError.getDefaultMessage())
                                .build()
                );
            }


        });



        return buildApiErrorWithSubError(HttpStatus.BAD_REQUEST, "Errores varios en la validación",
                request,
                subErrorList.isEmpty() ? null : subErrorList

        );
    }

```

Los pasos que se siguen son:

1. Creamos una lista vacía para albergar todos los suberrores.
2. Obtenemos todos los errores (del tipo que sean) a partir de la excepción, 
   e iteramos sobre ellos.
3. Si el error es del tipo `FieldError`, lo tratamos como ya hicimos en 
   ejemplos anteriores. Creamos una nueva instancia de ApiValidationSubError 
   y la añadimos a la lista.
4. Si el error es del tipo `GlobalError`, solamente tendremos valor para las 
   propiedades  `objeto` y `mensaje`, por lo que solamente seteamos las mismas.
5. A la hora de devolver la respuesta, si hemos llegado a este método es 
   porque la lista de suberrores tendrá algún valor, pero aun así podemos 
   comprobar que no está vacía, y de estarlo, la seteamos a null.

La última modificación sobre el código base anterior estaría en la clase 
`ApiValidationSubError`:

```java
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ApiValidationSubError extends ApiSubError {

    private String objeto;
    private String mensaje;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String campo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object valorRechazado;


}

```

Al añadir la anotación `@JsonInclude(JsonInclude.Include.NON_NULL)` sobre 
los atributos `campo` y `valorRechazado` le estamos diciendo que si su valor 
es `null`, no aparezcan en el JSON de respuesta.

Si volvemos a probar con el siguiente JSON:

```json
{
    "login": "lmlopez",
    "email": "lmlopez-salesianos",
    "password": "",
    "verifyPassword": "12345"
}
```

La respuesta obtenida será:

```json
{
  "estado": "BAD_REQUEST",
  "codigo": 400,
  "mensaje": "Errores varios en la validación",
  "ruta": "/usuario/v1",
  "fecha": "14/12/2021 20:50:51",
  "subErrores": [
    {
      "objeto": "createUsuarioDtoV1",
      "mensaje": "no debe estar vacío",
      "campo": "password",
      "valorRechazado": ""
    },
    {
      "objeto": "createUsuarioDtoV1",
      "mensaje": "Las contraseñas del nuevo usuario no coinciden"
    },
    {
      "objeto": "createUsuarioDtoV1",
      "mensaje": "debe ser una dirección de correo electrónico con formato correcto",
      "campo": "email",
      "valorRechazado": "lmlopez-salesianos"
    }
  ]
}
```

# 3. Versión multiple (versión 2)

Supongamos que queremos hacer esta comprobación de parejas de campos más 
genérica. Por ejemplo, queremos comprobar, para la creación del mismo 
usuario, que las contraseñas coinciden, pero también los emails (la 
dirección de correo eletrónico es un campo sensible que en ocasiones es 
bueno verificar pidiéndolo dos veces).

¿Cuál sería la solución? ¿Crear una segunda anotación, por ejemplo llamada 
`@EmailsMatch`? Podría ser; pero también podemos crear una anotación más 
genérica, y que nos permita comprobar más de una pareja de campos en la 
misma clase.

## 3.1 Anotación

El código de la anotación podría ser como el siguiente:

```java
@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {

    String message() default "Los valores de los campos no coinciden";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String field();

    String fieldMatch();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }
}
```

Si nos fijamos, se parece al anterior, pero también añade los siguiente:

```java
@Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }
```

¿Qué significa? Pues quiere decir que esta anotación podrá definirse a 
través del método `List`, que podrá incluir un array de anotaciones de este 
tipo. Y por tanto, para una misma clase, podremos definir una lista de 
parejas de campos a verificar.

## 3.2 Validador

El validador sería parecido al anterior:

```java
public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        
        Object fieldValue = PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(field);
        Object fieldMatchValue = PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(fieldMatch);
        
        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return fieldMatchValue == null;
        }
    }
}

```

- La inicialización es muy parecida a la del punto 2.2
- El método `isValid` también lo es, con la salvedad de que en este caso lo 
  hemos dejado más genérico, utilizando referencias de tipo `Object`. De 
  esta forma podemos usarlo no solamente con  `String`, sino con objetos más 
  complejos, siempre y cuando tengan correctamente implementado el método 
  `equals`.

## 3.3 Aplicación de la validación

Sea una segunda versión del DTO para crear un usuario:

```java
@Data
@NoArgsConstructor @AllArgsConstructor
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Las contraseñas no coinciden"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "verifyEmail",
                message = "Las direcciones de correo electrónico no coinciden"
        )
})
public class CreateUsuarioDtoV2 {

    private String login;
    @Email
    private String email;
    @Email
    private String verifyEmail;
    @NotEmpty
    private String password;
    private String verifyPassword;

}
```

En este caso:

- A través del método `List` de la anotación, podemos definir un array de 
  anotaciones de validación de campo.
- Para cada una de ellas, podemos declarar el nombre de los campos a 
  comprobar, y el mensaje de error.

Si tratamos de crear un usuario con el siguiente JSON:

```json
{
    "login": "lmlopez",
    "email": "lmlopez-salesianos",
    "password": "",
    "verifyPassword": "12345"
}
```

El mensaje de error sería este:

```json
{
    "estado": "BAD_REQUEST",
    "codigo": 400,
    "mensaje": "Errores varios en la validación",
    "ruta": "/usuario/v2",
    "fecha": "14/12/2021 21:00:00",
    "subErrores": [
        {
            "objeto": "createUsuarioDtoV2",
            "mensaje": "debe ser una dirección de correo electrónico con formato correcto",
            "campo": "email",
            "valorRechazado": "lmlopez-salesianos"
        },
        {
            "objeto": "createUsuarioDtoV2",
            "mensaje": "Las contraseñas no coinciden"
        },
        {
            "objeto": "createUsuarioDtoV2",
            "mensaje": "Las direcciones de correo electrónico no coinciden"
        },
        {
            "objeto": "createUsuarioDtoV2",
            "mensaje": "no debe estar vacío",
            "campo": "password",
            "valorRechazado": ""
        }
    ]
}
```


# 4. Bibliografía

- https://www.baeldung.com/spring-mvc-custom-validator
- https://blog.tericcabrel.com/write-custom-validator-for-body-request-in-spring-boot/