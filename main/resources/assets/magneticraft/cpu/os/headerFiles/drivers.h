
//disk driver
char* getBufferPointer(int address); //the buffer has 128 bytes

int isBufferReady(char* buffer); //if the current operation is complete

void loadDiskNameToBuffer(char* buffer);

void saveDiskNameFromBuffer(char* buffer);

void loadSerialNumber(char* buffer);

void loadSector(char* buffer);

void saveSector(char* buffer);

void setSector(char* buffer, int sector);

int getSector(char* buffer);

//monitor driver
int isKeyPressed();

void resetPressedKey();

char getKeyPresed();

int getCursorPosition();

void setCursorPosition(int pos);

void addChar(char c);

void setChar(char c, int pos);

char getChar(int pos);

void clearScreen();

//computer driver
int getTime();

void shutdown();

void restart();

void wait();

int getMaxMemory();

char getPeripheralsConnected();

//drone driver
int isMoving();

int getDirection();

void rotate(int rot);

int moveFront();

int moveBack();

int mine();

int getEnergyLevel();

int getSlotContent();

int getSlotCount();

int getSelectedSlot();

void setSelectedSlot(int slot);

//end





