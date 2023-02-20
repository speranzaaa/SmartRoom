#include "SerialTask.h"
#include "Arduino.h"
#include "Config.h"

extern Status currentStatus;
extern double waterDistance;
extern bool isLightOn;

SerialTask::SerialTask(unsigned long period) : Task(period) {
    this->service = new MsgService();
    this->service->init();
}

void SerialTask::toExecute() {
    String status;
    switch (currentStatus) {
    case NORMAL:
        status = "normal";
        break;
    case PRE_ALARM:
        status = "pre-alarm";
        break;
    default:
        status = "alarm";
        break;
    }
    String light = isLightOn ? "ON" : "OFF";
    this->service->sendMsg(status + " " + light + " " + String(MAX_DIST - waterDistance));
}