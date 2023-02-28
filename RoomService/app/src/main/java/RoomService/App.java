package RoomService;

import com.google.gson.Gson;
import RoomService.activities.ActivityLogger;
import RoomService.activities.LightStatus;
import RoomService.activities.PersistentActivityLogger;
import RoomService.activities.Status;
import RoomService.actuators.Device;
import RoomService.dashboardServer.DashboardServer;
import RoomService.dashboardServer.SSEMessageImpl;

public class App {
	
	final static int PORT = 80;
	final static String ROOM_ACTIVITIES_LOG_PATH = "src/main/resources/roomActivities/activityLog.json";

    public static void main(String[] args) {
    	DashboardServer s = new DashboardServer(PORT);
    	s.start();
    	ActivityLogger activityLogger = new PersistentActivityLogger(ROOM_ACTIVITIES_LOG_PATH);
    	
    	//send a new "SSE" Vert.x event on the server event bus. Then all client handlers receive the event and send the SSE message
    	activityLogger.addNewActivityListener((activity)->s.sendSSEMessage(
    			new SSEMessageImpl(
    					activity.getDevice().toString(), 
    					new Gson().toJson(activity))
    	));
    	
    	//fake light implementation!!
    	Device light = new Device() {
    		
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
    		
    	};
    		
		//thread for testing activities: every second the light is turned on/off.
    	new Thread(()->{
    		while(true) {
    			activityLogger.logActivity(light, light.getCurrentStatus());
    			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}).start();
    }
}
