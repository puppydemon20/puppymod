package com.puppydemon.puppymod.blocks;

import com.puppydemon.puppymod.blocks.PuppyBlockBase.HarvestLevelEnum;
import com.puppydemon.puppymod.blocks.PuppyBlockBase.HarvestToolEnum;
import com.puppydemon.puppymod.items.PuppyItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PuppyBlocks {

	public static PuppyBlockBase orePotassium;
	public static PuppyBlockBase oreSulfer;

	public static void init() {
		orePotassium = register(new PuppyBlockBase(Material.ROCK, "potassium_ore", 3.0F, 5.0F, HarvestToolEnum.PICKAXE, HarvestLevelEnum.IRON, CreativeTabs.BUILDING_BLOCKS, 1, 6, 15, 10, 32, 120, true, false, false).setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
		oreSulfer = register(new PuppyBlockBase(Material.ROCK, "sulfur_ore", 3.0F, 3.0F, HarvestToolEnum.PICKAXE, HarvestLevelEnum.IRON, CreativeTabs.BUILDING_BLOCKS, 1, 30, 35, 100, 0, 255, new Block[]{Blocks.STONE, Blocks.NETHERRACK}, new Block[]{Blocks.LAVA}, 1, true, true, false).setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
		
		orePotassium.setDrops(PuppyItems.potassium);
		oreSulfer.setDrops(PuppyItems.sulfur);
	}

	private static <T extends Block> T register(T block, ItemBlock itemBlock) {
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);

		if (block instanceof PuppyBlockBase) {
			((PuppyBlockBase)block).registerItemModel(itemBlock);
		}

		return block;
	}

	private static <T extends Block> T register(T block) {
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		return register(block, itemBlock);
	}

}