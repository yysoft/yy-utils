#!/bin/sh  
#memcached auto-start   
#  
# description: Auto-starts memcached  
# processname: memcached  
# pidfile: /var/memcached.pid   

 
case $1 in  
p)  
    mvn clean package -Pproduction -Dmaven.test.skip
    ;;
pt)
	mvn clean package -Ptest -Dmaven.test.skip
	;;
ci)
	mvn clean install -Dmaven.test.skip -DdownloadSources=true
	;;
ee)
	mvn eclipse:eclipse -Dwtpversion=2.0 -DdownloadSources=true
	;;
ec)
	mvn eclipse:clean
	;;
td)
	mvnDebug tomcat7:run
	;;
t)
	mvn tomcat7:run
	;;
s)
	mvn dependency:sources
	;;
gb)
	git branch -av --color
	;;
*)  
    echo 'p:package -Pproduction; pt:package -Ptest; ci:clean install; ee: eclipse:eclipse'
    echo 'ec:eclipse:clean; td:debug tomcat7:run; t: tomcat7:run; s: dependency:sources'
    ;;  
esac  
  
exit 0
