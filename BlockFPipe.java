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
//            Block, mod_NWater, Material, World, 
//            IBlockAccess

public class BlockFPipe extends BlockBase
{

    public BlockFPipe(ApiController api)
    {
        super(api.getBlockID(BlockFPipe.class), mod_NWater.texx[0], Material.iron);
        blockIndexInTexture = mod_NWater.texx[0];
        setHardness(0.3F);
        setLightOpacity(3);
        setStepSound(Block.soundGlassFootstep);
        setBlockName("pipe");
    }

    public int getRenderType()
    {
        return mod_NWater.id;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int connects(World world, int i, int j, int k)
    {
        int l = 0;
        byte byte0 = 1;
        byte byte1 = 1;
        byte byte2 = 1;
        Random random = new Random();
        if(random.nextInt(2) == 0)
        {
            byte0 = -1;
        }
        if(random.nextInt(2) == 0)
        {
            byte1 = -1;
        }
        if(random.nextInt(2) == 0)
        {
            byte2 = -1;
        }
        for(int i1 = -1; i1 <= 1; i1++)
        {
            for(int j1 = -1; j1 <= 1; j1++)
            {
                for(int k1 = -1; k1 <= 1; k1++)
                {
                    int l1 = 0;
                    if(i1 == 0)
                    {
                        l1++;
                    }
                    if(j1 == 0)
                    {
                        l1++;
                    }
                    if(k1 == 0)
                    {
                        l1++;
                    }
                    if(!((i1 != 0 || j1 != 0 || k1 != 0) & (l1 > 1)))
                    {
                        continue;
                    }
                    if(world.getBlockId(i + i1 * byte0, j + j1 * byte1, k + k1 * byte2) == mod_NWater.pipe.blockID)
                    {
                        l++;
                    }
                    if(world.getBlockId(i + i1 * byte0, j + j1 * byte1, k + k1 * byte2) == mod_NWater.pump.blockID)
                    {
                        l++;
                    }
                }

            }

        }

        return l;
    }

    public void endOrTube(World world, int i, int j, int k)
    {
        if(connects(world, i, j, k) == 1)
        {
            world.setBlockAndMetadata(i, j, k, blockID, 1);
        } else
        if(world.getBlockMetadata(i, j, k) != 2)
        {
            world.setBlockAndMetadata(i, j, k, blockID, 0);
        }
    }

    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        int i1 = iblockaccess.getBlockMetadata(i, j, k);
        if(i1 == 1)
        {
            return mod_NWater.texx[1];
        }
        if(i1 == 2)
        {
            return mod_NWater.texx[4];
        } else
        {
            return mod_NWater.texx[0];
        }
    }

    public int getBlockTextureFromSide(int i)
    {
        return mod_NWater.texx[0];
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        endOrTube(world, i, j, k);
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(world.getBlockId(i + 1, j, k) == mod_NWater.pump.blockID)
        {
            world.setBlockMetadata(i, j, k, 0);
            return;
        }
        if(world.getBlockId(i - 1, j, k) == mod_NWater.pump.blockID)
        {
            world.setBlockMetadata(i, j, k, 0);
            return;
        }
        if(world.getBlockId(i, j + 1, k) == mod_NWater.pump.blockID)
        {
            world.setBlockMetadata(i, j, k, 0);
            return;
        }
        if(world.getBlockId(i, j - 1, k) == mod_NWater.pump.blockID)
        {
            world.setBlockMetadata(i, j, k, 0);
            return;
        }
        if(world.getBlockId(i, j, k + 1) == mod_NWater.pump.blockID)
        {
            world.setBlockMetadata(i, j, k, 0);
            return;
        }
        if(world.getBlockId(i, j, k - 1) == mod_NWater.pump.blockID)
        {
            world.setBlockMetadata(i, j, k, 0);
            return;
        }
        if(l > 0 && Block.blocksList[l].canProvidePower())
        {
            boolean flag = world.isBlockIndirectlyGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i, j + 1, k);
            if(flag)
            {
                world.setBlockMetadataWithNotify(i, j, k, 2);
            } else
            {
                world.setBlockMetadataWithNotify(i, j, k, 0);
            }
        }
        endOrTube(world, i, j, k);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
    }
}
