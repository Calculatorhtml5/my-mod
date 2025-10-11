#!/usr/bin/env sh

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"

# Need this for relative resolution of APP_HOME
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

# Change to APP_HOME as it contains gradle-wrapper.jar and gradle-wrapper.properties
# This also ensures that we find the Gradle distribution correctly
cd "$APP_HOME"

# Add default JVM options here. You may also use JAVA_OPTS.
# The JVM options will be applied to every Gradle client command.
# For example, to change the JVM heap size to 2GB, uncomment the following
# line: DEFAULT_JVM_OPTS='-Xmx2G -Xms2G'
DEFAULT_JVM_OPTS='-Xmx64m -Xms64m'

# OS specific support (must be 'true' or 'false')
cygwin=false
darwin=false
solaris=false
debug=0
case "`uname`" in
  CYGWIN*) cygwin=true ;; 
  Darwin*) darwin=true ;; 
  SunOS*) solaris=true
          debug=1
          ;;
esac

# Helper function to get a file's canonical path
get_canonical_path() {
    if [ "$cygwin" = "true" ]; then
        echo `cygpath -m "$1"`
    else
        echo "$1"
    fi
}

# Helper function to get an absolute path from a relative path
absolute_path() {
    (cd "`dirname "$1"`" && echo "$(pwd -P)/`basename "$1"`")
}

# Determine the Java command to run.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses java_base/jre/sh/java
        JAVACMD="$JAVA_HOME/jre/sh/java"
    elif [ -x "$JAVA_HOME/bin/java" ] ; then
        JAVACMD="$JAVA_HOME/bin/java"
    else
        echo "Warning: JAVA_HOME is set but $JAVA_HOME/bin/java does not exist. Attempting to use default 'java' command." >&2
        JAVACMD="java"
    fi
else
    JAVACMD="java"
fi

if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not set or no 'java' command can be found in your PATH." >&2
  echo "Please set the JAVA_HOME environment variable or add the 'java' command to your PATH." >&2
  exit 1
fi

# Detect older JVMs to warn about, or fail if unsupported
java_version_output=`"$JAVACMD" -version 2>&1`
java_version_major=$(echo "$java_version_output" | grep -oP '(version|openjdk version) "\K[0-9]+' | head -1)
if [ -z "$java_version_major" ]; then
    # Fallback for older JVMs or different output formats
    java_version_major=$(echo "$java_version_output" | grep -oP 'java version "1\.\K[0-9]+' | head -1)
fi

if [ "$java_version_major" -lt 17 ] ; then
    echo "Error: Gradle requires Java 17 or later to run. You are currently using Java $java_version_major." >&2
    echo "Please install Java 17 or later, or set JAVA_HOME to a Java 17 or later installation." >&2
    exit 1
fi

# Determine the script name
SCRIPT_NAME=`basename "$0"`

# Set the path to the wrapper jar
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Execute Gradle
exec "$JAVACMD" $DEFAULT_JVM_OPTS $JAVA_OPTS -classpath "$(get_canonical_path "$WRAPPER_JAR")" org.gradle.wrapper.GradleWrapperMain "$@"
