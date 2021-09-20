# Reconocedor de ADN mutante
El objetivo de este programa es detectar si un humano es mutante o no en 
dependencia de una secuencia de ADN dada en forma de matriz (NxN), en la cual 
deberá chequear si existe mas de una combinación de 4 letras consecutivas iguales 
tanto en filas, columnas o diagonales de la matriz representada, el resultado del
análisis se almacena en una base de datos, de la cual se puede consultar las 
estadísticas del porcentaje de mutantes entre humanos analizados.

## API Rest con método o función `isMutant`
Se creó un método `isMutant` en el lenguaje de programación JAVA, el cual 
recibe como parámetro un arreglo de cadenas y devuelve un valor lógico en dependencia 
de si la secuencia del ADN brindada cumple o no con las condiciones para que sea
mutante.

La declaración del método es la siguiente:

    boolean isMutant(ArrayList<String> dna);
    
El método realiza algunas validaciones sobre el parámetro recibido:
* Secuencia con mas de 1 elemento
* Secuencia solo con los caracteres permitidos 'A', 'T', 'C' y 'G'
* Secuencia que represente una matriz cuadrada

Si pasa las validaciones se comienzan a chequear los elementos en busca de las 
combinaciones que permitan identificar ADN mutante, para ello se realiza el 
chequeo en el siguiente orden:
1. Filas
2. Columnas
3. Diagonales de izquierda a derecha
4. Diagonales de derecha a izquierda

En el procedimiento del chequeo, se van almacenando la cantidad de combinaciones 
encontradas con 4 letras consecutivas iguales y en el momento que se detecte mas 
de una el método devuelve verdadero, en caso contrario devolverá falso.

Para publicar dicho método se creó una API Rest que expone un servicio HTTP POST 
con la ruta `/mutant`, el cual recibe como cuerpo de la petición un objeto JSON
con la secuencia a analizar, ejemplos de objetos JSON permitidos son los siguientes:
* `{ "dna":["AGCTAA", "ATTACC", "CTCTGC", "TCAACG", "GCCCAG", "GTCCTT"] }`
* `{ "dna":["ATCCT", "ACTTC", "CATTG", "GTCCA", "TAACT"] }`

El servicio pasará la secuencia al método `isMutant` y devolverá un código de 
respuesta acorde al valor devuelto por dicho método:
* HTTP 200-OK si la respuesta es verdadero
* HTTP 403-Forbidden si la respuesta es falso

## API Rest con método o función `stats`
A medida que se van realizando invocaciones al servicio `/mutant`, este va 
persistiendo en una base de datos los resultados de los análisis, se excluyen de 
las estadísticas, aquellas secuencias que no cumplan con las validaciones 
anteriormente mencionadas y las que se encuentren repetidas.

Para publicar dicho método se creó una API Rest que expone un servicio HTTP GET 
con la ruta `/stats`, el cual devuelve como cuerpo de la respuesta un objeto JSON
con las estadisticas de las secuencias analizadas, ejemplos del objeto JSON 
devuelto son los siguientes:
* `{"count_mutant_dna":1,"count_human_dna":2,"ratio":0.5}`
* `{"count_mutant_dna":40,"count_human_dna":100,"ratio":0.4}`

Donde:
* `count_mutant_dna`: Indica la cantidad de secuencias analizadas con 
características de mutante.
* `count_human_dna`: Indica la cantidad de secuencias analizadas con ausencia 
de características de mutante, o sea, no mutantes o humanos.
* `ratio`: Indica la proporción de mutantes sobre los no mutantes o humanos.

## Estructura de la solución
Para la realización del objetivo propuesto se crearon 3 proyectos utilizando el 
lenguaje de programación JAVA, la herramienta de Spring Boot y el entorno de 
desarrollo integrado (IDE por sus siglas en inglés) Eclipse.

Los proyectos creados fueron son los siguientes:
1. **dna-checker**: Contiene los servicios de chequeo y consulta de estadísticas 
de secuencias de ADN.
2. **naming-server**: Contiene el servidor de registro de nombres para realizar 
escalado y balanceo de carga entre las instancias de los servicios del proyecto "dna-checker".
3. **api-gateway**: Contiene el servidor de puerta de enlace a los servicios 
detrás del balanceador de carga.

**NOTA**: Los proyectos 2 y 3 solo son requeridos para un despliegue local que 
permita el escalamiento de instancias para el manejo de grandes volúmenes de 
peticiones del servicio.

