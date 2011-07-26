// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import MCP.ApiController;
import MCP.api.BlockBase;

// Referenced classes of package net.minecraft.src:
//            Block, Material, IBlockAccess, mod_NWater, 
//            World

public class BlockLiquidSensor extends BlockBase {

	protected BlockLiquidSensor(ApiController api) {
		super(api.getBlockID(BlockLiquidSensor.class), Material.rock);
		setTickOnLoad(true);
		float f = 0.0F;
		setBlockBounds(f, 0.0F, f, 1.0F - f, 1.0F, 1.0F - f);
		setHardness(0.3F);
		setLightOpacity(3);
		setStepSound(Block.soundGlassFootstep);
		setBlockName("liquidsensor");
	}

	@Override
    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
		int i1 = iblockaccess.getBlockMetadata(i, j, k);
		if (i1 == 1) {
			return mod_NWater.texx[10];
		} else {
			return mod_NWater.texx[9];
		}
	}

	@Override
    public int getBlockTextureFromSide(int i) {
		blockIndexInTexture = mod_NWater.texx[10];
		return mod_NWater.texx[10];
	}

	@Override
    public int tickRate() {
		return 20;
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

	@Override
    public void onBlockAdded(World world, int i, int j, int k) {
		setStateIfMobInteractsWithPlate(world, i, j, k);
	}

	@Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		setStateIfMobInteractsWithPlate(world, i, j, k);
	}

	@Override
    public void updateTick(World world, int i, int j, int k, Random random) {
		if (world.multiplayerWorld) {
			return;
		}
		if (world.getBlockMetadata(i, j, k) == 0) {
			return;
		} else {
			setStateIfMobInteractsWithPlate(world, i, j, k);
			return;
		}
	}

	public boolean checkLiq(World world, int i, int j, int k) {
		if (mod_NWater.isWater(world, i - 1, j, k)) {
			return true;
		}
		if (mod_NWater.isWater(world, i + 1, j, k)) {
			return true;
		}
		if (mod_NWater.isWater(world, i, j - 1, k)) {
			return true;
		}
		if (mod_NWater.isWater(world, i, j + 1, k)) {
			return true;
		}
		if (mod_NWater.isWater(world, i, j, k - 1)) {
			return true;
		}
		return mod_NWater.isWater(world, i, j, k + 1);
	}

	private void setStateIfMobInteractsWithPlate(World world, int i, int j,
			int k) {
		boolean flag = world.getBlockMetadata(i, j, k) == 1;
		boolean flag1 = false;
		flag1 = checkLiq(world, i, j, k);
		if (flag1 && !flag) {
			world.setBlockMetadataWithNotify(i, j, k, 1);
			world.notifyBlocksOfNeighborChange(i, j, k, blockID);
			world.notifyBlocksOfNeighborChange(i - 1, j - 1, k, blockID);
			world.notifyBlocksOfNeighborChange(i + 1, j - 1, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j - 1, k - 1, blockID);
			world.notifyBlocksOfNeighborChange(i, j - 1, k + 1, blockID);
			world.markBlocksDirty(i, j, k, i, j, k);
			world.playSoundEffect(i + 0.5D,
					j + 0.10000000000000001D, k + 0.5D,
					"random.click", 0.3F, 0.6F);
		}
		if (!flag1 && flag) {
			world.setBlockMetadataWithNotify(i, j, k, 0);
			world.notifyBlocksOfNeighborChange(i, j, k, blockID);
			world.notifyBlocksOfNeighborChange(i - 1, j - 1, k, blockID);
			world.notifyBlocksOfNeighborChange(i + 1, j - 1, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j - 1, k - 1, blockID);
			world.notifyBlocksOfNeighborChange(i, j - 1, k + 1, blockID);
			world.markBlocksDirty(i, j, k, i, j, k);
			world.playSoundEffect(i + 0.5D,
					j + 0.10000000000000001D, k + 0.5D,
					"random.click", 0.3F, 0.5F);
		}
		if (flag1) {
			world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
		}
	}

	@Override
    public void onBlockRemoval(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		if (l > 0) {
			world.notifyBlocksOfNeighborChange(i, j, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
		}
		super.onBlockRemoval(world, i, j, k);
	}

	@Override
    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
		return iblockaccess.getBlockMetadata(i, j, k) > 0;
	}

	@Override
    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k,
			int l) {
		return world.getBlockMetadata(i, j, k) != 0;
	}

	@Override
    public boolean canProvidePower() {
		return true;
	}
}
