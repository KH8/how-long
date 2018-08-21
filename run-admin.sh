#!/bin/sh

BASEDIR=$(dirname "$0")

. ${BASEDIR}/version.properties
java -jar ${BASEDIR}/how-long-admin/target/how-long-admin-${VERSION}.jar $* 2> /dev/null