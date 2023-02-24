#include "BTTask.h"
#include <Arduino.h>
#include "Scheduler.h"


BTTask::BTTask(int rxPin, int txPin, SmartRoom* smartRoom) : 
  rxPin(rxPin), txPin(txPin), smartRoom(smartRoom) {}
  
void BTTask::init(int period){
  Task::init(period);
  channel = new SoftwareSerial(rxPin, txPin);
  channel->begin(9600);
}
  
void BTTask::tick(){
  if(channel->available()){
    
  }
  else{
    
  }
}