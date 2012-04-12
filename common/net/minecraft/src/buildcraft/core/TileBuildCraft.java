/** 
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 * 
 * BuildCraft is distributed under the terms of the Minecraft Mod Public 
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package net.minecraft.src.buildcraft.core;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_BuildCraftCore;
import net.minecraft.src.buildcraft.api.IPowerReceptor;
import net.minecraft.src.buildcraft.core.network.ISynchronizedTile;
import net.minecraft.src.buildcraft.core.network.PacketPayload;
import net.minecraft.src.buildcraft.core.network.PacketTileUpdate;
import net.minecraft.src.buildcraft.core.network.PacketUpdate;
import net.minecraft.src.buildcraft.core.network.TilePacketWrapper;

public abstract class TileBuildCraft extends TileEntity implements ISynchronizedTile {

	@SuppressWarnings("rawtypes")
	private static Map<Class, TilePacketWrapper> updateWrappers = new HashMap<Class, TilePacketWrapper>();
	@SuppressWarnings("rawtypes")
	private static Map<Class, TilePacketWrapper> descriptionWrappers = new HashMap<Class, TilePacketWrapper>();
	
	private TilePacketWrapper descriptionPacket;
	private TilePacketWrapper updatePacket;
	
	private boolean init = false;
	
	public TileBuildCraft () {
		if (!updateWrappers.containsKey(this.getClass())) {
			updateWrappers.put(this.getClass(), new TilePacketWrapper(this.getClass ()));
		}
		
		if (!descriptionWrappers.containsKey(this.getClass())) {
			descriptionWrappers.put(this.getClass(), new TilePacketWrapper(this.getClass ()));
		}
		
		updatePacket = updateWrappers.get(this.getClass ());
		descriptionPacket = descriptionWrappers.get(this.getClass ());

	}
	
	@Override
	public void updateEntity () {
		if (!init) {
			initialize();
			init = true;
		}
		
		if (this instanceof IPowerReceptor) {
			IPowerReceptor receptor = ((IPowerReceptor) this);
			
			receptor.getPowerProvider().update(receptor);
		}
	}
	
	public void initialize () {
		Utils.handleBufferedDescription(this);
	}
	
	public void destroy () {
		
	}
	
	public void sendNetworkUpdate() {
		if (this instanceof ISynchronizedTile) {
			CoreProxy.sendToPlayers(
					((ISynchronizedTile) this).getUpdatePacket(), xCoord,
					yCoord, zCoord, 50, mod_BuildCraftCore.instance);
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		return new PacketTileUpdate(this).getPacket();
    }
	
	public PacketPayload getPacketPayload() {
		return updatePacket.toPayload(this);
	}

	@Override
	public Packet getUpdatePacket() {
		return new PacketTileUpdate(this).getPacket();
	}
    
	@Override
	public void handleDescriptionPacket (PacketUpdate packet) {
		descriptionPacket.fromPayload(this, packet.payload);
	}
	
	@Override
	public void handleUpdatePacket (PacketUpdate packet) {
		updatePacket.fromPayload(this, packet.payload);
	}
	
	@Override
	public void postPacketHandling (PacketUpdate packet) {
	
	}
	
}
