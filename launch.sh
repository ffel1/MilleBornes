#!/bin/bash

cd "$(dirname "$0")"

rm MilleBornes.jar
rm -rf bin/com
javac -d bin -sourcepath src src/com/millebornes/*.java 
find . -name ".DS_Store" -delete 
jar cfm MilleBornes.jar MANIFEST.MF -C bin . -C resources .

java -jar MilleBornes.jar