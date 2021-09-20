# Reconocedor de ADN mutante
El objetivo de este programa es detectar si un humano es mutante o no en 
dependencia de una secuencia de ADN dada en forma de matriz (NxN), en la cual 
deber� chequear si existe mas de una combinaci�n de 4 letras consecutivas iguales 
tanto en filas, columnas o diagonales de la matriz representada, el resultado del
an�lisis se almacena en una base de datos, de la cual se puede consultar las 
estad�sticas del porcentaje de mutantes entre humanos analizados.

## API Rest con m�todo o funci�n `isMutant`
Se cre� un m�todo `isMutant` en el lenguaje de programaci�n JAVA, el cual 
recibe como par�metro un arreglo de cadenas y devuelve un valor l�gico en dependencia 
de si la secuencia del ADN brindada cumple o no con las condiciones para que sea
mutante.

La declaraci�n del m�todo es la siguiente:

    boolean isMutant(ArrayList<String> dna);
    
El m�todo realiza algunas validaciones sobre el par�metro recibido:
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
de una el m�todo devuelve verdadero, en caso contrario devolver� falso.

Para publicar dicho m�todo se cre� una API Rest que expone un servicio HTTP POST 
con la ruta `/mutant`, el cual recibe como cuerpo de la petici�n un objeto JSON
con la secuencia a analizar, ejemplos de objetos JSON permitidos son los siguientes:
* `{ "dna":["AGCTAA", "ATTACC", "CTCTGC", "TCAACG", "GCCCAG", "GTCCTT"] }`
* `{ "dna":["ATCCT", "ACTTC", "CATTG", "GTCCA", "TAACT"] }`

El servicio pasar� la secuencia al m�todo `isMutant` y devolver� un c�digo de 
respuesta acorde al valor devuelto por dicho m�todo:
* HTTP 200-OK si la respuesta es verdadero
* HTTP 403-Forbidden si la respuesta es falso

## API Rest con m�todo o funci�n `stats`
A medida que se van realizando invocaciones al servicio `/mutant`, este va 
persistiendo en una base de datos los resultados de los an�lisis, se excluyen de 
las estad�sticas, aquellas secuencias que no cumplan con las validaciones 
anteriormente mencionadas y las que se encuentren repetidas.

Para publicar dicho m�todo se cre� una API Rest que expone un servicio HTTP GET 
con la ruta `/stats`, el cual devuelve como cuerpo de la respuesta un objeto JSON
con las estadisticas de las secuencias analizadas, ejemplos del objeto JSON 
devuelto son los siguientes:
* `{"count_mutant_dna":1,"count_human_dna":2,"ratio":0.5}`
* `{"count_mutant_dna":40,"count_human_dna":100,"ratio":0.4}`

Donde:
* `count_mutant_dna`: Indica la cantidad de secuencias analizadas con 
caracter�sticas de mutante.
* `count_human_dna`: Indica la cantidad de secuencias analizadas con ausencia 
de caracter�sticas de mutante, o sea, no mutantes o humanos.
* `ratio`: Indica la proporci�n de mutantes sobre los no mutantes o humanos.

## Estructura de la soluci�n
Para la realizaci�n del objetivo propuesto se crearon 3 proyectos utilizando el 
lenguaje de programaci�n JAVA, la herramienta de Spring Boot y el entorno de 
desarrollo integrado (IDE por sus siglas en ingl�s) Eclipse.

Los proyectos creados fueron son los siguientes:
1. **dna-checker**: Contiene los servicios de chequeo y consulta de estad�sticas 
de secuencias de ADN.
2. **naming-server**: Contiene el servidor de registro de nombres para realizar 
escalado y balanceo de carga entre las instancias de los servicios del proyecto "dna-checker".
3. **api-gateway**: Contiene el servidor de puerta de enlace a los servicios 
detr�s del balanceador de carga.

**NOTA**: Los proyectos 2 y 3 solo son requeridos para un despliegue local que 
permita el escalamiento de instancias para el manejo de grandes vol�menes de 
peticiones del servicio.

## Estructura del proyecto `dna-checker`
Para realizar el proyecto se utiliz� la herramienta de Spring Boot, la cual permite 
hacer uso de diferentes marcos de trabajo como Spring, Spring Data, Spring Cloud, 
etc. Permitiendo sacar el m�ximo provecho a la arquitectura y al uso de patrones como MVC, 
Repositorio e Inyecci�n de dependecias.

