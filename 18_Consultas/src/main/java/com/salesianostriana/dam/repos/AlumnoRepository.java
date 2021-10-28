/**
 * 
 */
package com.salesianostriana.dam.repos;

import com.salesianostriana.dam.model.Alumno;
import com.salesianostriana.dam.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

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
	
}
