// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import java.util.Random;

import net.minecraft.src.*;

// Referenced classes of package net.minecraft.src:
//            Block, Material, World, mod_NWater, 
//            EntityPlayer, Entity

public class NBlockSponge extends Block
{

    protected NBlockSponge(int i)
    {
        super(i, Material.sponge);
        blockIndexInTexture = 48;
        setTickOnLoad(true);
        setHardness(0.6F);
        setStepSound(Block.soundGrassFootstep);
        setBlockName("sponge");
    }

    @Override
    public int tickRate()
    {
        return 200;
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(world.getBlockMetadata(i, j, k) > 0)
        {
            world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
        }
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        int l = world.getBlockMetadata(i, j, k);
        if((l > 0) & (!borderOcean(world, i, j, k)))
        {
            l--;
            world.setBlockMetadataWithNotify(i, j, k, l);
            if(l > 0)
            {
                world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
            }
        }
    }

    public boolean borderOcean(World world, int i, int j, int k)
    {
        if(world.getBlockId(i + 1, j, k) == mod_NWater.nwater_ocean.blockID)
        {
            return true;
        }
        if(world.getBlockId(i - 1, j, k) == mod_NWater.nwater_ocean.blockID)
        {
            return true;
        }
        if(world.getBlockId(i, j, k + 1) == mod_NWater.nwater_ocean.blockID)
        {
            return true;
        }
        if(world.getBlockId(i, j, k - 1) == mod_NWater.nwater_ocean.blockID)
        {
            return true;
        }
        return world.getBlockId(i, j + 1, k) == mod_NWater.nwater_ocean.blockID;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        if(j > 14)
        {
            return mod_NWater.texx[16];
        }
        if(j > 0)
        {
            return mod_NWater.texx[15];
        } else
        {
            return blockIndexInTexture;
        }
    }

    @Override
    protected int damageDropped(int i)
    {
        return i;
    }

    public static int func_21034_c(int i)
    {
        return ~i & 0xf;
    }

    public static int func_21035_d(int i)
    {
        return ~i & 0xf;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        if(world.multiplayerWorld)
        {
            return;
        } else
        {
            return;
        }
    }

    @Override
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(l == 0)
        {
            return false;
        }
        Random random = new Random();
        if(random.nextInt(4) == 0)
        {
            l--;
            world.setBlockMetadata(i, j, k, l);
        }
        boolean flag = false;
        if((!flag) & (world.getBlockId(i, j - 1, k) == 0))
        {
            world.setBlockAndMetadataWithNotify(i, j - 1, k, mod_NWater.nwater.blockID, 2);
            flag = true;
        }
        if((!flag) & (world.getBlockId(i - 1, j, k) == 0))
        {
            world.setBlockAndMetadataWithNotify(i - 1, j, k, mod_NWater.nwater.blockID, 2);
            flag = true;
        }
        if((!flag) & (world.getBlockId(i + 1, j, k) == 0))
        {
            world.setBlockAndMetadataWithNotify(i + 1, j, k, mod_NWater.nwater.blockID, 2);
            flag = true;
        }
        if((!flag) & (world.getBlockId(i, j, k - 1) == 0))
        {
            world.setBlockAndMetadataWithNotify(i, j, k - 1, mod_NWater.nwater.blockID, 2);
            flag = true;
        }
        if((!flag) & (world.getBlockId(i, j, k + 1) == 0))
        {
            world.setBlockAndMetadataWithNotify(i, j, k + 1, mod_NWater.nwater.blockID, 2);
            flag = true;
        }
        if((!flag) & (world.getBlockId(i, j + 1, k) == 0))
        {
            world.setBlockAndMetadataWithNotify(i, j + 1, k, mod_NWater.nwater.blockID, 2);
            flag = true;
        }
        return flag;
    }
}
