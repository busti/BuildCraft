/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package buildcraft.builders.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import buildcraft.api.enums.EnumBlueprintType;
import buildcraft.api.properties.BuildCraftProperties;

import buildcraft.builders.BCBuildersGuis;
import buildcraft.builders.tile.TileBuilder;
import buildcraft.lib.block.BlockBCTile_Neptune;
import buildcraft.lib.block.IBlockWithFacing;

public class BlockBuilder extends BlockBCTile_Neptune implements IBlockWithFacing {
    public static final IProperty<EnumBlueprintType> BLUEPRINT_TYPE = BuildCraftProperties.BLUEPRINT_TYPE;

    public BlockBuilder(Material material, String id) {
        super(material, id);
        setDefaultState(getDefaultState().withProperty(BLUEPRINT_TYPE, EnumBlueprintType.NONE));
    }

    // BlockState

    @Override
    protected void addProperties(List<IProperty<?>> properties) {
        super.addProperties(properties);
        properties.add(BLUEPRINT_TYPE);
    }

    // Others

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileBuilder();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            BCBuildersGuis.BUILDER.openGUI(player, pos);
        }
        return true;
    }

    @Override
    public boolean canBeRotated(World world, BlockPos pos, IBlockState state, EnumFacing sideWrenched) {
        TileEntity tile = world.getTileEntity(pos);
        return !(tile instanceof TileBuilder) || ((TileBuilder) tile).getBuilder() == null;
    }
}