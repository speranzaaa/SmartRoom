#include "SerialTask.h"
#include <Arduino.h>
#include "Scheduler.h"
#include "SmartRoom.h"
#include "MsgService.h"
#include "Task.h"
#include "ArduinoJson.h"

SerialTask::SerialTask(SmartRoom* smartRoom) {
  this->room = smartRoom;
  this->service = new MsgService();
} 

void SerialTask::init(int period) {
  this->service->init();
  Task::init(period);
}

void SerialTask::write(bool ledState, int servoOpening) {
  this->service->sendMsg(ledState, servoOpening);
}


void SerialTask::tick() {
  this->service->readSerial();
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
  // This is for testing ?
  this->write(this->room->getLedState(), this->room->getServoOpening());
  delete msg;
}