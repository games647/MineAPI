package com.w67clement.mineapi.tab;

import com.w67clement.mineapi.nms.PacketSender;

public abstract class TabTitle<T> extends PacketSender<T>
{
	public TabTitle(T packet) {
        super(packet);
    }

	public abstract String getHeader();

	public abstract TabTitle setHeader(String header);

	public abstract String getFooter();

	public abstract TabTitle setFooter(String footer);

}

// End of ITabTitle interface