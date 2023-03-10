#ifndef __BLUETOOTHTASK__
#define __BLUETOOTHTASK__

#include "Task.h"
#include "SmartRoom.h"
#include "SoftwareSerial.h"
#include <Arduino.h>
#include "Scheduler.h"
#include "MsgService.h"

extern bool BTReceiving;

class BTTask : public Task {
  int rxPin;
  int txPin;
  SoftwareSerial* channel;
  SmartRoom* smartRoom;
  MsgService* service;

  int disconnectionTimer;
  int currentDiscTimer;

public:
  BTTask(int rxPin, int txPin, MsgService* service, SmartRoom* smartRoom, int disconnectionTimer);  
  void init(int period);  
  void tick();
};

#endif