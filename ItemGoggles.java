package MCP.mod_finiteliquids;

import MCP.ApiController;
import net.minecraft.src.ItemArmor;

public class ItemGoggles extends ItemArmor {

	public ItemGoggles(ApiController api, int j, int k, int l) {
		super(api.getItemID(ItemGoggles.class), j, k, l);
	}

}
