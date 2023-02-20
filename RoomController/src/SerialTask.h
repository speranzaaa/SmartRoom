#ifndef __SERIALTASK_H__
#define __SERIALTASK_H__
#include "Task.h"
#include "MsgService.h"
#include "ArduinoJson.h"

class SerialTask : public Task {

public:
    SerialTask(unsigned long period);

protected: 
    void toExecute();

private:
    MsgService* service;
};

#endif