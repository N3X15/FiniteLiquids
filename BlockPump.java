// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import java.util.Random;

import MCP.ApiController;
import MCP.api.BlockBase;
import net.minecraft.src.*;

// Referenced classes of package net.minecraft.src:
//            Block, mod_NWater, Material, World, 
//            BlockNWater_Ocean, IBlockAccess, EntityPlayer

public class BlockPump extends BlockBase {

	public BlockPump(ApiController api) {
		super(api.getBlockID(BlockPump.class), mod_NWater.texx[3],Material.iron);
		setTickOnLoad(true);
		setHardness(0.3F);
		setLightOpacity(3);
		setStepSound(Block.soundGlassFootstep);
		setBlockName("pump");
	}

	@Override
    public int getBlockTextureFromSide(int i) {
		return mod_NWater.texx[3];
	}

	@Override
    public int tickRate() {
		return 5;
	}

	public int connects(World world, int i, int j, int k) {
		int l = 0;
		for (int i1 = -1; i1 <= 1; i1++) {
			for (int j1 = -1; j1 <= 1; j1++) {
				for (int k1 = -1; k1 <= 1; k1++) {
					int l1 = 0;
					if (i1 == 0) {
						l1++;
					}
					if (j1 == 0) {
						l1++;
					}
					if (k1 == 0) {
						l1++;
					}
					if (!((i1 != 0 || j1 != 0 || k1 != 0) & (l1 > 1))) {
						continue;
					}
					if (world.getBlockId(i + i1, j + j1, k + k1) == mod_NWater.pipe.blockID) {
						l++;
					}
					if (world.getBlockId(i + i1, j + j1, k + k1) == mod_NWater.pump.blockID) {
						l++;
					}
				}

			}

		}

		return l;
	}

	public boolean isLiquid(World world, int i, int j, int k) {
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
		if (world.getBlockId(i, j, k) == mod_NWater.nlava_still.blockID) {
			return true;
		}
		if (world.getBlockId(i, j, k) == mod_NWater.noil.blockID) {
			return true;
		}
		if (world.getBlockId(i, j, k) == mod_NWater.noil_still.blockID) {
			return true;
		}
		if (world.getBlockId(i, j, k) == mod_NWater.nqsand.blockID) {
			return true;
		}
		return world.getBlockId(i, j, k) == mod_NWater.nqsand_still.blockID;
	}

