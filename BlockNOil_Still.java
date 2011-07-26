// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import java.util.Random;

import MCP.ApiController;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

// Referenced classes of package net.minecraft.src:
//            BlockNWater_Still, mod_NWater, Material, World, 
//            Block, IBlockAccess

public class BlockNOil_Still extends BlockNWater_Still
{

    protected BlockNOil_Still(ApiController api, int j, Material material)
    {
        super(api.getBlockID(BlockNOil_Still.class), j, material);
        blockIndexInTexture = mod_NWater.texx[17];
        setHardness(100F);
        setLightOpacity(255);
        setBlockName("noil");
    }

    @Override
    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return mod_NWater.texx[17];
    }

    @Override
    public int getBlockTextureFromSide(int i)
    {
        return mod_NWater.texx[17];
    }

    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        if(blockMaterial == Material.lava && world.getBlockMaterial(i, j + 1, k) == Material.air && !world.isBlockOpaqueCube(i, j + 1, k) && random.nextInt(100) == 0)
        {
            double d = i + random.nextFloat();
            double d1 = j + maxY;
            double d2 = k + random.nextFloat();
            world.spawnParticle("lava", d, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public float getSurfaceMull()
    {
        return 1.0F;
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        super.updateTick(world, i, j, k, random);
    }

    @Override
    public int getMoving()
    {
        return mod_NWater.noil.blockID;
    }
}
