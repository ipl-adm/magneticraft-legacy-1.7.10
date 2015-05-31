
#include "system.h"
typedef int ELEMENT;

typedef struct node {
	ELEMENT element;
	struct node * sig;
	} LISTNODE;
typedef LISTNODE* NODE;
typedef struct lista {
	NODE start;
	int length;
	NODE end;
} SLIST;
typedef SLIST* List;

void create(List* l){
	*l = (List) malloc(sizeof(SLIST));
	(*l)->start = (NODE) malloc(sizeof(LISTNODE));
    (*l)->start->sig = NULL;
    (*l)->end = (*l)->start ;
    (*l)->length=0;
}

void delete(List* l){
	(*l)->end = (*l)->start;
	while ((*l)->end != NULL){
		(*l)->end = (*l)->end->sig;
		free((*l)->start);
		(*l)->start = (*l)->end;
    }
	free(*l);
}

NODE first(List t){
	return t->start;
}

NODE last(List t){
	return t->end;
}

int isEmpty(List t){
	return t->length == 0;
}

ELEMENT get(List t, NODE n){
	return n->sig->element;
}

int size(List t){
	return t->length;
}

void insert(List *p, NODE n, ELEMENT e){
	NODE q ;
    q=p->sig;
    p->sig = (NODE *) malloc(sizeof(NODE)) ;
    p->sig->element = e;
    p->sig->sig = q;
    if (q==NULL) (*l)->end=p->sig;
    (*l)->length ++ ;
}

void add(List *p, ELEMENT e){
	insert(p,last(*p),e);
}

void remove(List *l, NODE p){
	NODE q;
	
	q=p->sig;
	p->sig=q->sig;
	if (p->sig==NULL)
		(*l)->end = p;
	free(q);
	(*l)->length--;
}

void set(List *l, NODE p, ELEMENT e){
	p->sig->element = e;
}

NODE next(List t, NODE p){
	return p->sig;
}
