CC= gcc -Wall
LIBS = -lm
HEADER_FILES_DIR = ./header_files
INCLUDES = -I $(HEADER_FILES_DIR)

MAIN= test

SRCS = main.c monitor.c

DEPS = $(HEADER_FILES_DIR)/monitor.h
OBJS = $(SRCS:.c=.o)

$(MAIN): $(OBJS)
	$(CC) -o $(MAIN) $(OBJS) $(LIBS)
%.o: %.c $(DEPS)
	$(CC) -c $< $(INCLUDES)

cleanall: clean
	rm -f $(MAIN)
clean:
	rm -f *.o *~
