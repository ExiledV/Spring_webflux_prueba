package com.beteta.gomez.raul.webflux.springbootwebflux.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Categoria;
import com.beteta.gomez.raul.webflux.springbootwebflux.services.CategoriaService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping()
    public Mono<ResponseEntity<Flux<Categoria>>> findAll() {

        return Mono.just(ResponseEntity.ok().body(categoriaService.findAll()));

    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Categoria>> getMethodName(@PathVariable String id) {

        return categoriaService.findById(id).map(c -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> create(@Valid @RequestBody Mono<Categoria> monoCategoria) {
        Map<String, Object> response = new HashMap<String, Object>();

        return monoCategoria.flatMap(categoria -> {
            response.put("categoria", categoria);
            return this.categoriaService.save(categoria)
                    .map(c -> ResponseEntity.created(URI.create("/api/categorias/".concat(c.getId())))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(response))
                    .defaultIfEmpty(ResponseEntity.badRequest().build());
        }).onErrorResume(t -> {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        response.put("errors", list);

                        return Mono.just(ResponseEntity.badRequest().body(response));
                    });
        });

    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Categoria>> edit(@PathVariable String id, @RequestBody Categoria categoria) {

        return this.categoriaService.findById(id).flatMap(c -> {
            c.setNombre(categoria.getNombre());
            return this.categoriaService.save(c);
        }).map(c -> ResponseEntity.created(URI.create("/api/categorias/" + c.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return categoriaService.findById(id).flatMap(p -> {
            return this.categoriaService.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
