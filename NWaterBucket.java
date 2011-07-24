package MCP.mod_finiteliquids;

import MCP.ApiController;

public class NWaterBucket extends NItemBucket {

	public NWaterBucket(ApiController api, int j) {
		this(api.getItemID(NWaterBucket.class), j);
	}

	public NWaterBucket(int i, int j) {
		super(i, j);
		// TODO Auto-generated constructor stub
	}

}
