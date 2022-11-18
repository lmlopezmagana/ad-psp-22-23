package com.salesianostriana.dam.controller.web.withsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesianostriana.dam.controller.web.configuration.RestResponsePage;
import com.salesianostriana.dam.controller.web.configuration.SpringSecurityTestWebConfig;
import com.salesianostriana.dam.dto.CreateProductoDTO;
import com.salesianostriana.dam.dto.ProductoDTO;
import com.salesianostriana.dam.dto.converter.ProductoDTOConverter;
import com.salesianostriana.dam.modelo.Categoria;
import com.salesianostriana.dam.modelo.Producto;
import com.salesianostriana.dam.service.ProductoServicio;
import com.salesianostriana.dam.util.pagination.PaginationLinksUtils;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

import static com.salesianostriana.dam.controller.utils.ResponseBodyMatchers.responseBody;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {SpringSecurityTestWebConfig.class})
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true"})
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
    Page result1element, result1elementDto;


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

        result1element = new RestResponsePage(List.of(producto));


        result1elementDto = new RestResponsePage(List.of(productoDTO));


    }


    @Test
    //@WithUserDetails(value="admin", userDetailsServiceBeanName = "customUserDetailsService")
    @WithUserDetails("admin")
    @DisplayName("GET /producto/ sin parámetros de filtrado")
    void whenValidRequestWithAdmin_thenReturns200() throws Exception {

        when(productoServicio.findByArgs(eq(Optional.empty()), eq(Optional.empty()), any(Pageable.class)))
                        .thenReturn(result1element);


        when(productoDTOConverter.convertToDto(producto)).thenReturn(productoDTO);



        MvcResult result =  mockMvc.perform(get("/producto/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(content().json(objectMapper.writeValueAsString(result1elementDto)))
                .andReturn();

        log.info(result.getResponse().getContentAsString());

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