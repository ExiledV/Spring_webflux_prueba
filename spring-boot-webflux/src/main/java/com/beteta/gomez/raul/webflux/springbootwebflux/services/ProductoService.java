package com.beteta.gomez.raul.webflux.springbootwebflux.services;

import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

    Flux<Producto> findAll();

    Mono<Producto> findById(String id);

    Mono<Producto> save(Producto prod);
    
    Mono<Void> delete(Producto prod);
}
