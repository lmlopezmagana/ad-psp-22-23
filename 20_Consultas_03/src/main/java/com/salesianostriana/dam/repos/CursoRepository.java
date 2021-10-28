/**
 * 
 */
package com.salesianostriana.dam.repos;

import com.salesianostriana.dam.model.Curso;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio que gestiona todos los cursos de nuestro sistema
 * @author luismi
 *
 */
public interface CursoRepository extends JpaRepository<Curso, Long> {

	
	/**
	 * Buscar todos los cursos cuyo campo nombre contiene el valor proporcionado
	 * @param nombre
	 * @return Cursos que contienen dicha cadena de caracteres o una lista vacía
	 */
	List<Curso> findByNombreContains(String nombre);


	@EntityGraph(Curso.CURSO_CON_ALUMNOS)
	Optional<Curso> findById(Long id);
	
	
	
}
