#!/usr/bin/env bash

set -e

here="$(cd "$(dirname "$0")" && pwd)"
javaExe=""

# Check whether java_vm exists, if not fall back to runtime.
if [[ -f "$here/java_vm/bin/java" ]]; then
    javaExe="$here/java_vm/bin/java"
elif [[ -f "$here/../runtime/bin/java" ]]; then
    javaExe="$here/../runtime/bin/java"
else
    javaExe="java"
fi

"$javaExe" -classpath "$here/datdec.jar:$here/code/projectx-pcode.jar" com.lucasluqui.datdec.DatdecApp