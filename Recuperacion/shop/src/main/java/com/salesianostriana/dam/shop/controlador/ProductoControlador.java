package com.salesianostriana.dam.shop.controlador;


import com.salesianostriana.dam.shop.modelo.Producto;
import com.salesianostriana.dam.shop.repositorio.ProductoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoControlador {

    private final ProductoRepositorio repositorio;

    @GetMapping("/")
    //public List<Producto> todosLosProductos() {
    public ResponseEntity<List<Producto>> todosLosProductos() {
        //return repositorio.findAll();

        //List<Producto> result = repositorio.findAll();
        List<Producto> result = repositorio.findAll(Sort.by("nombre"));

        if (result.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/menos-de/{precio}")
    public ResponseEntity<List<Producto>> productosMenosXEuros(@PathVariable double precio) {
        List<Producto> result =
                repositorio.findByPrecioLessThan(precio);

        if (result.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);

    }

    @GetMapping("/nombre/{nom}")
    public ResponseEntity<List<Producto>> productosPorNombre(@PathVariable("nom") String nombre) {
        List<Producto> result =
                repositorio.findByNombreContainsIgnoreCase(nombre);

        if (result.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);

    }

    @GetMapping("/{id}")
    //public Producto unProductoPorId(@PathVariable Long id) {
    public ResponseEntity<Producto> unProductoPorId(@PathVariable Long id) {
        //return repositorio.findById(id).orElse(null);
        return ResponseEntity.of(repositorio.findById(id));
    }

    @PostMapping("/")
    //public Producto crearProducto(@RequestBody Producto nuevo) {
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto nuevo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(repositorio.save(nuevo));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto> editarProducto(
            @PathVariable Long id, @RequestBody Producto aEditar) {
        // 1) Buscar el producto por ID y comprobar que existe
        // 2) Modificarlo

        return repositorio.findById(id)
                .map(p -> {
                    p.setNombre(aEditar.getNombre());
                    p.setPrecio(aEditar.getPrecio());
                    p.setImagen(aEditar.getImagen());

                    return repositorio.save(p);
                })
                .map(p -> ResponseEntity.ok(p))
                .orElseGet(() -> ResponseEntity.notFound().build());



    }
    // IDEMPOTENTES - La ejecución repetida de la misma
    // petición debe devolver el mismo resultado

    @DeleteMapping("/{id}")
    public ResponseEntity borrar(@PathVariable Long id) {
        if (repositorio.existsById(id))
            repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
