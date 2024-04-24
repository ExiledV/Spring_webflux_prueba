package com.beteta.gomez.raul.webflux.springbootwebflux.models.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Categoria;

import reactor.core.publisher.Mono;

public interface CategoriaRepository extends ReactiveMongoRepository<Categoria, String> {

    Mono<Categoria> findByNombreIgnoreCase(String nombre);
    
}
