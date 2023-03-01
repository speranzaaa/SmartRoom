package RoomService;

import com.google.gson.Gson;
import RoomService.activities.ActivityImpl;
import RoomService.activities.LightStatus;
import RoomService.activities.ObservableActivityLogger;
import RoomService.activities.ObservableActivityLoggerImpl;
import RoomService.activities.PersistentActivityLogger;
import RoomService.devices.Status;
import RoomService.devices.actuators.Actuator;
import RoomService.dashboardServer.DashboardServer;
import RoomService.dashboardServer.SSEMessageImpl;

public class App {
	
	final static int PORT = 80;
	final static String ROOM_ACTIVITIES_LOG_PATH = "src/main/resources/roomActivities/activityLog.json";

    public static void main(String[] args) {
    	DashboardServer s = new DashboardServer(PORT);
    	s.start();
    	ObservableActivityLogger activityLogger = new ObservableActivityLoggerImpl(new PersistentActivityLogger(ROOM_ACTIVITIES_LOG_PATH));
    	
    	//send a new "SSE" Vert.x event on the server event bus. Then all client handlers receive the event and send the SSE message
    	activityLogger.addActivitiesObserver((activity)->s.sendSSEMessage(
    			new SSEMessageImpl(
    					activity.getDevice().getName(), 
    					new Gson().toJson(activity))
    	));
    	
    	//fake light implementation!!
    	Actuator light = new Actuator() {
    		
    		boolean on = true;
    				
    		@Override
    		public Status getCurrentStatus() {
    			//simulate on-off changing value every time the method is called
    			on = !on;
    			return new LightStatus(on);
    		}
    		
    		@Override
    		public String toString() {
    			return "lights";
    		}

			@Override
			public String getName() {
				return "lights-subgroup";
			}
    		
    	};
    		
		//thread for testing activities: every second the light is turned on/off.
    	new Thread(()->{
    		while(true) {
    			activityLogger.logActivity(new ActivityImpl(light, light.getCurrentStatus()));
    			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}).start();
    }
}
