#ifndef __BLUETOOTHTASK__
#define __BLUETOOTHTASK__

#include "Task.h"
#include "SmartRoom.h"
#include "SoftwareSerial.h"


class BTTask : public Task {
  int rxPin;
  int txPin;
  SoftwareSerial* channel;
  SmartRoom* smartRoom;

public:
  BTTask(int rxPin, int txPin, SmartRoom* smartRoom);  
  void init(int period);  
  void tick();
};

#endif