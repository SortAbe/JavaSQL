export CLASSPATH=/usr/share/java/mysql-connector-java-8.0.33.jar:/usr/share/java/mysql-connector-j-8.0.33.jar:.
javac *.java
java Bench 4 test

rm -f Bench*.class
