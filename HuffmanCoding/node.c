#include "node.h"
//initialize heap/tree node.
Node *InitializeNode(char c, int count){
	Node *n = malloc(sizeof(Node));
	n -> c = c;
	n -> count = count;
	n -> left = NULL;
	n -> right = NULL;
	return n;

}

//Print heap/tree node
void printNode(Node *n){
	if (n != NULL){
		printf("Char: %c, Count: %d\n", n -> c, n-> count);	
	}
	else{
		printf("EMPTY NODE");
	}
	
}



//Helper function to compare Count of to nodes
//Return 1 if n1 is lower priority, 0 if n2 is greater or = 
int compareNodes(Node *n1, Node *n2){
	if (n1 -> count < n2 -> count){
		return 1;
	}
	else{
		return 0;
	}

}
