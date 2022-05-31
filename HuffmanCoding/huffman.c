#include "dictionary.h"
#include <string.h>

/*This method takes in an encoded input file and a huffman tree, and writes a decoded version of the file. It also prints the files decoding to stdout.*/
int decode(char *inputFileName, Node *tree){
	//Open the Files
	FILE *input = fopen(inputFileName, "r");
	FILE *output = fopen("decoded.txt", "w");

	//Deal with invalid input file name
	if (input == NULL){
		printf("ERROR READING FILE");
		return 0;
	}

	//Algorithm: Start at root of tree, read in file char by char. Traverse left for 0, right for 1. When I find a leaf node, i print the character code attached to that leaf node and return to root
	Node *currentRoot = tree;
	char ch;

	//Read char by char to EOF
	while((ch = fgetc(input)) != EOF){

		//Go right
		if (ch == '1'){
			currentRoot = currentRoot -> right;
		}

		//Go left
		else if (ch == '0'){
			currentRoot = currentRoot -> left;	
		}

		//Error handling if there is invalid character in file
		else{
			//Print error message
			printf("INVALID CHARACTER! EXITING\n");

			//Clean up output file. overwrite decoding with error message
			fclose(output);
			output = fopen("decoded.txt", "w");
			fprintf(output, "INVALID INPUT FILE");
			fclose(output);
			return 0;
		}

		//If we are at a leaf, print char and return to root.
		if (isLeaf(currentRoot)){
			printf("%c", currentRoot -> c);
			fprintf(output, "%c", currentRoot -> c);
			currentRoot = tree;
		}

	}
	printf("\n");
	fclose(input);
	fclose(output);

	//return 1 if decoding was succseful
	return 1;
	
}

/*This method takes in a plaintext input file, and writes a encoded version of the file. It also prints the Number of bits in the original, and compressed file and the compression ratio.*/
int encode(char *inputFileName, HashTab *dict){
	

	//Open Files
	FILE *input = fopen(inputFileName, "r");
	FILE *output = fopen("encoded.txt", "w");

	//Return error for invalid input file
	if (input == NULL){
		printf("ERROR READING FILE");
		return 0;
	}
	char ch;
	int osize = 0;

	//Read in file char by char
	while((ch = fgetc(input)) != EOF){

		//Char is 8 bits. Add that to original file for each char
		osize += 8;

		//Look for char in huffman dictionary. 
		List_element *l = hashFind(dict, ch);

		//Print if valid character
		if(l != NULL){
			fprintf(output, "%s", l -> code);
			printf("%s", l -> code);
		}

		//Print error messager and Return 0 otherwise
		else{
			printf("INVALID CHARACTER FOUND. EXITING");
			return 0;
		}

	}

	//This finds number of 1s and 0s in encoded file
	int esize = ftell(output);


	//Calculate compression ratio
	float compressionRatio = (float) esize / osize;


	//Print and close files
	printf("\n\n");
	printf("Original Size: %d bits\n", osize);
	printf("Encoded Size: %d bits\n", esize);
	printf("Compression Ratio: %f\n", compressionRatio);
	fclose(input);
	fclose(output);

	//return 1 if successful
	return 1;

}

/*Main method stores interface*/
int main(int argc, char **argv){

	//Intialize variables for interface
	char input[100];
	Node **tree = malloc(sizeof(Node*));
	HashTab *dict;
	bool built = false;
	char token1[100];
	char token2[100];
	token1[0] = '$';
	
	//read in from stdin until quit is input
	while(strcmp(token1, "quit")){
		printf("Please Input a Command: ");
		//Read in first word
		scanf("%s", token1);
		
		//Exit if word == quit
		if (!strcmp(token1, "quit")){
			break;
		}

		//dump if dictionary has been built, error message otherwise
		if (!strcmp(token1, "dump")){

			
			if(built){
				dumpDict(dict);
			}

			else{
				printf("NO DICTIONARY STORED. PLEASE IMPORT A FILE\n");		
			}
		}
		else{

			//read in second word of input
			scanf("%s", token2);

			//Attempt to build dict. Print error message if this fails. Set built flag to true if build suceeds
			if (!strcmp(token1, "import")){
				
				if (built){
					//Overwrite previous tree & dict 
					destroyTable(dict);	
					destroyTree(*tree);	
					dict = NULL;
				}

				dict = buildDictionary(token2, tree);
				if (dict == NULL){
					printf("INVALID IMPORT. PLEAST TRY AGAIN\n");
					built = false;
				}
				else{
					built = true;
				}
				
			}

			//Attempt to encode file. If dict not built yet or encode fails, print error flag.
			else if (!strcmp(token1, "encode")){
				if(built){
					if (!encode(token2, dict)){
						printf("INVALID ENCODE. PLEASE TRY AGAIN.\n");
					}
				}

				else{
					printf("NO DICTIONARY STORED. PLEASE IMPORT A FILE\n");		
				}
			}

			//Attempt to decode file. If dict not built yet or decode fails, print error flag.
			else if (!strcmp(token1, "decode")){
				if(built){
					if (!decode(token2, *tree)){
						printf("INVALID DECODE. PLEASE TRY AGAIN.\n");
					}
				}

				else{
					printf("NO DICTIONARY STORED. PLEASE IMPORT A FILE\n");		
				}
			}
			else{
				printf("INVALID INPUT\n");		
			}
		}
	}

	//If dict was built succesfully, clean up memory
	if (built){
		destroyTable(dict);	
		destroyTree(*tree);		
		free(tree);
	}

	
	

	return 0;
}