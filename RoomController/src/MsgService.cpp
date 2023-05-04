#include "Arduino.h"
#include "MsgService.h"
#include "ArduinoJson.h"

void MsgService::init() {
    Serial.begin(9600);
    while (!Serial) {}
    this->content.reserve(256);
    this->content = "";
    this->currentMsg = NULL;
    this->messageAvailable = false;
}

void MsgService::readSerial() {
   while(Serial.available()) {
    char ch = (char)Serial.read();
    if (ch == '\n') {
      this->currentMsg = new Msg(this->content);
      this->messageAvailable = true;
    } else {
      this->content += ch;
    }
  }
}

void MsgService::sendMsg(bool ledState, int servoOpening) {
    DynamicJsonDocument doc(128);
    doc["Light"] = ledState;
    doc["Servo"] = servoOpening;
    serializeJson(doc, Serial);
    Serial.println();
}

void MsgService::sendMsg(String msg) {
    Serial.println(msg);
}

bool MsgService::isMessageAvailable() {
    return this->messageAvailable == true;
};

Msg* MsgService::receiveMsg() {
    if (this->isMessageAvailable()) {
        Msg* msg = this->currentMsg;
        this->messageAvailable = false;
        this->currentMsg = NULL;
        content = "";
        return msg;
    } else {
        return NULL;
    }
};