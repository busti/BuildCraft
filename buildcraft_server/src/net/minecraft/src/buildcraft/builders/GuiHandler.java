package net.minecraft.src.buildcraft.builders;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.buildcraft.core.GuiIds;
import net.minecraft.src.forge.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {

		if(!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);

		switch(ID) {

		case GuiIds.BUILDER:
			if(!(tile instanceof TileBuilder))
				return null;
			return new CraftingBuilder(player.inventory, (TileBuilder)tile);

		case GuiIds.FILLER:
			if(!(tile instanceof TileFiller))
				return null;
			return new CraftingFiller(player.inventory, (TileFiller)tile);
			
		case GuiIds.TEMPLATE:
		if(!(tile instanceof TileTemplate))
			return null;
		return new CraftingTemplateRoot(player.inventory, (TileTemplate)tile);
		

		default:
			return null;
		}
	}

}
