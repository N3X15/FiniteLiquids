package MCP.mod_finiteliquids;

import MCP.ApiController;

public class NOilBucket extends NItemBucket {

	public NOilBucket(ApiController api, int j) {
		this(api.getItemID(NOilBucket.class), j);
	}

	public NOilBucket(int i, int j) {
		super(i, j);
		// TODO Auto-generated constructor stub
	}

}
