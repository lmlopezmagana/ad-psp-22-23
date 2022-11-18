# Validación (tercera parte)

# 1. Validador personalizado

Cuando las anotaciones disponibles de Spring (o Hibernate Validator) no son 
suficientes, podemos crear nuestras anotaciones propias.

> ¡Qué emoción! Es casi seguro que será la primera vez que vayas a crear una 
> anotación por tu cuenta

El primer paso es definir una anotación con `@interface`. Veamos primero el 
código para poder describirlo.

> Una anotación en Java se define como una interfaz. La única diferencia es 
> que la palabra reservada `interface` viene con el sufijo de una arroba, 
> para indicar que se trata de una anotación.

```java
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueNameValidator.class)
@Documented
public @interface UniqueName {
  String message() default "El nombre del producto ya existe. Por favor, intenta crear el producto con uno nuevo.";
  
  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
```

- `@Target`: indica dónde podremos utilizar esta anotación. En este caso la 
  podremos usar en atributos de una clase y en métodos.
- `@Retention`: indica el momento en el que estará disponible la anotación. 
  Al indicar `RUNTIME`, le estamos diciendo que estará disponible para la 
  JVM durante la ejecución.
- `@Constraint`: nos permite decir que la anotación que estamos creando es 
  una restricción, e indicarle la clase que vamos a utilizar para realizar 
  la validación.
- `@Documented`: no es obligatoria. Simplemente indica que debe ser mostrada 
  en la documentación de Javadoc.

Nuestra `@interface` debe incluir forzosamente 3 métodos, que son:

- `message()`: se trata del mensaje que se mostrará en caso de que al 
  aplicar esta validación se produzca un error. Puede ser una cadena de 
  caracteres o la clave de una entrada en el fichero de properties 
  correspondiente.
- `groups()`: nos permite definir bajo qué circunstancias la validación será 
  lanzada, ya que se puede customizar validaciones diferentes en una misma 
  clase. Por ejemplo, podemos usar un mismo objeto en la creación y la 
  edición, pero posiblemente nos interese que la validación no sea 
  exactamente igual. 
> Tienes más información sobre este apartado aquí: https://reflectoring.io/bean-validation-with-spring-boot/#using-validation-groups-to-validate-objects-differently-for-different-use-cases
- `payload()`: nos permite añadir una _carga útil_ a esta validación. Su uso 
  es extremadamente raro, pero debemos incluirlo para cumplir con el 
  estándar de Spring.

La implementación del validador sería como esta:

```java
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

  @Autowired
  private ProductoRepositorio repositorio;

  @Override
  public void initialize(UniqueName constraintAnnotation) { }

  @Override
  public boolean isValid(String nombre, ConstraintValidatorContext context) {
    return StringUtils.hasText(nombre) && !repositorio.existsByNombre(nombre);
  }
}
```

Hay que hacer notar que nuestro validador debe implementar la interfaz 
`ConstraintValidator`. Se trata de una interfaz genérica, que debe recibir 
dos tipos:

- la anotación (`UniqueName`)
- el tipo de dato que vamos a validar (en este caso `String`).

Spring Framework automáticamente detecta todas las clases que implementan la 
interfaz `ConstraintValidator`. El framework los instancia y conecta todas 
las dependencias como si la clase fuera un bean cualquiera.

El uso de esta validación es sencillo:

```java
public class Producto {
    
    @NotBlank(message = "{producto.nombre.blank}")
    @UniqueName(message = "{producto.nombre.unico}")
    private String nombre;

    // Resto del código
  
}
```

**¡IMPORTANTE!*. Si por alguna razón, entre los ejemplos anteriores y este 
**sigues utilizando las entidades** para realizar las peticiones POST y PUT 
**en lugar de un DTO** ***te vas a encontrar con un problema***. Y es que la 
validación se va a realizar dos veces:

- Una, al encontrarse la anotación `@Valid` (esta es la que nosotros queremos).
- Otra, al persistir la información a través de Spring Data JPA.

Esta segunda validación, además de no ser necesaria, es un antipatrón. En 
teoría, si nuestra lógica de negocio (nuestros servicios) están bien 
implementados, es imposible que pueda llegar un dato no validado al momento 
de persistir una entidad. Por tanto:

- O bien utilizamos las anotaciones de validación sobre los DTOs.
- O añadimos la propiedad `spring.jpa.properties.javax.persistence.
  validation.mode` con valor `none` en nuestro fichero _application.properties_.