package tracking;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import util.LogUtility;
import util.Rectangle;
import util.Utility;

public class Tracker {
	public void Tracking() {
		currentPositions = GlobalParameters.initPostions;
		int positionNum = currentPositions.length;
		featureParameters = new FeatureParameter[GlobalParameters.initPostions.length];
		for (int i = 0; i < positionNum; ++i) {
			featureParameters[i] = new FeatureParameter(GlobalParameters.featureNum,
					                                    currentPositions[i].getWidth(),
					                                    currentPositions[i].getHeight(),
					                                    GlobalParameters.minWidthOfRect,
					                                    GlobalParameters.minHeightOfRect);
		}
		
		imageDisplayer = new ImageDisplayer();
				
		Model[] posModel = new Model[positionNum];
		Model[] negModel = new Model[positionNum];
		for (int i = 0; i < positionNum; ++i) {
			posModel[i] = new Model(GlobalParameters.featureNum, GlobalParameters.learnRate);
			negModel[i] = new Model(GlobalParameters.featureNum, GlobalParameters.learnRate);
		}	
		
		imageList = Utility.readImageList(GlobalParameters.imageList);
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(imageList.get(0)));
			imageHeight = image.getHeight();
			imageWidth = image.getWidth();
			IntegralImage integralImage = new IntegralImage(image);
			for (int i = 0; i < positionNum; ++i) {
				ArrayList<Rectangle> positiveRect = 
						samplePosition(currentPositions[i], 0, GlobalParameters.posSampleRad, 
								       GlobalParameters.negSampleNum);
				ArrayList<Rectangle> negativeRect = 
						samplePosition(currentPositions[i], GlobalParameters.posSampleRad,
                                       GlobalParameters.searchRad, GlobalParameters.negSampleNum);
				//LogUtility.PrintRectangle(currentPositions[i]);
				//System.out.println(positiveRect.size() + "===" + negativeRect.size());
				double[] mean = new double[GlobalParameters.featureNum];
				double[] variance = new double[GlobalParameters.featureNum];
				for (int j = 0; j < positiveRect.size(); ++j) {
					Window window = new Window(positiveRect.get(j), integralImage, featureParameters[i]);
					double[] data = window.calculateFeature();
					for (int k = 0; k < data.length; ++k) {
						mean[k] += data[k];
						variance[k] += data[k] * data[k];
					}
				}
				//System.out.println(positiveRect.size() + "========");
				for (int j = 0; j < GlobalParameters.featureNum; ++j) {
					//System.out.print(mean[j] + " ");
					mean[j] /= positiveRect.size();
					variance[j] /= positiveRect.size();
					variance[j] -= mean[j] * mean[j];
				}
				//System.out.println();
				posModel[i].updateModel(mean, variance);
				System.out.println("Pos model " + i);
				posModel[i].printModel();
				mean = new double[GlobalParameters.featureNum];
				variance = new double[GlobalParameters.featureNum];
				for (int j = 0; j < negativeRect.size(); ++j) {
					Window window = new Window(negativeRect.get(j), integralImage, featureParameters[i]);
					double[] data = window.calculateFeature();
					for (int k = 0; k < data.length; ++k) {
						mean[k] += data[k];
						variance[k] += data[k] * data[k];
					}
				}
				for (int j = 0; j < GlobalParameters.featureNum; ++j) {
					mean[j] /= positiveRect.size();
					variance[j] /= positiveRect.size();
					variance[j] -= mean[j] * mean[j];
				}
				negModel[i].updateModel(mean, variance);
				System.out.println("Neg model " + i);
				negModel[i].printModel();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("\n" + imageHeight + "==========" + imageWidth + "\n");
		for (int index = 0; index < imageList.size(); ++index) {
			//System.out.println("\n====" + index + "\n");
			BufferedImage image = null;
			try {
				image = ImageIO.read(new FileInputStream(imageList.get(index)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IntegralImage integralImage = new IntegralImage(image);
			for (int i = 0; i < positionNum; ++i) {
				// get position
				ArrayList<Rectangle> rects = 
						samplePosition(currentPositions[i], 0, GlobalParameters.searchRad, 10000000);				
				int id = 0;
				double maxScore = -1000000000000000f;
				for (int j = 0; j < rects.size(); ++j) {
					Window window = new Window(rects.get(j), integralImage, featureParameters[i]);
					double[] data = window.calculateFeature();
					double posScore = posModel[i].calculateScore(data);
					double negScore = negModel[i].calculateScore(data);
					System.out.println(rects.get(j).getRow() + "==" + rects.get(j).getCol());
					for (int k = 0; k < data.length; ++k) {
						System.out.print(data[k] + " ");
					}
					System.out.println();
					//System.out.println(rects.get(j).getRow() + "==" + rects.get(j).getCol() + "==" +
					//		           posScore + "==" + negScore);
					double score = posScore - negScore;
					if (score > maxScore) {
						maxScore = score;
						id = j;
					}
				}
				currentPositions[i] = rects.get(id);
				// update model
				ArrayList<Rectangle> positiveRect = 
						samplePosition(currentPositions[i], 0, GlobalParameters.posSampleRad, 
								       GlobalParameters.negSampleNum);
				ArrayList<Rectangle> negativeRect = 
						samplePosition(currentPositions[i], GlobalParameters.posSampleRad,
                                       GlobalParameters.searchRad, GlobalParameters.negSampleNum);
				double[] mean = new double[GlobalParameters.featureNum];
				double[] variance = new double[GlobalParameters.featureNum];
				for (int j = 0; j < positiveRect.size(); ++j) {
					Window window = new Window(positiveRect.get(j), integralImage, featureParameters[i]);
					double[] data = window.calculateFeature();
					for (int k = 0; k < data.length; ++k) {
						mean[k] += data[k];
						variance[k] += data[k] * data[k];
					}
				}
				for (int j = 0; j < GlobalParameters.featureNum; ++j) {
					mean[j] /= positiveRect.size();
					variance[j] /= positiveRect.size();
					variance[j] -= mean[j] * mean[j];
				}
				posModel[i].updateModel(mean, variance);
				mean = new double[GlobalParameters.featureNum];
				variance = new double[GlobalParameters.featureNum];
				for (int j = 0; j < negativeRect.size(); ++j) {
					Window window = new Window(negativeRect.get(j), integralImage, featureParameters[i]);
					double[] data = window.calculateFeature();
					for (int k = 0; k < data.length; ++k) {
						mean[k] += data[k];
						variance[k] += data[k] * data[k];
					}
				}
				for (int j = 0; j < GlobalParameters.featureNum; ++j) {
					mean[j] /= positiveRect.size();
					variance[j] /= positiveRect.size();
					variance[j] -= mean[j] * mean[j];
				}
				negModel[i].updateModel(mean, variance);
			}
			imageDisplayer.setParameter(image, currentPositions);
			imageDisplayer.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public ArrayList<Rectangle> samplePosition(Rectangle position, double minRadius, double maxRadius, int nums) {
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		int row = position.getRow();
		int col = position.getCol();
		double centerRow = row + position.getHeight() / 2.0;
		double centerCol = col + position.getWidth() / 2.0;
		//System.out.println(centerRow + "====" + centerCol);
		//LogUtility.PrintRectangle(position);
		Random random = new Random();
		for (int i = (int) Math.max(0, row - maxRadius - 1); 
			 i < row + maxRadius + 1 && i + position.getHeight() < imageHeight; ++i) {
			for (int j = (int) Math.max(0, col - maxRadius - 1); 
				 j < col + maxRadius + 1 && j + position.getWidth() < imageWidth; ++j) {
				//System.out.println(i + "=aaaa=" + j);
				double sampleCenterRow = i + position.getHeight() / 2.0;
				double sampleCenterCol = j + position.getWidth() / 2.0;
				double dist = Math.sqrt((sampleCenterRow - centerRow) * (sampleCenterRow - centerRow) +
										(sampleCenterCol - centerCol) * (sampleCenterCol - centerCol));
				if (dist >= minRadius && dist <= maxRadius) {
					Rectangle temp = new Rectangle(i, j, position.getHeight(), position.getWidth());
					if (rectangles.size() < nums) {
						rectangles.add(temp);
					} else {
						int index = random.nextInt(nums);
						rectangles.set(index, temp);
					}
				}
			}
		}
		return rectangles;
	}
	
	private Rectangle[] currentPositions;
	private FeatureParameter[] featureParameters;
	private ImageDisplayer imageDisplayer;
	private ArrayList<String> imageList;
	private int imageWidth = 0;
	private int imageHeight = 0;
}
