package com.beteta.gomez.raul.webflux.springbootwebflux.models.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Producto;

import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String>{

    public Mono<Producto> findByNombreIgnoreCase(String nombre);

}
