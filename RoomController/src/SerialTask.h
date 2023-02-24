#ifndef __SERIALTASK__
#define __SERIALTASK__

#include "Task.h"
#include "SmartRoom.h"

class SerialTask : public Task {
  SmartRoom* smartRoom;
  bool message;

public:
  SerialTask(SmartRoom* smartRoom);  
  void init(int period);  
  void tick();
};

#endif