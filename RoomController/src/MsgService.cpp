#include "Arduino.h"
#include "MsgService.h"
#include "ArduinoJson.h"

void MsgService::init() {
    Serial.begin(9600);
    Serial.setTimeout(1);
}

void MsgService::sendMsg(bool ledState, int servoOpening) {
    DynamicJsonDocument doc(128);
    doc["Light"] = ledState;
    doc["Servo"] = servoOpening;
    serializeJson(doc, Serial);
}

bool isMessageAvailable();

Msg* receiveMsg();