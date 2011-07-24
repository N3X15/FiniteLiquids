// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import java.util.Random;

import MCP.ApiController;
import net.minecraft.src.*;

// Referenced classes of package net.minecraft.src:
//            BlockNWater_Still, mod_NWater, Material, World, 
//            Block, IBlockAccess

public class BlockNLava_Still extends BlockNWater_Still {

	protected BlockNLava_Still(ApiController api, int j, Material material) {
		super(api.getBlockID(BlockNLava_Still.class), j, material);
		blockIndexInTexture = Block.lavaStill.blockIndexInTexture;
		setHardness(100F);
		setLightValue(1.0F);
		setLightOpacity(255);
		setBlockName("nlava");
	}

	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
		return mod_NWater.texx[6];
	}

	public int getBlockTextureFromSide(int i) {
		return mod_NWater.texx[6];
	}

	public void randomDisplayTick(World world, int i, int j, int k,
			Random random) {
		if (blockMaterial == Material.lava
				&& world.getBlockMaterial(i, j + 1, k) == Material.air
				&& !world.isBlockOpaqueCube(i, j + 1, k)
				&& random.nextInt(100) == 0) {
			double d = (float) i + random.nextFloat();
			double d1 = (double) j + maxY;
			double d2 = (float) k + random.nextFloat();
			world.spawnParticle("lava", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}

	public float getSurfaceMull() {
		return 1.8F;
	}

	public void updateTick(World world, int i, int j, int k, Random random) {
		super.updateTick(world, i, j, k, random);
	}

	public int getMoving() {
		return mod_NWater.nlava.blockID;
	}
}
