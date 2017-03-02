.PHONY: all clean run
TARGET = client
MAIN_CLASS = SetClient

SOURCE_DIR = src

SRC = $(wildcard $(SOURCE_DIR)/*.java)
CLS = $(SRC:.java=.class)

JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

$(TARGET).jar: $(CLS)
	jar cfve $@ $(MAIN_CLASS) -C $(SOURCE_DIR)/ .

%.class: %.java
	$(JC) $(JFLAGS) $<

run: $(TARGET).jar
	java -jar $^

clean:
	rm -rf $(SOURCE_DIR)/*.class
	rm -f $(TARGET).jar
