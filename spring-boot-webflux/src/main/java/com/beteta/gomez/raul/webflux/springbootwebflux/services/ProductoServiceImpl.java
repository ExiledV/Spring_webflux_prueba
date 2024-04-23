package com.beteta.gomez.raul.webflux.springbootwebflux.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Producto;
import com.beteta.gomez.raul.webflux.springbootwebflux.models.repository.ProductoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    ProductoRepository productoRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);

    @Override
    public Flux<Producto> findAll() {
        Flux<Producto> productos = productoRepository.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());

            return producto;
        }).doOnNext(producto -> log.info(producto.getNombre()));

        return productos;
    }

    @Override
    public Mono<Producto> findById(String id) {
        return this.productoRepository.findById(id).map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        }).doOnNext(producto -> log.info(producto.getNombre()));
    }

    @Override
    public Mono<Producto> save(Producto prod) {
        return this.productoRepository.save(prod);
    }

    @Override
    public Mono<Void> delete(Producto prod){
        return this.productoRepository.delete(prod);
    }

}
