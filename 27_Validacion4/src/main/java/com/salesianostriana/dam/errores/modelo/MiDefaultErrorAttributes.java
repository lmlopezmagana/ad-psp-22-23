package com.salesianostriana.dam.errores.modelo;


import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class MiDefaultErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        Map<String,Object> result =  Map.of(
                "estado",errorAttributes.get("status"),
                "codigo", HttpStatus.valueOf((int) errorAttributes.get("status")).name(),
                "fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                "ruta", errorAttributes.get("path")
        );

        if (errorAttributes.containsKey("message")) {
            result.put("mensaje", errorAttributes.get("message"));
        }

        if (errorAttributes.containsKey("errors")) {
            result.put("subErrores", errorAttributes.get("errors"));
        }

        return result;
    }
}
