/**
 * 
 */
package com.salesianostriana.dam.repos;

import com.salesianostriana.dam.model.Alumno;
import com.salesianostriana.dam.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author lmlopez
 *
 */
public interface AlumnoRepository 
	extends JpaRepository<Alumno, Long> {

	/**
	 * Devuelve el número de alumnos que hay en un curso dado 
	 */
	public long countByCurso(Curso curso);
	
	/**
	 * Devuelve 5 alumnos, ordenados por fecha de nacimiento descendente
	 * cuyo apellido sea igual al proporcionado
	 */
	public List<Alumno> findTop5ByApellido1OrderByFechaNacimientoDesc(String apellido1);
	
	/**
	 * Devuelve 3 alumnos cuyo primer o segundo apellido es alguno de los proporcionados
	 * @return Un Stream de alumnos
	 */
	public Stream<Alumno> findTop3ByApellido1ContainsOrApellido2Contains(String apellido1, String apellido2);



	@Query("select a from Alumno a where a.curso is null")
	public List<Alumno> encuentraAlumnoSinCurso();

	@Query("select a from Alumno a where fecha_nacimiento >= :fechaNacimiento and lower(a.curso.nombre) LIKE lower(concat('%',:nombre,'%'))")
	public List<Alumno> alumnosNacidosDespuesDe(@Param("fechaNacimiento") LocalDate fechaNacimiento, @Param("nombre") String nombreCurso);


}
