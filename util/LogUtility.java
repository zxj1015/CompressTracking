package util;

public class LogUtility {
	static public void PrintRectangle3D(Rectangle3D rectangle) {
		System.out.println("\n" +
						   "row:" + rectangle.getRow() + "   " + 
				           "col:" + rectangle.getCol() + "   " +
				           "height:" + rectangle.getHeight() + "   " +
				           "width:" + rectangle.getWidth() + "   " +
				           "channel:" + rectangle.getChannelId());
	}
	static public void PrintRectangle(Rectangle rectangle) {
		System.out.println("\n" +
						   "row:" + rectangle.getRow() + "   " + 
				           "col:" + rectangle.getCol() + "   " +
				           "height:" + rectangle.getHeight() + "   " +
				           "width:" + rectangle.getWidth());
	}

}
