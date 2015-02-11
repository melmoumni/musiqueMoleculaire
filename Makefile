comp: midi src/*.java test
	javac -cp build -d build src/*.java

exec:
	java -cp build Main

midi: 
	javac src/Utilitaires/Midi.java -d build -cp build

test:
	javac ./Test.java -d build -cp build

clean:
	rm -rf build/*
