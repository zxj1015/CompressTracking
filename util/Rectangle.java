package util;

public class Rectangle {
	public Rectangle(int row, int col, int height, int width) {
		this.row = row;
		this.col = col;
		this.height = height;
		this.width = width;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	private int row;
	private int col;
	private int height;
	private int width;
	
}
