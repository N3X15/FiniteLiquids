// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import net.minecraft.src.*;


// Referenced classes of package net.minecraft.src:
//            ItemBlock, Block, BlockSponge

public class ItemSponge extends ItemBlock
{

    public ItemSponge(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getIconFromDamage(int i)
    {
        return Block.sponge.getBlockTextureFromSideAndMetadata(2, NBlockSponge.func_21034_c(i));
    }

    @Override
    public int getPlacedBlockMetadata(int i)
    {
        return i;
    }
}
