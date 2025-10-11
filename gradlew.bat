@rem
@rem Copyright (c) 2007-2014, The Gradle Goodness Project
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem     http://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@rem To change the JVM arguments, change the DEFAULT_JVM_OPTS variable.
@rem For example, to change the JVM heap size to 2GB, uncomment the following
@rem line: set DEFAULT_JVM_OPTS=-Xmx2G -Xms2G
set DEFAULT_JVM_OPTS=-Xmx64m -Xms64m

@rem Set to the Java 17+ installation directory if not already set
@rem set JAVA_HOME=C:\Program Files\Java\jdk-17

@rem Determine the script path
set SCRIPT_PATH=%~dp0

@rem Change to the script's directory
cd "%SCRIPT_PATH%"

@rem Check if JAVA_HOME is set and points to a valid JDK
if not defined JAVA_HOME (
    for /f "tokens=*" %%i in ('where java') do (
        set "_JAVA_PATH=%%i"
        goto checkJavaVersion
    )
    echo Error: JAVA_HOME is not set and 'java' command is not found in PATH.
    echo Please set the JAVA_HOME environment variable or add the 'java' command to your PATH.
    goto :EOF
)

if not exist "%JAVA_HOME%\bin\java.exe" (
    echo Warning: JAVA_HOME is set but "%JAVA_HOME%\bin\java.exe" does not exist. Attempting to use default 'java' command.
    for /f "tokens=*" %%i in ('where java') do (
        set "_JAVA_PATH=%%i"
        goto checkJavaVersion
    )
    echo Error: JAVA_HOME is set but invalid, and 'java' command is not found in PATH.
    echo Please set the JAVA_HOME environment variable correctly or add the 'java' command to your PATH.
    goto :EOF
)
set "_JAVA_PATH=%JAVA_HOME%\bin\java.exe"

:checkJavaVersion
if not exist "%_JAVA_PATH%" (
    echo Error: No 'java' command found. Please ensure Java 17+ is installed and accessible.
    goto :EOF
)

for /f "tokens=*" %%i in ('"%_JAVA_PATH%" -version 2^>^&1') do (
    echo %%i | findstr /R "version \"17\..*" > nul && set "JAVA_VERSION_OK=true"
    echo %%i | findstr /R "openjdk version \"17\..*" > nul && set "JAVA_VERSION_OK=true"
)

if not defined JAVA_VERSION_OK (
    echo Error: Gradle requires Java 17 or later to run. Your current Java version is too old.
    echo Please install Java 17 or later, or set JAVA_HOME to a Java 17 or later installation.
    goto :EOF
)

@rem Call the wrapper jar
java %DEFAULT_JVM_OPTS% %JAVA_OPTS% -jar "%SCRIPT_PATH%gradle\wrapper\gradle-wrapper.jar" %*
