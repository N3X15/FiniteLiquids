package MCP.mod_finiteliquids;

import MCP.ApiController;

public class ItemWaterTank extends ItemEmptyTank {

	public ItemWaterTank(ApiController api, int j, int k, int l, int i1) {
		super(api.getItemID(ItemWaterTank.class), j, k, i1);
	}

	public ItemWaterTank(int id, int j, int k, int i1) {
		super(id, j, k, i1);
	}

}
