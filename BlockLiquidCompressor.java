// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import MCP.ApiController;
import MCP.api.BlockBase;
import net.minecraft.src.*;
// Referenced classes of package net.minecraft.src:
//            Block, Material, IBlockAccess, mod_NWater, 
//            World

public class BlockLiquidCompressor extends BlockBase {

	protected BlockLiquidCompressor(ApiController api) {
		super(api.getBlockID(BlockLiquidCompressor.class), Material.rock);
		setTickOnLoad(true);
		float f = 0.0F;
		setBlockBounds(f, 0.0F, f, 1.0F - f, 1.0F, 1.0F - f);
		setHardness(0.3F);
		setLightOpacity(3);
		setStepSound(Block.soundGlassFootstep);
		setBlockName("liquidcompressor");
	}

	@Override
    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
		int i1 = iblockaccess.getBlockMetadata(i, j, k);
		if (i1 == 7) {
			return mod_NWater.texx[20];
		}
		if (i1 == 15) {
			return mod_NWater.texx[22];
		}
		if (i1 > 7) {
			return mod_NWater.texx[21];
		} else {
			return mod_NWater.texx[19];
		}
	}

	@Override
    public int getBlockTextureFromSide(int i) {
		blockIndexInTexture = mod_NWater.texx[19];
		return mod_NWater.texx[19];
	}

	@Override
    public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
    public boolean isOpaqueCube() {
		return false;
	}

	@Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return true;
	}
}
