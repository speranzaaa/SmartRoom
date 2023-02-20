#include <stdlib.h>
#include "LinkedTaskList.hpp"

LinkedTaskList::LinkedTaskList() {
    this->first = NULL;
    this->last = NULL;
}

bool LinkedTaskList::isEmpty() {
    if(this->first == NULL) {
        return true;
    }
    return false;
}

int LinkedTaskList::getSize() {
    int size = 0;
    for(Node* node = this->first; node != NULL; node = node->next) {
        size++;
    }
    return size;
}

void LinkedTaskList::addTask(Task* task) {
    Node* node = (Node*)calloc(1, sizeof(Node));
    node->task = task;
    if(isEmpty()) {
        this->first = node;
        this->last = node;
    } else {
        this->last->next = node;
        this->last = node;
    }
}

void LinkedTaskList::forEach(TaskConsumer* taskConsumer) {
    for(Node* node = this->first; node != NULL; node = node->next) {
        taskConsumer->consume(node->task);
    }
}