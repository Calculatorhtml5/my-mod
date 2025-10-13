#!/usr/bin/env sh

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Determine the Java command to use to start the JVM.
if [ -n """$JAVA_HOME""" ] ; then
    if [ -x """$JAVA_HOME/jre/sh/java""" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="""$JAVA_HOME/jre/sh/java"""
    else
        JAVACMD="""$JAVA_HOME/bin/java"""
    fi
    if [ ! -x """$JAVACMD""" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

          Please set the JAVA_HOME environment variable to the root directory of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

          Please set the JAVA_HOME environment variable to the root directory of your Java installation.
          Please check that you have Java installed and in your PATH."
fi

# OS specific support (must be 'true' or 'false').
cygwin=false
mingw=false
darwin=false
case "$(uname)" in
  CYGWIN*) cygwin=true;;
  MINGW*) mingw=true;;
  Darwin*) darwin=true
           if [ -z """$USER""" ] ; then
               USER=`id -un`
           fi
           ;;
esac

# For Darwin, add options to allow Java6 to run without using the AWTEventQueue Thread
if [ """$darwin""" = """true""" ]; then
    DEFAULT_JVM_OPTS='-Xdock:name="Gradle" -Xdock:icon="$(dirname "$0")"/media/gradle.icns -Xmx64m -Xms64m'
else
    DEFAULT_JVM_OPTS='-Xmx64m -Xms64m'
fi

# Increase the Gradle daemon heap size, if configured.
if [ -n """$GRADLE_OPTS""" ] ; then
    DEFAULT_JVM_OPTS="""$DEFAULT_JVM_OPTS $GRADLE_OPTS"""
elif [ -n """$JAVA_OPTS""" ] ; then
    DEFAULT_JVM_OPTS="""$DEFAULT_JVM_OPTS $JAVA_OPTS"""
fi

# Determine the location of the Gradle distribution.
if [ -n """$GRADLE_HOME""" ] ; then
    GRADLE_HOME="""$GRADLE_HOME"""
elif [ -n """$(dirname "$(readlink -f "$0")")""" ] ; then
    GRADLE_HOME="""$(dirname "$(readlink -f "$0")")"""
else
    GRADLE_HOME="""$(dirname "$0")"""
fi

# Make GRADLE_HOME absolute if it's not already.
if [ ! -d """$GRADLE_HOME""" ] ; then
    die "ERROR: GRADLE_HOME is set to an invalid directory: $GRADLE_HOME"
fi

# Determine the Gradle wrapper JAR file.
GRADLE_WRAPPER_JAR="""$GRADLE_HOME/gradle/wrapper/gradle-wrapper.jar"""

# Run the Gradle wrapper.
exec """$JAVACMD""" $DEFAULT_JVM_OPTS -classpath """$GRADLE_WRAPPER_JAR""" org.gradle.wrapper.GradleWrapperMain """$@"""