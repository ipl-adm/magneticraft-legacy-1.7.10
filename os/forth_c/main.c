

#define null 0


//data tipes
typedef unsigned char byte;
typedef unsigned int word;

typedef struct dictionarys{
	byte count;
	char name[32];
	struct dictionarys *next; 
	void *code;
} dictionary;

//stacks

word fpStack[128];
word spStack[128];

word *fp = fpStack;
word *sp = spStack;

//msg
char mgs_unknow[] = "\0x14Unknow Token: ";

//buffers
byte temp[128];
byte output[128];
byte word_buf[80];
word *R0 = fpStack;
word *S0 = spStack;

//dictionary
int indexHelp = 0;
dictionary *dict;

//funtions

void push(int data);
int pop();
void abortStacks();
void checkStack(int elements);


//functionalities

void push(word data){
	*fp = data;
	fp++;
}

word pop(){
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

void plus(){
	checkStack(2);
	word num1 = pop(), num2 = pop();
	push(num1 + num2);
}

void minus(){
	checkStack(2);
	word num1 = pop(), num2 = pop();
	push(num1 - num2);
}

void star(){
	checkStack(2);
	word num1 = pop(), num2 = pop();
	push(num1 * num2);
}

void slash(){
	checkStack(2);
	word num1 = pop(), num2 = pop();
	push(num1 / num2);
}

void slash_mod(){
	checkStack(2);
	word num1 = pop(), num2 = pop();
	push(num1 / num2);
	push(num1 % num2);
}

void star_slash(){
	checkStack(3);
	word num1 = pop(), num2 = pop(), num3 = pop();
	push(num1 * num2 / mun3);
}


void star_slash_mod(){
	checkStack(3);
	word num1 = pop(), num2 = pop(), num3 = pop();
	push(num1 * num2 / mun3);
	push(num1 * num2 % mun3);
}

void plus_set(){
	checkStack(2);
	word num1 = pop();
	word* addr = (word*) pop();
	*addr = num1;
}

void forth(){

}

//start

int main(){
	forth();
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
	addEntry(1, "+", &plus);
	addEntry(1, "-", &minus);
	addEntry(1, "*", &star);
	addEntry(1, "/", &slash);
	addEntry(4, "/MOD", &slash_mod);
	addEntry(2, "*/", &star_slash);
	addEntry(5, "*/MOD", &star_slash_mod);
	addEntry(5, "*/MOD", &plus_set);
}