@REM
@REM Copyright 2007-2015 the original author or authors.
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM      https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM

@REM Set to 'true' to run Gradle in debug mode
@set DEBUG=false

@REM The default locale uses different number separators for some locales, see https://github.com/gradle/gradle/issues/10634
@REM In order to avoid issues, we will use an English locale for the execution of the JVM
@set JAVA_TOOL_OPTIONS=-Duser.language=en -Duser.region=US

@if "%DEBUG%" == "true" (
  @set JAVA_OPTS=-Dorg.gradle.daemon.debug=true -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005
)

@REM Determine if a JRE or JDK is installed. See https://stackoverflow.com/questions/5923986/how-to-determine-if-java-is-32bit-or-64bit
@if defined JAVA_HOME (
  @set "_JAVACMD=%JAVA_HOME%\bin\java.exe"
)

@if not defined _JAVACMD (
  @for %%i in (java.exe) do @if not defined _JAVACMD @set "_JAVACMD=%%~$PATH:i"
)

@if not defined _JAVACMD (
  @echo ERROR: JAVA_HOME is not set and no 'java.exe' command could be found in your PATH.
  @echo.
  @echo Please set the JAVA_HOME variable in your environment to match the
  @echo location of your Java installation.
  @goto fail
)

@set DIR=%~dp0
@set CLASSPATH=%DIR%gradle\wrapper\gradle-wrapper.jar

@REM Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
@set DEFAULT_JVM_OPTS=-Xmx64m -Xms64m

@REM Use the maximum available, or set to 1GB (1024MB) if you have more memory.
@REM @set DEFAULT_JVM_OPTS=-Xmx1024m -Xms256m

"%_JAVACMD%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

@if %ERRORLEVEL% NEQ 0 goto fail
@goto end

:fail
@echo Build failed!
@exit /b %ERRORLEVEL%

:end
@exit /b 0
