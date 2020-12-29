#include <stdlib.h>
#include <stdio.h>

/* Heap of Nodes */
typedef struct _Node{
	char c;
	int count;
	struct _Node *left;
	struct _Node *right;
} Node;

Node *InitializeNode(char c, int count);

void printNode(Node *n);

int compareNodes(Node *n1, Node *n2);