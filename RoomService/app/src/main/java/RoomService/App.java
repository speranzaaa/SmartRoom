package RoomService;

import com.google.gson.Gson;
import RoomService.activities.ObservableActivityLogger;
import RoomService.activities.ObservableActivityLoggerWrapper;
import RoomService.activities.PersistentActivityLogger;
import RoomService.devices.actuators.Light;
import RoomService.devices.actuators.LightImpl;
import RoomService.devices.actuators.RollerBlinds;
import RoomService.devices.actuators.RollerBlindsImpl;
import RoomService.devices.controls.DashboardControlHandler;
import RoomService.logic.LogicControllerImpl;
import RoomService.mqtt.MQTTAgent;
import RoomService.room.Room;
import RoomService.room.RoomImpl;
import RoomService.utils.PortablePathBuilder;
import io.vertx.core.Vertx;
import RoomService.dashboardServer.DashboardServer;
import RoomService.dashboardServer.SSEMessageImpl;

public class App {
	
	final static int PORT = 80;
	final static String ROOM_ACTIVITIES_LOG_PATH = PortablePathBuilder
			.fromStringPath("src/main/resources/roomActivities/activityLog.json")
			.build();
	
	//devices of the room
	final static Light lightsSubgroup = new LightImpl("lights-subgroup");
	final static RollerBlinds rollerblindsSubgroup = new RollerBlindsImpl("rollerblinds-subgroup");

    public static void main(String[] args) {
    	
	// ----- DASHBOARD SERVER -----
    	
    	DashboardServer s = new DashboardServer(PORT);
    	s.start();
    	
	// ----- ACTIVITY LOGGER ------
    	
    	ObservableActivityLogger activityLogger = new ObservableActivityLoggerWrapper(new PersistentActivityLogger(ROOM_ACTIVITIES_LOG_PATH));
    	//send a new "SSE" Vert.x event on the server event bus. Then all client handlers receive the event and send the SSE message
    	activityLogger.addActivitiesObserver((activity)->s.sendSSEMessage(
    			new SSEMessageImpl(
    					activity.getDevice().getName(), 
    					new Gson().toJson(activity))
    	));
    	
    // ---------- ROOM MODEL ----------
    	
    	Room room = new RoomImpl(activityLogger);
    	room.addActuator(lightsSubgroup);
    	room.addActuator(rollerblindsSubgroup);
    	
    	// Update model on dashboard controls
    	s.addControlObserver(new DashboardControlHandler(room));
    	
    // ------ ROOM LOGIC -- MQTT CLIENT -------
    	LogicControllerImpl logicController = new LogicControllerImpl(room);
    	Vertx vertx = Vertx.vertx();
    	MQTTAgent agent = new MQTTAgent(logicController);
		vertx.deployVerticle(agent);
    }
}
