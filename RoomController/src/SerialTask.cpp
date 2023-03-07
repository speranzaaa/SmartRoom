#include "SerialTask.h"
#include <Arduino.h>
#include "Scheduler.h"
#include "SmartRoom.h"
#include "MsgService.h"
#include "Task.h"
#include "ArduinoJson.h"

extern MsgService* msgService;

SerialTask::SerialTask(SmartRoom* smartRoom) {
  this->room = smartRoom;
  this->service = msgService;
} 

void SerialTask::init(int period) {
  this->service->init();
  Task::init(period);
}

void SerialTask::write(bool ledState, int servoOpening) {
  this->service->sendMsg(ledState, servoOpening);
}


void SerialTask::tick() {
  Msg* msg = this->service->receiveMsg();
  if (msg) {
    String content = msg->getContent();
    StaticJsonDocument<56> doc;
    deserializeJson(doc, content);
    if(doc["deviceName"] == "Lights") {
      this->room->setLedState(doc["deviceValue"]);
    } else if (doc["deviceName"] == "RollerBlinds") {
      this->room->setServoOpening(doc["deviceValue"]);
    }
  }
  this->write(room->getLedState(), room->getServoOpening());
  delete msg;
}