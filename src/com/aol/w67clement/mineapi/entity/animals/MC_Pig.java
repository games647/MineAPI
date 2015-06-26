package com.aol.w67clement.mineapi.entity.animals;

import org.bukkit.entity.Pig;

import com.aol.w67clement.mineapi.entity.MC_EntityAnimals;

public interface MC_Pig extends MC_EntityAnimals {
	
	public boolean hasSaddle();
	
	public void setSaddle(boolean flag);
	
	public Object getMC_Handle();
	
	public Pig getHandle();

}
