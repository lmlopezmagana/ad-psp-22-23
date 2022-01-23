package com.salesianostriana.dam.controller.web.withoutsecurity;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesianostriana.dam.controller.FicherosController;
import com.salesianostriana.dam.controller.ProductoController;
import com.salesianostriana.dam.controller.web.configuration.RestResponsePage;
import com.salesianostriana.dam.controller.web.configuration.TestDisableSecurityConfig;
import com.salesianostriana.dam.dto.ProductoDTO;
import com.salesianostriana.dam.dto.converter.ProductoDTOConverter;
import com.salesianostriana.dam.modelo.Categoria;
import com.salesianostriana.dam.modelo.Producto;
import com.salesianostriana.dam.service.ProductoServicio;
import com.salesianostriana.dam.util.pagination.PaginationLinksUtils;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static com.salesianostriana.dam.controller.utils.ResponseBodyMatchers.responseBody;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = TestDisableSecurityConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Log
public class ProductoControllerWithoutSecurityTest {

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

    @MockBean
    private FicherosController ficherosController;


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
    @DisplayName("GET /producto/ sin parámetros de filtrado")
    void whenValidRequest_thenReturns200() throws Exception {

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


}
