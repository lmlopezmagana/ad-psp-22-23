
# Entidades y métodos `equals`, `hashCode` y `toString`

La implementación de los métodos `equals()` y `hashCode()` para las clases de entidad es una cuestión que se discute a menudo.

¿Realmente los necesitas? Hay muchas aplicaciones que funcionan perfectamente bien utilizando los métodos predeterminados de Java proporcionados por la clase `Object`.

Y si necesita implementarlos, ¿cómo debería hacerlo? ¿Debería utilizar todos los atributos o solo las claves primarias?

## ¿Qué dice la especificación JPA?

Si echamos un vistazo a la especificación JPA, te sorprenderá encontrar solo 
2 menciones explícitas y 1 implícita de ambos métodos:

- Debes implementar los métodos `equals()` y `hashCode()` para las clases de 
claves primarias si asigna claves primarias compuestas.
- Si se [_mapea_ una asociación utilizando `Map`](https://thorben-janssen.
  com/map-association-java-util-map/) en lugar de `List`, la clave del `Map` 
  debe implementar los métodos `equals()` y `hashCode()`. Entonces, si usa una 
  entidad como clave, debe proporcionar ambos métodos.
- Puedes asignar asociaciones de one-to-many y de many-to-many a 
  diferentes subtipos de colección. Si se usa un `Set`, nuestras entidades 
  deben tener métodos `equals()` y `hashCode()`.
  
Desafortunadamente, solo la primera referencia proporciona una indicación 
clara de que se deben implementar estos métodos para las clases de clave 
primaria. De lo contrario, 2 instancias diferentes de su objeto de clave 
principal, que tienen los mismos valores de atributo, serían iguales en la 
base de datos pero no en nuestro código Java.

Obviamente, eso crearía muchos problemas, pero no responde a la pregunta de 
si es necesario implementar estos métodos para nuestras clases de entidad. La 
clase `Object` ya proporciona una implementación predeterminada de estos 
métodos. ¿Son lo suficientemente buenos o necesitamos sobrescribirlos?

## `equals` y `hashCode` en la clase `Object`



# Bibliografía

