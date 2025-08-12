#!/bin/sh

JAVA_HOME=
export JAVA_HOME

if [ -z "$JAVA_HOME" ]; then
echo "Please configure the JAVA_HOME!"
exit
fi

JLIBDIR=./lib
export JLIBDIR

CLASSPATH=./classes

for LL in `ls $JLIBDIR/*.jar`
do
CLASSPATH=$CLASSPATH:$LL
export CLASSPATH
done

COMPILER=${JAVA_HOME}/bin/javac
COMPILE_OPTION="-encoding UTF-8 -sourcepath .\src -d classes -target 1.4 -source 1.4 -classpath ${CLASSPATH}"
export COMPILER
export COMPILE_OPTION
${COMPILER} ${COMPILE_OPTION} src/sample/*.java