@echo off
:start
setx PATH "%PATH%;%JAVA_HOME%\bin";
echo :
set mypath=%cd%
echo : Your Current Directory
@echo %mypath%
echo :
echo Java is found at %JAVA_HOME%
echo :
echo :
echo "************* ALWAYS CHECK  PROPERTY FILE BEFORE CONTINUE ************"
echo :
echo : Content of the application.properties file
echo :
FOR /F "tokens=* delims=" %%x in (%mypath%\application.properties) DO echo %%x
echo :
echo :
@echo 1. Have You Updated application.properties file correctly ? 
set /p Input=(yes/no):
If /I "%Input%"=="yes" goto yes
If /I "%Input%"=="no" goto no 
:yes
echo :
echo :
@echo 2. Have you placed xls sheet on the correct location (as mentioned in the proprty file) ?
set /p Input=(yes/no):
If /I "%Input%"=="yes" goto yes
If /I "%Input%"=="no" goto no
:yes
echo :
echo :
@echo 3. Do you want to finally run java CLI App to upload xls sheet data over API call ?
set /p Input=(yes/no):
If /I "%Input%"=="yes" goto yes
If /I "%Input%"=="no" goto no
:yes
echo :
echo :
@echo ".................<<< Uploading file >>>................"

java -Dpath.properties=%mypath%\application.properties -jar App.jar

pause
:no 
echo :
echo :
echo Please update property file & come again
cls
pause
goto start
:no
echo :
echo :
echo Please place xlsx sheet as per property file location
cls
pause
goto start
:no
echo :
echo :
echo please update application.properties file
echo Do you want to close the program ?
set /p Input4=(yes/no):  	
If /I "%Input4%"=="yes" goto yes
If /I "%Input4%"=="no" goto no
:yes
exit
:no
cls
pause
goto start