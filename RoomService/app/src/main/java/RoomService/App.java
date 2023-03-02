package RoomService;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import RoomService.activities.ActivityImpl;
import RoomService.activities.ObservableActivityLogger;
import RoomService.activities.ObservableActivityLoggerWrapper;
import RoomService.activities.PersistentActivityLogger;
import RoomService.devices.Device;
import RoomService.devices.actuators.LightLed;
import RoomService.devices.actuators.LightLedImpl;
import RoomService.devices.controls.ModelController;
import RoomService.dashboardServer.DashboardServer;
import RoomService.dashboardServer.SSEMessageImpl;

public class App {
	
	final static int PORT = 80;
	final static String ROOM_ACTIVITIES_LOG_PATH = "src/main/resources/roomActivities/activityLog.json";

    public static void main(String[] args) {
    	
    	//----- DASHBOARD SERVER ------
    	DashboardServer s = new DashboardServer(PORT);
    	s.start();
    	
    	
    	//------ ACTIVITY LOGGER --------
    	ObservableActivityLogger activityLogger = new ObservableActivityLoggerWrapper(new PersistentActivityLogger(ROOM_ACTIVITIES_LOG_PATH));
    	//send a new "SSE" Vert.x event on the server event bus. Then all client handlers receive the event and send the SSE message
    	activityLogger.addActivitiesObserver((activity)->s.sendSSEMessage(
    			new SSEMessageImpl(
    					activity.getDevice().getName(), 
    					new Gson().toJson(activity))
    	));
    	
    	//DEVICES
    	Map<String, Device> devices = new HashMap<>();
    	
    	//------- LIGHTS SUBGROUP ------
    	LightLed light = new LightLedImpl("lights-subgroup");
    	devices.put(light.getName(), light);
    	//log new activity when lights status change
    	light.addStatusObserver((status)->activityLogger.logActivity(new ActivityImpl(light, status)));
    	
    	//------ REACT TO DASHBOARD CONTROLS -------
    	//control model from DashBoard controls
    	s.addControlObserver(new ModelController(devices));
    }
}
