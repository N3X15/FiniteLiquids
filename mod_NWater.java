// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package MCP.mod_finiteliquids;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

import org.lwjgl.opengl.GL11;

import MCP.ApiController;
import MCP.ApiCraftMgr;
import MCP.Mod;
import MCP.api.BlockBase;
import MCP.api.CustomRenderedBlockBase;

public class mod_NWater extends Mod
{

	public static int id;
	public static int id2;
	public static int humidity;
	private RenderBlocks myRenderBlocks;
	private IBlockAccess myBlockAccess;
	private static Field field_armorList;
	private Field field_modifiers;
	public static int idBlock1;
	public static int idBlock2;
	public static int idBlock6;
	public static int idBlock3;
	public static int idBlock4;
	public static int idBlock5;
	public static int idBlock7;
	public static int idBlock8;
	public static int idBlock9;
	public static int idBlock10;
	public static int idBlock11;
	public static int idBlock12;
	public static int idBlock13;
	public static int idBlock14;
	public static int idBlock15;
	public static int idBlock16;
	public static int idBlock17;
	public static int idBlock18;
	public static int idBlock19;
	public static int idBlock20;
	public static int idBlock21;
	public static int idBlock22;
	public static int idBlock23;
	public static int idBlock24;
	public static int idBlock25;
	public static int idBlock26;
	public static int idBlock27;
	private static PropertyReader propReader = new PropertyReader("mods/DJoslin/FiniteLiquid.properties");
	public static int stW1;
	public static int stW2;
	public static int stW3;
	public static int stW4;
	public static int stW5;
	public static int semi1;
	public static int semi2;
	public static int semi3;
	public static int semi4;
	public static int semi5;
	public static int pipeLimit;
	public static int lakeLimit;
	public static int enableRain;
	public static int rainAmount;
	public static int checkCave;
	public static CustomRenderedBlockBase nwater=null;
	public static BlockBase nwater_still=null;
	public static BlockNWater nlava=null;
	public static BlockBase nlava_still=null;
	public static BlockNWater noil=null;
	public static BlockBase noil_still=null;
	public static BlockNWater nqsand=null;
	public static BlockBase nqsand_still=null;
	public static BlockBase nwater_ocean=null;
	public static BlockBase nwater_pressure=null;
	public static BlockBase pipe=null;
	public static BlockBase pump=null;
	public static BlockBase liquidsensor=null;
	public static BlockBase liquidcompressor=null;
	public static BlockBase grate=null;
	public static BlockBase mossyCobble=null;
	public static BlockBase mossyStone=null;
	public static Item bucketNLava=null;
	public static Item bucketNWater=null;
	public static Item bucketNOil=null;
	public static Item bucketNQSand=null;
	public static Item tankEmpty=null;
	public static Item tankAir=null;
	public static Item tankWater=null;
	public static Item goggles=null;
	public static Item chickenCooked=null;
	public static Item beefCooked=null;
	public static int texx[];


