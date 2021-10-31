#!/bin/bash

function ayuda(){
    echo "Este script se utiliza para realizar el pull de la rama del repositorio, compilar el jar con dependencias y lanzar la aplicacion"
    echo
    echo "Usage:. deploy.sh [rama] [version]"
    echo
    echo "rama indica la rama sobre la que se esta trabajando, por ejemplo dev o master"
    echo "version es el numero de version de la aplicacion por ejemplo 0.9.1 o 1.2.3"
    echo
}

function pullfunction {
    git pull origin $1
}

function compilejar {
    mvn -Pjar-with-dependencies clean install
}

function execjar {
    java -jar target/radiance-$1.jar server config/config.yml
}

if [ $# -lt 1 ]
    then
        echo "error: no se ha introducido ningun argumento"
        echo
        ayuda
elif [ "$1" == "--help" ] || [ "$1" == "-h" ]
    then
        ayuda
else
    pullfunction $1
    compilejar
    execjar $2
fi