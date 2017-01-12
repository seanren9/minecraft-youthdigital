package mymod.dimension;

import mymod.Main;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.world.biome.SpawnListEntry;
import api.dimension.ModBiome;
import api.dimension.gen.ModWorldGenEntry;
import api.dimension.gen.ModWorldGenLakes;
import api.dimension.gen.ModWorldGenTrees;
import api.util.ModColor;

public class MyDimensionBiome_1 extends ModBiome
{
    public MyDimensionBiome_1(int par1)
    {
        super(par1);
        
        this.setBiomeName("My Dimension Biome");

//  BLOCKS        
        this.topBlock =(byte) Block.grass.blockID;
        this.fillerBlock =(byte) Block.dirt.blockID;
        this.undergroundBlock =(byte) Block.stone.blockID;
           
//  MOBS
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.add(new SpawnListEntry(EntityOcelot.class, 10, 1, 5));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 1, 5));

//  FEATURES
        this.setMinMaxHeight(0.5F, 1F);
        this.setTemperatureRainfall(0.9F, 1.2F);
        this.theBiomeDecorator.treesPerChunk =(int) .5;
        
//  TREES        
        ModWorldGenTrees treeGen = new ModWorldGenTrees(10, 20, Block.wood.blockID, 2, Block.leaves.blockID, 2, false);
        treeGen.addLeafHeight(1);
        treeGen.addLeafWidth(3);
        this.worldGeneratorTrees = treeGen;
        
//  LAKES       
        ModWorldGenEntry lakeGen = new ModWorldGenEntry(new ModWorldGenLakes(Block.waterStill.blockID, 16));
        lakeGen.setRarity(5);
        lakeGen.setCount(1);
        addWorldGenerator(lakeGen);
        this.waterColorMultiplier = ModColor.rgbToInt(0, 0, 250);
    }
}