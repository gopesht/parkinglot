mkdir -p target
javac -d target/ src/com/gojek/beans/*.java
javac -d target/ -cp target/ src/com/gojek/Main.java
cd target/
java com.gojek.Main $1
