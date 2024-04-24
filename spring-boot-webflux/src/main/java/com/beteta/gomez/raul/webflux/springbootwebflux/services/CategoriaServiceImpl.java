package com.beteta.gomez.raul.webflux.springbootwebflux.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Categoria;
import com.beteta.gomez.raul.webflux.springbootwebflux.models.repository.CategoriaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoriaServiceImpl implements CategoriaService{

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public Flux<Categoria> findAll() {
        return this.categoriaRepository.findAll();
    }

    @Override
    public Mono<Categoria> findById(String id) {
        return this.categoriaRepository.findById(id);
    }

    @Override
    public Mono<Categoria> save(Categoria categoria) {
        return this.categoriaRepository.save(categoria);
    }

    @Override
    public Mono<Void> delete(Categoria categoria) {
        return this.categoriaRepository.delete(categoria);
    }

    @Override
    public Mono<Categoria> findByNombre(String nombre) {
        return this.categoriaRepository.findByNombreIgnoreCase(nombre);
    }

    
    
}
