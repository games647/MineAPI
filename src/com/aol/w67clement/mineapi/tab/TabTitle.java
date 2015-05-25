package com.aol.w67clement.mineapi.tab;

import com.aol.w67clement.mineapi.nms.PacketSender;

public interface TabTitle extends PacketSender {
	
	public String getHeader();
	
	public String getFooter();
	
	public TabTitle setHeader(String header);
	
	public TabTitle setFooter(String footer);

}

//End of ITabTitle interface