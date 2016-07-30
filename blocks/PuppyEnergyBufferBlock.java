package com.puppydemon.puppymod.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.puppydemon.puppymod.energy.BlockEnergyStorage;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PuppyEnergyBufferBlock extends BlockEnergyStorage {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>(){
		public boolean apply(@Nullable EnumFacing face) {
			return face != EnumFacing.DOWN && face != EnumFacing.UP;
		}});

	public PuppyEnergyBufferBlock (Material material, String name, float hardness, float resistance, HarvestToolEnum harvesttool, HarvestLevelEnum harvestlevel, CreativeTabs creativetab, int capacity) {
		this(material, name, hardness, resistance, harvesttool, harvestlevel, creativetab, capacity, capacity, capacity);
	}
	
	public PuppyEnergyBufferBlock (Material material, String name, float hardness, float resistance, HarvestToolEnum harvesttool, HarvestLevelEnum harvestlevel, CreativeTabs creativetab, int capacity, int maxTransfer) {
		this(material, name, hardness, resistance, harvesttool, harvestlevel, creativetab, capacity, maxTransfer, maxTransfer);
	}
		
	public PuppyEnergyBufferBlock (Material material, String name, float hardness, float resistance, HarvestToolEnum harvesttool, HarvestLevelEnum harvestlevel, CreativeTabs creativetab, int capacity, int maxReceive, int maxExtract) {
		super(material, name, hardness, resistance, harvesttool, harvestlevel, creativetab, capacity, maxReceive, maxExtract);
        setUnlocalizedName(name);
        setRegistryName(name);
		this.name = name;
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }
    
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
    {
        list.add(this.getEnergyStored() +"/" + this.getMaxEnergyStored() +" RF");
        if (this.getMaxReceive()  != 0 && this.getMaxExtract() != 0)
        list.add("In: " + this.getMaxReceive() + " RF Out: " + this.getMaxExtract() + " RF");
    }


    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
    	if (facing != null && facing != EnumFacing.DOWN && facing != EnumFacing.UP)
    		return this.getDefaultState().withProperty(FACING, facing);
    	else
    		return this.getDefaultState();
        }
    
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta) {
            case 1:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
                break;
            case 3:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
                break;
            case 4:
            default:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
        }

        return iblockstate;
    }
    
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        switch ((EnumFacing)state.getValue(FACING))
        {
            case EAST:
                i = i | 1;
                break;
            case WEST:
                i = i | 2;
                break;
            case SOUTH:
                i = i | 3;
                break;
            case NORTH:
            case DOWN:
            case UP:
            default:
                i = i | 4;
        }

        return i;
    }
    
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }
    
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
    
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

}
