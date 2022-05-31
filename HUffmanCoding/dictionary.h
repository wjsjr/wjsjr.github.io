#include "hashTable.h"
#include "heap.h"

/*These are public huffman dictionary methods used in interface. */
HashTab *buildDictionary(char *inputFile, Node **huffTree);
void dumpDict(HashTab *dict);
void destroyTree(Node *root);
int isLeaf(Node *tNode);
void printTree(Node *tree);