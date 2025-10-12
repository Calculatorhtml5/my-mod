@IF EXIST "%~dp0java.exe" (SET "_JAVACMD=%~dp0java.exe") ELSE (SET "_JAVACMD=java")
@SETLOCAL

@REM Determine "true" application home
@SET APP_HOME=%~dp0

@REM Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
SET DEFAULT_JVM_OPTS=-Xmx64m -Xms64m

@REM Collect all arguments for the Java command
SET JAVA_OPTS=%DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS%

@REM Execute Gradle
"%_JAVACMD%" %JAVA_OPTS% -classpath "%APP_HOME%gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
