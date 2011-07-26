// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import MCP.ApiController;
import net.minecraft.src.*;


// Referenced classes of package net.minecraft.src:
//            ItemArmor, EntityPlayer, Vec3D, MathHelper, 
//            ItemStack, World, MovingObjectPosition, EnumMovingObjectType, 
//            mod_NWater, Block, Material

public class ItemEmptyTank extends ItemArmor
{

    public ItemEmptyTank(ApiController api, int j, int k, int l, int i1)
    {
		this(api.getBlockItemID(ItemEmptyTank.class), j, k, i1);
    }
    public ItemEmptyTank(int id, int j, int k, int i1) {
    	super(id,j,k,i1);
        maxStackSize = 1;
        isFull = j;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        float f = 1.0F;
        float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * f;
        double d1 = (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * f + 1.6200000000000001D) - entityplayer.yOffset;
        double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * f;
        Vec3D vec3d = Vec3D.createVector(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5D;
        Vec3D vec3d1 = vec3d.addVector(f7 * d3, f8 * d3, f9 * d3);
        MovingObjectPosition movingobjectposition = world.rayTraceBlocks_do(vec3d, vec3d1, isFull == 0);
        if(movingobjectposition == null)
        {
            return itemstack;
        }
        if(movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
        {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            if(!world.func_6466_a(entityplayer, i, j, k))
            {
                return itemstack;
            }
            if(isFull != -10)
            {
                if(isFull == -1)
                {
                    if(world.getBlockId(i, j, k) == mod_NWater.liquidcompressor.blockID)
                    {
                    	/*
                    	 * TODO: Fixme
                        if(world.getBlockMetadata(i, j, k) == 7)
                        {
                            world.setBlockMetadataWithNotify(i, j, k, 0);
                            return new ItemStack(mod_NWater.tankWater);
                        }
                        if(world.getBlockMetadata(i, j, k) == 15)
                        {
                            world.setBlockMetadataWithNotify(i, j, k, 0);
                            return new ItemStack(mod_NWater.tankAir);
                        }
                        */
                    }
                    return itemstack;
                }
                if(movingobjectposition.sideHit == 0)
                {
                    j--;
                }
                if(movingobjectposition.sideHit == 1)
                {
                    j++;
                }
                if(movingobjectposition.sideHit == 2)
                {
                    k--;
                }
                if(movingobjectposition.sideHit == 3)
                {
                    k++;
                }
                if(movingobjectposition.sideHit == 4)
                {
                    i--;
                }
                if(movingobjectposition.sideHit == 5)
                {
                    i++;
                }
                /* TODO: FIXME
                if(world.isAirBlock(i, j, k) || !world.getBlockMaterial(i, j, k).isSolid())
                {
                    world.setBlockAndMetadataWithNotify(i, j, k, isFull, 15);
                    return new ItemStack(mod_NWater.tankEmpty);
                }*/
            }
        }
        return itemstack;
    }

    private int isFull;
}
