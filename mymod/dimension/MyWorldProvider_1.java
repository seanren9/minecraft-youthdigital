package mymod.dimension;

import mymod.Main;
import api.dimension.ModSingleBiomeManager;
import api.dimension.ModWorldProvider;

public class MyWorldProvider_1 extends ModWorldProvider {

    public MyWorldProvider_1() {
        super();
        
    //  WORLD PROPERTIES
        
        
        
        
    /** initialize world and sky with the properties above **/
        init();
    }
    
    @Override
    public void registerWorldChunkManager() {
        super.registerWorldChunkManager();
        this.dimensionId = Main.MyDimensionID_1;
        this.worldChunkMgr = new ModSingleBiomeManager(Main.MyDimensionBiome_1, this.dimensionId, this.dimensionId);
    }

}
