package RoomService.activities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import RoomService.activities.gsonUtils.InterfaceSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import RoomService.devices.Status;
import RoomService.devices.actuators.Actuator;

public class PersistentActivityLogger extends VolatileActivityLogger {
	
	private final File logFile;
	private final Type activityListType = new TypeToken<ArrayList<ActivityImpl>>() {}.getType();
	private final Gson gson = new GsonBuilder()
			.registerTypeAdapter(Status.class, InterfaceSerializer.interfaceSerializer(LightStatus.class))
			.create();
	
	public PersistentActivityLogger(String logJsonFilePath) {
		this.logFile = new File(logJsonFilePath);
		if(!this.logFile.getName().endsWith(".json")) {
			throw new IllegalArgumentException("The jsonFilePath must point to a .json file");
		}
		if(!logFile.exists()) {
			try {
				this.logFile.createNewFile();
			} catch (IOException e) {
				throw new IllegalArgumentException("The file does not exists and a new file cannot be created: " + e.getStackTrace());
			}
		}
		this.log = readLogFile();
	}
	
	private List<Activity> readLogFile() {
		List<Activity> log = new ArrayList<>();
		if(!(this.logFile.length() == 0)) {
			try (FileReader reader = new FileReader(this.logFile)) {
				log = this.gson.fromJson(reader, this.activityListType);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return log;
	}

	private void writeLogFile(List<Activity> log) {
		try (FileWriter writer = new FileWriter(this.logFile, false)) {
			writer.write(this.gson.toJson(log, this.activityListType));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void logActivity(Actuator device, Status newStatus) {
		super.logActivity(device, newStatus);
		this.writeLogFile(this.log);
	}

}
