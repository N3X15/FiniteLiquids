package MCP.mod_finiteliquids;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

public class NEntityLiving extends EntityLiving {

	public NEntityLiving(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	public boolean getJumping() {
		return isJumping;
	}
}
