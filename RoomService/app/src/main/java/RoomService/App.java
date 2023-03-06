package RoomService;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import RoomService.activities.ActivityImpl;
import RoomService.activities.ObservableActivityLogger;
import RoomService.activities.ObservableActivityLoggerWrapper;
import RoomService.activities.PersistentActivityLogger;
import RoomService.devices.Device;
import RoomService.devices.actuators.Light;
import RoomService.devices.actuators.LightImpl;
import RoomService.devices.actuators.RollerBlinds;
import RoomService.devices.actuators.RollerBlindsImpl;
import RoomService.devices.controls.DashboardControlsHandler;
import RoomService.logic.LogicControllerImpl;
import RoomService.mqtt.MQTTAgent;
import RoomService.utils.PortablePathBuilder;
import io.vertx.core.Vertx;
import RoomService.dashboardServer.DashboardServer;
import RoomService.dashboardServer.SSEMessageImpl;

public class App {
	
	final static int PORT = 80;
	final static String ROOM_ACTIVITIES_LOG_PATH = PortablePathBuilder
			.fromStringPath("src/main/resources/roomActivities/activityLog.json")
			.build();

    public static void main(String[] args) {
    	
    // ---------- MODEL ----------
    	
    	//Create devices
    	Map<String, Device> devices = new HashMap<>();
    	
    	//Create lights-subgroup
    	Light light = new LightImpl("lights-subgroup");
    	devices.put(light.getName(), light);
    	
    	//Create rollerblinds-subgroup
    	RollerBlinds rollerblinds = new RollerBlindsImpl("rollerblinds-subgroup");
    	devices.put(rollerblinds.getName(), rollerblinds);

	// ----- DASHBOARD SERVER -----
    	
    	DashboardServer s = new DashboardServer(PORT);
    	s.start();
	
    	// Update model on dashboard controls
    	s.addControlObserver(new DashboardControlsHandler(devices));
    	
	// ----- ACTIVITY LOGGER ------
    	
    	ObservableActivityLogger activityLogger = new ObservableActivityLoggerWrapper(new PersistentActivityLogger(ROOM_ACTIVITIES_LOG_PATH));
    	//send a new "SSE" Vert.x event on the server event bus. Then all client handlers receive the event and send the SSE message
    	activityLogger.addActivitiesObserver((activity)->s.sendSSEMessage(
    			new SSEMessageImpl(
    					activity.getDevice().getName(), 
    					new Gson().toJson(activity))
    	));
    	
    	//log new activities when lights status change
    	light.addStatusObserver((status)->activityLogger.logActivity(new ActivityImpl(light, status)));
    	//log new activities when rollerblinds status change
    	rollerblinds.addStatusObserver((status)->activityLogger.logActivity(new ActivityImpl(rollerblinds, status)));
    	
    // ------ ROOM LOGIC AND MQTT -------
    	LogicControllerImpl logicController = new LogicControllerImpl(devices);
    	Vertx vertx = Vertx.vertx();
    	MQTTAgent agent = new MQTTAgent(logicController);
		vertx.deployVerticle(agent);
    }
}
