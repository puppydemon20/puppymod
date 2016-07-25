package com.puppydemon.puppymod.proxies;

import com.puppydemon.puppymod.Main;
import com.puppydemon.puppymod.blocks.PuppyBlockBase;
import com.puppydemon.puppymod.blocks.PuppyBlockBase.HarvestLevelEnum;
import com.puppydemon.puppymod.blocks.PuppyBlockBase.HarvestToolEnum;
import com.puppydemon.puppymod.blocks.PuppyBlocks;
import com.puppydemon.puppymod.items.PuppyItemBase;
import com.puppydemon.puppymod.items.PuppyItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void registerItemRenderer(Item item, int meta, String id) {
	}
	
	public void preInit(FMLPreInitializationEvent e)
	{
		PuppyItems.init();
		PuppyBlocks.init();
	}
	
	public void init(FMLInitializationEvent e)
	{
		GameRegistry.addShapelessRecipe(new ItemStack(Items.GUNPOWDER, 2), new Object[]{PuppyItems.potassium, PuppyItems.sulfur, Items.COAL});
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		
	}
}

