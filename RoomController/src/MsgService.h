#ifndef __MSGSERVICE__
#define __MSGSERVICE__

#include "Arduino.h"
#include "ArduinoJson.h"

class Msg {
DynamicJsonDocument doc = DynamicJsonDocument(128); 
public:
    Msg(String content) {
        deserializeJson(doc, content);
    }
    DynamicJsonDocument getContent() {
        return this->doc;
    }
};

class MsgService {    
public:   
    void init();  
    void sendMsg(bool ledState, int servoOpening);
    bool isMessageAvailable();
    Msg* receiveMsg();
    Msg* currentMsg;
    bool messageAvailable;
};

#endif