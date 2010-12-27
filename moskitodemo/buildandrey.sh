#!/bin/bash
rm -rf ~/.ivy2/cache; rm -rf ../ivy-local-repository
cd ../ivy-shared-repository
svn update
#cd ../moskito-core/; ant -Doverride.ivy.checkout=true clean publish.local; cd - 
cd ../moskito-webui/; ant -Doverride.ivy.checkout=true clean publish.local; cd -
echo BUILDING in `pwd`
rm lib/moskito-*
ant -Doverride.ivy.checkout=true clean dist
cp dist/moskitodemo.war /opt/app/apache-tomcat-6.0.20/webapps/