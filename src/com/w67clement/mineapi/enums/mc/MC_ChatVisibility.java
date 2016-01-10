package com.w67clement.mineapi.enums.mc;

public enum MC_ChatVisibility
{

	FULL(0, "options.chat.visibility.full"),
	SYSTEM(1, "options.chat.visibility.system"),
	HIDDEN(2, "options.chat.visibility.hidden");

	private static MC_ChatVisibility[] a;
	private int id;

	private MC_ChatVisibility(int id, String s) {
		this.id = id;
	}

	public int getId()
	{
		return this.id;
	}

	public static MC_ChatVisibility getById(int id)
	{
		if (id <= a.length) return a[id];
		return null;
	}

	static
	{
		a = new MC_ChatVisibility[values().length];
		int j = a.length;
		for (int i = 0; i < j; i++)
		{
			a[i] = values()[i];
		}
	}
}
