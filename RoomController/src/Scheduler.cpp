#include "Scheduler.h"
#include "TimerOne.h"

volatile bool timerFlag;

void timerManager(void) {
  timerFlag = true;
}

Scheduler::Scheduler(const unsigned long period) : period(period) {}

void Scheduler::init() {
  timerFlag = false;
  Scheduler::BTReceiving = false;
  long period = 1000l*period;
  Timer1.initialize(period);
  Timer1.attachInterrupt(timerManager);
  taskTot = 0;
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
  while (!timerFlag) {}
  timerFlag = false;
  for (int i = 0; i < taskTot; i++) {
    if (taskList[i]->updateTime(period)) {
      taskList[i]->tick();
    }
  }
}

bool Scheduler::isBTReceiving(){
  return Scheduler::BTReceiving;
}

void Scheduler::setBTReceiving(bool state){
  Scheduler::BTReceiving = state;
}