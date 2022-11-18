
# Ejemplo 7 - Ejemplo de un Servicio Base abstracto

## Antecedentes

Ya hemos comentado en ejemplos anteriores que una buena práctica para evitar el acoplamiento es no utilizar los repositorios directamente desde los controladores. En su lugar, es bueno implementar una capa de servicios que capture toda la lógica de negocio.

Los métodos CRUD (inserción de una nueva entidad, edición de una existente, borrado de una entidad, búsqueda de una entidad por su Id y la búsqueda de todas las entidades de un tipo) suelen ser muy repetitivos de implementar. De hecho, su codificación en el servicio es simplemente un envoltorio de los métodos correspondientes en el repositorio.

Por eso, es sensato proporcionar una implementación base de un servicio abstracto, que nos proporcione esta implementación, y nos permita centrarnos solo en nuestra lógica de negocio.

## Ejemplo de un servicio base: `BaseService`

Proponemos la siguiente implementación:

```java
public abstract class BaseService<T, ID, R extends JpaRepository<T, ID>> {

	@Autowired
	protected R repositorio;
	
	public T save(T t) {
		return repositorio.save(t);
	}
	
	public Optional<T> findById(ID id) {
		return repositorio.findById(id);
	}
	
	public List<T> findAll() {
		return repositorio.findAll();
	}
	
	public T edit(T t) {
		return repositorio.save(t);
	}
	
	public void delete(T t) {
		repositorio.delete(t);
	}
	
	public void deleteById(ID id) {
		repositorio.deleteById(id);
	}
	
	
}
```

Utilizamos _genéricos_ para establecer los tipos de datos que necesitamos:

- `T`, el tipo de entidad que vamos a gestionar.
- `ID`, el tipo de dato su de Id (_clave primaria_).
- `R`, el tipo de dato de su repositorio (por ello utilizamos un parámetro de _tipo delimitado_).

## Creación de un nuevo servicio a partir del servicio base

La creación de un nuevo servicio a partir de este es ya muy sencilla

```java
@Service
public class AlumnoServicio 
	extends BaseService<Alumno, Long, AlumnoRepository>{

}
```

## Limitaciones de esta solución

Una de las limitaciones que tiene es fácilmente subsanable. Se podría dar el caso de que el tipo `T` no fuese una entidad de nuestro modelo. Para solucionarlo, podríamos _forzar_ a que todas nuestras clases modelo implementaran una interfaz, y utilizar dicha interfaz para delimitar el tipo del parámetro `T`.