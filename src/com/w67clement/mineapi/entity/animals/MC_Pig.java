package com.w67clement.mineapi.entity.animals;

import com.w67clement.mineapi.entity.MC_EntityAnimals;
import org.bukkit.entity.Pig;

public interface MC_Pig extends MC_EntityAnimals
{

	public boolean hasSaddle();

	public void setSaddle(boolean flag);

	public Object getMC_Handle();

	public Pig getHandle();

}
