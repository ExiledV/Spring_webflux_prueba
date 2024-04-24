package com.beteta.gomez.raul.webflux.springbootwebflux.services;

import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Categoria;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoriaService {

    Flux<Categoria> findAll();

    Mono<Categoria> findById(String id);

    Mono<Categoria> findByNombre(String nombre);

    Mono<Categoria> save(Categoria prod);
    
    Mono<Void> delete(Categoria prod);
    
}