	public mod_NWater(ApiController ctrl)
	{
		super(ctrl);

		stW1 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"SolidToWater1_BlockID",-1,"Solid block");
		stW2 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"SolidToWater2_BlockID", -1,"Solid block");
		stW3 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"SolidToWater3_BlockID", -1,"Solid block");
		stW4 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"SolidToWater4_BlockID", -1,"Solid block");
		stW5 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"SolidToWater5_BlockID", -1,"Solid block");
		semi1 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"SemiSolidToWater1_BlockID", -1,"Semisolid block");
		semi2 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"SemiSolidToWater2_BlockID", -1,"Semisolid block");
		semi3 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"SemiSolidToWater3_BlockID", -1,"Semisolid block");
		semi4 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"SemiSolidToWater4_BlockID", -1,"Semisolid block");
		semi5 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"SemiSolidToWater5_BlockID", -1,"Semisolid block");
		pipeLimit = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"FiniteLiquid_PipeLengthLimit", 512,"Maximum length of a pipe");
		lakeLimit = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"FiniteLiquid_OceanToLakeLimit", 800,"How many blocks must be in a body of water for it to be considered an ocean");
		enableRain = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"FiniteLiquid_RainCreatesFiniteLiquid", 1,"Rain drops water on the ground?");
		rainAmount = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"FiniteLiquid_RainLiquidAmount", 12,"How much water is dropped by the rain?");
		checkCave = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"FiniteLiquid_CheckForCaveFlood", 1,"Check for caves?");
	}


	@Override
	public void onRegisterBlocksAndItems()
	{

		try {
			field_modifiers = (java.lang.reflect.Field.class).getDeclaredField("modifiers");
			field_modifiers.setAccessible(true);
			field_armorList = (net.minecraft.src.RenderPlayer.class).getDeclaredFields()[3];
			field_modifiers.setInt(field_armorList, field_armorList.getModifiers() & 0xffffffef);
			field_armorList.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* Handled by MCPMS
        idBlock1 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NWater_BlockID", "213");
        idBlock2 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NWater_Still_BlockID", "214");
        idBlock6 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NWater_Ocean_BlockID", "217");
        idBlock3 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"Pipe_BlockID", "215");
        idBlock4 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"Pump_BlockID", "216");
        idBlock5 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NWaterBucket_ItemID", "377");
        idBlock7 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NLava_BlockID", "218");
        idBlock8 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NLava_Still_BlockID", "219");
        idBlock9 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NLavaBucket_ItemID", "378");
        idBlock10 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"LiquidSensor_BlockID", "220");
        idBlock11 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"Grate_BlockID", "221");
        idBlock12 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"Compressor_BlockID", "224");
        idBlock13 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"CobblestoneMossy_BlockID", "222");
        idBlock14 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"StoneMossy_BlockID", "223");
        idBlock15 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"CookedChicken_ItemID", "379");
        idBlock16 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"CookedBeef_ItemID", "380");
        idBlock17 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"EmptyTank_ItemID", "381");
        idBlock18 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"WaterTank_ItemID", "382");
        idBlock19 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"AirTank_ItemID", "383");
        idBlock20 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"Goggles_ItemID", "384");
        idBlock21 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NOil_BlockID", "225");
        idBlock22 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NOil_Still_BlockID", "226");
        idBlock23 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NQSand_BlockID", "227");
        idBlock24 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NQSand_Still_BlockID", "228");
        idBlock25 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NQSandBucket_ItemID", "385");
        idBlock26 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"NOilBucket_ItemID", "386");
        idBlock27 = propReader.prop(PropertyReader.ARGU_NULL,new int[0],"Water_Compressed_BlockID", "229");
		 */
		texx = new int[23];
		int txid=0;
		texx[0] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/pipe.png"),txid++);
		texx[1] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/pipegrate.png"),txid++);
		texx[2] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/pump.png"),txid++);
		texx[3] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/pump_on.png"),txid++);
		texx[4] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/pipe_off.png"),txid++);
		texx[5] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/nwater.png"),txid++);
		texx[6] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/nlava.png"),txid++);
		texx[7] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/iron_h1.png"),txid++);
		texx[8] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/iron_h2.png"),txid++);
		texx[9] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/sensor_off.png"),txid++);
		texx[10] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/sensor_on.png"),txid++);
		texx[11] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/grate_off.png"),txid++);
		texx[12] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/grate_on.png"),txid++);
		texx[13] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/newmoss.png"),txid++);
		texx[14] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/newmossstone.png"),txid++);
		texx[15] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/sponge_1.png"),txid++);
		texx[16] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/sponge_2.png"),txid++);
		texx[17] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/noil.png"),txid++);
		texx[18] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/nqsand.png"),txid++);
		texx[19] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/wc_off.png"),txid++);
		texx[20] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/wc_on.png"),txid++);
		texx[21] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/ac_off.png"),txid++);
		texx[22] = api().registerItemIcon(this.imageName(this.getClass(), "gfx/ac_on.png"),txid++);

		nwater 			= new BlockNWater		(api(), texx[5], Material.water);
		nwater_still 	= new BlockNWater_Still	(api(), texx[5], Material.water);
		nwater_ocean 	= new BlockNWater_Ocean	(api());
		nwater_pressure = new BlockNWater_Pressure(api(), texx[5], Material.water);
		nlava 			= new BlockNLava		(api(), texx[6], Material.lava);
		nlava_still 	= new BlockNLava_Still	(api(), texx[6], Material.lava);
		noil 			= new BlockNOil			(api(), texx[17], Material.water);
		noil_still 		= new BlockNOil_Still	(api(), texx[17], Material.water);
		nqsand 			= new BlockNQSand		(api(), texx[18], Material.water);
		nqsand_still 	= new BlockNQSand_Still	(api(), texx[18], Material.water);

		pipe 			= new BlockFPipe			(api());
		pump 			= new BlockPump				(api());
		liquidsensor 	= new BlockLiquidSensor		(api());
		liquidcompressor= new BlockLiquidCompressor	(api());
		grate 			= new BlockGrate			(api());

		mossyCobble 	= new BlockMossy		(api(), texx[13]);
		mossyStone 		= new BlockMossyStone	(api(), texx[14]);

		bucketNWater = (new NWaterBucket(api(), nwater.blockID)).setIconCoord(11,4).setItemName("bucketNWater").setContainerItem(Item.bucketEmpty);
		bucketNLava = (new NLavaBucket(api(), nlava.blockID)).setIconCoord(12,4).setItemName("bucketNLava").setContainerItem(Item.bucketEmpty);
		bucketNOil = (new NOilBucket(api(), noil.blockID));
		bucketNOil = bucketNOil.setIconIndex(api().registerItemIcon(this.imageName(this.getClass(), "gfx/itembucket_oil.png"), txid++)).setItemName("bucketNOil").setContainerItem(Item.bucketEmpty);
		bucketNQSand = (new NSandBucket(api(), nqsand.blockID));
		bucketNQSand=bucketNQSand.setIconIndex(api().registerItemIcon(this.imageName(this.getClass(), "gfx/itembucket_qsand.png"),txid++)).setItemName("bucketNQSand").setContainerItem(Item.bucketEmpty);

		Item.bucketEmpty = (new NItemBucket(Item.bucketEmpty.shiftedIndex,0)).setIconCoord(10, 4);

		tankEmpty = (new ItemEmptyTank(api(), -1, 0, addArmor("tank"), 1)).setItemName("tankEmpty");
		tankEmpty.setIconIndex(api().registerItemIcon(this.imageName(this.getClass(), "gfx/tank_empty.png"),txid++));
		tankAir = (new ItemAirTank(api(), 0, 3, addArmor("tank"), 1)).setItemName("tankAir").setContainerItem(tankEmpty);
		tankAir.setIconIndex(api().registerItemIcon(this.imageName(this.getClass(), "gfx/tank_air.png"),txid++));
		tankWater = (new ItemWaterTank(api(),nwater_pressure.blockID, 0, addArmor("tank"), 1)).setItemName("tankWater").setContainerItem(tankEmpty);
		tankWater.setIconIndex(api().registerItemIcon(this.imageName(this.getClass(), "gfx/tank_water.png"),txid++));
		goggles = (new ItemGoggles(api(), 0, addArmor("tank"), 0)).setItemName("goggles");
		goggles.setIconIndex(api().registerItemIcon(this.imageName(this.getClass(), "gfx/goggles.png"),txid++));

		chickenCooked = (new ItemChickenBoiled(api(), 3, true));
		chickenCooked=chickenCooked.setIconIndex(api().registerItemIcon(this.imageName(this.getClass(), "gfx/cookedchicken.png"),txid++)).setItemName("chickenCooked");
		beefCooked = (new ItemBeefBoiled(api(), 8, true));
		beefCooked=beefCooked.setIconIndex(api().registerItemIcon(this.imageName(this.getClass(), "gfx/cookedbeef.png"),txid++)).setItemName("beefCooked");

		Item.itemsList[Block.sponge.blockID] = (new ItemSponge(Block.sponge.blockID - 256)).setItemName("sponge");

		id = 5000;
		id2 = 5002;
	}
	
	
	@Override
	public void onRegisterRecipes(ApiCraftMgr craftMgr)
	{

		craftMgr.addRecipe(new ItemStack(pipe, 8), new Object[] {
			" I ", "I I", " I ", Character.valueOf('I'), Item.ingotIron
		});
		craftMgr.addRecipe(new ItemStack(goggles, 1), new Object[] {
			"III", "IGI", "I I", Character.valueOf('I'), Item.ingotIron, Character.valueOf('G'), Block.glass
		});
		craftMgr.addRecipe(new ItemStack(tankEmpty, 1), new Object[] {
			" I ", " I ", " I ", Character.valueOf('I'), Item.ingotIron
		});
		craftMgr.addRecipe(new ItemStack(pump, 2), new Object[] {
			" I ", "IFI", " I ", Character.valueOf('I'), Item.ingotIron, Character.valueOf('F'), Block.stoneOvenIdle
		});
		craftMgr.addRecipe(new ItemStack(grate, 6), new Object[] {
			"I I", " I ", "I I", Character.valueOf('I'), Item.ingotIron
		});
		craftMgr.addRecipe(new ItemStack(liquidcompressor, 2), new Object[] {
			"I I", " F ", "I I", Character.valueOf('I'), Item.ingotIron, Character.valueOf('F'), Block.stoneOvenIdle
		});
		craftMgr.addRecipe(new ItemStack(liquidsensor, 4), new Object[] {
			"I I", " R ", "I I", Character.valueOf('I'), Item.ingotIron, Character.valueOf('R'), Item.redstone
		});
		craftMgr.addRecipe(new ItemStack(mossyCobble, 3), new Object[] {
			"   ", "CMC", "   ", Character.valueOf('C'), Block.cobblestone, Character.valueOf('M'), Block.cobblestoneMossy
		});
	}


	public static int addArmor(String s)
	{
		try
		{
			String as[] = (String[])field_armorList.get(null);
			List list = Arrays.asList(as);
			ArrayList arraylist = new ArrayList();
			arraylist.addAll(list);
			if(!arraylist.contains(s))
			{
				arraylist.add(s);
			}
			int i = arraylist.indexOf(s);
			field_armorList.set(null, ((Object) (arraylist.toArray(new String[0]))));
			return i;
		}
		catch(IllegalArgumentException illegalargumentexception)
		{
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public static boolean chunkPass(World world, int i, int j, int k)
	{
		if(world.getClosestPlayer(i, j, k, 96D) != null)
		{
			return world.doChunksNearChunkExist(i, j, k, 10);
		}
		if(world.doChunksNearChunkExist(i, j, k, 10) && world.getBlockId(i, j, k) == nwater_ocean.blockID && (world.getBlockId(i + 1, j, k) == 0 || world.getBlockId(i - 1, j, k) == 0 || world.getBlockId(i, j - 1, k) == 0 || world.getBlockId(i, j, k + 1) == 0 || world.getBlockId(i, j, k - 1) == 0))
		{
			Random random = new Random();
			world.scheduleBlockUpdate(i, j, k, nwater_ocean.blockID, 50 + random.nextInt(500));
		}
		return false;
	}

	public int AddFuel(int i)
	{
		return i != bucketNOil.shiftedIndex ? 0 : 6400 /*GL_COLOR_INDEX*/;
	}

	public boolean OnTickInGame(Minecraft minecraft)
	{
		if(minecraft.theWorld.multiplayerWorld)
		{
			return false;
		}
		if(minecraft.isGamePaused)
		{
			return false;
		}
		Random random = new Random();
		if(humidity > 1310)
		{
			humidity = 1310;
		}
		if(random.nextInt(rainAmount) == 0)
		{
			if(minecraft.theWorld.getWorldInfo().getRaining() & (enableRain == 1))
			{
				if(minecraft.thePlayer != null)
				{
					int i = (int)((minecraft.thePlayer.posX + (double)random.nextInt(16)) - 8D);
					int j = (int)((minecraft.thePlayer.posZ + (double)random.nextInt(16)) - 8D);
					if(minecraft.theWorld.getWorldChunkManager().getBiomeGenAt(i, j).canSpawnLightningBolt())
					{
						boolean flag = false;
						int k = 127;
						for(int l = 1; l < 80; l++)
						{
							if(!((minecraft.theWorld.getBlockId(i, k - l, j) != 0) & (l > 2) & (l < 190)))
							{
								continue;
							}
							boolean flag1 = true;
							if(minecraft.theWorld.getBlockId(i, k - l, j) == Block.crops.blockID)
							{
								flag1 = false;
							}
							k = (127 - l) + 1;
							l = 200;
						}

						minecraft.theWorld.setBlockAndMetadataWithNotify(i, k, j, nwater.blockID, 0);
					}
				}
			} else
				if(humidity > 1300)
				{
					humidity = 0;
					minecraft.theWorld.getWorldInfo().setRaining(true);
					minecraft.theWorld.getWorldInfo().setRainTime(2000);
				}
		}
		return true;
	}

	public void RenderInvBlock(RenderBlocks renderblocks, Block block, int i, int j)
	{
		Tessellator tessellator = Tessellator.instance;
		if(block.blockID == pipe.blockID)
		{
			for(int k = 0; k < 3; k++)
			{
				float f = 0.125F;
				float f1 = 0.15F;
				float f2 = 0.85F;
				float f3 = 0.1F;
				float f4 = 0.9F;
				if(k == 1)
				{
					block.setBlockBounds(f1, f1, f1, f2, f2, f2);
				}
				if(k == 0)
				{
					block.setBlockBounds(f3, f3, 0.0F, f4, f4, f1);
				}
				f = 0.0625F;
				if(k == 2)
				{
					block.setBlockBounds(f3, f3, f2, f4, f4, 1.0F);
				}
				GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, -1F, 0.0F);
				renderblocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderblocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, -1F);
				renderblocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderblocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(-1F, 0.0F, 0.0F);
				renderblocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderblocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
				tessellator.draw();
				GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			}

			block.setBlockBounds(0.05F, 0.05F, 0.05F, 0.95F, 0.95F, 0.95F);
		} else
		{
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.35F, 1.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1F, 0.0F);
			renderblocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderblocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1F);
			renderblocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderblocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1F, 0.0F, 0.0F);
			renderblocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderblocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
			tessellator.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
	}

	public static void handleHealthUpdate(byte byte0, Entity entity, World world)
	{
		Random random = new Random();
		EntityLiving entityliving = (EntityLiving)entity;
		//world.playSoundAtEntity(entity, entityliving.getDeathSound(), entityliving.getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
		entityliving.health = 0;
		world.func_9425_a(entity, (byte)3);
	}

	public static void dropItem(World world, double d, double d1, double d2, Item item)
	{
		EntityItem entityitem = new EntityItem(world, d, d1, d2, new ItemStack(item));
		entityitem.delayBeforeCanPickup = 10;
		world.entityJoinedWorld(entityitem);
	}

	public static void boilWater(World world, int i, int j, int k, Entity entity)
	{
		Random random = new Random();
		if(random.nextInt(10) != 0)
		{
			return;
		}
		if(isBoiling(world, i, j, k) & (random.nextInt(10) == 1) && (entity instanceof EntityLiving))
		{
			EntityLiving entityliving = (EntityLiving)entity;
			if((entity instanceof EntityChicken) & (entityliving.health == 1))
			{
				dropItem(world, entityliving.posX, entityliving.posY, entityliving.posZ, chickenCooked);
				handleHealthUpdate((byte)3, entity, world);
				return;
			}
			if((entity instanceof EntityPig) & (entityliving.health == 1))
			{
				dropItem(world, entityliving.posX, entityliving.posY, entityliving.posZ, Item.porkCooked);
				handleHealthUpdate((byte)3, entity, world);
				return;
			}
			if((entity instanceof EntityCow) & (entityliving.health == 1))
			{
				dropItem(world, entityliving.posX, entityliving.posY, entityliving.posZ, beefCooked);
				handleHealthUpdate((byte)3, entity, world);
				return;
			}
			entityliving.attackEntityFrom(null, 1);
		}
	}

	public static boolean isBoiling(World world, int i, int j, int k)
	{
		boolean flag = false;
		for(int l = 0; l < 8; l++)
		{
			if((world.getBlockId(i - 1, j - l, k) == Block.blockSteel.blockID) & (world.getBlockMetadata(i - 1, j - l, k) > 10) || (world.getBlockId(i + 1, j - l, k) == Block.blockSteel.blockID) & (world.getBlockMetadata(i + 1, j - l, k) > 10) || (world.getBlockId(i, (j + 1) - l, k) == Block.blockSteel.blockID) & (world.getBlockMetadata(i, (j + 1) - l, k) > 10) || (world.getBlockId(i, j - l, k - 1) == Block.blockSteel.blockID) & (world.getBlockMetadata(i, j - l, k - 1) > 10) || (world.getBlockId(i, j - l, k + 1) == Block.blockSteel.blockID) & (world.getBlockMetadata(i, j - l, k + 1) > 10))
			{
				flag = true;
				l = 32;
			}
			if(isWater(world, i, j - l, k))
			{
				continue;
			}
			if((world.getBlockId(i, j - l, k) == Block.blockSteel.blockID) & (world.getBlockMetadata(i, j - l, k) > 10))
			{
				flag = true;
			}
			l = 32;
		}

		return flag;
	}

	public static boolean canMoveAmt(World world, int i, int j, int k, int l)
	{
		int i1 = world.getBlockId(i, j, k);
		if(l < 6)
		{
			if(i1 == Block.plantYellow.blockID)
			{
				return false;
			}
			if(i1 == Block.plantRed.blockID)
			{
				return false;
			}
			if(i1 == Block.mushroomBrown.blockID)
			{
				return false;
			}
			if(i1 == Block.mushroomRed.blockID)
			{
				return false;
			}
			if(i1 == Block.tallGrass.blockID)
			{
				return false;
			}
			if(i1 == Block.deadBush.blockID)
			{
				return false;
			}
			if(i1 == Block.crops.blockID)
			{
				return false;
			}
			if(i1 == Block.rail.blockID)
			{
				return false;
			}
			if(i1 == Block.railPowered.blockID)
			{
				return false;
			}
			if(i1 == Block.railDetector.blockID)
			{
				return false;
			}
			if(i1 == Block.cactus.blockID)
			{
				return false;
			}
			if(i1 == Block.redstoneWire.blockID)
			{
				return false;
			}
			if(i1 == Block.torchWood.blockID)
			{
				return false;
			}
			if((semi1 != -1) & (i1 == semi1))
			{
				return false;
			}
			if((semi2 != -1) & (i1 == semi2))
			{
				return false;
			}
			if((semi3 != -1) & (i1 == semi3))
			{
				return false;
			}
			if((semi4 != -1) & (i1 == semi4))
			{
				return false;
			}
			if((semi5 != -1) & (i1 == semi5))
			{
				return false;
			}
		}
		return canMove(world, i, j, k);
	}

	public static boolean canMove(World world, int i, int j, int k)
	{
		int l = world.getBlockId(i, j, k);
		if((stW1 != -1) & (l == stW1))
		{
			return false;
		}
		if((stW2 != -1) & (l == stW2))
		{
			return false;
		}
		if((stW3 != -1) & (l == stW3))
		{
			return false;
		}
		if((stW4 != -1) & (l == stW4))
		{
			return false;
		}
		if((stW5 != -1) & (l == stW5))
		{
			return false;
		}
		if(l == Block.signPost.blockID)
		{
			return false;
		}
		if(l == Block.lever.blockID)
		{
			return false;
		}
		if(l == Block.leaves.blockID)
		{
			return false;
		}
		if(l == Block.fence.blockID)
		{
			return false;
		}
		if(l == Block.signWall.blockID)
		{
			return false;
		}
		if(l == liquidsensor.blockID)
		{
			return false;
		}
		if(l == Block.blockDiamond.blockID)
		{
			return false;
		}
		if(l == Block.blockSteel.blockID)
		{
			return false;
		}
		if(l == Block.blockGold.blockID)
		{
			return false;
		}
		if(l == Block.mobSpawner.blockID)
		{
			return false;
		}
		if(l == Block.stairCompactPlanks.blockID)
		{
			return false;
		}
		if(l == Block.stairCompactCobblestone.blockID)
		{
			return false;
		}
		if(l == Block.stairDouble.blockID)
		{
			return false;
		}
		if(l == Block.stairSingle.blockID)
		{
			return false;
		}
		if(l == Block.torchRedstoneIdle.blockID)
		{
			return false;
		}
		if(l == Block.torchRedstoneActive.blockID)
		{
			return false;
		}
		if(l == Block.ice.blockID)
		{
			return false;
		}
		if(l == Block.glass.blockID)
		{
			return false;
		}
		if(l == liquidcompressor.blockID)
		{
			return false;
		}
		if(l == Block.pistonStickyBase.blockID)
		{
			return false;
		}
		if(l == Block.pistonBase.blockID)
		{
			return false;
		}
		if(l == Block.pistonExtension.blockID)
		{
			return false;
		}
		if(l == Block.pistonMoving.blockID)
		{
			return false;
		}
		if(l == Block.tilledField.blockID)
		{
			return false;
		}
		if(l == Block.waterStill.blockID)
		{
			return false;
		}
		if(l == Block.waterMoving.blockID)
		{
			return false;
		}
		if(l == Block.lavaMoving.blockID)
		{
			return false;
		}
		if(l == Block.lavaStill.blockID)
		{
			return false;
		}
		if(l == Block.ladder.blockID)
		{
			return false;
		}
		if(l == Block.signPost.blockID)
		{
			return false;
		}
		if(l == Block.doorWood.blockID)
		{
			return false;
		}
		if(l == Block.doorSteel.blockID)
		{
			return false;
		}
		if(l == nwater.blockID)
		{
			return false;
		}
		if(l == nwater_still.blockID)
		{
			return false;
		}
		if(l == grate.blockID)
		{
			return false;
		}
		if(l == nlava.blockID)
		{
			return false;
		}
		if(l == nlava_still.blockID)
		{
			return false;
		}
		if(l == noil.blockID)
		{
			return false;
		}
		if(l == noil_still.blockID)
		{
			return false;
		}
		if(l == nqsand.blockID)
		{
			return false;
		}
		if(l == nqsand_still.blockID)
		{
			return false;
		}
		if(l == nwater_ocean.blockID)
		{
			return false;
		}
		if(l == nwater_pressure.blockID)
		{
			return false;
		}
		if(l == pipe.blockID)
		{
			return false;
		}
		if(l == pump.blockID)
		{
			return false;
		}
		if(l == 0)
		{
			return true;
		}
		return !world.isBlockOpaqueCube(i, j, k);
	}

	public boolean RenderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
	{
		myRenderBlocks = renderblocks;
		myBlockAccess = iblockaccess;
		if(l == id)
		{
			return renderBlockPipe(block, i, j, k);
		}
		if(l == id2)
		{
			return renderBlockNWater(block, i, j, k);
		} else
		{
			return false;
		}
	}

	public boolean canDrawWater(int i, int j, int k)
	{
		if(myBlockAccess.isBlockOpaqueCube(i, j, k))
		{
			return false;
		}
		return !isWater(i, j, k);
	}

	public boolean canDrawWater2(int i, int j, int k)
	{
		return !isWater(i, j, k);
	}

	public boolean isWater(int i, int j, int k)
	{
		if(myBlockAccess.getBlockId(i, j, k) == nwater.blockID)
		{
			return true;
		}
		if(myBlockAccess.getBlockId(i, j, k) == nwater_still.blockID)
		{
			return true;
		}
		if(myBlockAccess.getBlockId(i, j, k) == nlava.blockID)
		{
			return true;
		}
		if(myBlockAccess.getBlockId(i, j, k) == nlava_still.blockID)
		{
			return true;
		}
		if(myBlockAccess.getBlockId(i, j, k) == noil.blockID)
		{
			return true;
		}
		if(myBlockAccess.getBlockId(i, j, k) == noil_still.blockID)
		{
			return true;
		}
		if(myBlockAccess.getBlockId(i, j, k) == nqsand.blockID)
		{
			return true;
		}
		if(myBlockAccess.getBlockId(i, j, k) == nqsand_still.blockID)
		{
			return true;
		}
		return myBlockAccess.getBlockId(i, j, k) == nwater_ocean.blockID;
	}

	public static boolean isWater(World world, int i, int j, int k)
	{
		if(world.getBlockId(i, j, k) == nwater.blockID)
		{
			return true;
		}
		if(world.getBlockId(i, j, k) == nwater_still.blockID)
		{
			return true;
		}
		if(world.getBlockId(i, j, k) == nlava.blockID)
		{
			return true;
		}
		if(world.getBlockId(i, j, k) == nlava_still.blockID)
		{
			return true;
		}
		if(world.getBlockId(i, j, k) == noil.blockID)
		{
			return true;
		}
		if(world.getBlockId(i, j, k) == noil_still.blockID)
		{
			return true;
		}
		if(world.getBlockId(i, j, k) == nqsand.blockID)
		{
			return true;
		}
		if(world.getBlockId(i, j, k) == nqsand_still.blockID)
		{
			return true;
		}
		return world.getBlockId(i, j, k) == nwater_ocean.blockID;
	}

	public boolean isFWater(int i, int j, int k)
	{
		if(myBlockAccess.getBlockId(i, j, k) == nwater.blockID)
		{
			return true;
		}
		if(myBlockAccess.getBlockId(i, j, k) == nlava.blockID)
		{
			return true;
		}
		if(myBlockAccess.getBlockId(i, j, k) == noil.blockID)
		{
			return true;
		}
		return myBlockAccess.getBlockId(i, j, k) == nqsand.blockID;
	}

	public static int isPistonWater(int i, int j)
	{
		if(i == 0)
		{
			return 1;
		}
		if(i == nwater.blockID)
		{
			return 200 + j;
		}
		if(i == nwater_still.blockID)
		{
			return 200 + j;
		}
		if(i == nwater_ocean.blockID)
		{
			return 215;
		}
		if(i == nwater_pressure.blockID)
		{
			return 215;
		}
		if(i == nlava.blockID)
		{
			return 300 + j;
		}
		if(i == nlava_still.blockID)
		{
			return 300 + j;
		}
		if(i == noil.blockID)
		{
			return 400 + j;
		}
		if(i == noil_still.blockID)
		{
			return 400 + j;
		}
		if(i == nqsand.blockID)
		{
			return 500 + j;
		}
		if(i == nqsand_still.blockID)
		{
			return 500 + j;
		} else
		{
			return 0;
		}
	}

	public static boolean attemptPlace(World world, int i, int j, int k, int l, int i1, int j1)
	{
		if(world.getBlockId(i, j, k) == 0 || world.getBlockId(i, j, k) == l || world.getBlockId(i, j, k) == i1)
		{
			int k1 = world.getBlockMetadata(i, j, k);
			if(k1 > 14)
			{
				return false;
			}
			k1 += j1;
			int l1 = 0;
			if(k1 > 15)
			{
				l1 = k1 - 15;
				k1 = 15;
			}
			world.setBlockAndMetadata(i, j, k, l, k1);
			if(l1 > 0)
			{
				attemptPlace(world, i, j + 1, k, l, i1, l1);
			}
			return true;
		} else
		{
			return false;
		}
	}

	public static void dealWithPistonWater(World world, int i, int j, int k, int l, int i1)
	{
		boolean flag = false;
		boolean flag2 = false;
		boolean flag3 = false;
		byte byte0 = 0;
		byte byte2 = 0;
		byte byte4 = 0;
		if(j > -1)
		{
			flag3 = true;
		}
		if((i == 1) & flag3)
		{
			if(j == 0)
			{
				byte2 = -1;
			}
			if(j == 1)
			{
				byte2 = 1;
			}
			if(j == 2)
			{
				byte4 = -1;
			}
			if(j == 3)
			{
				byte4 = 1;
			}
			if(j == 4)
			{
				byte0 = -1;
			}
			if(j == 5)
			{
				byte0 = 1;
			}
			int j1 = world.getBlockId(k + byte0, l + byte2, i1 + byte4);
			int l1 = world.getBlockMetadata(k + byte0, l + byte2, i1 + byte4);
			if(j1 == liquidcompressor.blockID)
			{
				l1++;
				if(l1 < 8)
				{
					l1 = 8;
				}
				if(l1 > 15)
				{
					l1 = 15;
				}
				world.setBlockMetadataWithNotify(k + byte0, l + byte2, i1 + byte4, l1);
			}
		}
		if(i > 199)
		{
			int k1 = nwater_still.blockID;
			int i2 = nwater.blockID;
			int j2 = nwater_ocean.blockID;
			char c = '\310';
			if((i > 299) & (i < 400))
			{
				k1 = nlava_still.blockID;
				i2 = nlava.blockID;
				j2 = -123;
				c = '\u012C';
			}
			if((i > 399) & (i < 500))
			{
				k1 = noil_still.blockID;
				i2 = noil.blockID;
				j2 = -123;
				c = '\u0190';
			}
			if((i > 499) & (i < 600))
			{
				k1 = nqsand_still.blockID;
				i2 = nqsand.blockID;
				j2 = -123;
				c = '\u01F4';
			}
			boolean flag1 = true;
			do
			{
				if(!flag1)
				{
					break;
				}
				if(!flag2)
				{
					flag2 = true;
					flag1 = false;
				}
				byte byte1 = 0;
				byte byte3 = 0;
				byte byte5 = 0;
				if(j == -1)
				{
					flag1 = true;
				}
				if(flag1 && ++j == 5)
				{
					flag1 = false;
				}
				if(j == 0)
				{
					byte3 = -1;
				}
				if(j == 1)
				{
					byte3 = 1;
				}
				if(j == 2)
				{
					byte5 = -1;
				}
				if(j == 3)
				{
					byte5 = 1;
				}
				if(j == 4)
				{
					byte1 = -1;
				}
				if(j == 5)
				{
					byte1 = 1;
				}
				int k2 = world.getBlockId(k + byte1, l + byte3, i1 + byte5);
				if((k2 == liquidcompressor.blockID) & flag3)
				{
					if((c > '\u012B') & (c < '\u01F4'))
					{
						world.createExplosion(null, k + byte1, l + byte3, i1 + byte5, 2.0F);
						return;
					}
					int l2 = i - c;
					if(l2 >= 8)
					{
						int j3 = world.getBlockMetadata(k + byte1, l + byte3, i1 + byte5);
						if(j3 > 7)
						{
							j3 = 0;
						}
						if(j3 < 7)
						{
							j3++;
							world.setBlockMetadata(k + byte1, l + byte3, i1 + byte5, j3);
						} else
						{
							boolean flag5 = false;
							if(!flag5)
							{
								flag5 = attemptPlace(world, k + byte1, (l + byte3) - 1, i1 + byte5, i2, k1, l2);
							}
							if(!flag5)
							{
								flag5 = attemptPlace(world, k + byte1 + 1, l + byte3, i1 + byte5, i2, k1, l2);
							}
							if(!flag5)
							{
								flag5 = attemptPlace(world, (k + byte1) - 1, l + byte3, i1 + byte5, i2, k1, l2);
							}
							if(!flag5)
							{
								flag5 = attemptPlace(world, k + byte1, l + byte3, i1 + byte5 + 1, i2, k1, l2);
							}
							if(!flag5)
							{
								flag5 = attemptPlace(world, k + byte1, l + byte3, (i1 + byte5) - 1, i2, k1, l2);
							}
							if(!flag5)
							{
								flag5 = attemptPlace(world, k + byte1, l + byte3 + 1, i1 + byte5, i2, k1, l2);
							}
							if(!flag5)
							{
								world.createExplosion(null, k + byte1, l + byte3, i1 + byte5, 2.0F);
							}
						}
					} else
					{
						boolean flag4 = false;
						if(!flag4)
						{
							flag4 = attemptPlace(world, k + byte1, (l + byte3) - 1, i1 + byte5, i2, k1, l2);
						}
						if(!flag4)
						{
							flag4 = attemptPlace(world, k + byte1 + 1, l + byte3, i1 + byte5, i2, k1, l2);
						}
						if(!flag4)
						{
							flag4 = attemptPlace(world, (k + byte1) - 1, l + byte3, i1 + byte5, i2, k1, l2);
						}
						if(!flag4)
						{
							flag4 = attemptPlace(world, k + byte1, l + byte3, i1 + byte5 + 1, i2, k1, l2);
						}
						if(!flag4)
						{
							flag4 = attemptPlace(world, k + byte1, l + byte3, (i1 + byte5) - 1, i2, k1, l2);
						}
						if(!flag4)
						{
							flag4 = attemptPlace(world, k + byte1, l + byte3 + 1, i1 + byte5, i2, k1, l2);
						}
						if(!flag4)
						{
							world.createExplosion(null, k + byte1, l + byte3, i1 + byte5, 2.0F);
						}
					}
					System.out.print((new StringBuilder()).append(l2).append("c\n").toString());
					return;
				}
				if(k2 == k1 || k2 == i2 || k2 == j2)
				{
					int i3 = world.getBlockMetadata(k + byte1, l + byte3, i1 + byte5);
					if(k2 == j2)
					{
						i3 = 15;
					}
					int k3 = i - c;
					if(i3 + k3 < 16)
					{
						world.setBlockAndMetadataWithNotify(k + byte1, l + byte3, i1 + byte5, i2, i3 + k3);
						flag1 = false;
					} else
					{
						int l3 = world.getBlockId(k + byte1, l + byte3 + 1, i1 + byte5);
						if(l3 != j2)
						{
							world.setBlockMetadata(k + byte1, l + byte3, i1 + byte5, 15);
						}
						if(l3 == 0)
						{
							world.setBlockAndMetadataWithNotify(k + byte1, l + byte3 + 1, i1 + byte5, i2, (i3 + k3) - 15);
						}
						if(l3 == i2 || l3 == k1)
						{
							int i4 = (world.getBlockMetadata(k + byte1, l + byte3 + 1, i1 + byte5) + (i3 + k3)) - 15;
							if(i4 < 0)
							{
								i4 = 0;
							}
							if(i4 < 15)
							{
								i4 = 15;
							}
							world.setBlockAndMetadataWithNotify(k + byte1, l + byte3 + 1, i1 + byte5, i2, i4);
						}
						flag1 = false;
					}
				}
			} while(true);
		}
	}

	public boolean isLDWater(int i, int j, int k)
	{
		return (myBlockAccess.getBlockId(i, j, k) == 0) & isWater(i, j - 1, k) && (!isWater(i - 1, j, k) && !isWater(i + 1, j, k) && !isWater(i, j, k + 1) && !isWater(i, j, k - 1));
	}

	public static int getMetaAvg(World world, int i, int j, int k)
	{
		int l = world.getBlockMetadata(i, j, k);
		if(isWater(world, i - 1, j, k))
		{
			l += world.getBlockMetadata(i - 1, j, k);
		}
		if(isWater(world, i + 1, j, k))
		{
			l += world.getBlockMetadata(i + 1, j, k);
		}
		if(isWater(world, i, j, k - 1))
		{
			l += world.getBlockMetadata(i, j, k - 1);
		}
		if(isWater(world, i, j, k + 1))
		{
			l += world.getBlockMetadata(i, j, k + 1);
		}
		if(isWater(world, i - 1, j, k - 1))
		{
			l += world.getBlockMetadata(i - 1, j, k - 1);
		}
		if(isWater(world, i + 1, j, k + 1))
		{
			l += world.getBlockMetadata(i + 1, j, k + 1);
		}
		if(isWater(world, i + 1, j, k - 1))
		{
			l += world.getBlockMetadata(i + 1, j, k - 1);
		}
		if(isWater(world, i - 1, j, k + 1))
		{
			l += world.getBlockMetadata(i - 1, j, k + 1);
		}
		return l;
	}

	public static int getH(World world, int i, int j, int k)
	{
		if(world.getBlockId(i, j, k) == nwater_ocean.blockID)
		{
			return 15;
		} else
		{
			return world.getBlockMetadata(i, j, k);
		}
	}

	public static float getMetaAvg2(World world, int i, int j, int k)
	{
		int l = world.getBlockMetadata(i, j, k);
		int i1 = 1;
		if(isWater(world, i - 1, j, k))
		{
			l += getH(world, i - 1, j, k);
			i1++;
		}
		if(isWater(world, i + 1, j, k))
		{
			l += getH(world, i + 1, j, k);
			i1++;
		}
		if(isWater(world, i, j, k - 1))
		{
			l += getH(world, i, j, k - 1);
			i1++;
		}
		if(isWater(world, i, j, k + 1))
		{
			l += getH(world, i, j, k + 1);
			i1++;
		}
		if(isWater(world, i - 1, j, k - 1))
		{
			l += getH(world, i - 1, j, k - 1);
			i1++;
		}
		if(isWater(world, i + 1, j, k + 1))
		{
			l += getH(world, i + 1, j, k + 1);
			i1++;
		}
		if(isWater(world, i + 1, j, k - 1))
		{
			l += getH(world, i + 1, j, k - 1);
			i1++;
		}
		if(isWater(world, i - 1, j, k + 1))
		{
			l += getH(world, i - 1, j, k + 1);
			i1++;
		}
		return (float)l / (float)i1;
	}

	public static void waterPush(World world, int i, int j, int k, Entity entity, Vec3D vec3d)
	{
		int l = j;
		if(isWater(world, i, l + 1, k))
		{
			l++;
		}
		if(isWater(world, i, l + 1, k))
		{
			l++;
		}
		float f = compare2(world, i, l, k, i + 1, l, k);
		f -= compare2(world, i, l, k, i - 1, l, k);
		float f1 = 0.0F;
		float f2 = compare2(world, i, l, k, i, l, k + 1);
		f2 -= compare2(world, i, l, k, i, l, k - 1);
		vec3d.xCoord += f;
		vec3d.yCoord += f1;
		vec3d.zCoord += f2;
		double d = 0.00050000000000000012D;
		if(entity instanceof EntityLiving)
		{
			if(entity instanceof EntityPlayer)
			{
				entity.motionX += vec3d.xCoord * d;
				entity.motionZ += vec3d.zCoord * d;
				vec3d.xCoord = 0.0D;
				vec3d.yCoord = 0.0D;
				vec3d.zCoord = 0.0D;
				return;
			} else
			{
				entity.motionX += vec3d.xCoord * d * 1.3D;
				entity.motionZ += vec3d.zCoord * d * 1.3D;
				vec3d.xCoord = 0.0D;
				vec3d.yCoord = 0.0D;
				vec3d.zCoord = 0.0D;
				return;
			}
		}
		if(entity instanceof EntityItem)
		{
			entity.motionX += vec3d.xCoord * d * 15D;
			entity.motionZ += vec3d.zCoord * d * 15D;
			vec3d.xCoord = 0.0D;
			vec3d.yCoord = 0.0D;
			vec3d.zCoord = 0.0D;
			return;
		} else
		{
			return;
		}
	}

	public static float compare2(World world, int i, int j, int k, int l, int i1, int j1)
	{
		float f = getMetaAvg2(world, i, j, k);
		float f1;
		f1 = f1 = f + 1.0F;
		boolean flag = false;
		if(world.isBlockOpaqueCube(l, i1, j1))
		{
			f1 = f + 1.0F;
		}
		if(world.getBlockId(l, i1, j1) == 0)
		{
			f1 = f - 1.0F;
		}
		if(isWater(world, l, i1, j1))
		{
			f1 = getMetaAvg2(world, l, i1, j1);
			if((f - f1 == 0.0F) & (world.getBlockMetadata(i, j, k) < world.getBlockMetadata(l, i1, j1)))
			{
				f--;
			}
		}
		if(world.getBlockId(l, i1, j1) == nwater_ocean.blockID)
		{
			f1 = 15F;
		}
		float f2 = (float)Math.pow(f - f1, 3D) / 2.0F;
		if(f2 > 2.0F)
		{
			f2 = 2.0F;
		}
		if(f2 < -2F)
		{
			f2 = -2F;
		}
		return f2;
	}

	public float getHeight(int i, int j, int k, int l, int i1)
	{
		if(myBlockAccess.getBlockId(i, j, k) == nwater_ocean.blockID)
		{
			return 1.0F;
		}
		float f = myBlockAccess.getBlockMetadata(i, j, k) + 1;
		float f1 = myBlockAccess.getBlockMetadata(i, j, k);
		if(isWater(i, j + 1, k))
		{
			return 1.0F;
		}
		boolean flag = true;
		if((myBlockAccess.getBlockId(i + 1, j, k) == 0) & (myBlockAccess.getBlockId(i - 1, j, k) == 0) & (myBlockAccess.getBlockId(i, j, k + 1) == 0) & (myBlockAccess.getBlockId(i, j, k - 1) == 0))
		{
			flag = false;
		}
		if((l == 1) & (i1 == 1))
		{
			int j1 = 1;
			boolean flag1 = false;
			if(myBlockAccess.getBlockId(i + 1, j, k) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i + 1, j, k))
			{
				j1++;
				f = f + (float)myBlockAccess.getBlockMetadata(i + 1, j, k) + 1.0F;
			}
			if(isFWater(i + 1, j + 1, k))
			{
				return 1.0F;
			}
			if(isLDWater(i + 1, j, k))
			{
				return 0.0F;
			}
			if(myBlockAccess.getBlockId(i + 1, j, k + 1) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i + 1, j, k + 1))
			{
				j1++;
				f = f + (float)myBlockAccess.getBlockMetadata(i + 1, j, k + 1) + 1.0F;
			}
			if(isFWater(i + 1, j + 1, k + 1))
			{
				return 1.0F;
			}
			if(myBlockAccess.getBlockId(i, j, k + 1) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i, j, k + 1))
			{
				j1++;
				f = f + (float)myBlockAccess.getBlockMetadata(i, j, k + 1) + 1.0F;
			}
			if(isFWater(i, j + 1, k + 1))
			{
				return 1.0F;
			}
			if(isLDWater(i, j, k + 1))
			{
				return 0.0F;
			} else
			{
				return f / 16F / (float)j1;
			}
		}
		if((l == -1) & (i1 == -1))
		{
			int k1 = 1;
			boolean flag2 = false;
			if(myBlockAccess.getBlockId(i - 1, j, k) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i - 1, j, k))
			{
				k1++;
				f = f + (float)myBlockAccess.getBlockMetadata(i - 1, j, k) + 1.0F;
			}
			if(isFWater(i - 1, j + 1, k))
			{
				return 1.0F;
			}
			if(isLDWater(i - 1, j, k))
			{
				return 0.0F;
			}
			if(myBlockAccess.getBlockId(i - 1, j, k - 1) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i - 1, j, k - 1))
			{
				k1++;
				f = f + (float)myBlockAccess.getBlockMetadata(i - 1, j, k - 1) + 1.0F;
			}
			if(isFWater(i - 1, j + 1, k - 1))
			{
				return 1.0F;
			}
			if(myBlockAccess.getBlockId(i, j, k - 1) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i, j, k - 1))
			{
				k1++;
				f = f + (float)myBlockAccess.getBlockMetadata(i, j, k - 1) + 1.0F;
			}
			if(isFWater(i, j + 1, k - 1))
			{
				return 1.0F;
			}
			if(isLDWater(i, j, k - 1))
			{
				return 0.0F;
			} else
			{
				return f / 16F / (float)k1;
			}
		}
		if((l == 1) & (i1 == -1))
		{
			int l1 = 1;
			boolean flag3 = false;
			if(myBlockAccess.getBlockId(i + 1, j, k) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i + 1, j, k))
			{
				l1++;
				f = f + (float)myBlockAccess.getBlockMetadata(i + 1, j, k) + 1.0F;
			}
			if(isFWater(i + 1, j + 1, k))
			{
				return 1.0F;
			}
			if(isLDWater(i + 1, j, k))
			{
				return 0.0F;
			}
			if(myBlockAccess.getBlockId(i + 1, j, k - 1) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i + 1, j, k - 1))
			{
				l1++;
				f = f + (float)myBlockAccess.getBlockMetadata(i + 1, j, k - 1) + 1.0F;
			}
			if(isFWater(i + 1, j + 1, k - 1))
			{
				return 1.0F;
			}
			if(myBlockAccess.getBlockId(i, j, k - 1) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i, j, k - 1))
			{
				l1++;
				f = f + (float)myBlockAccess.getBlockMetadata(i, j, k - 1) + 1.0F;
			}
			if(isFWater(i, j + 1, k - 1))
			{
				return 1.0F;
			}
			if(isLDWater(i, j, k - 1))
			{
				return 0.0F;
			} else
			{
				return f / 16F / (float)l1;
			}
		}
		if((l == -1) & (i1 == 1))
		{
			int i2 = 1;
			boolean flag4 = false;
			if(myBlockAccess.getBlockId(i - 1, j, k) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i - 1, j, k))
			{
				i2++;
				f = f + (float)myBlockAccess.getBlockMetadata(i - 1, j, k) + 1.0F;
			}
			if(isFWater(i - 1, j + 1, k))
			{
				return 1.0F;
			}
			if(isLDWater(i - 1, j, k))
			{
				return 0.0F;
			}
			if(myBlockAccess.getBlockId(i - 1, j, k + 1) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i - 1, j, k + 1))
			{
				i2++;
				f = f + (float)myBlockAccess.getBlockMetadata(i - 1, j, k + 1) + 1.0F;
			}
			if(isFWater(i - 1, j + 1, k + 1))
			{
				return 1.0F;
			}
			if(myBlockAccess.getBlockId(i, j, k + 1) == nwater_ocean.blockID)
			{
				return 1.0F;
			}
			if(isWater(i, j, k + 1))
			{
				i2++;
				f = f + (float)myBlockAccess.getBlockMetadata(i, j, k + 1) + 1.0F;
			}
			if(isFWater(i, j + 1, k + 1))
			{
				return 1.0F;
			}
			if(isLDWater(i, j, k + 1))
			{
				return 0.0F;
			} else
			{
				return f / 16F / (float)i2;
			}
		} else
		{
			return f / 16F;
		}
	}

	public boolean renderBlockNWater(Block block, int i, int j, int k)
	{
		Tessellator tessellator = Tessellator.instance;
		int l = block.getBlockTextureFromSide(0);
		float f = block.getBlockBrightness(myBlockAccess, i, j, k);
		tessellator.setColorOpaque_F(f, f, f);
		int i1 = (l & 0xf) << 4;
		int j1 = l & 0xf0;
		double d = (float)i1 / 256F;
		double d1 = ((float)i1 + 15.99F) / 256F;
		double d2 = (float)j1 / 256F;
		double d3 = ((float)j1 + 15.99F) / 256F;
		int k1 = myBlockAccess.getBlockMetadata(i, j, k);
		if(myBlockAccess.getBlockId(i, j, k) == nwater_ocean.blockID)
		{
			k1 = 15;
		}
		float f1 = (float)k1 / 16F;
		float f2 = 0.0F;
		if(canDrawWater2(i, j + 1, k))
		{
			tessellator.addVertexWithUV((float)i + 1.0F, (float)j + getHeight(i, j, k, 1, 1), (float)(k + 1), d, d2);
			tessellator.addVertexWithUV((float)i + 1.0F, (float)j + getHeight(i, j, k, 1, -1), (float)(k + 0), d, d3);
			tessellator.addVertexWithUV((float)i + 0.0F, (float)j + getHeight(i, j, k, -1, -1), (float)(k + 0), d1, d3);
			tessellator.addVertexWithUV((float)i + 0.0F, (float)j + getHeight(i, j, k, -1, 1), (float)(k + 1), d1, d2);
		}
		if(canDrawWater(i, j - 1, k))
		{
			tessellator.addVertexWithUV((float)i + 1.0F, (float)j, (float)(k + 1), d, d2);
			tessellator.addVertexWithUV((float)i + 1.0F, (float)j, (float)(k + 0), d, d3);
			tessellator.addVertexWithUV((float)i + 0.0F, (float)j, (float)(k + 0), d1, d3);
			tessellator.addVertexWithUV((float)i + 0.0F, (float)j, (float)(k + 1), d1, d2);
		}
		if(canDrawWater(i - 1, j, k))
		{
			tessellator.addVertexWithUV((float)i, (float)j + getHeight(i, j, k, -1, 1) + f2, (float)(k + 1), d, d2);
			tessellator.addVertexWithUV((float)i, (float)(j + 0) - f2, (float)(k + 1), d, d3);
			tessellator.addVertexWithUV((float)i, (float)(j + 0) - f2, (float)(k + 0), d1, d3);
			tessellator.addVertexWithUV((float)i, (float)j + getHeight(i, j, k, -1, -1) + f2, (float)(k + 0), d1, d2);
		}
		if(canDrawWater(i + 1, j, k))
		{
			tessellator.addVertexWithUV((float)i + 1.0F, (float)j + getHeight(i, j, k, 1, 1) + f2, (float)(k + 1), d, d2);
			tessellator.addVertexWithUV((float)i + 1.0F, (float)(j + 0) - f2, (float)(k + 1), d, d3);
			tessellator.addVertexWithUV((float)i + 1.0F, (float)(j + 0) - f2, (float)(k + 0), d1, d3);
			tessellator.addVertexWithUV((float)i + 1.0F, (float)j + getHeight(i, j, k, 1, -1) + f2, (float)(k + 0), d1, d2);
		}
		if(canDrawWater(i, j, k - 1))
		{
			tessellator.addVertexWithUV((float)i + 1.0F, (float)j + getHeight(i, j, k, 1, -1) + f2, (float)(k + 0), d, d2);
			tessellator.addVertexWithUV((float)i + 1.0F, (float)(j + 0) - f2, (float)(k + 0), d, d3);
			tessellator.addVertexWithUV((float)i, (float)(j + 0) - f2, (float)(k + 0), d1, d3);
			tessellator.addVertexWithUV((float)i, (float)j + getHeight(i, j, k, -1, -1) + f2, (float)(k + 0), d1, d2);
		}
		if(canDrawWater(i, j, k + 1))
		{
			tessellator.addVertexWithUV((float)i + 1.0F, (float)j + getHeight(i, j, k, 1, 1) + f2, (float)(k + 1), d, d2);
			tessellator.addVertexWithUV((float)i + 1.0F, (float)(j + 0) - f2, (float)(k + 1), d, d3);
			tessellator.addVertexWithUV((float)i, (float)(j + 0) - f2, (float)(k + 1), d1, d3);
			tessellator.addVertexWithUV((float)i, (float)j + getHeight(i, j, k, -1, 1) + f2, (float)(k + 1), d1, d2);
		}
		return true;
	}

	public boolean renderBlockPipe(Block block, int i, int j, int k)
	{
		float f = 0.0F;
		float f1 = 0.0F;
		if(myRenderBlocks == null)
		{
			return false;
		}
		if(myBlockAccess == null)
		{
			return false;
		}
		float f2 = 0.15F;
		float f3 = 0.85F;
		float f4 = 0.1F;
		float f5 = 0.9F;
		block.setBlockBounds(f2, f2, f2, f3, f3, f3);
		myRenderBlocks.renderStandardBlock(block, i, j, k);
		if(myBlockAccess.getBlockId(i, j + 1, k) == pipe.blockID || myBlockAccess.getBlockId(i, j + 1, k) == pump.blockID)
		{
			block.setBlockBounds(f4, f3, f4, f5, 1.0F, f5);
			myRenderBlocks.renderStandardBlock(block, i, j, k);
		}
		if(myBlockAccess.getBlockId(i, j - 1, k) == pipe.blockID || myBlockAccess.getBlockId(i, j - 1, k) == pump.blockID)
		{
			block.setBlockBounds(f4, 0.0F, f4, f5, f2, f5);
			myRenderBlocks.renderStandardBlock(block, i, j, k);
		}
		if(myBlockAccess.getBlockId(i - 1, j, k) == pipe.blockID || myBlockAccess.getBlockId(i - 1, j, k) == pump.blockID)
		{
			block.setBlockBounds(0.0F, f4, f4, f2, f5, f5);
			myRenderBlocks.renderStandardBlock(block, i, j, k);
		}
		if(myBlockAccess.getBlockId(i + 1, j, k) == pipe.blockID || myBlockAccess.getBlockId(i + 1, j, k) == pump.blockID)
		{
			block.setBlockBounds(f3, f4, f4, 1.0F, f5, f5);
			myRenderBlocks.renderStandardBlock(block, i, j, k);
		}
		if(myBlockAccess.getBlockId(i, j, k - 1) == pipe.blockID || myBlockAccess.getBlockId(i, j, k - 1) == pump.blockID)
		{
			block.setBlockBounds(f4, f4, 0.0F, f5, f5, f2);
			myRenderBlocks.renderStandardBlock(block, i, j, k);
		}
		if(myBlockAccess.getBlockId(i, j, k + 1) == pipe.blockID || myBlockAccess.getBlockId(i, j, k + 1) == pump.blockID)
		{
			block.setBlockBounds(f4, f4, f3, f5, f5, 1.0F);
			myRenderBlocks.renderStandardBlock(block, i, j, k);
		}
		block.setBlockBounds(0.05F, 0.05F, 0.05F, 0.95F, 0.95F, 0.95F);
		return true;
	}

	public static int convertToActive(int i)
	{
		if(i == nwater.blockID || i == nwater_still.blockID)
		{
			return nwater.blockID;
		}
		if(i == nlava.blockID || i == nlava_still.blockID)
		{
			return nlava.blockID;
		}
		if(i == noil.blockID || i == noil_still.blockID)
		{
			return noil.blockID;
		}
		if(i == nqsand.blockID || i == nqsand_still.blockID)
		{
			return nqsand.blockID;
		} else
		{
			return -1;
		}
	}

	public static boolean checkGoggles(EntityPlayer entityplayer)
	{
		return entityplayer != null && entityplayer.inventory.armorItemInSlot(3) != null && entityplayer.inventory.armorItemInSlot(3).getItem() != null && entityplayer.inventory.armorItemInSlot(3).getItem().shiftedIndex == goggles.shiftedIndex;
	}

	public static boolean canBlockBePlacedAt(World world, int i, int j, int k, int l, boolean flag)
	{
		int i1 = world.getBlockId(j, k, l);
		Block block = Block.blocksList[i1];
		Block block1 = Block.blocksList[i];
		AxisAlignedBB axisalignedbb = block1.getCollisionBoundingBoxFromPool(world, j, k, l);
		if(flag)
		{
			axisalignedbb = null;
		}
		if(axisalignedbb != null && !world.checkIfAABBIsClear(axisalignedbb))
		{
			return false;
		}
		if(block == nwater || block == nwater_still || block == nwater_ocean)
		{
			return true;
		}
		if(block == nlava || block == nlava_still)
		{
			return true;
		}
		if(block == noil || block == noil_still)
		{
			return true;
		}
		if(block == nqsand || block == nqsand_still)
		{
			return true;
		}
		if(block == Block.waterMoving || block == Block.waterStill || block == Block.lavaMoving || block == Block.lavaStill || block == Block.fire || block == Block.snow)
		{
			return true;
		} else
		{
			return i > 0 && block == null && block1.canPlaceBlockAt(world, j, k, l);
		}
	}

	public String Version()
	{
		return "v3";
	}

	@Override
	public String getMinecraftVersion() {
		return "1.7.3";
	}


	@Override
	public String getModAuthor() {
		return "djoslin";
	}


	@Override
	public String getModName() {
		return "FiniteLiquids";
	}


	@Override
	public String getModSystemVersion() {
		return "1.7.1";
	}


	@Override
	public String getModVersion() {
		return "3.0.N3X.1";
	}
}
