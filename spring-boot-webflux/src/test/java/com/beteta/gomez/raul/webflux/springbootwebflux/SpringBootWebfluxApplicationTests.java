package com.beteta.gomez.raul.webflux.springbootwebflux;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Categoria;
import com.beteta.gomez.raul.webflux.springbootwebflux.models.documents.Producto;
import com.beteta.gomez.raul.webflux.springbootwebflux.services.CategoriaService;
import com.beteta.gomez.raul.webflux.springbootwebflux.services.ProductoService;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringBootWebfluxApplicationTests {

	@Autowired
	private WebTestClient client;

	@Autowired
	private ProductoService productoService;

	@Autowired
	private CategoriaService categoriaService;

	@Value("${config.url.base}")
	private String url;

	@Test
	void listarTest() {
		client.get()
			.uri(url)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Producto.class)
			.consumeWith(response -> {
				List<Producto> productos = response.getResponseBody();

				productos.forEach(p -> {
					System.out.println(p.getNombre());
				});

				Assertions.assertThat(productos.size() > 0).isTrue();
			});
			//.hasSize(8);
	}

	@Test 
	void verDetalleTest() {
		Producto producto = productoService.findByNombre("HEWLETT PACKARD MULTIFUNCTIONAL").block();
		
		client.get().uri(url + "/{id}", Collections.singletonMap("id", producto.getId()))
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody()
			.jsonPath("id").isNotEmpty()
			.jsonPath("nombre").isEqualTo("HEWLETT PACKARD MULTIFUNCTIONAL");
	}

	@Test
	void crearTest(){

		Categoria categoria = categoriaService.findByNombre("electronico").block();
		Producto prod = new Producto("Nombre nuevo del producto", 999.99, categoria);

		client.post().uri(url)
			.contentType(MediaType.APPLICATION_JSON)
			.body(Mono.just(prod), Producto.class)
			.exchange()
			.expectStatus().isCreated()
			.expectBody(Producto.class)
			.consumeWith(response -> {
				Producto producto = response.getResponseBody();

				Assertions.assertThat(producto.getNombre()).isEqualTo(prod.getNombre());
				Assertions.assertThat(producto.getPrecio()).isEqualTo(prod.getPrecio());
				Assertions.assertThat(producto.getCategoria().getId()).isEqualTo(prod.getCategoria().getId());
				Assertions.assertThat(producto.getCategoria().getNombre()).isEqualTo(prod.getCategoria().getNombre());
			});
			//.jsonPath("$.id").isNotEmpty()
			//.jsonPath("$.nombre").isEqualTo("Nombre nuevo del producto");
	}

	@Test
	void editarTest(){

		Producto prod = this.productoService.findByNombre("HP NOTEBOOK OMEN 17").block();
		Categoria cat = categoriaService.findByNombre("electronico").block();

		Producto productoEditado = new Producto("Asus Notebook", 700.00, cat);

		client.put().uri(url +"/{id}", Collections.singletonMap("id", prod.getId()))
			.contentType(MediaType.APPLICATION_JSON)
			.body(Mono.just(productoEditado), Producto.class)
			.exchange()
			.expectStatus().isCreated()
			.expectBody(Producto.class)
			.consumeWith(response -> {
				Producto producto = response.getResponseBody();

				Assertions.assertThat(producto.getId()).isEqualTo(prod.getId());
				Assertions.assertThat(producto.getNombre()).isEqualTo(productoEditado.getNombre());
				Assertions.assertThat(producto.getPrecio()).isEqualTo(productoEditado.getPrecio());
				Assertions.assertThat(producto.getCategoria().getId()).isEqualTo(productoEditado.getCategoria().getId());
				Assertions.assertThat(producto.getCategoria().getNombre()).isEqualTo(productoEditado.getCategoria().getNombre());
			});
	}

	@Test
	void deleteTest(){
		Producto prod = productoService.findByNombre("MICA CÓMODA 5 CAJONES").block();

		client.delete().uri(url + "/{id}", Collections.singletonMap("id", prod.getId()))
			.exchange()
			.expectStatus().isNoContent();

		client.get().uri(url + "/{id}", Collections.singletonMap("id", prod.getId()))
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isNotFound()
		.expectBody()
		.isEmpty();
	}

}
