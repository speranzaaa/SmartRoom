package RoomService.logic;

import RoomService.mqtt.SensorBoardData;

public interface LogicController {

	/**
	 * Updates actuators based on sensor board data.
	 * @param data the {@link SensorBoardData} object received from the sensor board.
	 */
	public void updateRoom(final SensorBoardData data);

}
