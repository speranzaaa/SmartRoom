package RoomService.serial;

import java.util.concurrent.*;
import jssc.*;

public class SerialCommChannel implements CommChannel, SerialPortEventListener {
	
	private static String SERIAL_PORT = "/dev/ttyACM0";
	private static int BAUD_RATE = 9600;

	private final SerialPort serialPort;
	private final BlockingQueue<String> queue;
	private StringBuffer currentMsg = new StringBuffer("");
	
	public SerialCommChannel(String port, int rate) throws Exception {
		this.queue = new ArrayBlockingQueue<String>(100);
		this.serialPort = new SerialPort(port);
		this.serialPort.openPort();
		this.serialPort.setParams(rate, SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		this.serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
				| SerialPort.FLOWCONTROL_RTSCTS_OUT);
		this.serialPort.addEventListener(this);
	}
	
	public SerialCommChannel() throws Exception {
		this(SerialCommChannel.SERIAL_PORT, SerialCommChannel.BAUD_RATE);
	}
	
	@Override
	public void sendMsg(String msg) {
		char[] array = (msg + '\n').toCharArray();
		byte[] bytes = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			bytes[i] = (byte) array[i];
		}
		try {
			synchronized (this.serialPort) {
				this.serialPort.writeBytes(bytes);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String receiveMsg() throws InterruptedException {
		return this.queue.take();
	}

	@Override
	public boolean isMsgAvailable() {
		return !this.queue.isEmpty();
	}

	public void close() {
		try {
			if (this.serialPort != null) {
				this.serialPort.removeEventListener();
				this.serialPort.closePort();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void serialEvent(SerialPortEvent serialPortEvent) {
		if (serialPortEvent.isRXCHAR()) {
			try {
				String msg = this.serialPort.readString(serialPortEvent.getEventValue());
				msg = msg.replaceAll("\r", "");
				this.currentMsg.append(msg);
				boolean goAhead = true;
				while (goAhead) {
					String msg2 = this.currentMsg.toString();
					int index = msg2.indexOf("\n");
					if (index >= 0) {
						this.queue.put(msg2.substring(0, index));
						this.currentMsg = new StringBuffer("");
						if (index + 1 < msg2.length()) {
							this.currentMsg.append(msg2.substring(index + 1));
						}
					} else {
						goAhead = false;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Error receiving string from port: " + ex);
			}
		}
	}

}
