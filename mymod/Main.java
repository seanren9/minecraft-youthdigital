package mymod;

import mymod.dimension.MyDimensionBiome_1;
import mymod.dimension.MyWorldProvider_1;
import mymod.portal.MyFrameBlock_1;
import mymod.portal.MyPlacer;
import mymod.portal.MyPortalBlock_1;
import mymod.portal.MyPortalShape_1;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import api.ModAPI;
import api.Structure;
import api.forge.ModPacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


/* 	MOD INFO */
	@Mod( modid = "mydimensionmod", name = "Kings Dimension Mod", version = "1.0")
	@NetworkMod(clientSideRequired=true, serverSideRequired=false, packetHandler = ModPacketHandler.class, channels = { "mymodData" })	


public class Main {

	
/**	
 * DECLARATION SECTION 
 * *********************************************************** */
	//DECLARE THE DIMENSION ID
		public static int MyDimensionID_1 = 2;
	//  DECLARE THE PORTAL BLOCKS
        public static Block MyPortalBlock_1;
        public static Block MyFrameBlock_1;
    
    //  DECLARE THE PORTAL STRUCTURE
        public static Structure MyPortal_1;

    //  DECLARE THE PLACER
        public static Item MyPlacer_1;
        
    //  DECLARE THE DIMENSION BIOME
        public static BiomeGenBase MyDimensionBiome_1;
        
        
/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */	


@EventHandler	
	public  void preInit( FMLPreInitializationEvent event ) 
	{
/**	
 * LOAD SECTION 
 * *********************************************************** */ 


//  LOAD THE PORTAL BLOCK
    MyPortalBlock_1 = new MyPortalBlock_1(2101, "MyPortalBlock_1");
    GameRegistry.registerBlock(MyPortalBlock_1, "MyPortalBlock_1");

//  LOAD THE FRAME BLOCK
    MyFrameBlock_1 = new MyFrameBlock_1(2102, Material.rock, "MyFrameBlock_1");
    GameRegistry.registerBlock(MyFrameBlock_1, "MyFrameBlock_1");
    LanguageRegistry.addName(MyFrameBlock_1, "Pro Block");
    
//  LOAD THE PLACER
    MyPlacer_1 = new MyPlacer(2103, "MyPlacer_1");
    GameRegistry.registerItem(MyPlacer_1, "MyPlacer_1");
    LanguageRegistry.addName(MyPlacer_1, "DimesionCrafter"
    		+ "");
    //  LOAD THE PORTAL SHAPE
    MyPortal_1 = new MyPortalShape_1();
/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */	
    	
	}

@EventHandler
	public static void init( FMLInitializationEvent event ) 
	{
		ModAPI.init();
	
/**	
 * RECIPES SECTION 
 * *********************************************************** */

	//  PORTAL FRAME BLOCK RECIPE
        GameRegistry.addRecipe(new ItemStack(MyFrameBlock_1, 1), new Object[]
        {
                "SSS",
                "SRX",
                "XXX",
              'R', Block.anvil,
              'X', Item.swordDiamond,
              'S', Item.diamond,
        });

   
/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */	

	
/**	
 * EXTRA METHODS SECTION 
 * *********************************************************** */
        //  REGISTER THE DIMENSION BIOME
        MyDimensionBiome_1 = new MyDimensionBiome_1(40);

    //  REGISTER WORLD PROVIDER
        DimensionManager.registerProviderType(MyDimensionID_1, MyWorldProvider_1.class, true);
        DimensionManager.registerDimension(MyDimensionID_1, MyDimensionID_1);



/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */	

	
	}

@EventHandler
	public static void postInit( FMLPostInitializationEvent event ) 
	{

	}
	
}
