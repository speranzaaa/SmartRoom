#ifndef __BLUETOOTHTASK__
#define __BLUETOOTHTASK__

#include "Task.h"
#include "SmartRoom.h"
#include "SoftwareSerial.h"
#include <Arduino.h>
#include "Scheduler.h"

extern bool BTReceiving;

class BTTask : public Task {
  int rxPin;
  int txPin;
  SoftwareSerial* channel;
  SmartRoom* smartRoom;

  int disconnectionTimer;
  int disconectionTimerExpiry;

public:
  BTTask(int rxPin, int txPin, SmartRoom* smartRoom);  
  void init(int period);  
  void tick();
};

#endif