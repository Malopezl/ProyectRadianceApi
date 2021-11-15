# ProyectRadianceApi
Proyect Radiance es un proyecto creado para los cursos de Gestión de proyectos de ingenieria en informatica y sistemas 2 y programación web. 

## Que contiene?

Este repositorio contiene el codigo para una REST API que da servicio a una aplicación web de una revista digital. Para información acerca de los endpoints se puede revisar swagger.

## Instalación

Para la instalación lo primero sera clonar el repositorio y pasar a la rama sobre la que se trabajara. Dentro de la rama para correr desde consola
unicamente hace falta utilizar el siguiente comando:

```shell
$ cd /path/to/proyect/radiance/
$ . dev-deploy.sh <branch> <version>
```

Con esto se ejecutara el script que realiza el pull automatico de la rama, realiza la compilación del .jar y ejecuta la aplicación con los parametros necesarios para correr la API.

## Deploy
Para ingresar a la screen se usa 
```shell
$ screen -r radiance
```
El deploy es muy parecido con la instalación con la diferencia del nombre del script y los parametros, ya que este solo necesita el numero de versión. Si se esta deployando una version dev se utilizaria

```shell
$ cd /path/to/proyect/radiance/
$ DeployDevRadiance <branch> <version>
```

En caso ser la version de master

```shell
$ cd /path/to/proyect/radiance/
$ DeployRadiance <version>
```

## Swagger
Para visualizar el swagger podemos utilizar
localización | link
---|---
Localhost | [localhost swagger](http://localhost:8080/swagger)
AWS | [aws swagger](http://ec2-18-221-222-10.us-east-2.compute.amazonaws.com:8080/swagger)

### Aviso

Este repositorio fue creado para un proyecto universitario jejeje salu2 :metal: 
