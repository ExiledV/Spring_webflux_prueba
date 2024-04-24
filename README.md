# Spring_webflux_prueba
## Introducción
Esta es una API REST creada con la finalidad de poner en práctica los conocimientos adquiridos con la programación reactiva con Spring WebFlux, para el aprovechamiento al máximo de la eficiencia del framework, haciendo uso del paradigma de programación funcional, y realizando pruebas básicas de integración.

Se ha seguido el curso de Udemy "Programación Reactiva con Spring Boot y Spring Webflux" de Andrés Guzmán para la realización de esta API básica.

La idea es aplicar estos conocimientos en proyectos con arquitectura microservicios que desarrolle en un futuro, habiendo priorizado la compresión de la programación funcional y reactiva.

Se han desarrollado tanto controladores REST parecidos a los de Spring MVC como al enrutador RouterFunction que aprovecha al máximo la reactividad del microservicio.

## Tecnologías usadas

- **Tecnologías:** Spring WebFlux
- **Persistencia:** Spring Data Reactive Mongo
- **Base de datos:** MongoDB
- **Testing:** JUnit

## Uso
1. Levantar el microservicio:

- Acceda a la carpeta servicio-productos.
- Ejecute el siguiente comando en el terminal **`./mvnw spring-boot:run`**

2. Probar el CRUD:
- Importe las requests disponibles en la carpeta raíz del repositorio en Postman y pruebelas.

## Conclusiones personales
En mi opinión, Spring Webflux puede enriquecer significativamente mis habilidades en Java para desarrollar aplicaciones con alta concurrencia de usuarios. Sin embargo, es crucial evaluar si realmente es necesario para el servidor específico antes de adoptar esta tecnología. Aunque es eficiente, su uso puede complicar la legibilidad del código y dificultar la adherencia a los principios de Clean Code. 
Observo incompatibilidades entre patrones y considero que quienes no tengan experiencia previa con librerías reactivas o programación funcional pueden enfrentar dificultades para familiarizarse con este entorno. Una vez superada la curva de aprendizaje, su manejo se vuelve más sencillo, pero es importante reconocer que, en la práctica, la programación funcional no siempre garantiza una mejor comprensión del código, como se podría esperar con WebFlux.