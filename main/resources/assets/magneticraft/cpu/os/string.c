#include "system.h"

typedef struct{
	int length;
	char* characters;
}charArray;

typedef charArray* String

String create(int len){
	String s = (String)malloc(len);
	s->length = len;
	return s;
}

void printString(String s){
	int i;
	for(i = 0; i< s->length; i++){
		putChar(s->characters[i]);
	}
}