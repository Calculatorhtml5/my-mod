@IF EXIST "%~dp0java.exe" (SET "JAVA_EXE=%~dp0java.exe") ELSE (SET "JAVA_EXE=java")
@CALL "%~dp0gradlew.bin" %*
