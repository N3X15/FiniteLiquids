package MCP.mod_finiteliquids;

import MCP.ApiController;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;

public class ItemChickenBoiled extends ItemFood {

	public ItemChickenBoiled(ApiController api, int i, boolean a) {
		super(api.getItemID(ItemChickenBoiled.class),i,a);
	}

}
