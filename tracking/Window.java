package tracking;

import util.*;
import java.util.ArrayList;

public class Window {
	public Window(Rectangle position, IntegralImage image, FeatureParameter featureParameter) {
		this.position = position;
		this.image = image;
		this.featureParameter = featureParameter;
	}
	
	public double[] calculateFeature() {
		ArrayList<ArrayList<Rectangle3D>> rects = featureParameter.getRectangles();
		double features[] = new double[rects.size()];
		for (int i = 0; i < rects.size(); ++i) {
			for (int j = 0; j < rects.get(i).size(); ++j) {
				//System.out.println(rects.get(i).size() + "======");
				Rectangle3D rect = rects.get(i).get(j);
				Rectangle3D newRect = new Rectangle3D(rect.getRow() + position.getRow(),
						rect.getCol() + position.getCol(),
						rect.getHeight(), rect.getWidth(), rect.getChannelId());
				features[i] += image.calculateFeature(newRect);
			}
		}
		return features;
	}
	
	private Rectangle position;
	private IntegralImage image;
	private final FeatureParameter featureParameter;
}
