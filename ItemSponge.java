// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import MCP.ApiController;
import MCP.api.BlockBase;
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

    public int getIconFromDamage(int i)
    {
        return Block.sponge.getBlockTextureFromSideAndMetadata(2, BlockSponge.func_21034_c(i));
    }

    public int getPlacedBlockMetadata(int i)
    {
        return i;
    }
}
