#!/bin/sh

JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.181-7.b13.el7.x86_64/jre
export JAVA_HOME
cd ..
if [ -z "$JAVA_HOME" ]; then
echo "Please configure the JAVA_HOME!"
exit
fi

export PATH

CLASSPATH=.:${JAVA_HOME}/jre/lib/rt.jar:./classes
export CLASSPATH

JLIBDIR=./lib
export JLIBDIR

for LL in `ls $JLIBDIR/*.jar`
do
CLASSPATH=$CLASSPATH:$LL
export CLASSPATH
done

JAVA_OPTION="-Dfile.encoding=GBK -Xmx1024M -Xms1024M"
RUN_CLASS=com.amarsoft.app.pbc.ecr.AmarECR
ARE_CONFIG_FILE=etc/ecr_are.xml
TASK_FILES=$1

export JAVA_OPTION
export RUN_CLASS
export ARE_CONFIG_FILE
export TASK_CONFIG_FILE

${JAVA_HOME}/bin/java ${JAVA_OPTION} -classpath ${CLASSPATH} ${RUN_CLASS} are=${ARE_CONFIG_FILE} task=${TASK_FILES}
cd ecr_sh