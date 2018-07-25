#!/usr/bin/env bash

BASEDIR=$(dirname "$0")
java -jar $BASEDIR/how-long-app/target/how-long-app-0.0.1-SNAPSHOT.jar $1 $2 2> /dev/null