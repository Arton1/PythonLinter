#!/bin/bash
rm classes/*.class
javac -d ./classes source/*.java source/token/*.java source/token/type/*.java
java -cp classes Linter $1