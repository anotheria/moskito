#!/bin/bash
export VERSION=2.5.0-SNAPSHOT

CLASSPATH=test/appdata:target/moskito-webui-$VERSION-jar-with-dependencies.jar
echo CLASSPATH: $CLASSPATH
java -Xmx256M -Xms64M -classpath $CLASSPATH -DlocalRmiRegistryPort=9401 -DskipCentralRegistry=true -Dconfigureme.defaultEnvironment=test $*
