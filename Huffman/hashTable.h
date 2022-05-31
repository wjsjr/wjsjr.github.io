#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include "List.h"

typedef struct _HTab {
	int tableSize;
	int tableCapacity;
	List **table;	
} HashTab;


HashTab *tableInit(int nBuckets);

int insertHashKey(HashTab *ht, char c);

unsigned int bernsteinHash(char c, int n);

HashTab *buildHashTable(char *input);

List_element *hashFind(HashTab *ht, char key);

void destroyTable(HashTab *ht);

void printTable(HashTab *ht);

int validInput(char c);


