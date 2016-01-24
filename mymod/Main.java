package mymod;

import java.awt.Color;

import mymod.armor.MyArmor;
import mymod.biome.MyBiome;
import mymod.blocks.MyBlock;
import mymod.blocks.MyBlockGen;
import mymod.entity.ghast.MyEntityGhast;
import mymod.entity.ghast.MyRenderGhast;
import mymod.entity.minion.MyEntityMinion;
import mymod.entity.minion.MyModelMinion;
import mymod.entity.minion.MyRenderMinion;
import mymod.items.MyFood;
import mymod.items.MyItem;
import mymod.items.MyPickaxe;
import mymod.items.MySword;
import mymod.proxies.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/* 	MOD INFO */
	@Mod( modid = "mymod", name = "i am a duck mod", version = "1.0")
	@NetworkMod(clientSideRequired=true, serverSideRequired=false)	


public class Main {

/*	PROXY INFO */
	@SidedProxy(clientSide = "mymod.proxies.ClientProxy", serverSide = "mymod.proxies.CommonProxy")
	public static CommonProxy proxy;
		
	
/**	
 * DECLARATION SECTION 
 * *********************************************************** */
   //  DECLARE THE SWORD 
    public static Item MySword_1;
    

    //  DECLARE THE KINGS PICKAXE 
    public static Item MyPickaxe_1;
    
    //  DECLARE THE JADE PICKAXE 
    public static Item MyPickaxe_2;

    //  DECLARE NEW TOOL MATERIAL
    															/** harvest ability, max uses, efficiency (f), damage (f), enchantability */
    public static EnumToolMaterial MyToolMaterial = EnumHelper.addToolMaterial("Material Prototype 9.0", 3, 900000000, 5.0F, 90.0F, 10);

//  DECLARE THE ITEM
    public static Item MyItem_1;
    
    
//  DECLARE THE FOOD
    public static Item MyFood_1;
    
