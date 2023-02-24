#ifndef __SCHEDULER__
#define __SCHEDULER__

#include "Task.h"
#include "Utils.h"

class Scheduler {
  
  static bool BTReceiving;

  int period;
  int taskTot;
  Task* taskList[MAX_TASKS];

public:
  Scheduler(const unsigned long period);
  void init();
  virtual bool addTask(Task* task);
  virtual void schedule();
  static bool isBTReceiving();
  static void setBTReceiving(bool BTReceiving);

};

#endif