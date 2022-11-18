# Validación (segunda parte)

# 1. `@Valid` vs `@Validated`

En el ejemplo 24 hemos podido ver que la anotación `@Valid` la podemos 
utilizar a nivel de método para validar un objeto que recibimos (normalmente 
a través de una petición POST o PUT).

Spring también nos ofrece la anotación `@Validated`, que se puede utilizar 
**a nivel de clase**, y que nos permite validar otros elementos de entrada, 
como por ejemplo los _RequestParam_ y los _PathVariable_.

# 2. Validación con `@Validated`

El uso de `@Validated` es un poco diferente. No estamos validando un objeto 
Java _complejo_, ya que normalmente las variables en el _path_ o los parámetros 
de una petición suelen ser valores `int` o `Integer`; o `String`.

En este caso, en lugar de añadir las anotaciones de validación en las 
propiedades de una clase, podemos añadirlas directamente en los argumentos 
de los métodos del controlador.

```java
@RestController
@RequiredArgsConstructor
@RequestMapping("/producto")
@Validated
public class ProductoControlador {
    
    @GetMapping("/{id}")
    public Producto uno(@PathVariable @Min(value = 0, message = "No se pueden buscar productos con un identificador negativo") Long id) {
        return servicio.findById(id);
    }

    // Resto del código


}


```

Al añadir la anotación `@Validated`, se realizará la validación de cada 
petición que se realice y que incluya alguna anotación de validación.

A diferencia de la anotación `@Valid`, que lanzaba una excepción de tipo 
`MethodArgumentNotValidException`, la anotación `@Validated` lanzará una 
excepción de tipo `ConstraintViolationException`. Spring no tiene un 
manejador por defecto para estas situaciones, por lo que se producirá un 
error 500. En nuestro caso, al haber sobrescrito el método 
`handleExceptionInternal`, el mensaje de error tendrá la estructura que ya 
conocemos, pero con un código de error que no es razonable.

Si queremos devolver una respuesta con error 400, y la estructura de error 
que venimos utilizando en ejemplos anteriores, debemos añadir un método a 
nuestra clase de gestión global de errores.

```java

@ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstrintViolationException(ConstraintViolationException ex, WebRequest request) {
        return buildApiErrorWithSubError(HttpStatus.BAD_REQUEST,
                "Errores varios en la validación",
                request,
                ex.getConstraintViolations()
                        .stream()
                        .map(cv -> ApiValidationSubError.builder()
                                .objeto(cv.getRootBeanClass().getSimpleName())
                                .campo(((PathImpl)cv.getPropertyPath()).getLeafNode().asString())
                                .valorRechazado(cv.getInvalidValue())
                                .mensaje(cv.getMessage())
                                .build())
                        .collect(Collectors.toList())
                );

    }


```

Este método se parece al que hicimos en el ejemplo anterior, pero con alguna 
variante:

- En el atributo _objeto_, devolveremos el nombre del controlador en el que 
  se ha producido el error de validación.
- En el atributo _campo_, devolveremos el _Path Variable_ o el _Request 
  Param_ sobre el que se ha producido el error de validación.
- El valor rechazado y el mensaje son idénticos al ejemplo anterior.

# 3. Validación en otros niveles diferentes a los controladores

Además de validar la entrada de los controladores, se pueden validar otros 
componentes de Spring, como por ejemplo los servicios. Podremos usar en 
ellos tanto la anotación `@Validated` como `@Valid`.

Si quieres saber más sobre este tema, puedes consultar la bibliografía.

# 4. Bibliografía

- https://reflectoring.io/bean-validation-with-spring-boot/
