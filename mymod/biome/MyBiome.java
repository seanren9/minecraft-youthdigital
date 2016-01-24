package mymod.biome;

import mymod.Main;
import net.minecraft.block.Block;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityMooshroom;    
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;

public class MyBiome extends BiomeGenBase
{
    public MyBiome(int par1)
    {
        super(par1);
        
        this.setBiomeName("Mob Land!");
        
        this.topBlock = (byte)Main.MyBlock_1.blockID;
        this.fillerBlock = (byte)Block.lavaMoving.blockID;
        
        this.theBiomeDecorator.deadBushPerChunk = 10;
        this.theBiomeDecorator.bigMushroomsPerChunk = 500;

        this.spawnableCreatureList.add(new SpawnListEntry(EntityIronGolem.class, 20, 7, 14));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityDragon.class, 1, 1, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySpider.class, 20, 7, 14));
        
        this.setMinMaxHeight(0.1F, 0.5F);
        this.setTemperatureRainfall(1.5F, 0.2F);
   
    }
}