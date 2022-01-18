package com.salesianostriana.dam.controller.web.withsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesianostriana.dam.controller.web.configuration.SpringSecurityTestWebConfig;
import com.salesianostriana.dam.controller.web.configuration.WebConfig;
import com.salesianostriana.dam.dto.CreateProductoDTO;
import com.salesianostriana.dam.dto.ProductoDTO;
import com.salesianostriana.dam.dto.converter.ProductoDTOConverter;
import com.salesianostriana.dam.modelo.Categoria;
import com.salesianostriana.dam.modelo.Producto;
import com.salesianostriana.dam.service.ProductoServicio;
import com.salesianostriana.dam.util.pagination.PaginationLinksUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import static com.salesianostriana.dam.controller.utils.ResponseBodyMatchers.responseBody;
import static org.hamcrest.Matchers.*;


import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = ProductoController.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import({ SpringSecurityTestWebConfig.class, WebConfig.class })
@ExtendWith(MockitoExtension.class)
class ProductoControllerWithSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductoServicio productoServicio;

    @MockBean
    private ProductoDTOConverter productoDTOConverter;

    @MockBean
    private PaginationLinksUtils paginationLinksUtils;

    Producto producto;
    ProductoDTO productoDTO;
    Page result1element;

    @BeforeEach
    void initTest() {
        producto = Producto.builder()
                .id(1L)
                .categoria(new Categoria(1L, "Categoría"))
                .nombre("Nombre del producto")
                .precio(12.34f)
                .imagen("http://")
                .build();
        productoDTO = ProductoDTO.builder()
                .id(1L)
                .categoria("Categoría")
                .nombre("Nombre del producto")
                .precio(12.34f)
                .imagen("http://")
                .build();

        result1element = new PageImpl(List.of(productoDTO));

    }


    @Test
    @WithMockUser(username = "admin", password = "admin")
    @DisplayName("GET /producto/ sin parámetros de filtrado")
    void whenValidRequestWithAdmin_thenReturns200() throws Exception {

        when(productoServicio.findByArgs(any(Optional.class),any(Optional.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(producto)));

        when(productoDTOConverter.convertToDto(producto)).thenReturn(productoDTO);



        mockMvc.perform(get("/producto/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content", hasSize(1)));
                //.andExpect(responseBody().containsObjectAsJson(result, RestResponsePage.class)); // No funciona como se espera con Page

    }

    @Test
    @DisplayName("GET /producto/ sin parámetros de filtrado sin usuario")
    void whenValidRequestWithoutUser_thenReturn401() throws Exception {
        mockMvc.perform(get("/producto/"))
                .andExpect(status().isUnauthorized());
    }


    /*
        Se pueden implementar más test para el controlador GET /producto/

        - Con otro rol de usuario, con respuesta OK
        - Filtrando productos
        - Sin productos que devolver, para comprobar que la respuesta es 404.

     */


    @Test
    //@WithMockUser(username = "admin", password = "admin")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void whenPostValidProductoDTO_thenReturn201() throws Exception {

        CreateProductoDTO createProductoDTO = new CreateProductoDTO();
        createProductoDTO.setNombre("Nombre del Producto");
        createProductoDTO.setCategoriaId(1l);
        createProductoDTO.setPrecio(12.34f);

        when(productoServicio.nuevoProducto(eq(createProductoDTO), any(MultipartFile.class))).thenReturn(producto);

        MockMultipartFile file = new MockMultipartFile("file", "producto.png", "image/png", new FileInputStream(ResourceUtils.getFile("classpath:producto.png")));
        MockMultipartFile json = new MockMultipartFile("nuevo", "", "application/json", objectMapper.writeValueAsBytes(createProductoDTO));


        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        request.addFile(file);
        request.addFile(json);


        mockMvc.perform(multipart("/producto/")
                        .file(file)
                        .file(json)
                )
                .andExpect(status().isCreated())
                .andDo(print());
                //.andExpect(responseBody().containsObjectAsJson(producto, Producto.class));


    }


}