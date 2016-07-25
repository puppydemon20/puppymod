package com.puppydemon.puppymod.items;

import com.puppydemon.puppymod.Main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PuppyItemBase extends Item
{
	protected String name;
	
	public PuppyItemBase(String name)
	{
		this.name = name;
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
	}
	
	public void registerItemModel() {
		Main.proxy.registerItemRenderer(this, 0, name);
	}

	@Override
	public PuppyItemBase setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
}
