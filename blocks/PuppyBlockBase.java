package com.puppydemon.puppymod.blocks;

import java.util.Random;

import com.puppydemon.puppymod.Main;
import com.puppydemon.puppymod.gen.CustomBlockGen;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PuppyBlockBase extends Block
{
	protected String name;
	private Item dropOnHarvest;
	private int dropamountmax;
	private int maxharvestEXP = 2;
	
	public PuppyBlockBase(Material material, String name, float hardness, float resistance, HarvestToolEnum harvesttool, HarvestLevelEnum harvestlevel, CreativeTabs creativetab, int dropamountmax, int minVienSize, int maxVienSize, int genChance, int minGenY, int maxGenY, Block[] generateIn, Block[] generateNear, int generateDistance, boolean generateInOverworld, boolean generateInNether, boolean generateInEnder)
	{
		super(material);
		this.name = name;
		this.dropamountmax = dropamountmax;
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setSoundType(SoundType.STONE);
		this.setHarvestLevel(harvesttool, harvestlevel);
		this.dropOnHarvest = Item.getItemFromBlock(this);
		GameRegistry.registerWorldGenerator(new CustomBlockGen(this, minVienSize, maxVienSize, genChance, minGenY, maxGenY, generateIn, generateNear, generateDistance, generateInOverworld, generateInNether, generateInEnder), 1);
	}
	
	
	public PuppyBlockBase(Material material, String name, float hardness, float resistance, HarvestToolEnum harvesttool, HarvestLevelEnum harvestlevel, CreativeTabs creativetab, int dropamountmax, int minVienSize, int maxVienSize, int genChance, int minGenY, int maxGenY, Block[] generateIn, boolean generateInOverworld, boolean generateInNether, boolean generateInEnder)
	{
		this(material, name, hardness, resistance, harvesttool, harvestlevel, creativetab, dropamountmax, minVienSize, maxVienSize, genChance, minGenY, maxGenY, generateIn, null, 1, generateInOverworld, generateInNether, generateInEnder);
	}
	
	public PuppyBlockBase(Material material, String name, float hardness, float resistance, HarvestToolEnum harvesttool, HarvestLevelEnum harvestlevel, CreativeTabs creativetab, int dropamountmax, int minVienSize, int maxVienSize, int genChance, int minGenY, int maxGenY, boolean generateInOverworld, boolean generateInNether, boolean generateInEnder)
	{
		this(material, name, hardness, resistance, harvesttool, harvestlevel, creativetab, dropamountmax, minVienSize, maxVienSize, genChance, minGenY, maxGenY, new Block[]{Blocks.STONE}, generateInOverworld, generateInNether, generateInEnder);
	}
	
	public PuppyBlockBase(Material material, String name, float hardness, float resistance, HarvestToolEnum harvesttool, HarvestLevelEnum harvestlevel, CreativeTabs creativetab, int dropamountmax)
	{
		super(material);
		
		this.dropamountmax = dropamountmax;
		this.setUnlocalizedName(name);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setSoundType(SoundType.STONE);
		this.setHarvestLevel(harvesttool, harvestlevel);
		this.setCreativeTab(creativetab);
		this.dropOnHarvest = Item.getItemFromBlock(this);
	}
	
	public void registerItemModel(ItemBlock itemBlock) {
		Main.proxy.registerItemRenderer(itemBlock, 0, name);
	}

	@Override
	public PuppyBlockBase setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
	
	
	public void setHarvestLevel(HarvestToolEnum harvesttool, HarvestLevelEnum harvestlevel)
	{
		int level;
		String tool;
		
		switch(harvesttool)
		{
			case PICKAXE:
				tool = "pickaxe";
				break;
			case SHOVEL:
				tool = "shovel";
				break;
			case AXE:
				tool = "axe";
			default:
				tool = "pickaxe";
		}
		switch(harvestlevel)
		{
			case WOOD:
				level = 0;
				break;
			case STONE:
				level = 1;
				break;
			case IRON:
				level = 2;
				break;
			case GOLD:
				level = 0;
				break;
			default:
				level = 0;
			
		}
		super.setHarvestLevel(tool, level);
	}
	
	public static enum HarvestToolEnum
	{
		PICKAXE,
		SHOVEL,
		AXE;
	}
	public static enum HarvestLevelEnum
	{
		WOOD,
		STONE,
		IRON,
		DIAMOND,
		GOLD;
	}
	
	public void setMaxHarvestEXP (int expAmount)
	{
		maxharvestEXP = expAmount;
	}
	
	public void setDrops (Item drops)
	{
		this.dropOnHarvest = drops;
	}
	
	public void setDrops (Block drops)
	{
		this.dropOnHarvest = Item.getItemFromBlock(drops);
	}
	
	public void setDropMaxAmount(int dropamount)
	{
		this.dropamountmax = dropamount;
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return this.dropOnHarvest;
	}
	
	public int guantityDropped(Random random)
	{
		int amount = random.nextInt(this.dropamountmax)+1;
		return amount;
	}
	
	public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
	{
		IBlockState state = world.getBlockState(pos);
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
		{
			return MathHelper.getRandomIntegerInRange(rand, 0, maxharvestEXP);
		}
		return 0;
	}
}
