comp: src/*.java
	javac -d build src/*.java

exec: comp
	java -cp build Main

midi: comp
	javac src/Utilitaires/Midi.java -d build -cp build

test: comp
	javac ./Test.java -d build -cp build

clean:
	rm build/*.class
