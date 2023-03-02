package RoomService;

import com.google.gson.Gson;
import RoomService.activities.ActivityImpl;
import RoomService.activities.ObservableActivityLogger;
import RoomService.activities.ObservableActivityLoggerWrapper;
import RoomService.activities.PersistentActivityLogger;
import RoomService.devices.actuators.LightLed;
import RoomService.devices.actuators.LightLedImpl;
import RoomService.dashboardServer.DashboardServer;
import RoomService.dashboardServer.SSEMessageImpl;

public class App {
	
	final static int PORT = 80;
	final static String ROOM_ACTIVITIES_LOG_PATH = "src/main/resources/roomActivities/activityLog.json";

    public static void main(String[] args) {
    	
    	//-----DASHBOARD_SERVER------
    	DashboardServer s = new DashboardServer(PORT);
    	s.start();
    	
    	//handle only dashboard controls triggered events
    	s.addControlObserver((obj)->{
    		System.out.println(obj.toString());
    	});
    	
    	//------ACTIVITY_LOGGER--------
    	ObservableActivityLogger activityLogger = new ObservableActivityLoggerWrapper(new PersistentActivityLogger(ROOM_ACTIVITIES_LOG_PATH));
    	//send a new "SSE" Vert.x event on the server event bus. Then all client handlers receive the event and send the SSE message
    	activityLogger.addActivitiesObserver((activity)->s.sendSSEMessage(
    			new SSEMessageImpl(
    					activity.getDevice().getName(), 
    					new Gson().toJson(activity))
    	));
    	
    	//-------LIGHTS_SUBGROUP------
    	LightLed light = new LightLedImpl("lights-subgroup");
    	//log new activity when lights status change
    	light.addStatusObserver((status)->activityLogger.logActivity(new ActivityImpl(light, status)));
    	
    		
		//thread for testing activities: every second the light is turned on/off.
    	new Thread(()->{
    		while(true) {
    			try {
    				Thread.sleep(1000);
    				light.turnOn();
					Thread.sleep(1000);
	    			light.turnOff();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}).start();
    }
}
