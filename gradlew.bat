@IF EXIST "%~dp0java.exe" (SET "_JAVACMD=%~dp0java.exe") ELSE (SET "_JAVACMD=java")

@REM Add default JVM options for client and server if they exist.
@IF EXIST "%~dp0gradle\jvmargs" (FOR /F "tokens=*" %%i IN (%~dp0gradle\jvmargs) DO @SET "_DEFAULT_JVM_OPTS=%%i")
@IF EXIST "%~dp0gradle\jvmargs.txt" (FOR /F "tokens=*" %%i IN (%~dp0gradle\jvmargs.txt) DO @SET "_DEFAULT_JVM_OPTS=%%i")
@IF NOT DEFINED _DEFAULT_JVM_OPTS SET _DEFAULT_JVM_OPTS=-Xmx1G -Dfile.encoding=UTF-8

@"%_JAVACMD%" %_DEFAULT_JVM_OPTS% -classpath "%~dp0gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
