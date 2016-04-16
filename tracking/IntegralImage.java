package tracking;

import java.awt.image.BufferedImage;

import util.*;

public class IntegralImage {
	public IntegralImage(BufferedImage inputImage) {
		int imageType = inputImage.getType();
		int height = inputImage.getHeight();
		int width = inputImage.getWidth();
		if (imageType == BufferedImage.TYPE_BYTE_GRAY || imageType == BufferedImage.TYPE_BYTE_BINARY) {
			data = new double[1][height][width];
			for (int i = 0; i < height; ++i) {
				for (int j = 0; j < width; ++j) {
					int[] temp = null;
					int[] pixel = inputImage.getRaster().getPixel(j, i, temp);
					data[0][i][j] = pixel[0];
				}
			}
		} else {
			/*data = new double[3][height][width];
			for (int i = 0; i < height; ++i) {
				for (int j = 0; j < width; ++j) {
					int value = inputImage.getRGB(j, i);
					data[0][i][j] = value % 256;
					data[1][i][j] = (value >> 8) % 256;
					data[1][i][j] = (value >> 16) % 256;
				}
			}*/
		}
		for (int index = 0; index < data.length; ++index) {
			for (int i = 0; i < height; ++i) {
				for (int j = 0; j < width; ++j) {
					double value1 = i - 1 >= 0 ? data[index][i - 1][j] : 0;
					double value2 = j - 1 >= 0 ? data[index][i][j - 1] : 0;
					double value3 = (i - 1 >= 0 && j - 1 >= 0) ? data[index][i - 1][j - 1] : 0; 
					data[index][i][j] += value1 + value2 - value3;
				}
			}
		}
	}
	
	public double calculateFeature(Rectangle3D rectangle) {
		//LogUtility.PrintRectangle3D(rectangle);
		int index = rectangle.getChannelId();
		int rowStart = rectangle.getRow() - 1;
		int colStart = rectangle.getCol() - 1;
		int rowEnd = rowStart + rectangle.getHeight();
		int colEnd = colStart + rectangle.getWidth();
		double value1 = rowStart >= 0 ? data[index][rowStart][colEnd] : 0;
		double value2 = colStart >= 0 ? data[index][rowEnd][colStart] : 0;
		double value3 = (rowStart >= 0 && colStart >= 0) ? data[index][rowStart][colStart] : 0;
		double res = data[index][rowEnd][colEnd] - value1 - value2 + value3;
		return res;
	}
	
	private double[][][] data;
}
