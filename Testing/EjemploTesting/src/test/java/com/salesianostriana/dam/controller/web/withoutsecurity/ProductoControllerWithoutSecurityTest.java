package com.salesianostriana.dam.controller.web.withoutsecurity;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesianostriana.dam.controller.ProductoController;
import com.salesianostriana.dam.controller.web.configuration.TestDisableSecurityConfig;
import com.salesianostriana.dam.dto.converter.ProductoDTOConverter;
import com.salesianostriana.dam.modelo.Categoria;
import com.salesianostriana.dam.modelo.Producto;
import com.salesianostriana.dam.service.ProductoServicio;
import com.salesianostriana.dam.util.pagination.PaginationLinksUtils;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.salesianostriana.dam.controller.utils.ResponseBodyMatchers.responseBody;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TestDisableSecurityConfig.class)
@AutoConfigureMockMvc
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


    @Test
    @DisplayName("GET /producto/ sin parámetros de filtrado")
    @Disabled
    void whenValidRequest_thenReturns200() throws Exception {

        Producto p = Producto.builder()
                .id(1L)
                .categoria(new Categoria(1L, "Categoría"))
                .nombre("Nombre del producto")
                .precio(12.34f)
                .imagen("http://")
                .build();

        Page result = new PageImpl<>(List.of(p));

        when(productoServicio.findByArgs(any(Optional.class),any(Optional.class), any(Pageable.class)))
                .thenReturn(result);

        mockMvc.perform(get("/producto/"))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(result, PageImpl.class));



    }


}
