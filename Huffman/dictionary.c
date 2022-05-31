#include "dictionary.h"

/*This is interface to build huffman dictionary and huffman tree. Most methods are kept private and used only in this file.*/


/*
This method takes in a hashtable of characters and their counts (but not codes yet) and arranges the chars & counts in a min heap
 */
Heap *buildHuffmanHeap(HashTab *ht){

	Heap *h = InitializeHeap(ht -> tableSize + 1);

	//iterate over buckets
	for (int i = 0; i < ht -> tableCapacity; i++){

		List *curr = ht -> table[i];

		//If bucket not null, continue
		if (curr != NULL){

			//if list not empty, iterate over list adding to min heap.
			if (curr -> head != NULL){
				List_element *lp = curr -> head;
				while (lp != NULL){

					//Create heap node from key and count
					Node *n = InitializeNode(lp -> key, lp -> count);

					//insert into heap
					heapInsert(h, n);

					//push pointer forward
            		lp = lp -> next;
				}
				
			}
	
			
		}
		
		
	}

	return h;
}

/*
This method takes a heap of chars & counts and combines the heap nodes into a huffman heap. The original heap is destroyed at the end of this process*
 */
Node *buildHuffmanTree(Heap *h){
	Node *a, *b, *newNode;
	int newCount;

	//While there is more than 1 element in heap, pop two largest off heap, make them parents of new "sum" node/ insert sum back into heap
	while (h -> size > 1){
		a = deleteMin(h);
		b = deleteMin(h);
		newCount = a -> count + b -> count;
		newNode = InitializeNode('$', newCount);
		newNode -> left = a;
		newNode -> right = b;
		heapInsert(h, newNode);
	}

	//Return root of huffman tree
	Node *tree = deleteMin(h);
	destroyHeap(h);
	return tree;
}

/*
Helper method. Returns true if current tree node is a leaf
 */
int isLeaf(Node *tNode){
	if (tNode -> left == NULL && tNode -> right == NULL){
		return 1;
	}
	return 0;

}
/*
Print tree in preorder traversal. Use for testing
 */
void printTree(Node *tree){
	if (tree != NULL){
		printf("Char: %c, Count: %d\n", tree -> c, tree -> count);
		printTree(tree -> left);
		printTree(tree -> right);	
	}
}

//Destroy tree from the bottom up
void destroyTree(Node *root){
	if (root != NULL){

		if (root -> left != NULL){
			destroyTree(root -> left);
		}
		if (root -> right != NULL){
			destroyTree(root -> right);
		}
		free(root);
	}
}

/*
Helper method to generate huffman codes. This takes a path code, and appends either 1 or 0 to it. Returns new path code
 */
char *appendToCode(char *code, int codeLength, int direction){

	//Init new code
	char *newCode = malloc( (codeLength * sizeof(char)) + 2);

	//Put string terminator in new code
	newCode[codeLength + 1] = '\0';

	//Copy old code
	for (int i = 0; i < codeLength; i++){
		newCode[i] = code[i];
	}

	//Put in new bit
	if (direction == 0){
		newCode[codeLength] = '0';	
	}
	else{
		newCode[codeLength] = '1';		
	}

	//Return new code
	return newCode;
	

}

/*
Recursive helper method to generate huffman codes. Traverse tree to leaf, tracking path thus far
 */
void generateCodesHelper(Node *root, HashTab *table, char *path, int pathLength){
	if(root != NULL){

		//If we are at a leaf, search hash table for leaf's character. Set code in hash table = path code
		if (isLeaf(root)){
			List_element *hashNode = hashFind(table, root -> c);
			hashNode -> code = path;
		}

		//Else, generate new left and right path codes. Traverse left and right children with corresponding path codes passed in
		else{
			char *leftPath = appendToCode(path, pathLength, 0);
			char *rightPath = appendToCode(path, pathLength, 1);
			generateCodesHelper(root -> left, table, leftPath, pathLength+1);
			generateCodesHelper(root -> right, table, rightPath, pathLength+1);
			free(path);
			
		}
	}
}

