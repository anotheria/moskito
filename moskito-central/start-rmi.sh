#!/bin/bash
lib_path=lib
dist_path=dist
artefact=moskito-central.jar
for file in $(ls $lib_path); do
 lib=$lib:$lib_path/$file
 rmicodebase="$rmicodebase file:$PWD/$lib_path/$file"
done

lib=$lib:dist/$artefact:java
#rmicodebase="file:$PWD/dist/$ $rmicodebase"
rmicodebase="$rmicodebase file:$PWD/dist/$artefact"
CLASSPATH=classes:etc:$lib
echo CLASSPATH: $CLASSPATH
echo rmicodebase=$rmicodebase

java -Xmx64M -Xms64M -classpath $CLASSPATH -Djava.rmi.server.codebase="$rmicodebase" -Dconfigureme.defaultEnvironment=dev $*
#java -Xmx64M -Xms64M -classpath $CLASSPATH -Dconfigureme.defaultEnvironment=dev $*
