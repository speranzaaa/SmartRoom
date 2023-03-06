#ifndef __TASK__
#define __TASK__

class Task {
  int currentPeriod;
  int timeElapsed;
  bool running;

  
public:
  virtual void init(int period){
    currentPeriod = period;  
    timeElapsed = 0;
    running = true;
  }

  virtual void tick() = 0;

  bool updateTime(int period){
    timeElapsed += period;
    if (timeElapsed >= currentPeriod){
      timeElapsed = 0;
      return true;
    } else {
      return false; 
    }
  }

  bool isRunning(){
    return running;
  }

  void setRunning(bool running){
    this->running = running;
  }
  
};

#endif