/*
Wrapper method to generate huffman codes
 */
void generateCodes(Node *tree, HashTab *table){
	//This is theoretical longest possible path in a degenerate huffman tree
	int maxPath = table -> tableSize;
	char *path = malloc(1 * sizeof(char));
	path[0] = '\0';
	generateCodesHelper(tree, table, path, 0);

}


/* 
This is method that huffman.c file uses to build huffman dict and tree. Takes in empty tree pointer and input file name. Builds dict and tree and returns both to main file.
*/
HashTab *buildDictionary(char *inputFile, Node **huffTree){

	//First, build basic hash table. This has chars and counts but no codes
	HashTab *table = buildHashTable(inputFile);	

	//This will be NULL if there is a problem reading input file. Either invalid filename or invalid char in file. In that case return NULL to main file, which is signal that there was an error
	if (table == NULL){
		return table;
	}

	//Build heap from hash table
	Heap *heap = buildHuffmanHeap(table);

	//Build tree from heap
	Node *tree = buildHuffmanTree(heap);

	//Generate codes and store them in hash table from tree
	generateCodes(tree, table);

	//Return tree and hash table
	*huffTree = tree;
	return table;
}

/*
Merge component of merge sort for sorting arrays of List Nodes. Used for "dump dict"
*/
void merge(List_element **arr, int l, int m, int r)  
{  
    int i, j, k;  
    int n1 = m - l + 1;  
    int n2 = r - m;  
  
    List_element **L = malloc(n1 * sizeof(List_element));
    List_element **R = malloc(n2 * sizeof(List_element));
  
    for (i = 0; i < n1; i++)  
        L[i] = arr[l + i];  
    for (j = 0; j < n2; j++)  
        R[j] = arr[m + 1 + j];  
  
    
    i = 0;
    j = 0;
    k = l;
    while (i < n1 && j < n2) {  
        if (L[i] -> key <= R[j] -> key) {  
            arr[k] = L[i];  
            i++;  
        }  
        else {  
            arr[k] = R[j];  
            j++;  
        }  
        k++;  
    }  
  
    while (i < n1) {  
        arr[k] = L[i];  
        i++;  
        k++;  
    }  
  
    while (j < n2) {  
        arr[k] = R[j];  
        j++;  
        k++;  
    }
    free(L);
    free(R);  
}  
  
 /*
 Merge sort for sorting arrays of List Nodes. Used for "dump dict"
 */

void mergeSort(List_element **arr, int l, int r)  
{  
    if (l < r) {  
        int m = (l + r) / 2;  
  
        // Sort first and second halves  
        mergeSort(arr, l, m);  
        mergeSort(arr, m + 1, r);  
  
        merge(arr, l, m, r);  
    }  
}

/*
This is dump dict method called by interface. Iterate over nodes in hash table, storing each node in array. Sort array using merge sort and print sorted array
 */
void dumpDict(HashTab *dict){

	//malloc node array
	List_element **dumpList = malloc(99 * sizeof(List_element)); //Malloc array of list elements
	int j = 0;
	List_element *curr;

	//Iterate over hash table
	for (int i = 0; i < dict -> tableCapacity; i++){
		if (dict ->table[i] -> head != NULL){
			curr = dict ->table[i] -> head;

			//Every time we find a node, add it to dump list
			while (curr != NULL){
				dumpList[j] = curr;
				j++;
				curr = curr -> next;
			}

		}
		
	}
	//Sort array
	mergeSort(dumpList, 0, j - 1);

	//Print sorted array
	for (int k = 0; k < j; k++){
		printf("%c;%s\n", dumpList[k] -> key, dumpList[k] -> code);	
	}

	//Print the array itself, but not the nodes. Nodes are still stored in dict
	free(dumpList);
	

}
