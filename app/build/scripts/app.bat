@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  app startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and APP_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\app.jar;%APP_HOME%\lib\commons-text-1.9.jar;%APP_HOME%\lib\log4j-core-2.18.0.jar;%APP_HOME%\lib\google-api-services-books-v1-rev20240214-2.0.0.jar;%APP_HOME%\lib\google-api-client-2.4.0.jar;%APP_HOME%\lib\google-oauth-client-1.35.0.jar;%APP_HOME%\lib\google-auth-library-oauth2-http-1.23.0.jar;%APP_HOME%\lib\google-http-client-gson-1.44.1.jar;%APP_HOME%\lib\google-http-client-apache-v2-1.44.1.jar;%APP_HOME%\lib\google-http-client-1.44.1.jar;%APP_HOME%\lib\opencensus-contrib-http-util-0.31.1.jar;%APP_HOME%\lib\guava-33.0.0-jre.jar;%APP_HOME%\lib\dagger-2.42.jar;%APP_HOME%\lib\gson-2.10.1.jar;%APP_HOME%\lib\aws-java-sdk-cloudwatch-1.12.726.jar;%APP_HOME%\lib\aws-java-sdk-dynamodb-1.12.726.jar;%APP_HOME%\lib\aws-java-sdk-s3-1.12.726.jar;%APP_HOME%\lib\aws-java-sdk-kms-1.12.726.jar;%APP_HOME%\lib\aws-java-sdk-core-1.12.726.jar;%APP_HOME%\lib\jmespath-java-1.12.726.jar;%APP_HOME%\lib\jackson-annotations-2.12.7.jar;%APP_HOME%\lib\jackson-dataformat-cbor-2.12.7.jar;%APP_HOME%\lib\jackson-databind-2.12.7.2.jar;%APP_HOME%\lib\jackson-core-2.12.7.jar;%APP_HOME%\lib\aws-lambda-java-core-1.2.1.jar;%APP_HOME%\lib\aws-lambda-java-events-3.11.0.jar;%APP_HOME%\lib\unit-api-2.1.3.jar;%APP_HOME%\lib\mockito-inline-4.11.0.jar;%APP_HOME%\lib\mockito-core-4.11.0.jar;%APP_HOME%\lib\commons-lang3-3.11.jar;%APP_HOME%\lib\log4j-api-2.18.0.jar;%APP_HOME%\lib\failureaccess-1.0.2.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\checker-qual-3.41.0.jar;%APP_HOME%\lib\error_prone_annotations-2.23.0.jar;%APP_HOME%\lib\j2objc-annotations-2.8.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\joda-time-2.12.7.jar;%APP_HOME%\lib\byte-buddy-1.12.19.jar;%APP_HOME%\lib\byte-buddy-agent-1.12.19.jar;%APP_HOME%\lib\objenesis-3.3.jar;%APP_HOME%\lib\httpclient-4.5.14.jar;%APP_HOME%\lib\commons-codec-1.16.1.jar;%APP_HOME%\lib\google-auth-library-credentials-1.23.0.jar;%APP_HOME%\lib\httpcore-4.4.16.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\auto-value-annotations-1.10.4.jar;%APP_HOME%\lib\opencensus-api-0.31.1.jar;%APP_HOME%\lib\grpc-context-1.60.1.jar;%APP_HOME%\lib\grpc-api-1.60.1.jar


@rem Execute app
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %APP_OPTS%  -classpath "%CLASSPATH%" julielerche.capstone.App %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable APP_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%APP_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
