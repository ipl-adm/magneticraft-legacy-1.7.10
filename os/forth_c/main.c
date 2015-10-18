

#define null 0


//data tipes
typedef unsigned char byte;

typedef struct dictionarys{
	byte count;
	char name[32];
	struct dictionarys *next; 
	void *code;
} dictionary;

//stacks

int fpStack[128];
int spStack[128];

int *fp = fpStack;
int *sp = spStack;

//msg
char mgs_unknow[] = "\0x14Unknow Token: ";

//buffers
byte temp[128];
byte output[128];
byte word_buf[80];
int *R0 = fpStack;
int *S0 = spStack;

//dictionary
int indexHelp = 0;
dictionary *dict;

//funtions

void push(int data);
int pop();
void abortStacks();
void checkStack(int elements);

void forth();
void set();
void at();

//functionalities

void push(int data){
	*fp = data;
	fp++;
}

int pop(){
	int data = *fp;
	fp--;
	return data;
}

void checkStack(int elements){
	if(fpStack+elements < fp){
		abortStacks();
	}
}

void abortStacks(){
	sp = spStack;
	fp = fpStack;
	forth();
}
//words

void forth(){

}

void set(){
	checkStack(2);
	int *addr = (int*)pop();
	int num = pop();
	*addr = num;
}

void at(){
	checkStack(1);
	int *addr = (int*)pop();
	push(*addr);
}

//start

int main(){
	return 0;
}

//init dictionary

void addEntry(byte count, char *name, void *code){
	int i;
	dict[indexHelp].count = count;
	for(i = 0; i < 32 && name[i] != '\0'; i++)
		dict[indexHelp].name[i] = name[i];
	dict[indexHelp].next = &dict[indexHelp+1];
	dict[indexHelp].code = code;
}

void loadDictionary(){
	addEntry(5, "FORTH", &forth);
	addEntry(1, "!", &set);
	addEntry(1, "@", &at);
}