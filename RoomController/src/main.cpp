#include "Scheduler.h"
#include "RollerBlinds.h"
#include "Led.h"
#include "SmartRoom.h"
#include "BTTask.h"
#include "SerialTask.h"
#include "Utils.h"
#include "MsgService.h"

bool BTReceiving;

Scheduler scheduler;

void setup(){

  MsgService* service = new MsgService();
  service->init();

  RollerBlinds* servo = new RollerBlinds(SERVOMOTOR_PIN);
  Led* led = new Led(LED_PIN);
  SmartRoom* smartRoom = new SmartRoom(servo, led);
  
  SerialTask* serialTask = new SerialTask(smartRoom, service);
  serialTask->init(SERIAL_PERIOD);

  BTTask* bttask = new BTTask(RX_BT_PIN, TX_BT_PIN, service, smartRoom, 10);
  bttask->init(BT_PERIOD);

  scheduler.init(SCHEDULER_PERIOD);
  
  scheduler.addTask(bttask);

  scheduler.addTask(serialTask);
}

void loop(){
  scheduler.schedule();
}