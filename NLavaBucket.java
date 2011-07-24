package MCP.mod_finiteliquids;

import MCP.ApiController;

public class NLavaBucket extends NItemBucket {

	public NLavaBucket(ApiController api, int j) {
		this(api.getItemID(NLavaBucket.class), j);
	}

	public NLavaBucket(int i, int j) {
		super(i, j);
		// TODO Auto-generated constructor stub
	}

}
