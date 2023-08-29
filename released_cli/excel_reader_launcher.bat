@echo off
:start
setx PATH "%PATH%;%JAVA_HOME%\bin";
echo :
set localPath=%cd%
echo : Your Current Directory
@echo %localPath%
echo :
echo Java is found at %JAVA_HOME%
echo :
echo :
echo "************* ALWAYS CHECK  PROPERTY FILE BEFORE CONTINUE ************"
echo :
echo : Content of the application.properties file
echo :
FOR /F "tokens=* delims=" %%x in (%localPath%\application.properties) DO echo %%x
echo :
echo :
@echo 1. Please confirm above application.properties values are correct ?
set /p Input=(yes/no):
If /I "%Input%"=="yes" goto yes
If /I "%Input%"=="no" goto no 
    :yes
        echo :
        echo :
        @echo 2. Have you placed correct xlsx sheet on the correct location (as mentioned in the property file) ?
        set /p Input=(yes/no):
        If /I "%Input%"=="yes" goto yes
        If /I "%Input%"=="no" goto no
            :yes
            echo :
            echo :
            @echo 3. Do you want proceed to upload xlsx sheet data to server ?
            set /p Input=(yes/no):
            If /I "%Input%"=="yes" goto yes
            If /I "%Input%"=="no" goto no
                :yes
                echo :
                echo :
                @echo ".................<<< Uploading file >>>................"
            
                (
			java -Dpath.properties=%localPath%\properties\application.properties -Dorg.slf4j.simpleLogger.logFile=%localPath%\logs\output.txt -jar %localpath%\jar\excelReaderApp_release_1.0.jar
                )
                pause
                :no
                echo :
                echo :
                echo Please update property file again
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