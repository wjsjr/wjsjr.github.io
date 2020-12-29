#include <string.h>
#include "List.h"


/*

Initialize empty list

 */

void list_init(List* list){
    list -> head = NULL;
    list -> tail = NULL;
    list -> size = 0;
}

/*

Create list node from input key. Note that code is always initially set to NULL. Codes are added in dictionary methods

 */

List_element *createListNode(char key){
    List_element *newNode = malloc(sizeof(List_element));
    newNode -> next = NULL;
    newNode -> prev = NULL;
    newNode -> key = key; 
    newNode -> count = 1;
    newNode -> code = NULL;
    return newNode;
}

/*

Add node to end of list

 */

void list_append(List* list, char key){
    List_element *curr, *prev;
    prev = list_tail(list);
    curr = createListNode(key); 
    list -> tail = curr;

    if (list_head(list) == NULL){
        list -> head = curr;
        curr -> prev = NULL;
    }
    else{
        curr -> prev = prev;
        prev -> next = curr;
    }

    ++list_size(list);
}
/*

Find node in list by key. Used by hash find method

 */
List_element *list_find(List* list, char val){
  int index;
  List_element *curr = NULL;
    for (index=0, curr = list->head; curr != NULL; curr = curr->next, ++index) {
        if (val == curr -> key){
             return curr;
        }    
    }
   
    
  return NULL;
}

/*

Destroy list. Pop nodes off one by one and free them individually. 

 */

void list_destroy (List* list) {
  if(list != NULL){
    while (list -> size > 0){
        List_element *node = list_pop_left(list);
        if (node != NULL){
            if (node -> code != NULL){
                //Free node code if it has been set
                free(node -> code);
            }
            free(node);    
        }
      }
  }
  
}

//Pop element off front of list. Update list head if necessary.
List_element *list_pop_left(List *list) {
    List_element *oldHead;
    if (list_head(list) == NULL) {
        return NULL;
    }
    oldHead = list_head(list);
    if (list_next(oldHead) != NULL) {
        List_element *newHead = list -> head -> next;
        list_head(list) = newHead;
        list_prev(newHead) = NULL;
    }
    else {
        list_head(list) = NULL;
        if (list_size(list) == 1) {
            list_tail(list) = NULL;
        }
    }
    --list_size(list);
    return oldHead;


}




void printList (List* list){
    if (list != NULL){
        List_element *curr = list -> head;
        while (!list_is_tail(list, curr)){
            printf("%c(%d), Code: %s -> ", curr -> key, curr -> count, curr -> code);
            curr = curr -> next;
        }

        if (curr != NULL){
            printf("%c(%d), Code: %s", curr -> key, curr -> count, curr -> code);
        }    
    }
    else{
        printf("EMPTY LIST");
    }
    printf("\n");
    
}