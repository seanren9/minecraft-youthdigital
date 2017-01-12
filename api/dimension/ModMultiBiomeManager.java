package api.dimension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class ModMultiBiomeManager extends WorldChunkManager {

	public ModMultiBiomeManager(long par1, WorldType par3WorldType) {
		super(par1, par3WorldType);
	}
	
	public ModMultiBiomeManager(ModWorldProvider provider, List<BiomeGenBase> biomes) {
		super(provider.getSeed(), newWorldType(biomes));
	}
	
	public static ModWorldType newWorldType(List<BiomeGenBase> biomes) {
		return new ModWorldType(biomes);
	}

}
