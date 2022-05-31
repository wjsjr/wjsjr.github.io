#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <stdbool.h>
#include "hashTable.h"

/*Initialize Hash table. Allocate nbuckets, init list in each bucket*/
HashTab *tableInit(int nBuckets){
	HashTab *ht = malloc(sizeof(HashTab));
	ht -> tableCapacity = nBuckets;
	ht -> table = malloc(sizeof(List *) * nBuckets);
	for (int i = 0; i < nBuckets; i++){
		ht -> table[i] = malloc(sizeof(List));
		list_init(ht->table[i]);
	} 
	return ht;
}



/*Chose bernstein hash after testing this, FNV and naive. Seemed to have less collisions*/
unsigned int bernsteinHash(char c, int n){
	unsigned int h = 33 * 5381 + c;
    return h % n;
}

/*Insert Key to table*/
int insertHashKey(HashTab *ht, char c){
	unsigned int hashCode = bernsteinHash(c, ht -> tableCapacity);

	
	List *currList = ht -> table[hashCode];
	List_element *currNode = list_find(currList, c);
	if (currNode == NULL){
		ht -> tableSize ++;
		list_append(currList, c);
		return 1;
	}
	else{
		
		currNode -> count ++;
		return 0;
	}
}

/*Helper function to make sure that input is in valid range*/
int validInput(char c){
	if ( (c >= 32 && c <= 127) || c == 10 || c == 13 || c == 26){
		return 1;
	}
	return 0;
}

/*Build Hash Table*/

HashTab *buildHashTable(char *input){
	FILE *file = fopen(input, "r");
	HashTab *ht = NULL;
	if (file == NULL){
		//Print error and return NULL table if there was invalid file name entered
		printf("ERROR READING FILE\n");
		return ht;
	}
	ht = tableInit(297); //96 Visible ascii characters + 3 Control Codes * 3 to reduce collisions
	char ch;
	// Read file char by char. If char is valid, insert into hash table. Insert method looks for char in table, if char is found, count is incremented, otherwise new is added to table. Otherwise print error and return NULL
	while((ch = fgetc(file)) != EOF){
		if(validInput(ch)){
			insertHashKey(ht, ch);
		}
		else{
			printf("INVALID CHAR\n");
			return NULL;

		}

	}
	fclose(file);
	return ht;
}


/*Print table*/
void printTable(HashTab *ht){
	printf("Table Capacity: %d\n", ht -> tableCapacity);
	printf("Table Size: %d\n", ht -> tableSize);
	for (int i = 0; i < ht -> tableCapacity; i++){
		printf("i: %d | ", i);
		printList(ht->table[i]);
	}



}


/*Iterate over each cell of table and destroy it*/
void destroyTable(HashTab *ht){
	for (int i = 0; i < ht -> tableCapacity; i++){
		
		List *curr = ht -> table[i];
		if (curr != NULL){
			if (curr -> head != NULL){
				//printList(curr);
				list_destroy(curr);
				
			}
			free(curr);
			
		}
		
		
	}
	free(ht -> table);
	free(ht);

}

/*Given a key, find index key hashes to and search for key in list*/
List_element *hashFind(HashTab *ht, char key){
	unsigned int index = bernsteinHash(key, ht -> tableCapacity);
	return list_find(ht -> table[index], key);
	
}
