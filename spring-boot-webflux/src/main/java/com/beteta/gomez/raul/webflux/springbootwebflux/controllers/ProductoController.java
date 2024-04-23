package com.beteta.gomez.raul.webflux.springbootwebflux.controllers;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;

import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Producto;
import com.beteta.gomez.raul.webflux.springbootwebflux.services.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Value("$(config.uploads.path)")
    private String path;

    @GetMapping()
    public Mono<ResponseEntity<Flux<Producto>>> findAll() {

        return Mono.just(ResponseEntity.ok().body(productoService.findAll()));

    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> getMethodName(@PathVariable String id) {

        return productoService.findById(id).map(p -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
        
    }

    @PostMapping
    public Mono<ResponseEntity<Producto>> create(@RequestBody Producto prod) {
        if(prod.getCreateAt() == null){
            prod.setCreateAt(new Date());
        }

        return this.productoService.save(prod).map(p -> ResponseEntity.created(URI.create("/api/productos/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(p))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Producto>> edit(@PathVariable String id, @RequestBody Producto prod) {

        return this.productoService.findById(id).flatMap(p -> {
            p.setNombre(prod.getNombre());
            p.setPrecio(prod.getPrecio());

            return this.productoService.save(p);
        }).map(p -> ResponseEntity.created(URI.create("/api/productos/" + p.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        return productoService.findById(id).flatMap(p -> {
            return this.productoService.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/upload/{id}")
    public Mono<ResponseEntity<Producto>> upload(@PathVariable String id ,@RequestPart FilePart file) {

        return this.productoService.findById(id).flatMap(p -> {
            p.setFoto(UUID.randomUUID().toString() + "-" + file.filename()
             .replace(" ", "")
             .replace(":", "")
             .replace("\\", ""));

            return file.transferTo(new File(path + p.getFoto())).then(this.productoService.save(p));
        }).map(p -> ResponseEntity.ok(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    
    
}
