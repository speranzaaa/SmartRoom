#ifndef __SERIALTASK__
#define __SERIALTASK__

#include "Task.h"
#include "SmartRoom.h"
#include "MsgService.h"

class SerialTask : public Task {
  
  public: 
    SerialTask(int period, SmartRoom* room);  

  protected: 
    void write(bool ledState, int servoOpening);
    void read();
  
  private:
    SmartRoom* room;
    MsgService* service;
}; 

#endif