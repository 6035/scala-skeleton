#!/usr/bin/env bash

add -r 6.035 scala java
export SCALA_HOME='/usr/share/scala-2.11'

ant "$@"
