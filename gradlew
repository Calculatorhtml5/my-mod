#!/usr/bin/env sh

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"

# Need this for relative symlinks.n
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

APP_HOME=`dirname "$PRG"`

# Change to APP_HOME as this is where we expect the gradle wrapper properties to be located.
APP_HOME=`cd "$APP_HOME" && pwd`

# OS specific support (must be 'true' or 'false').
cygwin=false
darwin=false
sun_os=false
case "`uname`" in
  CYGWIN*)        cygwin=true ;;
  Darwin*)        darwin=true ;;
  SunOS*)         sun_os=true ;;
esac

# Preamble
if [ "$JAVA_HOME" != "" ] ; then
  JAVA_EXE="$JAVA_HOME/bin/java"
elif [ "$JDK_HOME" != "" ] ; then
  JAVA_EXE="$JDK_HOME/bin/java"
else
  JAVA_EXE="java"
fi

# Increase the Gradle daemon JVM heap size by default to 1GB.
DEFAULT_JVM_OPTS='-Xmx1G -Dfile.encoding=UTF-8'

# Add default JVM options for client and server if they exist.
if [ -f "$APP_HOME/gradle/jvmargs" ]; then
   DEFAULT_JVM_OPTS="$DEFAULT_JVM_OPTS `cat "$APP_HOME/gradle/jvmargs"`"
elif [ -f "$APP_HOME/gradle/jvmargs.txt" ]; then
   DEFAULT_JVM_OPTS="$DEFAULT_JVM_OPTS `cat "$APP_HOME/gradle/jvmargs.txt"`"
fi

# Collect all arguments for the Java command.
APP_ARGS="-classpath ""$APP_HOME/gradle/wrapper/gradle-wrapper.jar"" org.gradle.wrapper.GradleWrapperMain $@"

# For Darwin (OS X) we need to add the -Xdock:name argument
if $darwin; then
  APP_ARGS="-Xdock:name=Gradle $APP_ARGS"
fi

# Execute Gradle
exec "$JAVA_EXE" $DEFAULT_JVM_OPTS $APP_ARGS
