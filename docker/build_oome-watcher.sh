cp ../moskito-extensions/moskito-outofmemory-watcher/target/*with-dependencies.jar moskito-oome-watcher/moskito-oome-watcher.jar
docker build -t moskito-oome-watcher -t anotheria/moskito-oome-watcher moskito-oome-watcher

echo 'use docker run -d -v `pwd`:`pwd`:ro -p 9451:9451 -eDIRECTORY=`pwd` -ePATTERN=stderr -eIP=<ip_to_expose> anotheria/moskito-oome-watcher:latest '