#include "node.h"
typedef struct _Heap{
	Node **heap;
	int size;
	int capacity;
} Heap;

Heap *InitializeHeap(int nVertices);

void destroyHeap(Heap *heap);

void heapInsert(Heap *h, Node *n);

void heapPercolateUp(Heap *h);

void printNode(Node *n);

void printHeap(Heap *h);

Node *deleteMin(Heap *h);

void percolateDown(Heap *h, int i);

int minChild(Heap *h, int i);

int heapPeek(Heap *h);
