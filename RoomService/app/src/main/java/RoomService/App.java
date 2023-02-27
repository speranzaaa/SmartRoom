/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package RoomService;

import RoomService.dashboardServer.DashboardServer;

public class App {
	
	final static int PORT = 80;

    public static void main(String[] args) {
    	DashboardServer s = new DashboardServer(PORT);
    	s.start();
    	new Thread(()->{
    		while(true) {
    			s.sendMsg();
    			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}).start();
    }
}
