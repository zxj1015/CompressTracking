package tracking;


import util.Rectangle;

public class GlobalParameters {
	public static final String imageList = "/Users/zxj/Pictures/imageList.txt";
	public static final int channelNum = 1;
	public static final double learnRate = 0.85;
	public static final Rectangle[] initPostions = {new Rectangle(55, 120, 95, 75)};
	public static final int featureNum = 100;
	public static final int minRectNumPerFeature = 2;
	public static final int maxRectNumPerFeature = 4;
	public static final int minWidthOfRect = 3;
	public static final int minHeightOfRect = 3;
	public static final double posSampleRad = 3;
	public static final double searchRad = 15;
	public static final int negSampleNum = 50;
}
