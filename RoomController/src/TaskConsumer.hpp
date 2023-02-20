#ifndef TASK_CONSUMER_H
#define TASK_CONSUMER_H

#include "Task.h"

class TaskConsumer {
public:
    virtual void consume(Task* task) = 0;
};

#endif