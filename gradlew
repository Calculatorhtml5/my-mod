#!/usr/bin/env sh

##############################################################################
##
##  Gradle wrapper script for UNIX
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a link to the actual shell script.
APP_HOME=`dirname "$0"`

# Need to use Perl due to limitations of sed/grep/awk on HP-UX
# to properly determine real APP_HOME

if [ "x$APP_HOME" = "x" ]; then
    APP_HOME="."
fi

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Use the maximum available, or set to 1GB (1024MB) if you have more memory.
# DEFAULT_JVM_OPTS='"-Xmx1024m" "-Xms256m"'

# OS specific support (must be 'true' or 'false')
cygwin=false
darwin=false
solaris=false
freebsd=false
case "`uname`" in
  CYGWIN*) cygwin=true;;
  Darwin*) darwin=true;;
  SunOS*) solaris=true;;
  FreeBSD*) freebsd=true;;
esac

# For Darwin, add options to allow for java 9 or higher to work
if $darwin; then
    DEFAULT_JVM_OPTS="$DEFAULT_JVM_OPTS "
fi

# For Solaris and FreeBSD, add options to allow for java 9 or higher to work
if $solaris || $freebsd; then
    DEFAULT_JVM_OPTS="$DEFAULT_JVM_OPTS "
fi

# Determine the Java command to run. See https://stackoverflow.com/questions/5923986/how-to-determine-if-java-is-32bit-or-64bit
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

	Please set the JAVA_HOME variable in your environment to match the
	location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

	Please set the JAVA_HOME variable in your environment to match the
	location of your Java installation."
fi

# Determine the path to the current script
SCRIPT_PATH=$(cd -P -- "$(dirname -- "$0")" && pwd -P)

# Determine the path to the wrapper jar
WRAPPER_JAR="$SCRIPT_PATH/gradle/wrapper/gradle-wrapper.jar"

# Find the path to the wrapper jar.
if [ ! -f "$WRAPPER_JAR" ]; then
    die "ERROR: The gradle-wrapper.jar file does not exist. This is required for the wrapper to function."
fi

# Construct the classpath
CLASSPATH="$WRAPPER_JAR"

# Determine the JVM arguments
JVM_OPTS="$DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS"

# Run the wrapper
exec "$JAVACMD" $JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
