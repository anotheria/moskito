#!/bin/bash
export VERSION=2.4.3-SNAPSHOT

CLASSPATH=test/appdata:target/moskito-webui-$VERSION-jar-with-dependencies.jar
echo CLASSPATH: $CLASSPATH
java -Xmx256M -Xms64M -classpath $CLASSPATH -Dconfigureme.defaultEnvironment=test $*
