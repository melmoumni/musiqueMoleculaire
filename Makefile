comp: src/*.java 
	javac -cp "forms-1.3.0.jar:build" -d build src/*.java
exec:
	java -cp "forms-1.3.0.jar:build" Main
t:
	java -cp "forms-1.3.0.jar:build" Main test
exectest: test
	java -cp build Test
test: comp
	javac -cp build ./Test.java -d build

clean:
	rm -rf build/*
