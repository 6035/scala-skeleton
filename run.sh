#!/usr/bin/env bash

declare -r ROOT=$(git rev-parse --show-toplevel)
declare -r JARS=$(find "$ROOT" -name '*jar' | xargs | sed 's/ /:/g')

scala -classpath "$JARS" compile.Compiler "$@"
