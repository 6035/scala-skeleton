#!/usr/bin/env bash

declare -r ROOT=$(git rev-parse --show-toplevel)
declare -r JARS=$(find "$ROOT" -name '*jar' | xargs | sed 's/ /:/g')

export SCALA_HOME='/usr/share/scala-2.11'

scala -classpath "$JARS" compile.Compiler "$@"
