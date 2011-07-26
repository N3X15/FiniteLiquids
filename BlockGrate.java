// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import java.util.Random;

import MCP.ApiController;
import MCP.api.BlockBase;
import net.minecraft.src.*;

// Referenced classes of package net.minecraft.src:
//            Block, Material, IBlockAccess, mod_NWater, 
//            World

public class BlockGrate extends BlockBase
{

    public BlockGrate(ApiController api)
    {
        super(api.getBlockID(BlockGrate.class), Material.iron);
        setTickOnLoad(true);
        setHardness(1.5F);
        setLightOpacity(3);
        setStepSound(Block.soundGlassFootstep);
        setBlockName("grate");
    }

    @Override
    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        int i1 = iblockaccess.getBlockMetadata(i, j, k);
        if(i1 == 10)
        {
            return mod_NWater.texx[11];
        } else
        {
            return mod_NWater.texx[12];
        }
    }

    @Override
    public int getBlockTextureFromSide(int i)
    {
        blockIndexInTexture = mod_NWater.texx[11];
        return mod_NWater.texx[11];
    }

    @Override
    public int tickRate()
    {
        return 5;
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(l > 0 && Block.blocksList[l].canProvidePower())
        {
            int i1 = world.getBlockMetadata(i, j, k);
            boolean flag = world.isBlockIndirectlyGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i, j + 1, k);
            if(flag)
            {
                if(i1 != 10)
                {
                    world.setBlockMetadataWithNotify(i, j, k, 10);
                }
            } else
            if(i1 == 10)
            {
                world.setBlockMetadataWithNotify(i, j, k, 0);
            }
        }
        world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
    }

    public void tryMove(World world, int i, int j, int k, int l, int i1, int j1)
    {
        int k1 = world.getBlockMetadata(l, i1, j1);
        int l1 = world.getBlockMetadata(i, j, k);
        int i2 = k1 / 2;
        if(k1 == 0)
        {
            return;
        }
        if(i2 == 0)
        {
            i2 = 1;
        }
        if(mod_NWater.canMove(world, i, j, k) & (world.getBlockId(i, j, k) != Block.redstoneWire.blockID))
        {
            int j2 = k1 - i2;
            if(j2 == 10)
            {
                Random random = new Random();
                if(random.nextInt(3) == 1)
                {
                    j2 = 11;
                } else
                {
                    j2 = 9;
                }
            }
            if(i2 == 10)
            {
                i2 = 9;
            }
            world.setBlockMetadataWithNotify(l, i1, j1, j2);
            world.setBlockAndMetadataWithNotify(i, j, k, mod_NWater.nwater.blockID, i2);
            return;
        }
        if((mod_NWater.isWater(world, i, j, k) || world.getBlockId(i, j, k) == blockID) & (l1 + 1 < k1))
        {
            int k2 = (k1 + l1 + 1) / 2;
            int l2 = (k1 + l1) / 2;
            int i3 = l2;
            if(i3 == 10)
            {
                Random random1 = new Random();
                if(random1.nextInt(3) == 1)
                {
                    i3 = 11;
                } else
                {
                    i3 = 9;
                }
            }
            if(k2 == 10)
            {
                k2 = 9;
            }
            world.setBlockMetadataWithNotify(l, i1, j1, i3);
            world.setBlockMetadataWithNotify(i, j, k, k2);
        }
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(l == 0 || l == 10)
        {
            return;
        } else
        {
            tryMove(world, i, j - 1, k, i, j, k);
            tryMove(world, i - 1, j, k, i, j, k);
            tryMove(world, i + 1, j, k, i, j, k);
            tryMove(world, i, j, k - 1, i, j, k);
            tryMove(world, i, j, k + 1, i, j, k);
            world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
            return;
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
