#!/bin/sh

BASEDIR=$(dirname "$0")
. ${BASEDIR}/version.properties

app() {
    java -jar ${BASEDIR}/how-long-app/target/how-long-app-${VERSION}.jar $* 2> /dev/null
}

admin() {
    java -jar ${BASEDIR}/how-long-admin/target/how-long-admin-${VERSION}.jar $* 2> /dev/null
}

case "$1" in
    'app')
            shift 1
            app $@
            ;;
    'admin')
            shift 1
            admin $@
            ;;
    *)
            echo
            echo "Usage: $0 { app | admin } { args }"
            echo
            exit 1
            ;;
esac

exit 0