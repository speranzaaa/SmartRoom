package RoomService;

import RoomService.activities.ActivitiyLogger;
import RoomService.activities.PersistentActivityLogger;
import RoomService.dashboardServer.DashboardServer;

public class App {
	
	final static int PORT = 80;
	final static String ROOM_ACTIVITIES_LOG_PATH = "src/main/resources/roomActivities/activityLog.json";

    public static void main(String[] args) {
    	DashboardServer s = new DashboardServer(PORT);
    	s.start();
    	ActivitiyLogger activityLogger = new PersistentActivityLogger(ROOM_ACTIVITIES_LOG_PATH);
    	activityLogger.addNewActivityListener((activity)->s.updateClients());
    	
    	new Thread(()->{
    		while(true) {
    			activityLogger.logActivity(null, null);
    			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}).start();
    }
}
