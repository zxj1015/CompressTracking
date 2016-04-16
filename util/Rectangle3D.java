package util;

public class Rectangle3D {
	public Rectangle3D(int row, int col, int height, int width, int channelId) {
		this.row = row;
		this.col = col;
		this.height = height;
		this.width = width;
		this.channelId = channelId;
	}
	public Rectangle3D(Rectangle rect, int channelId) {
		this.row = rect.getRow();
		this.col = rect.getCol();
		this.width = rect.getWidth();
		this.height = rect.getHeight();
		this.channelId = channelId;
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
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
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
	private int channelId;
}
