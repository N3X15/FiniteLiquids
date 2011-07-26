// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import java.util.Random;

import net.minecraft.src.*;

import MCP.ApiController;
import MCP.api.BlockBase;

// Referenced classes of package net.minecraft.src:
//            Block, mod_NWater, IBlockAccess, World, 
//            BlockNWater_Ocean, Material, Entity, Vec3D, 
//            AxisAlignedBB

public class BlockNWater_Still extends BlockBase
{

    public BlockNWater_Still(ApiController api, int j, Material material)
    {
		this(api.getBlockID(BlockNWater_Still.class), j, material);
    }
    
    public BlockNWater_Still(int idx,int texx,Material mat) {
    	super(idx,texx,mat);
        blockIndexInTexture = Block.waterStill.blockIndexInTexture;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0725F, 1.0F);
        blockResistance = 100F;
        setLightOpacity(15);
        setHardness(100F);
        setBlockName("nwater");
    }

    @Override
    public int tickRate()
    {
        return 6000;
    }

    @Override
    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return mod_NWater.texx[5];
    }

    @Override
    public void velocityToAddToEntity(World world, int i, int j, int k, Entity entity, Vec3D vec3d)
    {
        mod_NWater.waterPush(world, i, j, k, entity, vec3d);
    }

    @Override
    public int getBlockTextureFromSide(int i)
    {
        return mod_NWater.texx[5];
    }

    @Override
    public int idDropped(int i, Random random)
    {
        return 0;
    }

    public boolean canBlockBePlacedAt(int i, int j, int k, int l, boolean flag)
    {
        return true;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public float getBlockBrightness(IBlockAccess iblockaccess, int i, int j, int k)
    {
        float f = iblockaccess.getLightBrightness(i, j, k);
        float f1 = iblockaccess.getLightBrightness(i, j + 1, k);
        return f > f1 ? f : f1;
    }

    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        return true;
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
        if(world.getBlockMetadata(i, j, k) < 2)
        {
            world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
        }
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(world.getBlockMetadata(i, j, k) < 2)
        {
            if(random.nextInt(5) == 2)
            {
                world.setBlockWithNotify(i, j, k, 0);
            } else
            {
                world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
            }
        }
    }

    @Override
    public boolean canCollideCheck(int i, boolean flag)
    {
        return (i > 4) & flag;
    }

    @Override
    public int getRenderType()
    {
        return mod_NWater.id2;
    }

    public boolean checkSponge(World world, int i, int j, int k)
    {
        return (world.getBlockId(i, j, k) == Block.sponge.blockID) & (world.getBlockMetadata(i, j, k) < 15);
    }

    public boolean isWater(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j, k) == mod_NWater.nwater.blockID)
        {
            return true;
        }
        if(world.getBlockId(i, j, k) == mod_NWater.nwater_still.blockID)
        {
            return true;
        }
        if(world.getBlockId(i, j, k) == mod_NWater.nlava.blockID)
        {
            return true;
        }
        if(world.getBlockId(i, j, k) == mod_NWater.nlava_still.blockID)
        {
            return true;
        }
        return (world.getBlockId(i, j, k) == mod_NWater.grate.blockID) & (world.getBlockMetadata(i, j, k) != 10);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        if(world.multiplayerWorld)
        {
            return;
        } else
        {
            mod_NWater.boilWater(world, i, j, k, entity);
            return;
        }
    }

    public float getSurfaceMull()
    {
        return 1.0F;
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(!mod_NWater.chunkPass(world, i, j, k))
        {
            return;
        }
        if(world.getBlockMetadata(i, j, k) < 2)
        {
            world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
        }
        if(world.multiplayerWorld)
        {
            return;
        }
        if(BlockNWater_Ocean.noOcean)
        {
            return;
        }
        checkForHarden(world, i, j, k);
        int i1 = world.getBlockMetadata(i, j, k);
        boolean flag = false;
        if((world.getBlockId(i, j - 1, k) == mod_NWater.nwater_ocean.blockID) & (world.getBlockMetadata(i, j - 1, k) < 2 || world.getBlockMetadata(i, j - 1, k) > 3))
        {
            flag = true;
        }
        if(mod_NWater.canMoveAmt(world, i, j - 1, k, i1))
        {
            flag = true;
        }
        if(checkSponge(world, i - 1, j, k))
        {
            flag = true;
        }
        if(checkSponge(world, i + 1, j, k))
        {
            flag = true;
        }
        if(checkSponge(world, i, j - 1, k))
        {
            flag = true;
        }
        if(checkSponge(world, i, j, k - 1))
        {
            flag = true;
        }
        if(checkSponge(world, i, j, k + 1))
        {
            flag = true;
        }
        if((!flag) & isWater(world, i, j - 1, k))
        {
            int j1 = world.getBlockMetadata(i, j - 1, k);
            if(j1 < 15)
            {
                if(j1 + (1 + i1) < 16)
                {
                    flag = true;
                } else
                {
                    flag = true;
                }
            }
        }
        if(!flag)
        {
            int k1 = 1;
            int i2 = 1;
            for(int k2 = -1; k2 <= 1; k2++)
            {
                for(int i3 = -1; i3 <= 1; i3++)
                {
                    int k3 = k2 * k1;
                    int i4 = i3 * i2;
                    if(!((!flag) & (k2 != 0 || i3 != 0) & (k2 == 0 || i3 == 0)))
                    {
                        continue;
                    }
                    world.getBlockId(i + k3, j, k + i4);
                    world.getBlockMetadata(i + k3, j, k + i4);
                    boolean flag1 = mod_NWater.canMoveAmt(world, i + k3, j, k + i4, i1);
                    if(!((!flag) & flag1))
                    {
                        continue;
                    }
                    world.getBlockId(i + k3, j - 1, k + i4);
                    world.getBlockMetadata(i + k3, j - 1, k + i4);
                    flag1 = mod_NWater.canMoveAmt(world, i + k3, j - 1, k + i4, i1);
                    if((!flag) & flag1)
                    {
                        flag = true;
                    }
                    if(!((!flag) & isWater(world, i + k3, j - 1, k + i4)))
                    {
                        continue;
                    }
                    int k5 = world.getBlockMetadata(i + k3, j - 1, k + i4);
                    if(k5 >= 15)
                    {
                        continue;
                    }
                    if(k5 + (1 + i1) < 16)
                    {
                        flag = true;
                    } else
                    {
                        flag = true;
                    }
                }

            }

        }
        if((!flag) & (i1 > 0))
        {
            int l1 = 1;
            int j2 = 1;
            for(int l2 = -1; l2 <= 1; l2++)
            {
                for(int j3 = -1; j3 <= 1; j3++)
                {
                    int l3 = l2 * l1;
                    int j4 = j3 * j2;
                    if(!((!flag) & (l2 != 0 || j3 != 0) & (l2 == 0 || j3 == 0)))
                    {
                        continue;
                    }
                    world.getBlockId(i + l3, j, k + j4);
                    int j5 = world.getBlockMetadata(i + l3, j, k + j4);
                    boolean flag2 = mod_NWater.canMoveAmt(world, i + l3, j, k + j4, i1);
                    if((!flag) & flag2)
                    {
                        flag = true;
                    }
                    if(!((!flag) & isWater(world, i + l3, j, k + j4)))
                    {
                        continue;
                    }
                    if(j5 + 1 < i1)
                    {
                        flag = true;
                    }
                    float f = getSurfaceMull();
                    if(!((!flag) & (Math.pow(mod_NWater.getMetaAvg2(world, i + l3, j, k + j4), f) < mod_NWater.getMetaAvg2(world, i, j, k))))
                    {
                        continue;
                    }
                    int l5 = i1;
                    int j6 = (i1 + j5) / 2;
                    if(l5 != j6)
                    {
                        flag = true;
                    }
                }

            }

        }
        if(flag)
        {
            world.setBlockAndMetadata(i, j, k, getMoving(), i1);
        }
    }

    public int getMoving()
    {
        return mod_NWater.nwater.blockID;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        if(world.multiplayerWorld)
        {
            return null;
        }
        if(world.getBlockId(i, j + 1, k) != 0)
        {
            setBlockBounds(-0.01F, 0.01F, 0.01F, 1.01F, 1.01F, 1.01F);
        }
        return null;
    }

    private void checkForHarden(World world, int i, int j, int k)
    {
        Material material = blockMaterial;
        Material material1 = Material.water;
        if(material == Material.water)
        {
            material1 = Material.lava;
        }
        boolean flag = false;
        if(flag || world.getBlockMaterial(i, j, k - 1) == material1)
        {
            flag = true;
        }
        if(flag || world.getBlockMaterial(i, j, k + 1) == material1)
        {
            flag = true;
        }
        if(flag || world.getBlockMaterial(i - 1, j, k) == material1)
        {
            flag = true;
        }
        if(flag || world.getBlockMaterial(i + 1, j, k) == material1)
        {
            flag = true;
        }
        if(flag || world.getBlockMaterial(i, j + 1, k) == material1)
        {
            flag = true;
        }
        if(flag)
        {
            if(material == Material.lava)
            {
                world.setBlockWithNotify(i, j, k, Block.obsidian.blockID);
            } else
            {
                world.setBlockWithNotify(i, j, k, Block.cobblestone.blockID);
            }
        }
    }

    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        if(random.nextInt(4) == 1 && mod_NWater.isBoiling(world, i, j, k))
        {
            world.spawnParticle("bubble", i + random.nextFloat(), j + random.nextFloat(), k + random.nextFloat(), (random.nextFloat() - 0.5F) / 10F, random.nextFloat() / 5F, (random.nextFloat() - 0.5F) / 10F);
        }
        if(blockMaterial == Material.water)
        {
            if(random.nextInt(64) != 0);
        }
    }

    @Override
    public void onBlockRemoval(World world, int i, int j, int k)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(l > 0)
        {
            world.notifyBlocksOfNeighborChange(i, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        }
        super.onBlockRemoval(world, i, j, k);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F + iblockaccess.getBlockMetadata(i, j, k) / 16F, 1.0F);
    }

    @Override
    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }

    @Override
    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l)
    {
        return false;
    }

    @Override
    public boolean canProvidePower()
    {
        return false;
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        float f = 0.5F;
        float f1 = 0.125F;
        float f2 = 0.5F;
        setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
    }
}
