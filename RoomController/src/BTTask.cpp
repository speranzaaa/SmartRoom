#include "BTTask.h"

BTTask::BTTask(int rxPin, int txPin, SmartRoom* smartRoom) : 
  rxPin(rxPin), txPin(txPin), smartRoom(smartRoom) {}
  
void BTTask::init(int period){
  Task::init(period);
  channel = new SoftwareSerial(rxPin, txPin);
  channel->begin(9600);

  this->disconnectionTimer = disconectionTimerExpiry;
}
  
void BTTask::tick(){
  if(channel->available()){
    Scheduler::setBTReceiving(true);
    char msgChar = (char)channel->read();
    String msg = "";
    
    while(msgChar != '\n'){
      msg.concat(msgChar);
      msgChar = (char)channel->read();
    }

    if(msg == "on"){
      smartRoom->setLedState(true);
    }
    else if(msg == "off"){
      smartRoom->setLedState(false);
    }

    bool isNumber = true;
    for(int i = 0; i < msg.length(); i++){
      if(!isDigit(msg.charAt(i))){
        isNumber = false;
      }
    }
    if(isNumber){
      smartRoom->setServoOpening(msg.toInt());
    }
    
  }
  else{
    if(this->disconnectionTimer == 0){
      Scheduler::setBTReceiving(false);
    }
    else{
      this->disconnectionTimer--;
    }
  }
}