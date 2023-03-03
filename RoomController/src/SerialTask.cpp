#include "SerialTask.h"
#include <Arduino.h>
#include "Scheduler.h"
#include "SmartRoom.h"
#include "MsgService.h"
#include "Task.h"

SerialTask::SerialTask(int period, SmartRoom* room) {
  this->room = room;
  this->service = new MsgService();
  this->init(period);
}

void SerialTask::write(bool ledState, int servoOpening) {
  this->service->sendMsg(ledState, servoOpening);
}

void SerialTask::read() {
   while(Serial.available()) {
    char ch = (char)Serial.read();
    if (ch == '\n') {
      this-> service->currentMsg = new Msg(this->content);
      this->service->isMessageAvailable = true;
    } else {
      this->content += ch;
    }
  }
}

void SerialTask::tick() {
  this->read();
  if (this->service->isMessageAvailable) {
    this-> message = this->service-> receiveMsg();
    if(this-> message ["deviceName"] == "Lights") {
      this-> room -> setLedState(this-> message ["deviceValue"]);
    } else if (this-> message ["deviceName"] == "RollerBlinds") {
      this-> room -> setServoOpening(this-> message ["deviceValue"]);
    }
  }
}