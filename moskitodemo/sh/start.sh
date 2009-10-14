#!/bin/bash
lib_path=.
for file in $(ls $lib_path); do
 lib=$lib:$lib_path/$file
done

CLASSPATH=$lib
echo CLASSPATH: $CLASSPATH
java -classpath $CLASSPATH $*
