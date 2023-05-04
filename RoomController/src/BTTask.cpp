#include "BTTask.h"
#include "Scheduler.h"
#include "Task.h"
#include <ArduinoJson.h>
#include "MsgService.h"

BTTask::BTTask(int rxPin, int txPin, MsgService* service, SmartRoom* smartRoom, int disconnectionTimer) {
  this->txPin = txPin;
  this->rxPin = rxPin;
  this->service = service;
  this->smartRoom = smartRoom;
  this->disconnectionTimer = disconnectionTimer;
  this->currentDiscTimer = disconnectionTimer;
}
  
void BTTask::init(int period) {
  Task::init(period);
  channel = new SoftwareSerial(txPin, rxPin);
  channel->begin(9600);
}
  
void BTTask::tick() {
  if(channel->available()) {
    this->currentDiscTimer = this->disconnectionTimer;
    BTReceiving = true;
    char msgChar = (char)channel->read();
    String msg = "";
    
    while(msgChar != '\n') {
      msg.concat(msgChar);
      msgChar = (char)channel->read();
    }

    StaticJsonDocument<56> doc;
    deserializeJson(doc, msg);

    if(doc["Lights"] ==  true) {
      smartRoom->setLedState(true);
    } else if (doc["Lights"] == false) {
      smartRoom->setLedState(false);
    } 

    int servoOpening = doc["RollerBlinds"];
    if(servoOpening >= 0 && servoOpening <= 100) {
      smartRoom->setServoOpening(servoOpening);
    }

    service->sendMsg(smartRoom->getLedState(), smartRoom->getServoOpening());
  }
  else {
    if(this->currentDiscTimer == 0){
      BTReceiving = false;
    }
    else {
      this->currentDiscTimer--;
    }
  }
}