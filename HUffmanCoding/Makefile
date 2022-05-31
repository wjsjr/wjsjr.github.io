CC=gcc

default: huffman

huffman: huffman.o dictionary.o hashTable.o heap.o List.o node.o
	$(CC) -o huffman huffman.o dictionary.o hashTable.o heap.o List.o node.o

huffman.o: huffman.c dictionary.c dictionary.h
	$(CC) -c huffman.c dictionary.c

dictionary.o: dictionary.c dictionary.h hashTable.c hashTable.h heap.c heap.h
	$(CC) -c dictionary.c hashTable.c heap.c

hashTable.o: hashTable.c hashTable.h List.c List.h
	$(CC) -c hashTable.c List.c

heap.o: heap.c heap.h node.c node.h
	$(CC) -c heap.c node.c

List.o: List.c List.h
	$(CC) -c List.c	

node.o: node.c node.h
	$(CC) -c node.c
clean:
	\rm -f *.o huffman *~ *#

