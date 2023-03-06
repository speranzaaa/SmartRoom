#ifndef __SCHEDULER__
#define __SCHEDULER__

#include "Task.h"
#include "Utils.h"

extern bool BTReceiving;

class Scheduler {
  int period;
  int taskTot;
  Task* taskList[MAX_TASKS];

public:
  Scheduler(const unsigned long period);
  void init();
  virtual bool addTask(Task* task);
  virtual void schedule();
  bool isBTReceiving();
  void setBTReceiving(bool BTReceiving);

};

#endif