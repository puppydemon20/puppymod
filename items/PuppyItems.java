package com.puppydemon.puppymod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PuppyItems {

	public static PuppyItemBase potassium;
	public static PuppyItemBase sulfur;
	
	public static void init() {
		potassium = register(new PuppyItemBase("potassium").setCreativeTab(CreativeTabs.MATERIALS));
		sulfur = register(new PuppyItemBase("sulfur").setCreativeTab(CreativeTabs.MATERIALS));
	}

	private static <T extends Item> T register(T item) {
		GameRegistry.register(item);

		if (item instanceof PuppyItemBase) {
			((PuppyItemBase)item).registerItemModel();
		}

		return item;
	}

}