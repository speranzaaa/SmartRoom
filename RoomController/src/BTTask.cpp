#include "BTTask.h"
#include "Scheduler.h"
#include "Task.h"


BTTask::BTTask(int rxPin, int txPin, SmartRoom* smartRoom, int disconnectionTimer) {
  this->txPin = txPin;
  this->rxPin = rxPin;
  this->smartRoom = smartRoom;
  this->disconnectionTimer = disconnectionTimer;
  this->currentDiscTimer = disconnectionTimer;
}
  
void BTTask::init(int period){
  Task::init(period);
  channel = new SoftwareSerial(rxPin, txPin);
  channel->begin(9600);
}
  
void BTTask::tick(){
  if(channel->available()){
    this->currentDiscTimer = this->disconnectionTimer;
    BTReceiving = true;
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
    for(unsigned int i = 0; i < msg.length(); i++){
      if(!isDigit(msg.charAt(i))){
        isNumber = false;
      }
    }
    if(isNumber){
      smartRoom->setServoOpening(msg.toInt());
    }
    
  }
  else{
    if(this->currentDiscTimer == 0){
      BTReceiving = false;
    }
    else{
      this->currentDiscTimer--;
    }
  }
}