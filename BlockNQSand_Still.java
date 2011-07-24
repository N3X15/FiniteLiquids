// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import java.util.Random;

import MCP.ApiController;
import MCP.api.BlockBase;
import net.minecraft.src.*;

// Referenced classes of package net.minecraft.src:
//            BlockNWater_Still, mod_NWater, Entity, World, 
//            EntityLiving, Block, Material, IBlockAccess

public class BlockNQSand_Still extends BlockNWater_Still
{
	protected BlockNQSand_Still(ApiController api, int j, Material material) {
		super(api.getBlockID(BlockNQSand_Still.class), j, material);
		blockIndexInTexture = mod_NWater.texx[18];
		setHardness(100F);
		setLightOpacity(255);
		setBlockName("nqsand");
    }

    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return mod_NWater.texx[18];
    }

    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        super.onEntityCollidedWithBlock(world, i, j, k, entity);
        entity.motionX = entity.motionX * -0.69999999999999996D;
        entity.motionZ = entity.motionZ * -0.69999999999999996D;
        entity.motionY = entity.motionY * -0.45000000000000001D - 0.14000000059604645D;
        if((Math.abs(entity.motionX) < 0.0099999997764825821D) & (Math.abs(entity.motionZ) < 0.0099999997764825821D))
        {
            entity.motionY = entity.motionY + 0.18000000715255737D;
        }
        if(entity instanceof EntityLiving)
        {
            EntityLiving entityliving = (EntityLiving)entity;
            // TODO: get isJumping fixed
            /*
            if(entityliving.isJumping)
            {
                entity.motionY = entity.motionY * 0.80000001192092896D - 0.20000000298023224D;
            }
            */
        }
    }

    public int getBlockTextureFromSide(int i)
    {
        return mod_NWater.texx[18];
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
    }

    public float getSurfaceMull()
    {
        return 1.0F;
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        super.updateTick(world, i, j, k, random);
    }

    public int getMoving()
    {
        return mod_NWater.nqsand.blockID;
    }
}
