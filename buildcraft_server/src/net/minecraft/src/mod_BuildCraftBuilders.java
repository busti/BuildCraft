/** 
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 * 
 * BuildCraft is distributed under the terms of the Minecraft Mod Public 
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package net.minecraft.src;

public class mod_BuildCraftBuilders extends BaseModMp {

	public static mod_BuildCraftBuilders instance;
	
	public mod_BuildCraftBuilders () {
		instance = this;
	}
	
	@Override
	public void modsLoaded () {
		super.modsLoaded();
		
		BuildCraftBuilders.initialize();
	}
	
	@Override
	public String getVersion() {
		return "2.2.13";
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

}
