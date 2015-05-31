CC=  mips-linux-gnu-gcc -Wall -Os -EB -mips1
ARGS = -o
LIBS = -lm
HEADER_FILES_DIR = ./headerFiles
INCLUDES = -I $(HEADER_FILES_DIR)

MAIN= os

SRCS = main.c drivers.c

DEPS = $(HEADER_FILES_DIR)/drivers.h

$(MAIN): $(SRCS) $(DEPS)
	$(CC) $(ARGS) $(MAIN) $(SRCS) $(INCLUDES) $(LIBS)
	
cleanall: clean
	rm -f $(MAIN)
clean:
	rm -f *.o *~
