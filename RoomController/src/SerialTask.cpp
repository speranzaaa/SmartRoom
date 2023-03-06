#include "SerialTask.h"
#include <Arduino.h>
#include "Scheduler.h"
#include "SmartRoom.h"
#include "MsgService.h"
#include "Task.h"

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

void SerialTask::read() {
   while(Serial.available()) {
    char ch = (char)Serial.read();
    if (ch == '\n') {
      this-> service->currentMsg = new Msg(this->content);
      this->service->messageAvailable = true;
    } else {
      this->content += ch;
    }
  }
}

void SerialTask::tick() {
  this->read();
  if (this->service->isMessageAvailable()) {
    this-> message = this->service->receiveMsg();
    if(this-> message->getContent() ["deviceName"] == "Lights") {
      this-> room -> setLedState(this->message->getContent()["deviceValue"]);
    } else if (this-> message->getContent()  ["deviceName"] == "RollerBlinds") {
      this-> room -> setServoOpening(this->message->getContent()["deviceValue"]);
    }
  }
}