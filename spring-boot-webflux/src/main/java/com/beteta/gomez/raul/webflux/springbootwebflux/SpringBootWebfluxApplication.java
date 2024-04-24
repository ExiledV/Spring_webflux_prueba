package com.beteta.gomez.raul.webflux.springbootwebflux;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Categoria;
import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Producto;
import com.beteta.gomez.raul.webflux.springbootwebflux.models.repository.CategoriaRepository;
import com.beteta.gomez.raul.webflux.springbootwebflux.models.repository.ProductoRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner{

	@Autowired
	ProductoRepository productoRepository;

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		mongoTemplate.dropCollection("productos").subscribe();
		mongoTemplate.dropCollection("categorias").subscribe();

		Categoria electronico = new Categoria("electronico");
		Categoria deporte = new Categoria("deporte");
		Categoria computacion = new Categoria("computacion");
		Categoria muebles = new Categoria("muebles");

		Flux.just(electronico, deporte, computacion, muebles)
			.flatMap(this.categoriaRepository::save)
			.doOnNext(c -> System.out.println(c.getId()))
			.thenMany(
				Flux.just(
					new Producto("Tv Parasonic Pantalla LCD", 456.89, electronico),
					new Producto("Sony Camara HD Digital", 177.89, electronico),
					new Producto("Apple ipod", 46.89, electronico),
					new Producto("Hewlett Packard Multifunctional", 200.89, computacion),
					new Producto("Bianchi Bicicleta", 70.89, deporte),
					new Producto("HP Notebook Omen 17", 2500.89, computacion),
					new Producto("Mica Cómoda 5 cajones", 150.89, muebles),
					new Producto("TV Sony Bravia OLED 4K Ultra HD", 2255.89, electronico)
				)
				.flatMap(producto -> {
						producto.setCreateAt(new Date());
						
						return productoRepository.save(producto);
					})
			)
			.subscribe(producto -> System.out.println(producto.getId()));
			
	}

}
