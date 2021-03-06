package thepowderguy.mcflight.common;

import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import thepowderguy.mcflight.client.InterfaceKeyHandler;
import thepowderguy.mcflight.client.gui.McflightGUIHandler;
import thepowderguy.mcflight.common.entity.biplane.EntityBiplane;
import thepowderguy.mcflight.common.entity.biplane.ItemBiplane;
import thepowderguy.mcflight.common.item.AircraftPaint;
import thepowderguy.mcflight.common.item.BigFuselage;
import thepowderguy.mcflight.common.item.DoubleWing;
import thepowderguy.mcflight.common.item.Flap;
import thepowderguy.mcflight.common.item.Kerosene;
import thepowderguy.mcflight.common.item.Propeller;
import thepowderguy.mcflight.common.item.SmallFuselage;
import thepowderguy.mcflight.common.item.Tail;
import thepowderguy.mcflight.common.item.TurboFan;
import thepowderguy.mcflight.common.item.Wing;
import thepowderguy.mcflight.common.packet.AirplanePacketListener;
import thepowderguy.mcflight.common.packet.AirplaneStateListener;
import thepowderguy.mcflight.common.packet.AirplaneStatePacket;
import thepowderguy.mcflight.common.packet.AirplaneUpdatePacket;
import thepowderguy.mcflight.common.world.BlockOil;
import thepowderguy.mcflight.common.world.Oil;
@Mod.EventBusSubscriber
@Mod(modid = "mcflight", name = "Minecraft Flight Simulator", version = "0.1")
public class Mcflight {
	
	public static String MODID = "mcflight";
	public static String VERSION = "0.1";
	public static SimpleNetworkWrapper network = null;
	public static SimpleNetworkWrapper network2 = null;

	//parts
	public static Item item_flap;
	public static Item item_wing;
	public static Item item_doublewing;
	public static Item item_propeller;
	public static Item item_jetengine;
	public static Item item_bigfuselage;
	public static Item item_smallfuselage;
	public static Item item_tail;
	public static Item item_kerosene;
	
	//aircraft items
	//TODO:
	//* Military fighter aircraft
	//* Jet passenger liner?
	//* Small single wing propeller aircraft
	//* UFO??
	//* Seaplane
	//biplane
	public static Item item_airplane_biplane;
	
	public static Item item_paint;
	
	public static SoundEvent sound_engine;
	public static Oil fluid_oil;
	public static Block block_oil;
	
	public static McflightGUIHandler guihandler = new McflightGUIHandler();
	
	public static Mcflight instance;
	
	//blocks
	
	public static CreativeTabs tab_aircraft = new CreativeTabs("Aircraft")
	{
		public ItemStack getTabIconItem()
		{
				return item_propeller.getDefaultInstance();
		}
	};
	
	{
		FluidRegistry.enableUniversalBucket();
	}
	
