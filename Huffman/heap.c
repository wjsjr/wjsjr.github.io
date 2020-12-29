#include <stdio.h>
#include "heap.h"

//Initialize Heap
Heap *InitializeHeap(int nVertices){
	Heap *h = malloc(sizeof(Heap));
	h -> heap = malloc(nVertices * sizeof(Node *));	
	h -> capacity = nVertices;
	h -> size = 0;
	Node *dummyNode = InitializeNode('a', -1);
	h -> heap[0] = dummyNode;
	return h; 
}

//destpry heap
void destroyHeap(Heap *h){
	for (int i = 0; i < h -> size + 1; i++){
		free(h -> heap[i]);
	}
	free(h -> heap);
	free(h);
}


void heapInsert(Heap *h, Node *n){
	//Assumes we have already malloced and initialized heapNode
	h -> heap[++h->size] = n;
	heapPercolateUp(h);

}



//Percolate Up
//
//NOTE: THERE COULD DEFINITELY BE BUGS IN THIS DUE TO CHANGE FROM MIN TO MAX
void heapPercolateUp(Heap *h){
	Node *temp;
	int i = h -> size;
	while (i / 2 > 0) {
		//if h -> heap[i] is lower priorty than its parent, swap them
		if ( compareNodes(h -> heap[i], h -> heap[i/2]) ){
			temp = h -> heap[i];
			h -> heap[i] = h -> heap[i / 2];
			h -> heap[i / 2] = temp;

		}
		i /= 2;
	}
}


//Print heap. Heap printed in rows to try and simulate tree structure

void printHeap(Heap *h){
	if (h -> size == 0){
		printf("Empty heap!");
	}
	int rowSize = 1;
	int counter = 0;
	for (int i = 1; i < h -> size+1; i++){
		printNode(h -> heap[i]);
		counter ++;
		if (counter >= rowSize) {
			printf("\n");
			counter = 0;
			rowSize *= 2;
		}
	}
}


//Pop and return min value from heap
Node *deleteMin(Heap *h){
	if (h->size <= 0){
		printf("ERROR, CAN'T DELETE FROM EMPTY HEAP!");
		return NULL;
	}

	//Min Value
	Node *min = h -> heap[1];


	//Store Smallest Value as min Value;
	h -> heap[1] = h -> heap[h -> size];
	h -> heap[h -> size] = NULL;
	h -> size --;
	percolateDown(h, 1);
	return min;
}

//Percolate down after popping

//NOTE COULD HAVE BUGS DUE TO CHANGE FROM MIN TO MAX

void percolateDown(Heap *h, int i){
	Node *temp;
	while (i * 2 <= h->size){
		int mc = minChild(h, i);
		Node *n1 = h -> heap[i];
		Node *n2 = h -> heap[mc];	

		//If child is lower priority than parent, swap them
		if ( compareNodes(n2, n1) ){
			temp = h -> heap[i];
			h -> heap[i] = h -> heap[mc];
			h -> heap[mc] = temp;
		}
		i = mc;
	}
	

}

//Peek on top of heap
int heapPeek(Heap *h){
	if (h -> size > 0){
		return h -> heap[1] -> count;	
	}
	else{
		return -1;
	}
	

}

//NOTE COULD HAVE BUGS DUE TO CHANGE FROM MIN TO MAX

int minChild(Heap *h, int i) {
	//Gonna need to be careful with size here
	if ( ( (i * 2) + 1) > (h -> size) ){
		return i * 2;
	}
	Node *n1 = h -> heap[i * 2];
	Node *n2 = h -> heap[i * 2 + 1];
	if (compareNodes(n1, n2)){
		return i * 2;
	}
	else{
		return i * 2 + 1;
	}


}

