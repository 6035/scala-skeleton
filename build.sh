#!/bin/bash
eval $(attach -Padd -b -f scala)
export SCALA_HOME=/mit/scala/scala/scala-2.11.2/
ant "$@"
