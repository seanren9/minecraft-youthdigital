package api.dimension;

import java.util.List;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;

public class ModWorldType extends WorldType {
	
	public static int lastIDFound = -1;

	public ModWorldType(/*int par1, String par2Str, */List<BiomeGenBase> biomes) {
		super(getFreeID(), getNameFromID());
		this.biomesForWorldType = new BiomeGenBase[biomes.size()];
		this.biomesForWorldType = biomes.toArray(this.biomesForWorldType);
	}
	public static int getFreeID() {
		for (int i = 0; i < WorldType.worldTypes.length; i++) {
			if (WorldType.worldTypes[i] == null) {
				lastIDFound = i;
				return i;
			}
		}
		System.out.println("Out of free IDs for multi biome dimensions! You have too many dimensions!");
		return -1;
	}
	
	public static String getNameFromID() {
		return "ydWorldType_" + lastIDFound;
	}

}
