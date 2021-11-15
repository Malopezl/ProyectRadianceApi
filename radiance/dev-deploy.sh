#!/bin/bash

function hello(){
    echo "                   -                     Proyecto Radiance API"
    echo "                  .o+                    ------------------ "
    echo "                  ooo/                   Powered by Arch Linux "
    echo "                 +oooo:                  Marcos Sierra "
    echo "                +oooooo:                 Sharon Gomez "
    echo "               /-+oooooo+:               Marvin lopez "
    echo "              /:-:++oooo+:               ------------------- "
    echo "             /++++/+++++++:              powered by ArchTeam"
    echo "            /++++++++++++++:"
    echo "           /+++ooooooooooooo/"
    echo "         ./ooosssso++osssssso+           "
    echo "        .oossssso-^^^^-/ossssss+          "
    echo "       -osssssso.      :ssssssso.         "
    echo "      :osssssss/        osssso+++.        "
    echo "     /ossssssss/        +ssssooo/-       " 
    echo "    /ossssso+/:-         -:/+osssso+-    "  
    echo "   +sso+:-                   .-/+oso:  "   
    echo " ++:.                            -/+/ "
    echo ".                                     . "
}

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
    hello
    pullfunction $1
    compilejar
    execjar $2
fi