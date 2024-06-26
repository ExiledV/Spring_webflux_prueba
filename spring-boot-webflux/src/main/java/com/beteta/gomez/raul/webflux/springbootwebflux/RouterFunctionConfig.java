package com.beteta.gomez.raul.webflux.springbootwebflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.beteta.gomez.raul.webflux.springbootwebflux.handler.ProductoHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ProductoHandler handler){

        return route(GET("/api/v2/productos"), handler::listar)
                .andRoute(GET("/api/v2/productos/{id}"), handler::ver)
                .andRoute(GET("/api/v2/productos/findByNombre/{nombre}"), handler::findByNombre)
                .andRoute(POST("/api/v2/productos"), handler::crear)
                .andRoute(PUT("/api/v2/productos/{id}"), handler::editar)
                .andRoute(DELETE("/api/v2/productos/{id}"), handler::eliminar)
                .andRoute(PUT("/api/v2/productos/upload/{id}"), handler::upload)
                .andRoute(POST("/api/v2/productos/crearConFoto"), handler::crearConFoto);
    }
}
