#!/bin/bash
rm -r Linter/target/classes/linter/*
javac -d Linter/target/classes/ `find Linter/src/main -name '*.java'`
java -cp Linter/target/classes/ linter.Linter $1