	public int findWater(World world, int i, int j, int k, int l, int i1,
			int j1, int k1) {
		int l1 = connects(world, i, j, k);
		if (k1 > mod_NWater.pipeLimit) {
			return -1;
		}
		if (k1 != 0) {
			if (world.getBlockMetadata(i, j, k) == 3) {
				return -1;
			}
			world.setBlockMetadata(i, j, k, 3);
		}
		byte byte0 = 1;
		byte byte1 = 1;
		byte byte2 = 1;
		Random random = new Random();
		if (random.nextInt(2) == 0) {
			byte0 = -1;
		}
		if (random.nextInt(2) == 0) {
			byte1 = -1;
		}
		if (random.nextInt(2) == 0) {
			byte2 = -1;
		}
		for (int i2 = -1; i2 <= 1; i2++) {
			for (int j2 = -1; j2 <= 1; j2++) {
				for (int k2 = -1; k2 <= 1; k2++) {
					int l2 = 0;
					if (i2 == 0) {
						l2++;
					}
					if (j2 == 0) {
						l2++;
					}
					if (k2 == 0) {
						l2++;
					}
					if (!((i2 != 0 || j2 != 0 || k2 != 0) & (l2 > 1))) {
						continue;
					}
					if ((k1 != 0)
							& (!pikd)
							& (l1 == 1)
							& isLiquid(world, i + i2 * byte0, j + j2 * byte1, k
									+ k2 * byte2)) {
						int i3 = world.getBlockId(i + i2 * byte0, j + j2
								* byte1, k + k2 * byte2);
						int k3 = world.getBlockMetadata(i + i2 * byte0, j + j2
								* byte1, k + k2 * byte2);
						if (i3 == mod_NWater.nwater.blockID
								|| i3 == mod_NWater.nwater_still.blockID) {
							grabtype = 0;
						}
						if (i3 == mod_NWater.nlava.blockID
								|| i3 == mod_NWater.nlava_still.blockID) {
							grabtype = 1;
						}
						if (i3 == mod_NWater.noil.blockID
								|| i3 == mod_NWater.noil_still.blockID) {
							grabtype = 2;
						}
						if (i3 == mod_NWater.nqsand.blockID
								|| i3 == mod_NWater.nqsand_still.blockID) {
							grabtype = 3;
						}
						if (world.getBlockId(i + i2 * byte0, j + j2 * byte1, k
								+ k2 * byte2) == mod_NWater.nwater_ocean.blockID) {
							k3 = 15;
						}
						if (k3 <= pikmax) {
							world.setBlockAndMetadataWithNotify(i + i2 * byte0,
									j + j2 * byte1, k + k2 * byte2, 0, 0);
							pikd = true;
							return k3;
						}
						if (grabtype == 0) {
							world.setBlockAndMetadataWithNotify(i + i2 * byte0,
									j + j2 * byte1, k + k2 * byte2,
									mod_NWater.nwater.blockID, k3 - pikmax);
						}
						if (grabtype == 1) {
							world.setBlockAndMetadataWithNotify(i + i2 * byte0,
									j + j2 * byte1, k + k2 * byte2,
									mod_NWater.nlava.blockID, k3 - pikmax);
						}
						if (grabtype == 2) {
							world.setBlockAndMetadataWithNotify(i + i2 * byte0,
									j + j2 * byte1, k + k2 * byte2,
									mod_NWater.noil.blockID, k3 - pikmax);
						}
						if (grabtype == 3) {
							world.setBlockAndMetadataWithNotify(i + i2 * byte0,
									j + j2 * byte1, k + k2 * byte2,
									mod_NWater.nqsand.blockID, k3 - pikmax);
						}
						pikd = true;
						return 15;
					}
					if (!((l2 > -1) & (!pikd) & (world.getBlockId(i + i2
							* byte0, j + j2 * byte1, k + k2 * byte2) == mod_NWater.pipe.blockID))
							|| world.getBlockMetadata(i + i2 * byte0, j + j2
									* byte1, k + k2 * byte2) == 2) {
						continue;
					}
					int j3 = findWater(world, i + i2 * byte0, j + j2 * byte1, k
							+ k2 * byte2, l, i1, j1, k1 + 1);
					if (j3 != -1) {
						return j3;
					}
				}

			}

		}

		return -1;
	}

	@Override
    public void updateTick(World world, int i, int j, int k, Random random) {
		int l = world.getBlockMetadata(i, j, k);
		if (l != 0)
			;
		if (l == 1 || l == 2) {
			if (hasRoom(world, i, j, k)) {
				pikd = false;
				grabtype = 0;
				BlockNWater_Ocean.dontCheck = true;
				int i1 = findWater(world, i, j, k, i, j, k, 0);
				resetPipe(world, i, j, k, 0);
				if (i1 != -1) {
					output(world, pikx, piky, pikz, i1);
				}
			}
			world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
		}
	}

	public void resetPipe(World world, int i, int j, int k, int l) {
		if (l > mod_NWater.pipeLimit * 1.25D) {
			return;
		}
		if ((world.getBlockId(i, j, k) == mod_NWater.pipe.blockID)
				& (world.getBlockMetadata(i, j, k) == 3) || l == 0) {
			if ((world.getBlockId(i, j, k) == mod_NWater.pipe.blockID)
					& (l != 0)) {
				world.setBlockMetadata(i, j, k, 0);
			}
			if (world.getBlockId(i - 1, j, k) == mod_NWater.pipe.blockID) {
				resetPipe(world, i - 1, j, k, l + 1);
			}
			if (world.getBlockId(i + 1, j, k) == mod_NWater.pipe.blockID) {
				resetPipe(world, i + 1, j, k, l + 1);
			}
			if (world.getBlockId(i, j - 1, k) == mod_NWater.pipe.blockID) {
				resetPipe(world, i, j - 1, k, l + 1);
			}
			if (world.getBlockId(i, j + 1, k) == mod_NWater.pipe.blockID) {
				resetPipe(world, i, j + 1, k, l + 1);
			}
			if (world.getBlockId(i, j, k - 1) == mod_NWater.pipe.blockID) {
				resetPipe(world, i, j, k - 1, l + 1);
			}
			if (world.getBlockId(i, j, k + 1) == mod_NWater.pipe.blockID) {
				resetPipe(world, i, j, k + 1, l + 1);
			}
		}
	}

