#!/bin/bash
#export SOURCES=src
#export CLASSES=classes
#export CLASSPATH=`find lib -name "*.jar" | tr '\n' ':'`

#javac -cp ${CLASSPATH} -sourcepath ${SOURCES} -d ${CLASSES} $@ `find src -name "*.java"`

#javac -cp lib/program.jar:. src/JeuxPokemon.java
#javac -cp lib/program.jar:. src/Attack.java
javac -cp ../lib/program.jar:. map.java
#javac -cp lib/program.jar:. src/JeuxPokemon.java
#javac -cp lib/program.jar:. src/map.java
#javac -cp lib/program.jar:. src/Couleur.java