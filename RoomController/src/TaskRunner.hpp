#ifndef TASK_RUNNER_H
#define TASK_RUNNER_H

#include "TaskConsumer.hpp"

class TaskRunner : public TaskConsumer {
public:
    void consume(Task* task);
};

#endif