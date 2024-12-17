#!/bin/sh

set -e  # exit on error

JASM_VERSION="2.6.0"
JASM_FILE="jasm-cli-${JASM_VERSION}-all.jar"

if [ ! -f $JASM_FILE ]; then
    JASM_URL="https://github.com/jumanji144/Jasm/releases/download/${JASM_VERSION}/${JASM_FILE}"

    echo $JASM_URL
    curl -LO $JASM_URL
fi

java -jar $JASM_FILE compile -o Class.class Class.jasm
