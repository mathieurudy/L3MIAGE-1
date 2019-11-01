JAVA=java
JAVAC=javac
#JAVAFLAGS=-cp ../lib/jflex.jar:../lib/java-cup-11b.jar
JAVAFLAGS=-cp /usr/share/java/jflex.jar:/usr/share/java/cup.jar
LEX=$(JAVA) -jar /usr/share/java/jflex.jar
CUP=$(JAVA) -jar /usr/share/java/cup.jar

SRCS = Objet.java Variable.java Litteral.java Chaine.java Nombre.java Condition.java \
	Instruction.java Phrase.java Paragraphe.java \
	WorkingSect.java Data.java \
	Programme.java IdDiv.java DataDiv.java ProcDiv.java \
	MoveInst.java AddInst.java SubstractInst.java MultiplyInst.java DivideInst.java PerformInst.java\
	DisplayInst.java StopInst.java \
	IfInst.java \
	LexicalException.java Yylex.java sym.java parser.java


CLSS = $(addprefix classes/$(PACKAGE)/, $(SRCS:.java=.class))

PACKAGE=microcobol

all: $(PACKAGE).jar TestParser.class

%.class: %.java $(PACKAGE).jar
	$(JAVAC) $(JAVAFLAGS):$(PACKAGE).jar $<

$(CLSS) : $(SRCS)
	mkdir -p classes/$(PACKAGE)
	$(JAVAC) -d classes $(JAVAFLAGS) $(SRCS)

$(PACKAGE).jar: $(CLSS)
	jar -cvf $(PACKAGE).jar -C classes microcobol


Yylex.java: microcobol.lex
	$(LEX) $<

parser.java: microcobol.cup
	$(CUP) $<

sym.java: microcobol.cup
	$(CUP) $<

clean:
	rm -rf *.class classes $(PACKAGE).jar Yylex.java* parser.java sym.java
