#include "Scheduler.h"
#include <Arduino.h>
#include "Utils.h"

void Scheduler::init(unsigned long period) {
  this->period = period;
  taskTot = 0;
  tempo = millis();  
}

bool Scheduler::addTask(Task* task) {
  if (taskTot < MAX_TASKS-1) {
    taskList[taskTot] = task;
    taskTot++;
    return true;
  } else {
    return false; 
  }
}
  
void Scheduler::schedule() {   
  while (millis() - tempo < period) {}
    for (int i = 0; i < taskTot; i++) {
      if (taskList[i]->updateTime(period)) {
        taskList[i]->tick();
      }
    }
  tempo = millis();
}