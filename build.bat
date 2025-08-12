@echo off
rem setup classpath
echo set _CP=%%_CP%%;%%1> cp.bat
set _CP=.;.\classes
for %%i in (lib\*.jar) do call cp.bat %%i
set CLASSPATH=%_CP%
del cp.bat

set COMPILER=javac
set COMPILE_OPTION=-encoding UTF-8 -sourcepath .\src -d classes -target 1.4 -source 1.4 -classpath %CLASSPATH%
dir src\*.java /b/s > bl
%COMPILER% %COMPILE_OPTION% @bl
del bl
@echo on
  