    //  DECLARE THE BLOCK
    public static Block MyBlock_1;
    
//  DECLARE THE ARMOR
    public static Item MyHelmet_1;
    public static Item MyChest_1;
    public static Item MyLeggings_1;
    public static Item MyBoots_1;
    
    
    
    
    //  DECLARE THE ARMOR MATERIAL
   														/**maxDamageFactor, damageReductionAmountArray, enchantability*/
    public static EnumArmorMaterial MyArmorMaterial_1 = EnumHelper.addArmorMaterial("GemStone", 900000, new int[]{30, 80, 60, 30}, 900000000);
    
    
//  DECLARE THE BIOME
    public static  BiomeGenBase MyBiome_1;

//  DECLARE THE MOB ID
    static int MyEntityID = 300;

//  SEARCH FOR UNIQUE ID    
    public static int getUniqueEntityId() {
        do {
            MyEntityID++;
        }
        while (EntityList.getStringFromID(MyEntityID) != null);
        return MyEntityID++;
    }

//  DECLARE A NEW EGG
    public static void registerEntityEgg(Class <? extends Entity> entity, int primaryColor, int secondaryColor) {
        int id = getUniqueEntityId();
        EntityList.IDtoClassMapping.put(id, entity);
        EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
    }

    
/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */	


@EventHandler	
	public  void preInit( FMLPreInitializationEvent event ) 
	{
/**	
 * LOAD SECTION 
 * *********************************************************** */ 


		//  LOAD THE SWORD
        MySword_1 = new MySword(2021, MyToolMaterial, "MySword_1");
        GameRegistry.registerItem(MySword_1, "MySword_1");
        LanguageRegistry.addName(MySword_1, "The Jewel Sword");     

        //  LOAD THE PICKAXE
        MyPickaxe_1 = new MyPickaxe(2022, MyToolMaterial, "MyPickaxe_1");
        GameRegistry.registerItem(MyPickaxe_1, "MyPickaxe_1");
        LanguageRegistry.addName(MyPickaxe_1, "The Kings Pickaxe");
        
        
        //  LOAD THE THE WORST PICKAXE EVER
        MyPickaxe_2 = new MyPickaxe(26725, MyToolMaterial, "MyPickaxe_2");
        GameRegistry.registerItem(MyPickaxe_2, "MyPickaxe_2");
        LanguageRegistry.addName(MyPickaxe_2, "The Jade Pickaxe");
        
        //  LOAD THE ITEM
        MyItem_1 = new MyItem(2030, "MyItem_1").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(15);
        GameRegistry.registerItem(MyItem_1, "MyItem_1");
        LanguageRegistry.addName(MyItem_1, "colorful stone");  
     
        //  LOAD THE FOOD
        /**itemID, healAmount, saturationModifier, isWolfsFavoriteMeat, textureName   */
        MyFood_1 = new MyFood(2040, 10, 10.0F, true, "MyFood_1").setAlwaysEdible().setCreativeTab(CreativeTabs.tabFood);
        GameRegistry.registerItem(MyFood_1, "MyFood_1");
        LanguageRegistry.addName(MyFood_1, "hardy egg"); 
        
        //  LOAD THE BLOCK 
        MyBlock_1 = new MyBlock(250, Material.rock, "MyBlock_1").setLightValue(0.10F).setResistance(20.0F).setHardness(20.0F).setStepSound(Block.soundMetalFootstep);
        GameRegistry.registerBlock(MyBlock_1, "MyBlock_1");
        LanguageRegistry.addName(MyBlock_1, "mixed gem block"); 
		MinecraftForge.setBlockHarvestLevel(MyBlock_1, "pickaxe", 2);
		
		

	    //  LOAD HELMET 
	        MyHelmet_1 = new MyArmor(2060, MyArmorMaterial_1, 0, 0, "myarmor");
	        GameRegistry.registerItem(MyHelmet_1, "MyHelmet_1");
	        LanguageRegistry.addName(MyHelmet_1, "Sapphire Helmet");      
	    
	//  LOAD CHESTPLATE
	        MyChest_1 = new MyArmor(2061, MyArmorMaterial_1, 0, 1, "myarmor");
	        GameRegistry.registerItem(MyChest_1, "MyChest_1");
	        LanguageRegistry.addName(MyChest_1, "Gilded Gold Chestplate");

	//  LOAD LEGGINGS    
	        MyLeggings_1 = new MyArmor(2062, MyArmorMaterial_1, 0, 2, "myarmor");
	        GameRegistry.registerItem(MyLeggings_1, "MyLeggings_1");
	        LanguageRegistry.addName(MyLeggings_1, "Ruby Leggings");

	//  LOAD BOOTS   
	        MyBoots_1 = new MyArmor(2063, MyArmorMaterial_1, 0, 3, "myarmor");
	        GameRegistry.registerItem(MyBoots_1, "MyBoots_1");
	        LanguageRegistry.addName(MyBoots_1, "Emerald Boots");
	        
	    //  LOAD BIOME
	        MyBiome_1 = new MyBiome(30);
	        GameRegistry.addBiome(MyBiome_1);
	        
	//  REMOVE OTHER BIOMES
	        GameRegistry.removeBiome(BiomeGenBase.beach);
	        GameRegistry.removeBiome(BiomeGenBase.desert);
	        GameRegistry.removeBiome(BiomeGenBase.desertHills);
	        GameRegistry.removeBiome(BiomeGenBase.desertHills);
	        GameRegistry.removeBiome(BiomeGenBase.extremeHills);
	        GameRegistry.removeBiome(BiomeGenBase.extremeHillsEdge);
	        GameRegistry.removeBiome(BiomeGenBase.forest);
	        GameRegistry.removeBiome(BiomeGenBase.forestHills);
	        GameRegistry.removeBiome(BiomeGenBase.frozenOcean);
	        GameRegistry.removeBiome(BiomeGenBase.frozenRiver);
	        GameRegistry.removeBiome(BiomeGenBase.iceMountains);
	        GameRegistry.removeBiome(BiomeGenBase.icePlains);
	        GameRegistry.removeBiome(BiomeGenBase.jungle);
	        GameRegistry.removeBiome(BiomeGenBase.jungleHills);
	        GameRegistry.removeBiome(BiomeGenBase.mushroomIsland);
	        GameRegistry.removeBiome(BiomeGenBase.ocean);
	        GameRegistry.removeBiome(BiomeGenBase.plains);
	        GameRegistry.removeBiome(BiomeGenBase.river);
	        GameRegistry.removeBiome(BiomeGenBase.swampland);
	        GameRegistry.removeBiome(BiomeGenBase.taiga);
	        GameRegistry.removeBiome(BiomeGenBase.taigaHills);
	        
	    //  REGISTER YOUR ENTITY
	        EntityRegistry.registerGlobalEntityID(MyEntityGhast.class, "Eyes Of The King", EntityRegistry.findGlobalUniqueEntityId());
	        EntityRegistry.addSpawn(MyEntityGhast.class, 50, 100, 500, EnumCreatureType.monster, BiomeGenBase.desert);
	        EntityRegistry.addSpawn(MyEntityGhast.class, 50, 1, 5, EnumCreatureType.monster, MyBiome_1);     
	        registerEntityEgg(MyEntityGhast.class, (new Color(0, 0, 0)).getRGB(), (new Color(0, 0, 255)).getRGB());
	        RenderingRegistry.registerEntityRenderingHandler(MyEntityGhast.class, new MyRenderGhast());
	        ModLoader.addLocalization("entity.Eyes Of The King.name", "Eyes Of The King");
	        
	    //  REGISTER YOUR ENTITY
	        EntityRegistry.registerGlobalEntityID(MyEntityMinion.class, "Spy", EntityRegistry.findGlobalUniqueEntityId());
	        EntityRegistry.addSpawn(MyEntityMinion.class, 35, 1, 3, EnumCreatureType.creature, MyBiome_1);     
	        //EntityRegistry.addSpawn(MyEntityMinion.class, 50, 1, 5, EnumCreatureType.monster, MyBiome_1);  
	        registerEntityEgg(MyEntityMinion.class, (new Color(104, 104, 104)).getRGB(), (new Color(255, 0, 0)).getRGB());
	        RenderingRegistry.registerEntityRenderingHandler(MyEntityMinion.class, new MyRenderMinion(new MyModelMinion(), 0.3F));
	        ModLoader.addLocalization("entity.Spy.name", "Spy");
        
/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */	

	}

@EventHandler
	public static void init( FMLInitializationEvent event ) 
	{
	
/**	
 * RECIPES SECTION 
 * *********************************************************** */


    //  SWORD RECIPE  
        GameRegistry.addRecipe(new ItemStack(MySword_1, 1), new Object[]
        {
                " X ",
                " X ",
                " S ",
            'S', Item.leather,
            'X', MyItem_1,
        }); 
        
        
        
        //  PICKAXE RECIPE  
            GameRegistry.addRecipe(new ItemStack(MyPickaxe_1, 1), new Object[]
            {
                    "XXX",
                    " S ",
                    " S ",
                'S', Item.stick,
                'X', MyItem_1,
            }); 
            
            
            
            //  PICKAXE RECIPE  
            GameRegistry.addRecipe(new ItemStack(MyPickaxe_1, 1), new Object[]
            {
                    "XXX",
                    " S ",
                    " S ",
                'S', Item.diamond,
                'X', MyItem_1,
            }); 
            
        //  ITEM RECIPE         
            GameRegistry.addRecipe(new ItemStack(MyItem_1, 1), new Object[]
            {
                    "S S",
                    " S ",
                    "S S",
                'S', Item.diamond,
            });

            //  FOOD RECIPE         
            GameRegistry.addRecipe(new ItemStack(MyFood_1, 1), new Object[]
            {
                    " S ",
                    "SXS",
                    " S ",
                'X', Item.egg,
                'S', MyItem_1,           
            });
            
       
            //  SMELTING RECIPE
            GameRegistry.addSmelting(MyBlock_1.blockID, (new ItemStack(MyItem_1, 8)), 10);
            
            
        //  HELMET RECIPE   
            GameRegistry.addRecipe(new ItemStack(MyHelmet_1, 1), new Object[]
            {
                    "XXX",
                    "X X",
                'X', MyItem_1,
            });         

        //  CHESTPLATE RECIPE   
            GameRegistry.addRecipe(new ItemStack(MyChest_1, 1), new Object[]
            {
                    "X X",
                    "XXX",
                    "XXX",
                'X', MyItem_1,
            });         

        //  LEGGINGS RECIPE 
            GameRegistry.addRecipe(new ItemStack(MyLeggings_1, 1), new Object[]
            {
                    "XXX",
                    "X X",
                    "X X",
                'X', MyItem_1,
            });         

        //  BOOTS RECIPE    
            GameRegistry.addRecipe(new ItemStack(MyBoots_1, 1), new Object[]
            {
                    "X X",
                    "X X",
                'X', MyItem_1,
            });             
                       
            
/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */	

	
/**	
 * EXTRA METHODS SECTION 
 * *********************************************************** */
        //  REGISTER THE ORE GENERATION 
            GameRegistry.registerWorldGenerator(new MyBlockGen());

        


/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */	

	
	}

@EventHandler
	public static void postInit( FMLPostInitializationEvent event ) 
	{

	}
	
}
