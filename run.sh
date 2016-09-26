#!/bin/bash
eval $(attach -Padd -b -f scala)
gitroot=$(git rev-parse --show-toplevel)
if [ -n "$JAVA_TOOL_OPTIONS" ]; then
  export JAVA_OPTS="$JAVA_TOOL_OPTIONS"
  unset JAVA_TOOL_OPTIONS
fi
scala -classpath $gitroot/build/lib/project.jar:$gitroot/lib/antlr.jar compile.Compiler "$@"