	@SidedProxy(clientSide="thepowderguy.mcflight.client.MCFlightClientProxy", serverSide="thepowderguy.mcflight.common.MCFlightCommonProxy")
	public static MCFlightCommonProxy proxy;
	
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		System.out.println("HEYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
		item_flap = new Flap().setUnlocalizedName("flap").setCreativeTab(tab_aircraft).setMaxStackSize(1);
		item_wing = new Wing().setUnlocalizedName("wing").setCreativeTab(tab_aircraft).setMaxStackSize(1);
		item_doublewing = new DoubleWing().setUnlocalizedName("doublewing").setCreativeTab(tab_aircraft).setMaxStackSize(1);
		item_propeller = new Propeller().setUnlocalizedName("propeller").setCreativeTab(tab_aircraft).setMaxStackSize(1);
		item_jetengine = new TurboFan().setUnlocalizedName("jetengine").setCreativeTab(tab_aircraft).setMaxStackSize(1);
		item_bigfuselage = new BigFuselage().setUnlocalizedName("bigfuselage").setCreativeTab(tab_aircraft).setMaxStackSize(1);
		item_smallfuselage = new SmallFuselage().setUnlocalizedName("smallfuselage").setCreativeTab(tab_aircraft).setMaxStackSize(1);
		item_tail = new Tail().setUnlocalizedName("tail").setCreativeTab(tab_aircraft).setMaxStackSize(1);
		item_kerosene = new Kerosene().setUnlocalizedName("kerosene").setCreativeTab(tab_aircraft).setMaxStackSize(4);

		item_airplane_biplane = new ItemBiplane().setUnlocalizedName("biplane").setCreativeTab(tab_aircraft).setMaxStackSize(1);
		
		item_paint = new AircraftPaint().setUnlocalizedName("aircraftpaint").setCreativeTab(tab_aircraft).setMaxStackSize(1);
		
		
		event.getRegistry().register(item_flap.setRegistryName("flap"));
		event.getRegistry().register(item_wing.setRegistryName("wing")			);
		event.getRegistry().register(item_doublewing.setRegistryName("doublewing")		);
		event.getRegistry().register(item_propeller.setRegistryName("propeller")		);
		event.getRegistry().register(item_jetengine.setRegistryName("jetengine")		);
		event.getRegistry().register(item_bigfuselage.setRegistryName("bigfuselage")		);
		event.getRegistry().register(item_smallfuselage.setRegistryName("smallfuselage")	);
		event.getRegistry().register(item_tail.setRegistryName("tail")			);
		event.getRegistry().register(item_kerosene.setRegistryName("kerosene")			);
		
		event.getRegistry().register(item_airplane_biplane.setRegistryName("biplane"));
		event.getRegistry().register(item_paint.setRegistryName("aircraftpaint"));
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {

		fluid_oil = new Oil("oil", new ResourceLocation("mcflight:blocks/oilstill"), new ResourceLocation("mcflight:blocks/oilflowing"));
		FluidRegistry.registerFluid(fluid_oil);
		FluidRegistry.addBucketForFluid(fluid_oil);
		block_oil = new BlockOil(fluid_oil, Material.WATER).setCreativeTab(tab_aircraft).setUnlocalizedName("oil").setRegistryName(new ResourceLocation("mcflight:oil_block"));
	
		event.getRegistry().register(block_oil);//, new ResourceLocation("mcflight:oil_block")
		proxy.RegisterFluidModel((IFluidBlock)block_oil);
	}

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> event) {

		ResourceLocation soundloc = new ResourceLocation("mcflight", "airplane.biplane.engine");
		sound_engine = new SoundEvent(soundloc);
		sound_engine.setRegistryName(soundloc);
		event.getRegistry().register(sound_engine);
		
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
		network = NetworkRegistry.INSTANCE.newSimpleChannel("McflightPos");
		network.registerMessage(AirplanePacketListener.class, AirplaneUpdatePacket.class, 0, Side.SERVER);
		network2 = NetworkRegistry.INSTANCE.newSimpleChannel("McflightState");
		network2.registerMessage(AirplaneStateListener.class, AirplaneStatePacket.class, 0, Side.SERVER);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, guihandler);

		/******* uncomment to enable oil test **********/

		
		
		//Blocks.WATER
       // GameRegistry.register((new OilFlowing(Material.WATER)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water"), new ResourceLocation("mcflight:oil"));
       // registerBlock(9, "water", (new BlockStaticLiquid(Material.WATER)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());

		
		//item_airplane_cessna_skyhawk = new ItemCessnaSkyhawk().setUnlocalizedName("cessna_skyhawk");
		//item_airplane_boeing_737 = new ItemBoeing737().setUnlocalizedName("boeing_737");
		//item_airplane_douglas_dc3 = new ItemDouglasDC3().setUnlocalizedName("douglas_dc3");
		
		//GameRegistry.register(block_oil.setRegistryName("oil"));
        

		proxy.RegisterRenderEntities();

		
		MinecraftForge.EVENT_BUS.register(this);
		
		if (event.getSide() == Side.CLIENT) {
			System.out.println("CLIENT SIDE! " + Minecraft.getMinecraft().getLimitFramerate());
		} else {
			System.out.println("SERVER SIDE!");
		}
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
	    // register server commands

//		event.registerServerCommand(new CommandTestAirplane());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		ResourceLocation group = new ResourceLocation("mcflight", "airplanetab");
		
