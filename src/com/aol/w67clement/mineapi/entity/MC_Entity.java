package com.aol.w67clement.mineapi.entity;

public interface MC_Entity {

	public int getEntityId();
	
	public void setCustomName(String customeName);
	
	public String getCustomName();
	
	public boolean hasCustomName();
	
	public void setCustomNameVisible(boolean visible);
	
	public boolean getCustomNameVisible();
	
}