## Estructura del proyecto `dna-checker`
Para realizar el proyecto se utilizó la herramienta de Spring Boot, la cual permite 
hacer uso de diferentes marcos de trabajo como Spring, Spring Data, Spring Cloud, 
etc. Permitiendo sacar el máximo provecho a la arquitectura y al uso de patrones como MVC, 
Repositorio e Inyección de dependecias.

El proyecto se organizó en los siguientes paquetes:
* **com.exercise.mutant.webservices.dnachecker**: Contiene la clase con el método de 
entrada a la aplicación.
* **com.exercise.mutant.webservices.dnachecker.controller**: Contiene la clase controladora 
que expone los servicios a consumir por el cliente.
* **com.exercise.mutant.webservices.dnachecker.manager**: Contiene la clase con la lógica 
de negocio para el chequeo de las secuencias de ADN.
* **com.exercise.mutant.webservices.dnachecker.model**: Contiene las clases que representan 
objetos o beans que forman parte de la comunicación entre capas.
* **com.exercise.mutant.webservices.dnachecker.repository**: Contiene la clase 
repositorio que permite el enlace con la base de datos.

## Pruebas unitarias
Para comprobar el funcionamiento correcto de los servicios y los diferentes 
escenarios de validación de datos, se le realizaron pruebas unitarias a los 
servicios expuestos en el controlador, con un total de 11 casos de prueba.

Con las cuales garantizamos una covertura del 97.1 % del código escrito.

## Sistema de control de versiones y flujos de trabajo
Se utilizo GIT como sistema de control de versiones y como estrategia de flujo de 
trabajo se aplicó Gitflow.

Para ello se tiene como rama principal de desarrollo la `develop` y sobre esta 
se crean las ramas para las funcionalidades, las cuales son mezcladas de vuelta al 
completar la funcionalidad, luego de esta rama `develop` sacamos la rama que 
será liberada a producción, la cual posteriormente se mezclará tanto a la rama 
`main` como a `develop`, quedando siempre 2 ramas, la de producción y la de 
desarrollo.


## Despliegue de la solución
Para el despliegue de la solución se pueden utilizar varios enfoques, que pueden 
ir desde un despliegue local de una sola instancia, pasando por un despliegue local
de varias instancias con balanceador de carga y puerta de enlace al servicio hasta
la publicación en la nube con escalamiento automático. 

Para cualquiera de los enfoques mencionados anteriormente necesitamos los siguientes 
programas instalados para generar los artefactos a desplegar:

