package MCP.mod_finiteliquids;

//Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) braces deadcode 

import MCP.ApiController;

import java.util.Random;

import net.minecraft.src.*;

//Referenced classes of package net.minecraft.src:
//         Item, World, mod_NWater, Block, 
//         EntityPlayer, Vec3D, MathHelper, ItemStack, 
//         MovingObjectPosition, EnumMovingObjectType, Material, WorldProvider, 
//         EntityCow

public class NItemBucket extends Item {
	public NItemBucket(ApiController api, int j) {
		this(api.getItemID(NItemBucket.class), j);
	}

	public NItemBucket(int i, int j) {
		super(i);
		maxStackSize = 1;
		isFull = j;
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

	public boolean canGrab(World world, int i, int j, int k) {
		int l = i;
		int i1 = j;
		int j1 = k;
		if (world.getBlockId(l, i1, j1) == mod_NWater.nwater_ocean.blockID
				|| world.getBlockId(l, i1 - 1, j1) == mod_NWater.nwater_ocean.blockID) {
			return true;
		}
		if ((world.getBlockMetadata(l, i1, j1) < world.getBlockMetadata(l,
				i1 - 1, j1)) & isLiquid(world, l, i1 - 1, j1)) {
			i1--;
		}
		int k1 = 0;
		k1 = k1 + world.getBlockMetadata(l, i1, j1) + 1;
		if (isLiquid(world, l - 1, i1, j1)) {
			k1 = k1 + world.getBlockMetadata(l - 1, i1, j1) + 1;
		}
		if (isLiquid(world, l + 1, i1, j1)) {
			k1 = k1 + world.getBlockMetadata(l + 1, i1, j1) + 1;
		}
		if (isLiquid(world, l, i1, j1 - 1)) {
			k1 = k1 + world.getBlockMetadata(l, i1, j1 - 1) + 1;
		}
		if (isLiquid(world, l, i1, j1 + 1)) {
			k1 = k1 + world.getBlockMetadata(l, i1, j1 + 1) + 1;
		}
		return k1 >= 16;
	}

	public void grab(World world, int i, int j, int k) {
		int l = i;
		int i1 = j;
		int j1 = k;
		if (world.getBlockId(l, i1, j1) == mod_NWater.nwater_ocean.blockID) {
			world.setBlockWithNotify(l, i1, j1, 0);
			return;
		}
		if (world.getBlockId(l, i1 - 1, j1) == mod_NWater.nwater_ocean.blockID) {
			world.setBlockWithNotify(l, i1 - 1, j1, 0);
			return;
		}
		if ((world.getBlockMetadata(l, i1, j1) < world.getBlockMetadata(l,
				i1 - 1, j1)) & isLiquid(world, l, i1 - 1, j1)) {
			i1--;
		}
		int k1 = 0;
		boolean flag = false;
		k1 = k1 + world.getBlockMetadata(l, i1, j1) + 1;
		world.setBlockAndMetadataWithNotify(l, i1, j1, 0, 0);
		if (k1 >= 16) {
			return;
		}
		l = i - 1;
		if (isLiquid(world, l, i1, j1)) {
			int l1 = world.getBlockMetadata(l, i1, j1) + 1;
			k1 += l1;
			if (k1 > 16) {
				int i2 = 16 - k1 - 1;
				world.setBlockMetadataWithNotify(l, i1, j1, i2);
				return;
			}
			world.setBlockAndMetadataWithNotify(l, i1, j1, 0, 0);
		}
		l = i + 1;
		if (isLiquid(world, l, i1, j1)) {
			int j2 = world.getBlockMetadata(l, i1, j1) + 1;
			k1 += j2;
			if (k1 > 16) {
				int k2 = 16 - k1 - 1;
				world.setBlockMetadataWithNotify(l, i1, j1, k2);
				return;
			}
			world.setBlockAndMetadataWithNotify(l, i1, j1, 0, 0);
		}
		l = i;
		j1 = k - 1;
		if (isLiquid(world, l, i1, j1)) {
			int l2 = world.getBlockMetadata(l, i1, j1) + 1;
			k1 += l2;
			if (k1 > 16) {
				int i3 = 16 - k1 - 1;
				world.setBlockMetadataWithNotify(l, i1, j1, i3);
				return;
			}
			world.setBlockAndMetadataWithNotify(l, i1, j1, 0, 0);
		}
		j1 = k + 1;
		if (isLiquid(world, l, i1, j1)) {
			int j3 = world.getBlockMetadata(l, i1, j1) + 1;
			k1 += j3;
			if (k1 > 16) {
				int k3 = 16 - k1 - 1;
				world.setBlockMetadataWithNotify(l, i1, j1, k3);
				return;
			}
			world.setBlockAndMetadataWithNotify(l, i1, j1, 0, 0);
		}
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		float f = 1.0F;
		float f1 = entityplayer.prevRotationPitch
				+ (entityplayer.rotationPitch - entityplayer.prevRotationPitch)
				* f;
		float f2 = entityplayer.prevRotationYaw
				+ (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
		double d = entityplayer.prevPosX
				+ (entityplayer.posX - entityplayer.prevPosX) * (double) f;
		double d1 = (entityplayer.prevPosY
				+ (entityplayer.posY - entityplayer.prevPosY) * (double) f + 1.6200000000000001D)
				- (double) entityplayer.yOffset;
		double d2 = entityplayer.prevPosZ
				+ (entityplayer.posZ - entityplayer.prevPosZ) * (double) f;
		Vec3D vec3d = Vec3D.createVector(d, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
		float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
		float f5 = -MathHelper.cos(-f1 * 0.01745329F);
		float f6 = MathHelper.sin(-f1 * 0.01745329F);
		float f7 = f4 * f5;
		float f8 = f6;
		float f9 = f3 * f5;
		double d3 = 5D;
		Vec3D vec3d1 = vec3d.addVector((double) f7 * d3, (double) f8 * d3,
				(double) f9 * d3);
		MovingObjectPosition movingobjectposition = world.rayTraceBlocks_do(
				vec3d, vec3d1, isFull == 0);
		if (movingobjectposition == null) {
			return itemstack;
		}
		if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
			int i = movingobjectposition.blockX;
			int j = movingobjectposition.blockY;
			int k = movingobjectposition.blockZ;
			if (!world.func_6466_a(entityplayer, i, j, k)) {
				return itemstack;
			}
			if (isFull == 0) {
				if (isWater(world, i, j, k)) {
					if (canGrab(world, i, j, k)) {
						int l = world.getBlockId(i, j, k);
						grab(world, i, j, k);
						if (l == mod_NWater.nlava.blockID
								|| l == mod_NWater.nlava_still.blockID) {
							return new ItemStack(mod_NWater.bucketNLava);
						}
						if (l == mod_NWater.noil.blockID
								|| l == mod_NWater.noil_still.blockID) {
							return new ItemStack(mod_NWater.bucketNOil);
						}
						if (l == mod_NWater.nqsand.blockID
								|| l == mod_NWater.nqsand_still.blockID) {
							return new ItemStack(mod_NWater.bucketNQSand);
						} else {
							return new ItemStack(mod_NWater.bucketNWater);
						}
					} else {
						return new ItemStack(Item.bucketEmpty);
					}
				}
				if (world.getBlockMaterial(i, j, k) == Material.water
						&& world.getBlockMetadata(i, j, k) == 0) {
					world.setBlockWithNotify(i, j, k, 0);
					return new ItemStack(Item.bucketWater);
				}
				if (world.getBlockMaterial(i, j, k) == Material.lava
						&& world.getBlockMetadata(i, j, k) == 0) {
					world.setBlockWithNotify(i, j, k, 0);
					return new ItemStack(Item.bucketLava);
				}
			} else {
				if (isFull < 0) {
					return new ItemStack(Item.bucketEmpty);
				}
				if (movingobjectposition.sideHit == 0) {
					j--;
				}
				if (movingobjectposition.sideHit == 1) {
					j++;
				}
				if (movingobjectposition.sideHit == 2) {
					k--;
				}
				if (movingobjectposition.sideHit == 3) {
					k++;
				}
				if (movingobjectposition.sideHit == 4) {
					i--;
				}
				if (movingobjectposition.sideHit == 5) {
					i++;
				}
				if (world.isAirBlock(i, j, k)
						|| !world.getBlockMaterial(i, j, k).isSolid()) {
					if (world.worldProvider.isHellWorld
							&& isFull == Block.waterMoving.blockID) {
						world.playSoundEffect(d + 0.5D, d1 + 0.5D, d2 + 0.5D,
								"random.fizz", 0.5F,
								2.6F + (world.rand.nextFloat() - world.rand
										.nextFloat()) * 0.8F);
						for (int i1 = 0; i1 < 8; i1++) {
							world.spawnParticle("largesmoke",
									(double) i + Math.random(), (double) j
											+ Math.random(),
									(double) k + Math.random(), 0.0D, 0.0D,
									0.0D);
						}

					} else {
						int j1 = j;
						if (isLiquid(world, i, j, k)) {
							int k1 = 0;
							do {
								if (k1 >= 3) {
									break;
								}
								j1++;
								k1++;
								if (world.getBlockId(i, j1, k) == 0) {
									k1 = 10;
								}
								if (world.getBlockMaterial(i, j1, k).isSolid()) {
									k1 = 10;
								}
							} while (true);
							if (!world.getBlockMaterial(i, j1, k).isSolid()) {
								world.setBlockAndMetadataWithNotify(i, j1, k,
										isFull, 15);
								return new ItemStack(Item.bucketEmpty);
							}
							if (isFull == mod_NWater.nwater.blockID) {
								return new ItemStack(mod_NWater.bucketNWater);
							}
							if (isFull == mod_NWater.nlava.blockID) {
								return new ItemStack(mod_NWater.bucketNLava);
							}
							if (isFull == mod_NWater.noil.blockID) {
								return new ItemStack(mod_NWater.bucketNOil);
							}
							if (isFull == mod_NWater.nqsand.blockID) {
								return new ItemStack(mod_NWater.bucketNQSand);
							} else {
								return new ItemStack(mod_NWater.bucketNWater);
							}
						}
						byte byte0 = 0;
						if (isFull == mod_NWater.nwater.blockID) {
							byte0 = 15;
						}
						if (isFull == mod_NWater.nlava.blockID) {
							byte0 = 15;
						}
						if (isFull == mod_NWater.noil.blockID) {
							byte0 = 15;
						}
						if (isFull == mod_NWater.nqsand.blockID) {
							byte0 = 15;
						}
						world.setBlockAndMetadataWithNotify(i, j, k, isFull,
								byte0);
						return new ItemStack(Item.bucketEmpty);
					}
					return new ItemStack(Item.bucketEmpty);
				}
			}
		} else if (isFull == 0
				&& (movingobjectposition.entityHit instanceof EntityCow)) {
			return new ItemStack(Item.bucketMilk);
		}
		return itemstack;
	}

	private boolean isLiquid(World world, int i, int j, int k) {
		if (world.getBlockId(i, j, k) == mod_NWater.nwater_ocean.blockID) {
			return true;
		}
		if (world.getBlockId(i, j, k) == mod_NWater.nwater.blockID) {
			return true;
		}
		if (world.getBlockId(i, j, k) == mod_NWater.nwater_still.blockID) {
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

	private int isFull;
}
