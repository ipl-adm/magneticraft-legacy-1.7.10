
#include "system.h"

int getMemCursor(){
	return *((int*)0x8000);
}
void setMemCursor(int pos){
	*((int*)0x8000) = pos;
}

void reserve(int amount){
	setMemCursor(getMemCursor()+amount);
}
void* malloc(int bytes){
	void* v = (void*) (getMemCursor()+1);
	reserve(bytes);
	return v;
}

void free(void* pointer){}

void* salloc(int bytes){
	return malloc(bytes);
}

