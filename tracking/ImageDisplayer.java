package tracking;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.LogUtility; 

public class ImageDisplayer extends Frame {
	public ImageDisplayer() { 
		image = null;
		rects = null;
		this.setSize(300,300); 
		this.setVisible(true); 
		 this.addWindowListener(
	            new WindowAdapter() {
	                public void windowClosing(WindowEvent e) {
	                    System.exit(0);
	                }
	            }
	        );
	}
	public void setParameter(BufferedImage image, util.Rectangle[] rects) {
		this.image = image;
		this.rects = rects;
	}
	public void paint(Graphics g) { 
		if (image == null) {
			return ;
		}
		this.setSize(image.getWidth(null), image.getHeight(null)); 
		g.drawImage(image, 0, 0, this);
		g.setColor(Color.RED);
		//System.out.println("\n==================aaa" + rects.length);
		for (int j = 0; j < rects.length; ++j) {
			//LogUtility.PrintRectangle(rects[j]);
			g.drawRect(rects[j].getCol(), rects[j].getRow(), 
					   rects[j].getWidth(), rects[j].getHeight()); 
		}
	}
	private BufferedImage image; 
	private util.Rectangle[] rects;
}
