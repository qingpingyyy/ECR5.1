echo off
cd ..
rem setup classpath
echo set _CP=%%_CP%%;%%1> cp.bat
set _CP=.;.\classes;.\bin
for %%i in (lib\*.jar) do call cp.bat %%i
for %%i in (reflib\*.jar) do call cp.bat %%i
set CLASSPATH=%_CP%
del cp.bat
echo %CLASSPATH%

set JAVA_RUN="C:\Program Files\Java\jdk1.8.0_181\bin\java" -Dfile.encoding=GBK -Xmx1024M -Xms1024M
set RUN_CLASS=com.amarsoft.app.pbc.ecr.AmarECR
set ARE_CONFIG_FILE=etc\ecr_are.xml
set TASK_FILES=%1%
@echo on
%JAVA_RUN% -classpath %CLASSPATH% %RUN_CLASS% are=%ARE_CONFIG_FILE% task=%TASK_FILES%
cd ecr_bat
  