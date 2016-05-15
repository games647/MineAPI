package com.w67clement.mineapi.tileentity;

import com.w67clement.mineapi.chat.ChatComponent;

public interface TileEntitySign extends TileEntity {
	
	ChatComponent[] getLines();
	
	ChatComponent getLine(int index);
	
	boolean isEditable();
	
	void setEditable(boolean editable);
	
	void setLine(int index, ChatComponent text);

}
