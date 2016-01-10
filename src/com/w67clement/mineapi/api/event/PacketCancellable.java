package com.w67clement.mineapi.api.event;

public class PacketCancellable
{

	private boolean isCancelled;
	private boolean hasForceChanges = false;

	public boolean isCancelled()
	{
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled)
	{
		this.isCancelled = isCancelled;
	}

	public boolean hasForceChanges()
	{
		return this.hasForceChanges;
	}

	public void setForceChanges(boolean forceChanges)
	{
		this.hasForceChanges = forceChanges;
	}
}
