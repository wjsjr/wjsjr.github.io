#include <stdio.h>
#include <stdlib.h>

typedef struct List_element_{
    char key;
    int count;
    char *code;
    struct List_element_ *next;
    struct List_element_ *prev;
} List_element;

typedef struct List_{
    List_element *head;
    List_element *tail;
    unsigned int size;
} List;


void list_init(List* list);
void list_append(List* list, char key);
List_element *list_pop_left(List *list);
List_element *list_find(List* list, char val);
void list_destroy(List* list);
void printList (List* list);

#define list_size(list) ((list) ->size)
#define list_head(list) ((list) ->head)
#define list_tail(list) ((list) -> tail)
#define list_is_head(list, element) ((element)==(list) -> head)
#define list_is_tail(list, element) ((element)==(list) -> tail)
#define list_key(element) ((element)->key)
#define list_next(element) ((element)->next)
#define list_prev(element) ((element)->prev)