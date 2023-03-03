package RoomService.utils;

public class PortablePathBuilder {
	
	final static String SEP = System.getProperty("file.separator");
	private StringBuilder stringBuilder;
	
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
		path.replace("/", SEP);
		path.replace("\\", SEP);
		PortablePathBuilder builder = relative();
		builder.append(path);
		return builder;
	}
	
	public PortablePathBuilder append(String dirOrFile) {
		this.stringBuilder.append(SEP + dirOrFile);
		return this;
	}
	
	public String build() {
		return "";
	}

}