1. Java JDK versión 1.8 recomendada
2. Eclipse IDE
3. Descargar el código fuente desde el repositorio en 
[GitHub](https://github.com/rpereza/mutant-dna) rama `main`
4. Crear un workspace e importar los proyectos contenidos en el código fuente
5. Postman 

### Para una publicación en un ambiente local de una sola instancia

Solo debemos ejecutar el projecto 'dna-checker' como una 'Java Application', 
para ello, ubicamos el paquete 'com.exercise.mutant.webservices.dnachecker' 
dentro de la carpeta 'src/main/java', hacemos clic derecho con el ratón y 
seleccionamos la opción "Run As -> Java Application".

Una vez que termine de publicar, se podrán acceder a los servicios desde las 
siguientes rutas:
* Método POST a http://localhost:8080/mutant
* Método GET a http://localhost:8080/stats

**NOTA**: Se recomienda el uso de Postman o aplicativo similar para parametrizar
los tipo de petición así como los parámetros de entrada para el caso del servicio 
POST

### Pre-requisitos para una publicación en un ambiente de múltiples instancias

Para los escenarios de múltiples instancias hay que hacer modificaciones a algunas
configuraciones del projecto `dna-checker` por ejemplo se debería cambiar la 
persistencia a una base de datos externa al proyecto, por ajemplo a MySQL, la 
parametrización por defecto se realizó a una base de datos en memoria. 

Para activar la persistencia a MySQL se deben modificar 2 archivos:

1. Cambiar la etiqueta de la dependencia a la base de datos en memoria por la de 
MySQL en el archivo "pom.xml" en la raíz del proyecto `dna-checker`

Se debe eliminar

	<dependency>
		<groupId>com.h2database</groupId>
		<artifactId>h2</artifactId>
		<scope>runtime</scope>
	</dependency>
 
 y en su lugar adicionar
 
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<scope>runtime</scope>
	</dependency>
	
2. Cambiar las propiedades relacionadas con la base de datos en memoria por la 
de conexión a MySQL en el archivo "application.properties" dentro de la carpeta 
"src/main/resources" del proyecto `dna-checker`

Eliminar o comentar

	spring.datasource.url=jdbc:h2:mem:testdb
	
Adicionar

	spring.jpa.hibernate.ddl-auto=update
	spring.datasource.url=jdbc:mysql://${DB_HOST}:3306/${DB_NAME}
	spring.datasource.username=${DB_USER}
	spring.datasource.password=${DB_PASS}
	spring.datasource.driver-class-name =com.mysql.cj.jdbc.Driver
 
 Se recomienda utilizar variables de entorno para flexibilizar la parametrización
 en caso de utilizar despliegues en docker o kubernetes.
 
### Despliegue manual con servidor de registro de nombres y api-gateway
 
 Para realizar el despliegue manual solo hay que ejecutar las aplicaciones en el 
 siguiente orden:
 1. naming-server
 2. dna-checker
 3. api-gateway

Una vez que termine de publicar la última, se podrán acceder a los servicios desde las 
siguientes rutas (quitamos el puerto 8080 para que se acceda a traves del api-gateway):
* Método POST a http://localhost/mutant
* Método GET a http://localhost/stats

**NOTA**: Se recomienda el uso de Postman o aplicativo similar para parametrizar
los tipo de petición así como los parámetros de entrada para el caso del servicio 
POST

### Despliegue usando docker-compose con servidor de registro de nombres y api-gateway

Para realizar el despliegue con imágenes de docker se debe instalar primeramente
el cliente de Docker según su sistema operativo y validar que el servicio esté 
instalado y corriendo con los siguientes comandos:

	docker --version

Luego que esté corriendo el servicio de docker en el computador, se deben crear las 
imágenes de los diferentes servicios, para ello, en vez de ejecutarlas como 
"Java Application", debemos ejecutarlas como "Maven build..." y colocar en el campo 
"Goals" el siguiente comando "spring-boot:build-image -DskipTests" y hacer clic en 
el botón de Ejecutar o Run para que se creen las imágenes.

Se puede comprobar que las imágenes esten creadas ejecutando el comando de docker
desde la consola:

	docker images

Hasta este paso ya tendríamos las imágenes listas y para facilitar un despliegue 
automatizado, en la carpeta raíz del código fuente se elaboró un archivo 
"docker-compose.yaml" que tiene parametrizado el despliegue utilizando docker-compose, 
se debe editar para adicionar las posibles variables de entorno utilizadas y la 
cantidad de instancias del servicio `dna-checker`.

Desde la consola nos ubicamos en la carpeta raíz del código fuente y ejecutamos el 
siguiente comando:

	docker-compose up
	
O si se prefiere publicar las imágenes individualmente, se recomienda el siguiente 
orden de publicación de las imágenes:

1. `mutant_excercise/naming-server:1.0.0-RELEASE` por el puerto 8761
2. `mutant_excercise/dna-checker:1.0.0-RELEASE` por el puerto 8080
3. `mutant_excercise/api-gateway:1.0.0-RELEASE` por el puerto 80

Para publicar una única imagen a la vez se debe hacer uso del siguiente comando:

	docker run -p: 8761:8761 -d mutant_excercise/naming-server:1.0.0-RELEASE
	
Una vez que termine de publicar la última, se podrán acceder a los servicios desde las 
siguientes rutas (quitamos el puerto 8080 para que se acceda a traves del api-gateway):
* Método POST a http://localhost/mutant
* Método GET a http://localhost/stats

**NOTA**: Se recomienda el uso de Postman o aplicativo similar para parametrizar
los tipo de petición así como los parámetros de entrada para el caso del servicio 
POST

### Despliegue usando kubernetes en Google Cloud Platform

Para publicar en Google Cloud Platform, se requiere un poco mas de configuración:

1. Aprovisionar una base de datos en la nube
2. Crear una nueva imagen de nuestro servicio con la parametrización de la base de datos (opcional)
3. Tener un repositorio en DockerHub donde subir la imagen de nuestro servicio `dna-checker`
4. Aprovisionar un kubernetes engine y crear un clúster
5. Desplegar la imagen en el clúster y parametrizarla

Para este ejercicio se realizó una publicación con una base de datos en MySQL y 
un clúster de kubernetes en modo Autopilot, el cual debería ser capaz de manejar 
el escalado automáticamente en dependencia de la carga de los nodos.

Se podrán acceder a los servicios desde las siguientes rutas:
* Método POST a http://34.133.108.4:8080/mutant
* Método GET a http://34.133.108.4:8080/stats

**NOTA**: Se recomienda el uso de Postman o aplicativo similar para parametrizar
los tipo de petición así como los parámetros de entrada para el caso del servicio 
POST
