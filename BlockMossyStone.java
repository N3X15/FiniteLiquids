package MCP.mod_finiteliquids;

import net.minecraft.src.Material;
import MCP.ApiController;

public class BlockMossyStone extends BlockMossy {
	public BlockMossyStone(ApiController api, int texID) {
		super(api.getBlockID(BlockMossyStone.class), texID, Material.rock);
	}
}
