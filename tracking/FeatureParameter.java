package tracking;

import java.util.ArrayList;
import java.util.Random;

import util.*;

public class FeatureParameter {
	public FeatureParameter(int featureDimension, int imageWidth, int imageHeight, 
			                int minWidth, int minHeight) {
		assert(imageWidth >= minWidth && imageHeight >= minHeight);
		featureVector = new ArrayList<ArrayList<Rectangle3D>>(featureDimension);
		Random random = new Random();
		for (int i = 0; i < featureDimension; ++i) {
			ArrayList<Rectangle3D> currentDimension = new ArrayList<Rectangle3D>();
			int rectNum = random.nextInt(GlobalParameters.maxRectNumPerFeature - 
					                     GlobalParameters.minRectNumPerFeature + 1) + 
					      GlobalParameters.minRectNumPerFeature;
			for (int j = 0; j < rectNum; ++j) {
				int width = random.nextInt(imageWidth - minWidth) + minWidth;
				int height = random.nextInt(imageHeight - minHeight) + minHeight;
				int row = random.nextInt(imageHeight - height);
				int col = random.nextInt(imageWidth - width);
				int channelId = random.nextInt(GlobalParameters.channelNum);
				Rectangle3D rect = new Rectangle3D(row, col, height, width, channelId);
				//LogUtility.PrintRectangle3D(rect);
				currentDimension.add(rect);
			}
			featureVector.add(currentDimension);
		}
	//	System.out.println("Hello word" + featureVector.size());
	}
	
	public  ArrayList<ArrayList<Rectangle3D> > getRectangles() {
		return featureVector;
	}
	
	public int getTotalNumofRect() {
		int totalNum = 0;
		for (int i = 0; i < featureVector.size(); ++i) {
			totalNum += featureVector.get(i).size();
		}
		return totalNum;
	}
	
	private ArrayList<ArrayList<Rectangle3D>> featureVector;
}
