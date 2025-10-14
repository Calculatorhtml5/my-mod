#!/usr/bin/env sh

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Resolve symlinks and determine script directory
PRG="$0"

# Need this for relative path when executing from symlink
while [ -h "$PRG" ] ; do
  LS=`ls -ld "$PRG"`
  LINK=`expr "$LS" : '.*-> \(.*\)$'`
  if expr "$LINK" : '/.*' > /dev/null; then
    PRG="$LINK"
  else
    PRG=`dirname "$PRG"`/"$LINK"
  fi
done

SCRIPT_DIR=`dirname "$PRG"`

# Add default JVM options here. You may also use JAVA_OPTS and GRADLE_OPTS.
# The first two are common for all Java applications,
# the latter one is used only by Gradle.
DEFAULT_JVM_OPTS='-Xmx64m -Xms64m'

"$SCRIPT_DIR"/gradlew.bin "$@"
