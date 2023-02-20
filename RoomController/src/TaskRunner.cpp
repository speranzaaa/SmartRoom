#include "TaskRunner.hpp"

void TaskRunner::consume(Task* task) {
    task->tick();
}