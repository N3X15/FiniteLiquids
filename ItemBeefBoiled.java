package MCP.mod_finiteliquids;

import MCP.ApiController;
import net.minecraft.src.ItemFood;

public class ItemBeefBoiled extends ItemFood {

	public ItemBeefBoiled(ApiController api, int i, boolean a) {
		super(api.getItemID(ItemBeefBoiled.class),i,a);
	}

}
