#include "Scheduler.h"
#include "RollerBlinds.h"
#include "Led.h"
#include "SmartRoom.h"
#include "BTTask.h"
#include "SerialTask.h"
#include "Utils.h"

Scheduler scheduler(SCHEDULER_PERIOD);

bool BTReceiving;

void setup(){
  RollerBlinds* servo = new RollerBlinds(SERVOMOTOR_PIN);
  Led* led = new Led(LED_PIN);
  SmartRoom* smartRoom = new SmartRoom(servo, led);
  SerialTask* serialTask = new SerialTask(smartRoom);
  BTTask* bttask = new BTTask(RX_BT_PIN, TX_BT_PIN, smartRoom);

  scheduler.init();
  
  bttask->init(BT_PERIOD);
  scheduler.addTask(bttask);

  serialTask->init(SERIAL_PERIOD);
  scheduler.addTask(serialTask);
}

void loop(){
  scheduler.schedule();
}