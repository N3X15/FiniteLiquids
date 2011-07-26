// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import java.util.Random;

import MCP.ApiController;
import MCP.api.BlockBase;
import net.minecraft.src.*;

// Referenced classes of package net.minecraft.src:
//            Block, mod_NWater, Material, IBlockAccess, 
//            World, Entity, AxisAlignedBB

public class BlockNWater_Ocean extends BlockBase {

	protected BlockNWater_Ocean(ApiController api) {
		// super(i, mod_NWater.texx[5], Material.water);
		super(api.getBlockID(BlockNWater_Ocean.class), Block.waterStill.blockIndexInTexture, Material.water);
		given = 0;
		float f = 0.0F;
		noOcean = false;
		setBlockBounds(-0.5F, -0.5F, -0.5F, 1.5F, 1.5F, 1.5F);
		blockResistance = 100F;
		setHardness(0.3F);
		setLightOpacity(3);
		setStepSound(Block.soundGlassFootstep);
		setBlockName("nwater_ocean");
	}

	@Override
    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
		return mod_NWater.texx[5];
	}

	@Override
    public int getBlockTextureFromSide(int i) {
		return mod_NWater.texx[5];
	}

	@Override
    public int idDropped(int i, Random random) {
		return 0;
	}

	public boolean canBlockBePlacedAt(int i, int j, int k, int l, boolean flag) {
		return true;
	}

	@Override
    public int quantityDropped(Random random) {
		return 0;
	}

	@Override
    public boolean isOpaqueCube() {
		return false;
	}

	@Override
    public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
    public float getBlockBrightness(IBlockAccess iblockaccess, int i, int j,
			int k) {
		float f = iblockaccess.getLightBrightness(i, j, k);
		float f1 = iblockaccess.getLightBrightness(i, j + 1, k);
		return f > f1 ? f : f1;
	}

	@Override
    public int getRenderBlockPass() {
		return 1;
	}

	@Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return true;
	}

	@Override
    public void onBlockAdded(World world, int i, int j, int k) {
	}

	public boolean isWater(World world, int i, int j, int k) {
		if (world.getBlockId(i, j, k) == mod_NWater.nwater.blockID) {
			return true;
		}
		if (world.getBlockId(i, j, k) == mod_NWater.nwater_still.blockID) {
			return true;
		}
		if (world.getBlockId(i, j, k) == mod_NWater.nwater_ocean.blockID) {
			return true;
		}
		if (world.getBlockId(i, j, k) == mod_NWater.nlava.blockID) {
			return true;
		}
		return world.getBlockId(i, j, k) == mod_NWater.nlava_still.blockID;
	}

	@Override
    public boolean canCollideCheck(int i, boolean flag) {
		return flag;
	}

	@Override
    public int getRenderType() {
		return mod_NWater.id2;
	}

	public boolean canMoveS(World world, int i, int j, int k) {
		if (world.getBlockId(i, j, k) == Block.sponge.blockID) {
			int l = world.getBlockMetadata(i, j, k);
			if (l < 15) {
				l += 2;
				if (l > 15) {
					l = 15;
				}
				world.setBlockMetadata(i, j, k, l);
				return true;
			}
		}
		if (world.getBlockId(i, j, k) == mod_NWater.grate.blockID) {
			int i1 = world.getBlockMetadata(i, j, k);
			if ((i1 < 15) & (i1 != 10)) {
				i1 += 2;
				if (i1 > 15) {
					i1 = 15;
				}
				if (i1 == 10) {
					i1 = 11;
				}
				world.setBlockMetadata(i, j, k, i1);
				return true;
			}
		}
		return false;
	}

	@Override
    public void updateTick(World world, int i, int j, int k, Random random) {
		onNeighborBlockChange(world, i, j, k, 100);
	}

	@Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		boolean flag = false;
		boolean flag1 = false;
		if (Block.blocksList[l] == mod_NWater.nwater_ocean) {
			return;
		}
		if (!mod_NWater.chunkPass(world, i, j, k)) {
			return;
		}
		boolean flag2 = false;
		if ((world.getBlockId(i, j + 1, l) == blockID)
				& (world.getBlockId(i, j + 2, l) == blockID)) {
			flag2 = true;
		}
		int i1 = mod_NWater.nwater.blockID;
		byte byte0 = 4;
		if (world.getBlockMetadata(i, j, k) == 10 || flag2) {
			i1 = blockID;
			byte0 = 10;
			world.setBlockMetadata(i, j, k, 0);
		}
		if (mod_NWater.canMove(world, i - 1, j, k)) {
			world.setBlockAndMetadataWithNotify(i - 1, j, k, i1, byte0);
			flag = true;
		}
		if (mod_NWater.canMove(world, i + 1, j, k)) {
			world.setBlockAndMetadataWithNotify(i + 1, j, k, i1, byte0);
			flag = true;
		}
		if (mod_NWater.canMove(world, i, j, k - 1)) {
			world.setBlockAndMetadataWithNotify(i, j, k - 1, i1, byte0);
			flag = true;
		}
		if (mod_NWater.canMove(world, i, j, k + 1)) {
			world.setBlockAndMetadataWithNotify(i, j, k + 1, i1, byte0);
			flag = true;
		}
		if (mod_NWater.canMove(world, i, j - 1, k)) {
			world.setBlockAndMetadataWithNotify(i, j - 1, k, i1, byte0);
			flag = true;
		}
		if (canMoveS(world, i - 1, j, k)) {
			flag1 = true;
		}
		if (canMoveS(world, i + 1, j, k)) {
			flag1 = true;
		}
		if (canMoveS(world, i, j, k - 1)) {
			flag1 = true;
		}
		if (canMoveS(world, i, j, k + 1)) {
			flag1 = true;
		}
		if (canMoveS(world, i, j - 1, k)) {
			flag1 = true;
		}
		if (flag1 || (Block.blocksList[l] == null) & (!noOcean) & (flag)) {
			pikd = false;
			if (!checkOcean(world, i, j, k, i, j, k, 0)) {
				noOcean = true;
				setOcean(world, i, j, k, i, j, k, 0, 0);
				noOcean = false;
				dontCheck = false;
				return;
			} else {
				noOcean = true;
				setOcean(world, i, j, k, i, j, k, 0, 1);
				noOcean = false;
				dontCheck = false;
				return;
			}
		} else {
			return;
		}
	}

	public void setOcean(World world, int i, int j, int k, int l, int i1,
			int j1, int k1, int l1) {
		if (k1 > mod_NWater.lakeLimit * 1.5D) {
			return;
		}
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		if (l1 == 0 && (world.getBlockId(l, i1, j1) == blockID || k1 == 0)) {
			if (k1 == 0) {
				world.setBlockAndMetadataWithNotify(l, i1, j1,
						mod_NWater.nwater.blockID, 15);
			} else {
				world.setBlockAndMetadata(l, i1, j1,
						mod_NWater.nwater_still.blockID, 15);
			}
			int i2 = l - 1;
			int k2 = i1;
			int i3 = j1;
			if (isOcean(world, i2, k2, i3)) {
				setOcean(world, i, j, k, i2, k2, i3, k1 + 1, l1);
			}
			i2 = l + 1;
			k2 = i1;
			i3 = j1;
			if (isOcean(world, i2, k2, i3)) {
				setOcean(world, i, j, k, i2, k2, i3, k1 + 1, l1);
			}
			i2 = l;
			k2 = i1 + 1;
			i3 = j1;
			if (isOcean(world, i2, k2, i3)) {
				setOcean(world, i, j, k, i2, k2, i3, k1 + 1, l1);
			}
			i2 = l;
			k2 = i1;
			i3 = j1 - 1;
			if (isOcean(world, i2, k2, i3)) {
				setOcean(world, i, j, k, i2, k2, i3, k1 + 1, l1);
			}
			i2 = l;
			k2 = i1;
			i3 = j1 + 1;
			if (isOcean(world, i2, k2, i3)) {
				setOcean(world, i, j, k, i2, k2, i3, k1 + 1, l1);
			}
		}
		if (l1 == 1
				&& ((world.getBlockId(l, i1, j1) == blockID)
						& (world.getBlockMetadata(l, i1, j1) == 2) || k1 == 0)) {
			world.setBlockMetadata(l, i1, j1, 0);
			int j2 = l - 1;
			int l2 = i1;
			int j3 = j1;
			if (isOcean(world, j2, l2, j3)) {
				setOcean(world, i, j, k, j2, l2, j3, k1 + 1, l1);
			}
			j2 = l + 1;
			l2 = i1;
			j3 = j1;
			if (isOcean(world, j2, l2, j3)) {
				setOcean(world, i, j, k, j2, l2, j3, k1 + 1, l1);
			}
			j2 = l;
			l2 = i1 + 1;
			j3 = j1;
			if (isOcean(world, j2, l2, j3)) {
				setOcean(world, i, j, k, j2, l2, j3, k1 + 1, l1);
			}
			j2 = l;
			l2 = i1;
			j3 = j1 - 1;
			if (isOcean(world, j2, l2, j3)) {
				setOcean(world, i, j, k, j2, l2, j3, k1 + 1, l1);
			}
			j2 = l;
			l2 = i1;
			j3 = j1 + 1;
			if (isOcean(world, j2, l2, j3)) {
				setOcean(world, i, j, k, j2, l2, j3, k1 + 1, l1);
			}
		}
	}

	public boolean checkOcean(World world, int i, int j, int k, int l, int i1,
			int j1, int k1) {
		if (!isWater(world, l, i1, j1)) {
			return false;
		}
		if (isOcean(world, l, i1, j1)
				& (world.getBlockMetadata(l, i1, j1) == 2)) {
			return false;
		}
		if (isOcean(world, l, i1, j1)) {
			world.setBlockMetadata(l, i1, j1, 2);
		}
		if (pikd) {
			return true;
		}
		if (k1 > mod_NWater.lakeLimit) {
			pikd = true;
			return true;
		}
		if (isOcean(world, l - 1, i1, j1)
				&& checkOcean(world, i, j, k, l - 1, i1, j1, k1 + 1)) {
			return true;
		}
		if (isOcean(world, l + 1, i1, j1)
				&& checkOcean(world, i, j, k, l + 1, i1, j1, k1 + 1)) {
			return true;
		}
		if (isOcean(world, l, i1 + 1, j1)
				&& checkOcean(world, i, j, k, l, i1 + 1, j1, k1 + 1)) {
			return true;
		}
		if (isOcean(world, l, i1, j1 - 1)
				&& checkOcean(world, i, j, k, l, i1, j1 - 1, k1 + 1)) {
			return true;
		}
		if (isOcean(world, l, i1, j1 + 1)
				&& checkOcean(world, i, j, k, l, i1, j1 + 1, k1 + 1)) {
			return true;
		}
		return pikd;
	}

	public boolean isOcean(World world, int i, int j, int k) {
		return world.getBlockId(i, j, k) == blockID;
	}

	@Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k,
			Entity entity) {
		if (world.multiplayerWorld) {
			return;
		}
		if (world.getBlockMetadata(i, j, k) == 1) {
			return;
		} else {
			return;
		}
	}

	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i,
			int j, int k) {
		if (world.multiplayerWorld) {
			return null;
		} else {
			setBlockBounds(-0.01F, 0.01F, 0.01F, 1.01F, 1.01F, 1.01F);
			return null;
		}
	}

	@Override
    public void onBlockRemoval(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		if (l > 0) {
			world.notifyBlocksOfNeighborChange(i, j, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
		}
		super.onBlockRemoval(world, i, j, k);
	}

	@Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i,
			int j, int k) {
		setBlockBounds(0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
		return false;
	}

	@Override
    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k,
			int l) {
		return false;
	}

	@Override
    public boolean canProvidePower() {
		return false;
	}

	@Override
    public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	private boolean pikd;
	private int given;
	public static boolean noOcean;
	public static boolean dontCheck;
}
