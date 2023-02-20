#ifndef TASK_LIST_H
#define TASK_LIST_H

#include "Task.h"
#include "TaskConsumer.hpp"

class TaskList {
public:
    virtual bool isEmpty() = 0;
    virtual int getSize() = 0;
    virtual void addTask(Task* task) = 0;  
    virtual void forEach(TaskConsumer* taskConsumer) = 0;
};

#endif