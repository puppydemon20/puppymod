package com.puppydemon.puppymod.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class CustomBlockWorldGenMinable extends WorldGenerator
{
	private final IBlockState oreBlock;
	private final int numberOfBlocks;
	private final Block[] genIn;
	private final Block[] genNear;
	private final int genDistance;
	
	public CustomBlockWorldGenMinable(IBlockState block, int veinSize, Block[] generateIn, Block[] generateNear, int generateDistance)
	{
		this.oreBlock = block;
		this.numberOfBlocks = veinSize;
		this.genIn = generateIn;
		this.genNear = generateNear;
		this.genDistance = generateDistance;
	}
	
	public CustomBlockWorldGenMinable(IBlockState block, int veinSize)
	{
		this(block, veinSize, new Block[]{Blocks.STONE});
	}
	
	public CustomBlockWorldGenMinable(IBlockState block, int veinSize, Block[] generateIn)
	{
		this(block, veinSize, generateIn, null, 1);
	}
	
	private boolean isReplaceable(World worldIn, BlockPos blockpos, Block[] genIn)
	{
		for(Block block : genIn)
		{
			if(worldIn.getBlockState(blockpos).getBlock() == block)
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean isNear(BlockPos blockpos, Block[] near, int distance, World worldIn)
	{
		for (int x = (blockpos.getX()-distance); x <= (blockpos.getX()+distance); ++x)
		{
			for (int y = (blockpos.getY()-distance); y <= (blockpos.getY()+distance); ++y)
			{
				for (int z = (blockpos.getZ()-distance); z <= (blockpos.getZ()+distance); ++z)
				{
					BlockPos Nearblockpos = new BlockPos (x, y, z);
					
					for(Block nearblock : near)
					{
						if(worldIn.getBlockState(Nearblockpos).getBlock() == nearblock && Nearblockpos != blockpos)
						{
							System.out.println("BLock is close enough at distance: " + distance + " BLock will be spawned at: " + blockpos);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        float f = rand.nextFloat() * (float)Math.PI;
        double d0 = (double)((float)(position.getX() + 8) + MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d1 = (double)((float)(position.getX() + 8) - MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d2 = (double)((float)(position.getZ() + 8) + MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d3 = (double)((float)(position.getZ() + 8) - MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d4 = (double)(position.getY() + rand.nextInt(3) - 2);
        double d5 = (double)(position.getY() + rand.nextInt(3) - 2);

        for (int i = 0; i < this.numberOfBlocks; ++i)
        {
            float f1 = (float)i / (float)this.numberOfBlocks;
            double d6 = d0 + (d1 - d0) * (double)f1;
            double d7 = d4 + (d5 - d4) * (double)f1;
            double d8 = d2 + (d3 - d2) * (double)f1;
            double d9 = rand.nextDouble() * (double)this.numberOfBlocks / 16.0D;
            double d10 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
            int j = MathHelper.floor_double(d6 - d10 / 2.0D);
            int k = MathHelper.floor_double(d7 - d11 / 2.0D);
            int l = MathHelper.floor_double(d8 - d10 / 2.0D);
            int i1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int l1 = j; l1 <= i1; ++l1)
            {
                double d12 = ((double)l1 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                {
                    for (int i2 = k; i2 <= j1; ++i2)
                    {
                        double d13 = ((double)i2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for (int j2 = l; j2 <= k1; ++j2)
                            {
                                double d14 = ((double)j2 + 0.5D - d8) / (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
                                {
                                    BlockPos blockpos = new BlockPos(l1, i2, j2);
                                    IBlockState state = worldIn.getBlockState(blockpos);
                                    
                                    if (this.isReplaceable(worldIn, blockpos, this.genIn))
                                    {
                                    	if (this.genNear == null)
                                    	{
                                    		worldIn.setBlockState(blockpos, this.oreBlock, 2);
                                    		System.out.println("Ore Successfully spawned " +blockpos);
                                    	} 
                                    	else
                                    	{
                                    		if(this.isNear(blockpos, this.genNear, this.genDistance, worldIn))
                                    		{
                                    			worldIn.setBlockState(blockpos, this.oreBlock, 2);
                                    		}
                                    	}
                                    	
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}