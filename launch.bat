@echo off
cd /d "%~dp0"

del /F /Q MilleBornes.jar
rd /S /Q bin\com

javac -d bin -sourcepath src src\com\millebornes\*.java
del /S /F /Q .DS_Store

jar cfm MilleBornes.jar MANIFEST.MF -C bin . -C resources .

java -jar MilleBornes.jar
