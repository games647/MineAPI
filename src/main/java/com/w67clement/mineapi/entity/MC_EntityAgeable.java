package com.w67clement.mineapi.entity;

public interface MC_EntityAgeable extends MC_EntityCreature {
	
	public int getAge();
	
	public void setAge(int age);
	
	public boolean isBaby();
	
	public boolean isAdult();
	
	public void setAgeLocked(boolean lock);
	
	public boolean ageIsLocked();
	
	public void setSize(float f, float f1);

}
