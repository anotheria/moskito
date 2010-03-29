lib_sep=";"
lib_path=lib
artefact=moskito-central.jar
dist_path=dist
for file in $(ls $lib_path); do
 lib=$lib$lib_sep`cygpath -w $lib_path/$file`
 rmicodebase="$rmicodebase file:`cygpath -w $PWD/$lib_path/$file`"
done

lib=$lib"$lib_sep"`cygpath -w dist/$artefact`"$lib_sep"etc
#rmicodebase="file:$PWD/dist/moskitodemo.jar $rmicodebase"
rmicodebase="$rmicodebase file:`cygpath -w $PWD/dist/$artefact`"
CLASSPATH=classes"$lib_sep"etc"$lib_sep"$lib
echo CLASSPATH: $CLASSPATH
echo rmicodebase=$rmicodebase

java -Xmx64M -Xms64M -classpath $CLASSPATH -Djava.rmi.server.codebase="$rmicodebase" -Dconfigureme.defaultEnvironment=dev $*
#java -Xmx64M -Xms64M  -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=12000 -classpath $CLASSPATH -Djava.rmi.server.codebase="$rmicodebase" -Dconfigureme.defaultEnvironment=dev $*
#java -Xmx64M -Xms64M -classpath $CLASSPATH -Dconfigureme.defaultEnvironment=dev $*

#./start-rmi-cygwin.sh net.java.dev.moskito.central.demo.TestLocalClient
#./start-rmi-cygwin.sh net.java.dev.moskito.central.demo.generated.IDemoServer
#./start-rmi-cygwin.sh net.java.dev.moskito.central.demo.TestRemoteClient

#./start-rmi-cygwin.sh net.java.dev.moskito.central.generated.StatStorageServer