	public boolean hasRoom(World world, int i, int j, int k) {
		byte byte0 = 1;
		byte byte1 = 1;
		byte byte2 = 1;
		Random random = new Random();
		if (random.nextInt(2) == 0) {
			byte0 = -1;
		}
		if (random.nextInt(2) == 0) {
			byte1 = -1;
		}
		if (random.nextInt(2) == 0) {
			byte2 = -1;
		}
		for (int l = -1; l <= 1; l++) {
			for (int i1 = -1; i1 <= 1; i1++) {
				for (int j1 = -1; j1 <= 1; j1++) {
					int k1 = 0;
					if (l == 0) {
						k1++;
					}
					if (i1 == 0) {
						k1++;
					}
					if (j1 == 0) {
						k1++;
					}
					if (!((l != 0 || j1 != 0 || i1 != 0) & (k1 > 1))) {
						continue;
					}
					int l1 = world.getBlockId(i + l * byte0, j + i1 * byte1, k
							+ j1 * byte2);
					if (l1 == 0) {
						pikx = i + l * byte0;
						piky = j + i1 * byte1;
						pikz = k + j1 * byte2;
						pikmax = 15;
						return true;
					}
					int i2 = world.getBlockMetadata(i + l * byte0, j + i1
							* byte1, k + j1 * byte2);
					if ((i2 < 15)
							& isLiquid(world, i + l * byte0, j + i1 * byte1, k
									+ j1 * byte2)) {
						pikx = i + l * byte0;
						piky = j + i1 * byte1;
						pikz = k + j1 * byte2;
						pikmax = 15 - i2;
						return true;
					}
				}

			}

		}

		return false;
	}

	public void output(World world, int i, int j, int k, int l) {
		if (isLiquid(world, i, j, k)) {
			int i1 = world.getBlockMetadata(i, j, k) + l + 1;
			if (i1 > 15) {
				i1 = 15;
			}
			if (grabtype == 0) {
				world.setBlockAndMetadataWithNotify(i, j, k,
						mod_NWater.nwater.blockID, i1);
			}
			if (grabtype == 1) {
				world.setBlockAndMetadataWithNotify(i, j, k,
						mod_NWater.nlava.blockID, i1);
			}
			if (grabtype == 2) {
				world.setBlockAndMetadataWithNotify(i, j, k,
						mod_NWater.noil.blockID, i1);
			}
			if (grabtype == 3) {
				world.setBlockAndMetadataWithNotify(i, j, k,
						mod_NWater.nqsand.blockID, i1);
			}
			world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
		} else {
			if (grabtype == 0) {
				world.setBlockAndMetadataWithNotify(i, j, k,
						mod_NWater.nwater.blockID, l);
			}
			if (grabtype == 1) {
				world.setBlockAndMetadataWithNotify(i, j, k,
						mod_NWater.nlava.blockID, l);
			}
			if (grabtype == 2) {
				world.setBlockAndMetadataWithNotify(i, j, k,
						mod_NWater.noil.blockID, l);
			}
			if (grabtype == 3) {
				world.setBlockAndMetadataWithNotify(i, j, k,
						mod_NWater.nqsand.blockID, l);
			}
			world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
		}
	}

	@Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		if (l > 0 && Block.blocksList[l].canProvidePower()) {
			if (Block.blocksList[l].blockID == mod_NWater.pipe.blockID) {
				return;
			}
			int i1 = world.getBlockMetadata(i, j, k);
			boolean flag = world.isBlockIndirectlyGettingPowered(i, j, k)
					|| world.isBlockIndirectlyGettingPowered(i, j + 1, k);
			if (flag) {
				if (i1 == 0) {
					world.setBlockMetadataWithNotify(i, j, k, 2);
				}
				world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
			} else if (i1 == 2) {
				world.setBlockMetadataWithNotify(i, j, k, 0);
			}
		}
	}

	@Override
    public void onBlockAdded(World world, int i, int j, int k) {
		super.onBlockAdded(world, i, j, k);
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
	}

	@Override
    public boolean blockActivated(World world, int i, int j, int k,
			EntityPlayer entityplayer) {
		int l = world.getBlockMetadata(i, j, k);
		if (++l > 1) {
			l = 0;
		}
		world.setBlockMetadataWithNotify(i, j, k, l);
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
		return !world.multiplayerWorld ? true : true;
	}

	@Override
    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
		int i1 = iblockaccess.getBlockMetadata(i, j, k);
		if (i1 == 1) {
			return mod_NWater.texx[3];
		}
		if (i1 == 2) {
			return mod_NWater.texx[3];
		} else {
			return mod_NWater.texx[2];
		}
	}

	private boolean pikd;
	private int grabtype;
	private int pikmax;
	private int pikx;
	private int piky;
	private int pikz;
}
