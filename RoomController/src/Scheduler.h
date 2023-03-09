#ifndef __SCHEDULER__
#define __SCHEDULER__

#include "Task.h"
#include "Utils.h"

class Scheduler {
  unsigned long period;
  int taskTot;
  Task* taskList[MAX_TASKS];
  unsigned long tempo;

public:
  void init(unsigned long period);
  virtual bool addTask(Task* task);
  virtual void schedule();
};

#endif