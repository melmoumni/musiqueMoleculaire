comp: src/*.java test
	javac -cp "forms-1.3.0.jar:build" -d build src/*.java
exec:
	java -cp build Main
test:
	javac ./Test.java -d build -cp build

clean:
	rm -rf build/*
