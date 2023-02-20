#ifndef __SIMPLE_SCHEDULER_H
#define __SIMPLE_SCHEDULER_H

#include "Scheduler.hpp"
#include "LinkedTaskList.hpp"

class SimpleScheduler : public Scheduler {
public:
    SimpleScheduler();
    void schedule();
    void addTask(Task* task);
private: 
    TaskList* taskList;
    unsigned long period;
    unsigned long lastTick;
    void waitTick();
    unsigned long gcd(unsigned long a, unsigned long b);
};

#endif