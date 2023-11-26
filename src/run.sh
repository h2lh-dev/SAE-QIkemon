#!/bin/bash
#cd classes
#export CLASSPATH=`find ../lib -name "*.jar" | tr '\n' ':'`
#java -cp ${CLASSPATH}:. $@
#cd ..

java -cp ../lib/program.jar:. JeuxPokemon