		GameRegistry.addShapedRecipe(toResrouceLocation("r0"), group, toItemstack(item_flap), "   ", "pii", "   ",
				'p', toIngredient(Blocks.PISTON), 'i', toIngredient(Items.IRON_INGOT));
		GameRegistry.addShapedRecipe(toResrouceLocation("r1"), group, toItemstack(item_wing), " f ", "bbb", "   ",
				'f', toIngredient(item_flap), 'b', toIngredient(Blocks.IRON_BLOCK));
		GameRegistry.addShapelessRecipe(toResrouceLocation("r0"), group, toItemstack(item_doublewing), 
				toIngredient(item_wing), toIngredient(item_wing));
		GameRegistry.addShapedRecipe(toResrouceLocation("r0"), group, toItemstack(item_propeller), "iii", "ipi", "iii",
				'p', toIngredient(Blocks.PISTON), 'i', toIngredient(Items.IRON_INGOT));
		GameRegistry.addShapedRecipe(toResrouceLocation("r0"), group, toItemstack(item_jetengine), "ipi", "ifi", "i i",
				'p', toIngredient(item_propeller), 'i', toIngredient(Items.IRON_INGOT), 'f', toIngredient(Blocks.FURNACE));
		GameRegistry.addShapedRecipe(toResrouceLocation("r0"), group, toItemstack(item_bigfuselage), "bgb", "b b", "bbb",
				'g', toIngredient(Blocks.GLASS), 'b', toIngredient(Blocks.IRON_BLOCK));
		GameRegistry.addShapedRecipe(toResrouceLocation("r0"), group, toItemstack(item_smallfuselage), "   ", "bgb", "bbb",
				'g', toIngredient(Blocks.GLASS), 'b', toIngredient(Blocks.IRON_BLOCK));
		GameRegistry.addShapedRecipe(toResrouceLocation("r0"), group, toItemstack(item_tail), " b ", "fbf", " f ",
				'f', toIngredient(item_flap), 'b', toIngredient(Blocks.IRON_BLOCK));
		
		GameRegistry.addShapedRecipe(toResrouceLocation("r0"), group, toItemstack(item_airplane_biplane), " p ", "dfd", " t ",
				'p', toIngredient(item_propeller), 'd', toIngredient(item_doublewing), 'f', toIngredient(item_smallfuselage), 't', toIngredient(item_tail));
		GameRegistry.addSmelting((UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, (Fluid)fluid_oil)), toItemstack(item_kerosene), 0f);
		ForgeModContainer.getInstance().universalBucket.setCreativeTab(tab_aircraft); // this is kind of not good, prehaps forge will implement a better method to do this
		for (int i = 0; i < 16; ++i)
        {
           GameRegistry.addShapedRecipe(toResrouceLocation("r0"), group, new ItemStack(item_paint, 1, i), new Object[] {"###", "#X#", "###", 'X', new ItemStack(Items.BUCKET), '#', new ItemStack(Items.DYE, 8, i)});
        }
        //ItemColors.
		proxy.RegisterRenderItems();
		EntityRegistry.registerModEntity(new ResourceLocation("mcflight:biplane"), EntityBiplane.class, "biplane", 0, this, 64, 20, true);
		proxy.RegisterRenderGUI();
		MinecraftForge.EVENT_BUS.register(new McflightEventHandler());
		//GameRegistry.registerWorldGenerator(new McflightWorldGenerator(), 10);
		proxy.RegisterKeyBindings();
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		proxy.InjectStuff();
	}
	
	public static Ingredient toIngredient(Block b)
	{
		return Ingredient.func_193369_a(new ItemStack(b));
	}

	public static ItemStack toItemstack(Block b)
	{
		return new ItemStack(b);
	}
	
	public static Ingredient toIngredient(Item i)
	{
		return Ingredient.func_193367_a(i);
	}

	public static ItemStack toItemstack(Item b)
	{
		return new ItemStack(b);
	}
	public static ResourceLocation toResrouceLocation(String name) {
		return new ResourceLocation("mcflight", name);
	}
	
}