El proyecto se organiz� en los siguientes paquetes:
* **com.exercise.mutant.webservices.dnachecker**: Contiene la clase con el m�todo de 
entrada a la aplicaci�n.
* **com.exercise.mutant.webservices.dnachecker.controller**: Contiene la clase controladora 
que expone los servicios a consumir por el cliente.
* **com.exercise.mutant.webservices.dnachecker.manager**: Contiene la clase con la l�gica 
de negocio para el chequeo de las secuencias de ADN.
* **com.exercise.mutant.webservices.dnachecker.model**: Contiene las clases que representan 
objetos o beans que forman parte de la comunicaci�n entre capas.
* **com.exercise.mutant.webservices.dnachecker.repository**: Contiene la clase 
repositorio que permite el enlace con la base de datos.

## Pruebas unitarias
Para comprobar el funcionamiento correcto de los servicios y los diferentes 
escenarios de validaci�n de datos, se le realizaron pruebas unitarias a los 
servicios expuestos en el controlador, con un total de 11 casos de prueba.

Con las cuales garantizamos una covertura del 97.1 % del c�digo escrito.

## Sistema de control de versiones y flujos de trabajo
Se utilizo GIT como sistema de control de versiones y como estrategia de flujo de 
trabajo se aplic� Gitflow.

Para ello se tiene como rama principal de desarrollo la `develop` y sobre esta 
se crean las ramas para las funcionalidades, las cuales son mezcladas de vuelta al 
completar la funcionalidad, luego de esta rama `develop` sacamos la rama que 
ser� liberada a producci�n, la cual posteriormente se mezclar� tanto a la rama 
`main` como a `develop`, quedando siempre 2 ramas, la de producci�n y la de 
desarrollo.


## Despliegue de la soluci�n
Para el despliegue de la soluci�n se pueden utilizar varios enfoques, que pueden 
ir desde un despliegue local de una sola instancia, pasando por un despliegue local
de varias instancias con balanceador de carga y puerta de enlace al servicio hasta
la publicaci�n en la nube con escalamiento autom�tico. 

Para cualquiera de los enfoques mencionados anteriormente necesitamos los siguientes 
programas instalados para generar los artefactos a desplegar:

