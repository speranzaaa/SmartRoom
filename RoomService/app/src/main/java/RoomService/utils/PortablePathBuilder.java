package RoomService.utils;

public class PortablePathBuilder {
	
	final static String SEP = System.getProperty("file.separator");
	private StringBuilder stringBuilder;
	private boolean firstDir = true;
	
	private PortablePathBuilder() {
		this.stringBuilder = new StringBuilder();
	};
	
	public static PortablePathBuilder relative() {
		return new PortablePathBuilder();
	}
	
	public static PortablePathBuilder absolute() {
		PortablePathBuilder builder = relative();
		builder.append(SEP);
		return builder;
	}
	
	public static PortablePathBuilder fromStringPath(String path) {
		String portablePath = path.replace("/", SEP);
		portablePath = path.replace("\\", SEP);
		PortablePathBuilder builder = relative();
		builder.append(portablePath);
		return builder;
	}
	
	public PortablePathBuilder append(String dirOrFile) {
		if(!this.firstDir) {
			this.stringBuilder.append(SEP);
		} else {
			this.firstDir = false;
		}
		this.stringBuilder.append(dirOrFile);
		return this;
	}
	
	public String build() {
		return stringBuilder.toString();
	}

}
