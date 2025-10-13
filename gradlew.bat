@rem
@rem Copyright (C) 2017 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      http://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@echo off

setlocal

rem Determine script directory
set SCRIPT_DIR=%~dp0

rem Determine Java command to use
if defined JAVA_HOME (
    if exist "%JAVA_HOME%\jre\sh\java.exe" (
        set JAVACMD="%JAVA_HOME%\jre\sh\java.exe"
    ) else (
        set JAVACMD="%JAVA_HOME%\bin\java.exe"
    )
) else (
    set JAVACMD="java.exe"
)

if not exist %JAVACMD% (
    echo ERROR: JAVA_HOME is not set and no 'java.exe' command could be found in your PATH.
    echo.
    echo Please set the JAVA_HOME environment variable to the root directory of your Java installation.
    echo Please check that you have Java installed and in your PATH.
    exit /b 1
)

rem Set default JVM options
set DEFAULT_JVM_OPTS=-Xmx64m -Xms64m

rem Increase the Gradle daemon heap size, if configured.
if defined GRADLE_OPTS (
    set DEFAULT_JVM_OPTS=%DEFAULT_JVM_OPTS% %GRADLE_OPTS%
) else if defined JAVA_OPTS (
    set DEFAULT_JVM_OPTS=%DEFAULT_JVM_OPTS% %JAVA_OPTS%
)

rem Determine the Gradle wrapper JAR file
set GRADLE_WRAPPER_JAR=%SCRIPT_DIR%gradle\wrapper\gradle-wrapper.jar

rem Run the Gradle wrapper
%JAVACMD% %DEFAULT_JVM_OPTS% -classpath "%GRADLE_WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*

endlocal