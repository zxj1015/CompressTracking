package tracking;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.*;

public class Main {

	public static void main(String[] args) {
		Tracker tracker = new Tracker();
		tracker.Tracking();
		//TestImage();
	}
	
	public static void TestImage() {
		String name = "/Users/zxj/Pictures/data/img00458.png";
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(name));
	        int iw = image.getWidth();  
	        int ih = image.getHeight(); 
	        System.out.println("图像宽度为："+iw+";图像高度为："+ih);
	        int[] pixel=new int[iw*ih];
	        PixelGrabber pg=new PixelGrabber(image,0,0,iw,ih,pixel,0,iw);
	        pg.grabPixels();
			for (int i = 5; i < 10; ++i) {
				for (int j = 5; j < 10; ++j) {
					int[] temp = null;
					int[] value1 = image.getRaster().getPixel(i, j, temp);
					System.out.println(value1[0]);
					//int value2 = pixel[j * iw + i] & 0X00ffffff;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
