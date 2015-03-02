comp: midi src/*.java test
	javac -cp "forms-1.3.0.jar:build" -d build src/*.java
exec:
	java -cp build Main

midi: 
	javac src/Utilitaires/Midi.java -d build -cp build

test:
	javac ./Test.java -d build -cp build

clean:
	rm -rf build/*
