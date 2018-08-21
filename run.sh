#!/bin/sh

BASEDIR=$(dirname "$0")

. ${BASEDIR}/version.properties
java -jar ${BASEDIR}/how-long-app/target/how-long-app-${VERSION}.jar $* 2> /dev/null