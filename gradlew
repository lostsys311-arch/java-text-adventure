#!/bin/sh

# Add default JVM options here.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

APP_NAME="Gradle"
APP_BASE_NAME=${0##*/}

# Use the maximum available file descriptor limit.
MAX_FD=$(ulimit -H -n) 2>/dev/null && ulimit -n "$MAX_FD" 2>/dev/null || true

APP_HOME=$(cd "${APP_HOME:-./}" > /dev/null && pwd -P) || exit

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

exec java $DEFAULT_JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
