#include "SerialTask.h"
#include <Arduino.h>
#include "Scheduler.h"

SerialTask::SerialTask(SmartRoom* smartRoom) : 
  smartRoom(smartRoom) {
    message = false;
  }
  void SerialTask::init(int period){
  Task::init(period);
  Serial.begin(9600);
}
  
void SerialTask::tick() { 
  
}