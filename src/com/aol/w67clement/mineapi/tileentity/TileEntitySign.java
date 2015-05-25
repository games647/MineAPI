package com.aol.w67clement.mineapi.tileentity;

public interface TileEntitySign extends TileEntity {
	
	public String[] getLines();
	
	public String getLine(int index);
	
	public boolean isEditable();
	
	public void setEditable(boolean editable);
	
	public void setLine(int index, String text);

}
