package com.w67clement.mineapi.tab;

import com.w67clement.mineapi.nms.PacketSender;

public abstract class TabTitle extends PacketSender
{
	protected String header;
	protected String footer;

	public TabTitle(String header, String footer) {
		this.setHeader(header);
		this.setFooter(footer);
	}

	public String getHeader()
	{
		return this.header;
	}

	public TabTitle setHeader(String header)
	{
		if (header == null)
		{
			this.header = "";
		}
		else
			this.header = header;
		return this;
	}

	public String getFooter()
	{
		return this.footer;
	}

	public TabTitle setFooter(String footer)
	{
		if (footer == null)
		{
			this.footer = "";
		}
		else
			this.footer = footer;
		return this;
	}

}

// End of ITabTitle interface