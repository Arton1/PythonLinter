#!/bin/bash
rm -r Linter/target/classes/linter/*
javac -d Linter/target/classes/ ./Linter/src/main/java/linter/*.java ./Linter/src/main/java/linter/token/*.java ./Linter/src/main/java/linter/token/type/*.java
java -cp Linter/target/classes/ linter.Linter $1