#!/bin/bash
rm classes/*.class
javac -d ./classes source/*.java
java -cp classes Linter $1