#include "SimpleScheduler.hpp"
#include "Arduino.h"
#include "TaskRunner.hpp"

SimpleScheduler::SimpleScheduler() {
    this->taskList = new LinkedTaskList();
}

void SimpleScheduler::schedule() {
    waitTick();
    this->taskList->forEach(new TaskRunner());
}

void SimpleScheduler::addTask(Task* task) {
    this->taskList->addTask(task);
    if(taskList->getSize() == 1) {
        this->period = task->getPeriod(); 
    } else {
        this->period = gcd(this->period, task->getPeriod());
    }
}

void SimpleScheduler::waitTick() {
    while((millis()-this->lastTick)<this->period) {}
    this->lastTick = millis();
    return;
}

unsigned long SimpleScheduler::gcd(unsigned long a, unsigned long b){
    return b == 0 ? a :gcd(b, a%b);
}

