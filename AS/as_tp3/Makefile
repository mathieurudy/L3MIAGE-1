
CC= javac
EXEC= java
MAIN_PATH= ./lib/
JFLEX_PATH= $(MAIN_PATH)jflex-1.6.1.jar
CUP_PATH= $(MAIN_PATH)cup.jar:.

default: create compile createMain compileMain executeMain


create: bf.lex
	$(EXEC) -cp $(JFLEX_PATH) jflex.Main bf.lex


compile: Bf.java BfScan.java sym.java
	$(CC) -cp $(CUP_PATH) sym.java Bf.java BfScan.java  -g


createMain: Bf.class hello.bf
	$(EXEC) -cp $(CUP_PATH) BfScan hello.bf > Main.java


compileMain: Main.java
	$(CC) Main.java


executeMain: Main.class
	$(EXEC) Main


removeFiles:
	rm Main.java Bf.java
	rm *.class
