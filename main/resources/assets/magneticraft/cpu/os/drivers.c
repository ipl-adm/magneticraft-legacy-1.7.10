#include "drivers.h"

//disk driver
//the driver address can be any between 0x1 0xE
char* getBufferPointer(int address){
	int a = 0xFFF00000;
	address <<= 16;
	a |= address;
	return (char*)a;
}

int isBufferReady(char* buffer){
	return *(buffer+130) == -1;
}

void loadDiskNameToBuffer(char* buffer){
	*(buffer+130) = 1;
}

void saveDiskNameFromBuffer(char* buffer){
	*(buffer+130) = 2;
}

void loadSerialNumber(char* buffer){
	*(buffer+130) = 3;
}

void loadSector(char* buffer){
	*(buffer+130) = 4;
}

void saveSector(char* buffer){
	*(buffer+130) = 5;
}

void setSector(char* buffer, int sector){
	*(buffer+128) = (char)(sector & 255);
	*(buffer+129) = (char)((sector >> 8) & 255);
}

int getSector(char* buffer){
	int s;
	s = *(buffer+128);
	s |= (*(buffer+129) << 8);
	return s;
}

//monitor driver
//the monitor addres must be 0xF
int isKeyPressed(){
	char* p = (char*) 0xFF010000;
	return (*p) & 1;
}

void resetPressedKey(){
	char* p = (char*) 0xFF010000;
	*p = 0;
	return;
}

char getKeyPresed(){
	char* p = (char*) 0xFF010004;
	return *p;
}

int getCursorPosition(){
	int* p = (int*) 0xFF010008;
	return *p;
}
void setCursorPosition(int pos){
	int* p = (int*) 0xFF010008;
	*p = pos;
	return;
}

void setChar(char c, int pos){
	char* p = (char*) 0xFF010014;
	p = (p+pos);
	*p = c;
	return;
}
char getChar(int pos){
	char* p = (char*) 0xFF010014;
	p = (p+pos);
	return *p;
}

void clearScreen(){
	int i;
	for(i = 0;i<4000;i++){
		setChar(32,i);
	}
	return;
}

void addChar(char a){
	setChar(a, getCursorPosition());
}

//computer driver
//always is at 0x0
//not sure if this works fine
int getTime(){
	int* t = (int*)0x04;
	return *t;
}

void shutdown(){
	char* c = (char*) 0x1;
	*c = 1;
	return;
}

void restart(){
char* c = (char*) 0x2;
	*c = 1;
	return;
}

void wait(){//waits a tick
char* c = (char*) 0x3;
	*c = 1;
	return;
}

int getMaxMemory(){
	return *((int*)0x8);
}



