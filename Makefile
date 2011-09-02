all:
	javac -classpath lib/commons-lang3-3.0.1.jar:lib/stringsearch-2.jar -d bin/ com/szuhanchang/hangman/*.java 

clean:
	rm -rf bin/*
