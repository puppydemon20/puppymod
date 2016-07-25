package com.puppydemon.puppymod;

import com.puppydemon.puppymod.blocks.PuppyBlockBase;
import com.puppydemon.puppymod.items.PuppyItemBase;
import com.puppydemon.puppymod.proxies.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION, dependencies = "required-after:FML")
public class Main
{
	public static final String MODID = "puppymod";
	public static final String MODNAME = "Puppy's Mod";
	public static final String VERSION = "1.10.2";
	public static final String PUPPY = "com.puppydemon.puppymod.proxies";
	
	
	@Instance(value = MODID)
	public static Main instance;
	
	@SidedProxy(modId=MODID, clientSide=PUPPY+".ClientProxy", serverSide=PUPPY+".ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		proxy.preInit(e);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		proxy.init(e);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		proxy.postInit(e);
	}
}
