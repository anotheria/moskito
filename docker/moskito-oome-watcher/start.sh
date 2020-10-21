echo Directory is set to $DIRECTORY
echo Pattern is set to $PATTERN
echo binding IP: $IP
export JAVA_OPTIONS="-Djava.rmi.server.hostname=$IP -DserviceBindingPort=9451"

java $JAVA_OPTIONS -jar moskito-oome-watcher.jar -directory=$DIRECTORY -pattern=$PATTERN