1. Java JDK versi�n 1.8 recomendada
2. Eclipse IDE
3. Descargar el c�digo fuente desde el repositorio en 
[GitHub](https://github.com/rpereza/mutant-dna) rama `main`
4. Crear un workspace e importar los proyectos contenidos en el c�digo fuente
5. Postman 

### Para una publicaci�n en un ambiente local de una sola instancia

Solo debemos ejecutar el projecto 'dna-checker' como una 'Java Application', 
para ello, ubicamos el paquete 'com.exercise.mutant.webservices.dnachecker' 
dentro de la carpeta 'src/main/java', hacemos clic derecho con el rat�n y 
seleccionamos la opci�n "Run As -> Java Application".

Una vez que termine de publicar, se podr�n acceder a los servicios desde las 
siguientes rutas:
* M�todo POST a http://localhost:8080/mutant
* M�todo GET a http://localhost:8080/stats

**NOTA**: Se recomienda el uso de Postman o aplicativo similar para parametrizar
los tipo de petici�n as� como los par�metros de entrada para el caso del servicio 
POST

### Pre-requisitos para una publicaci�n en un ambiente de m�ltiples instancias

Para los escenarios de m�ltiples instancias hay que hacer modificaciones a algunas
configuraciones del projecto `dna-checker` por ejemplo se deber�a cambiar la 
persistencia a una base de datos externa al proyecto, por ajemplo a MySQL, la 
parametrizaci�n por defecto se realiz� a una base de datos en memoria. 

Para activar la persistencia a MySQL se deben modificar 2 archivos:

1. Cambiar la etiqueta de la dependencia a la base de datos en memoria por la de 
MySQL en el archivo "pom.xml" en la ra�z del proyecto `dna-checker`

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
de conexi�n a MySQL en el archivo "application.properties" dentro de la carpeta 
"src/main/resources" del proyecto `dna-checker`

Eliminar o comentar

	spring.datasource.url=jdbc:h2:mem:testdb
	
Adicionar

	spring.jpa.hibernate.ddl-auto=update
	spring.datasource.url=jdbc:mysql://${DB_HOST}:3306/${DB_NAME}
	spring.datasource.username=${DB_USER}
	spring.datasource.password=${DB_PASS}
	spring.datasource.driver-class-name =com.mysql.cj.jdbc.Driver
 
 Se recomienda utilizar variables de entorno para flexibilizar la parametrizaci�n
 en caso de utilizar despliegues en docker o kubernetes.
 
### Despliegue manual con servidor de registro de nombres y api-gateway
 
 Para realizar el despliegue manual solo hay que ejecutar las aplicaciones en el 
 siguiente orden:
 1. naming-server
 2. dna-checker
 3. api-gateway

Una vez que termine de publicar la �ltima, se podr�n acceder a los servicios desde las 
siguientes rutas (quitamos el puerto 8080 para que se acceda a traves del api-gateway):
* M�todo POST a http://localhost/mutant
* M�todo GET a http://localhost/stats

**NOTA**: Se recomienda el uso de Postman o aplicativo similar para parametrizar
los tipo de petici�n as� como los par�metros de entrada para el caso del servicio 
POST

### Despliegue usando docker-compose con servidor de registro de nombres y api-gateway

Para realizar el despliegue con im�genes de docker se debe instalar primeramente
el cliente de Docker seg�n su sistema operativo y validar que el servicio est� 
instalado y corriendo con los siguientes comandos:

	docker --version

Luego que est� corriendo el servicio de docker en el computador, se deben crear las 
im�genes de los diferentes servicios, para ello, en vez de ejecutarlas como 
"Java Application", debemos ejecutarlas como "Maven build..." y colocar en el campo 
"Goals" el siguiente comando "spring-boot:build-image -DskipTests" y hacer clic en 
el bot�n de Ejecutar o Run para que se creen las im�genes.

Se puede comprobar que las im�genes esten creadas ejecutando el comando de docker
desde la consola:

	docker images

Hasta este paso ya tendr�amos las im�genes listas y para facilitar un despliegue 
automatizado, en la carpeta ra�z del c�digo fuente se elabor� un archivo 
"docker-compose.yaml" que tiene parametrizado el despliegue utilizando docker-compose, 
se debe editar para adicionar las posibles variables de entorno utilizadas y la 
cantidad de instancias del servicio `dna-checker`.

Desde la consola nos ubicamos en la carpeta ra�z del c�digo fuente y ejecutamos el 
siguiente comando:

	docker-compose up
	
O si se prefiere publicar las im�genes individualmente, se recomienda el siguiente 
orden de publicaci�n de las im�genes:

1. `mutant_excercise/naming-server:1.0.0-RELEASE` por el puerto 8761
2. `mutant_excercise/dna-checker:1.0.0-RELEASE` por el puerto 8080
3. `mutant_excercise/api-gateway:1.0.0-RELEASE` por el puerto 80

Para publicar una �nica imagen a la vez se debe hacer uso del siguiente comando:

	docker run -p: 8761:8761 -d mutant_excercise/naming-server:1.0.0-RELEASE
	
Una vez que termine de publicar la �ltima, se podr�n acceder a los servicios desde las 
siguientes rutas (quitamos el puerto 8080 para que se acceda a traves del api-gateway):
* M�todo POST a http://localhost/mutant
* M�todo GET a http://localhost/stats

**NOTA**: Se recomienda el uso de Postman o aplicativo similar para parametrizar
los tipo de petici�n as� como los par�metros de entrada para el caso del servicio 
POST

### Despliegue usando kubernetes en Google Cloud Platform

Para publicar en Google Cloud Platform, se requiere un poco mas de configuraci�n:

1. Aprovisionar una base de datos en la nube
2. Crear una nueva imagen de nuestro servicio con la parametrizaci�n de la base de datos (opcional)
3. Tener un repositorio en DockerHub donde subir la imagen de nuestro servicio `dna-checker`
4. Aprovisionar un kubernetes engine y crear un cl�ster
5. Desplegar la imagen en el cl�ster y parametrizarla

Para este ejercicio se realiz� una publicaci�n con una base de datos en MySQL y 
un cl�ster de kubernetes en modo Autopilot, el cual deber�a ser capaz de manejar 
el escalado autom�ticamente en dependencia de la carga de los nodos.

Se podr�n acceder a los servicios desde las siguientes rutas:
* M�todo POST a http://34.133.108.4:8080/mutant
* M�todo GET a http://34.133.108.4:8080/stats

**NOTA**: Se recomienda el uso de Postman o aplicativo similar para parametrizar
los tipo de petici�n as� como los par�metros de entrada para el caso del servicio 
POST
