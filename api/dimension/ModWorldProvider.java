package api.dimension;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.WorldInfo;
import api.entity.EntityLightningBoltNew;
import api.forge.ModPacketHandler;
import api.util.ModColor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModWorldProvider extends WorldProvider {
    private float[] colorsSunriseSunset = new float[4];
    
    private boolean bHasNoSky;
    private int iAverageGroundLevel = 10;
    private boolean bDoesXZShowFog;
    private String sDimensionName;
    private float fStarBrightness;
    private float fSunSize = 1F;
    private float fMoonSize = 1F;
    private boolean bCanRespawnHere = true;
    private boolean bIsSurfaceWorld = true;
    private float fCloudHeight;
    private String sSunTexture = "textures/environment/sun.png";
    private String sMoonTexture = "textures/environment/moon_phases.png";
    private boolean bSunColorSet;
    private float sunsetRed;
    private float sunsetGreen;
    private float sunsetBlue;
    private float sunsetAlpha;
    //private Color cFogColor;
    private float fogRed = 1;
    private float fogGreen = 1;
    private float fogBlue = 1;
    private boolean bFogColorOverride;
    private float lightningRed = 1;
    private float lightningGreen = 1;
    private float lightningBlue = 1;
    private float skyRed = 1;
    private float skyGreen = 1;
    private float skyBlue = 1;
    private boolean skyColorOverride = false;
    /*private float cloudRed = 1;
    private float cloudGreen = 1;
    private float cloudBlue = 1;*/
    private int cloudColor = 16777215;
    private boolean cloudColorOverride = false;
    
    private int lightningIntensity = 1;

	public int starCount = 1500;
	
	private float gravityAddition = 0F;

	protected int updateLCG = (new Random()).nextInt();
	
	private ResourceLocation resRain = new ResourceLocation("textures/environment/rain.png");
    private ResourceLocation resSnow = new ResourceLocation("textures/environment/snow.png");
    
    public ModWorldProvider() {
        
    }
    
    /**
     * Initialize the sky with the customized values in your world provider
     */
    public void init() {
    	if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            setSkyRenderer(new ModSkyRenderer(this));
        }
    }
	
	public float getLightningIntensity() {
		return lightningIntensity;
	}
	
	/**
	 * Sets intensity of lighting, higher the value the more intense
	 * 
	 * @param lightningIntensity ranges between 0 and 100000
	 */
	public void setLightningIntensity(int lightningIntensity) {
		this.lightningIntensity = MathHelper.clamp_int(lightningIntensity, 0, 100000);
	}

    public void registerWorldChunkManager() {
        this.hasNoSky = bHasNoSky;
        this.shouldMapSpin("EntityPlayer", 12, 23, 43);
    }

    public IChunkProvider createChunkGenerator() {
        if (this.worldChunkMgr instanceof ModMultiBiomeManager) {
            return new ModChunkProviderMultiBiome(this.worldObj, this.worldObj.getSeed(), true);
        } else if (this.worldChunkMgr instanceof ModSingleBiomeManagerEnd) {
        	return new ModChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
        } else {
            return new ModChunkProvider(this.worldObj, this.worldObj.getSeed(), true);
        }
    }

    public int getAverageGroundLevel() {
        return iAverageGroundLevel;
    }

    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int par1, int par2) {
        return bDoesXZShowFog;
    }

    public String getDimensionName() {
        return sDimensionName;
    }
    
    @Override
    public float getStarBrightness(float par1) {
        return worldObj.getStarBrightnessBody(par1) * fStarBrightness;
    }

    public boolean canRespawnHere() {
        return bCanRespawnHere;
    }

    public boolean isSurfaceWorld() {
        return bIsSurfaceWorld;
    }

    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return fCloudHeight;
    }

    @SideOnly(Side.CLIENT)
    public String getSunTexture() {
        return sSunTexture;
    }
    
    @SideOnly(Side.CLIENT)
    public String getMoonTexture() {
        return sMoonTexture;
    }
    
    @SideOnly(Side.CLIENT)
    public ResourceLocation getRainTexture() {
        return resRain;
    }
    
    @SideOnly(Side.CLIENT)
    public ResourceLocation getSnowTexture() {
        return resSnow;
    }
    
    /**
     * Sets a new sun texture
     * 
     * @param parTex
     */
    public void setSunTexture(String parTex) {
        sSunTexture = "mydimensionmod:textures/sky/" + parTex;
    }
    
    /**
     * Sets a new moon texture
     * 
     * @param parTex
     */
    public void setMoonTexture(String parTex) {
        sMoonTexture = "mydimensionmod:textures/sky/" + parTex;
    }
    
    /**
     * Sets a new rain texture
     * 
     * @param parTex
     */
    public void setRainTexture(String parTex) {
        resRain = new ResourceLocation("mydimensionmod:textures/sky/" + parTex);
    }
    
    /**
     * Sets a new snow texture
     * 
     * @param parTex
     */
    public void setSnowTexture(String parTex) {
        resSnow = new ResourceLocation("mydimensionmod:textures/sky/" + parTex);
    }
    
    /**
     * Sets the amount of stars seen in the sky
     * 
     * @param parCount ranges between 0 and 50000 are recommended
     */
    public void setStarCount(int parCount) {
        starCount = parCount;
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    public boolean canCoordinateBeSpawn(int par1, int par2) {
    	if (worldObj == null) return false;
    	
    	return (getSafePoint(par1, par2).posY > 0);
    }

    public ChunkCoordinates getSafePoint(int x, int z) {
    	int k;

        for (k = 254; k > 0 && (worldObj.isAirBlock(x, k, z) || !worldObj.isAirBlock(x, k + 1, z)); --k)
        {
            ;
        }
        
        if (k > 0) {
        	return new ChunkCoordinates(x, k, z);
        } else {
        	return new ChunkCoordinates(x, 255, z);
        }
    }
    
    public ChunkCoordinates getEntrancePortalLocation() {
        return getSafePoint(50, 0);
    }

    protected void generateLightBrightnessTable() {
        super.generateLightBrightnessTable();
        /*float f = 12.0F;
        for (int i = 0; i <= 15; i++) {
            float f1 = 12.0F - i / 15.0F;
            this.lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3.0F + 1.0F)
                    * (1.0F - f) + f);
        }*/
    }

    /**
     * Returns array with sunrise/sunset colors
     */
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float par1, float par2) {
        if(bSunColorSet == true)
        {
        
            float f2 = 0.4F;
            float f3 = MathHelper.cos(par1 * 3.141593F * 2.0F) - 0.0F;
            float f4 = -0.0F;
            if ((f3 >= f4 - f2) && (f3 <= f4 + f2))
            {
                float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
                float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
                f6 *= f6;
                this.colorsSunriseSunset[0] = (f5 * 0.3F + sunsetRed);
                this.colorsSunriseSunset[1] = (f5 * f5 * 0.7F + sunsetGreen);
                this.colorsSunriseSunset[2] = (f5 * f5 * 0.0F + sunsetBlue);
                this.colorsSunriseSunset[3] = f6;
                return this.colorsSunriseSunset;
            }
            return null;
        }
        else
        {
            float f2 = 0.4F;
            float f3 = MathHelper.cos(par1 * 3.141593F * 2.0F) - 0.0F;
            float f4 = -0.0F;
            if ((f3 >= f4 - f2) && (f3 <= f4 + f2))
            {
                float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
                float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
                f6 *= f6;
                this.colorsSunriseSunset[0] = (f5 * 0.3F + 0.7F);
                this.colorsSunriseSunset[1] = (f5 * f5 * 0.7F + 0.2F);
                this.colorsSunriseSunset[2] = (f5 * f5 * 0.0F + 0.2F);
                this.colorsSunriseSunset[3] = f6;
                return this.colorsSunriseSunset;
            }
            return null;
        }
    }

    public float calculateCelestialAngle(long par1, float par3) {
        int j = (int) (par1 % 24000L);
        float f1 = (j + par3) / 24000.0F - 0.25F;
        if (f1 < 0.0F) {
            f1 += 1.0F;
        }
        if (f1 > 1.0F) {
            f1 -= 1.0F;
        }
        float f2 = f1;
        f1 = 1.0F - (float) ((Math.cos(f1 * 3.141592653589793D) + 1.0D) / 2.0D);
        f1 = f2 + (f1 - f2) / 3.0F;
        return f1;
    }

    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float par1, float par2) {
        if(bFogColorOverride == true)
        {
            return this.worldObj.getWorldVec3Pool().getVecFromPool(fogRed, fogGreen, fogBlue);
        }
        else
        {
            float f2 = MathHelper.cos(par1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

            if (f2 < 0.0F)
            {
                f2 = 0.0F;
            }
        
            if (f2 > 1.0F)
            {
                f2 = 1.0F;
            }
        
            float f3 = 0.7529412F;
            float f4 = 0.84705883F;
            float f5 = 1.0F;
            f3 *= f2 * 0.94F + 0.06F;
            f4 *= f2 * 0.94F + 0.06F;
            f5 *= f2 * 0.91F + 0.09F;
            return this.worldObj.getWorldVec3Pool().getVecFromPool((double)f3, (double)f4, (double)f5);
        }
    }
    
    @Override
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
    	if (skyColorOverride) {
    		return Vec3.createVectorHelper(skyRed, skyGreen, skyBlue);
    	} else {
    		return super.getSkyColor(cameraEntity, partialTicks);
    	}
    }
    
    /**
     * Everything below is added *
     */
    
    public void setAverageGroundLevel(int i) {
        iAverageGroundLevel = i;
    }

    @SideOnly(Side.CLIENT)
    public void doesXZShowFog(boolean b) {
        bDoesXZShowFog = b;
    }

    public void getDimensionName(String s) {
        sDimensionName = s;
    }
    
    /**
     * Amplifies normal star brightness for the time of day by this amount
     * 
     * @param brightness ranges between 0.1 and 2.0
     */
    public void setStarBrightness(float brightness) {
        fStarBrightness = brightness;
    }

    /**
     * Amplifies sun size by this value 
     * 
     * @param size range: 0.1 - 15
     */
    public void setSunSize(float size) {
        fSunSize = size;
    }

    /**
     * Amplifies moon size by this value
     * 
     * @param size range: 0.1 - 15
     */
    public void setMoonSize(float size) {
        fMoonSize = size;
    }
    
    public float getSunSize() {
        return fSunSize;
    }
    
    public float getMoonSize() {
        return fMoonSize;
    }

    public void canRespawnHere(boolean b) {
        bCanRespawnHere = b;
    }

    public void isSurfaceWorld(boolean b) {
        bIsSurfaceWorld = b;
    }

    /**
     * Adjusts height of clouds
     * 
     * @param height ranges between 0 and 512 are best
     */
    public void setCloudHeight(float height) {
        fCloudHeight = height;
    }

    /**
     * Sets color of fog
     * 
     * @param red ranges between 0 and 255
     * @param green ranges between 0 and 255
     * @param blue ranges between 0 and 255
     */
    public void setFogColor(int red, int green, int blue) {
        bFogColorOverride = true;
        this.fogRed = (float)red / 255F;
        this.fogGreen = (float)green / 255F;
        this.fogBlue = (float)blue / 255F;
    }
    
    /**
     * Sets color of sunset
     * 
     * @param red ranges between 0 and 255
     * @param green ranges between 0 and 255
     * @param blue ranges between 0 and 255
     */
    public void setSunsetColor(int red, int green, int blue)
    {
        bSunColorSet = true;
        sunsetRed = (float)red / 255F; 
        sunsetGreen = (float)green / 255F;
        sunsetBlue = (float)blue / 255F;
    }
    
    /**
     * Sets color of lightning
     * 
     * @param red ranges between 0 and 255
     * @param green ranges between 0 and 255
     * @param blue ranges between 0 and 255
     */
    public void setLightningColor(int red, int green, int blue) {
    	this.lightningRed = (float)red / 255F;
    	this.lightningGreen = (float)green / 255F;
    	this.lightningBlue = (float)blue / 255F;
    }
    
    /**
     * Sets color of sky
     * 
     * @param red ranges between 0 and 255
     * @param green ranges between 0 and 255
     * @param blue ranges between 0 and 255
     */
    public void setSkyColor(int red, int green, int blue) {
    	this.skyRed = (float)red / 255F;
    	this.skyGreen = (float)green / 255F;
    	this.skyBlue = (float)blue / 255F;
    	this.skyColorOverride = true;
    }
    
    /**
     * Sets the color of clouds
     * 
     * @param red ranges between 0 and 255
     * @param green ranges between 0 and 255
     * @param blue ranges between 0 and 255
     */
    @SideOnly(Side.CLIENT)
    public void setCloudColor(int red, int green, int blue) {
    	cloudColor = ModColor.rgbToInt(red, green, blue);
    	/*this.cloudRed = (float)red / 255F;
    	this.cloudGreen = (float)green / 255F;
    	this.cloudBlue = (float)blue / 255F;*/
    	this.cloudColorOverride = true;
    }
    
    public float getLightningRed() {
		return lightningRed;
	}

	public float getLightningGreen() {
		return lightningGreen;
	}

	public float getLightningBlue() {
		return lightningBlue;
	}
    
    public float getSkyRed() {
		return skyRed;
	}

	public float getSkyGreen() {
		return skyGreen;
	}

	public float getSkyBlue() {
		return skyBlue;
	}

	@Override
    public BiomeGenBase getBiomeGenForCoords(int x, int z) {
        if (this.worldChunkMgr instanceof ModMultiBiomeManager) {
			return worldObj.getBiomeGenForCoordsBody(x, z);
        } else if (this.worldChunkMgr instanceof ModSingleBiomeManagerEnd) {
			return worldObj.getBiomeGenForCoordsBody(x, z);
		} else {
			return this.worldChunkMgr.getBiomeGenAt(x, z);
		}
    }
    
	public float getGravityAddition() {
		return gravityAddition;
	}

	/**
	 * Adds an extra amount of force to entities per game tick to simulate gravity changes
	 * 
	 * @param gravityAddition positive for more gravity, negative for less gravity, eg -0.07F for moon like gravity
	 */
	public void setGravityAddition(float gravityAddition) {
		this.gravityAddition = gravityAddition;
	}
    
    public void tickServer() {
    
    	if (!worldObj.isRemote) {
    
	    	WorldServer world = (WorldServer) worldObj;
	    	
	    	/**
	    	 * Weather code
	    	 */
	    	
	    	world.getWorldInfo().setThunderTime(100);
	    	world.getWorldInfo().setThundering(false);
	    	
	    	Iterator iterator = world.activeChunkSet.iterator();
	
	        final long startTime = System.nanoTime();
	
	        while (iterator.hasNext())
	        {
	            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator.next();
	            int k = chunkcoordintpair.chunkXPos * 16;
	            int l = chunkcoordintpair.chunkZPos * 16;
	            Chunk chunk = world.getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
	            int i1;
	            int j1;
	            int k1;
	            int l1;
	
	            if (/*canDoLightning(chunk) && world.rand.nextInt(100000) == 0 &&*/ world.isRaining()/* && world.isThundering()*/)
	            {
	            	if (lightningIntensity > 0 && (lightningIntensity >= 100000 || world.rand.nextInt((int) (100000 - lightningIntensity)) == 0)) {
		                this.updateLCG = this.updateLCG * 3 + 1013904223;
		                i1 = this.updateLCG >> 2;
		                j1 = k + (i1 & 15);
		                k1 = l + (i1 >> 8 & 15);
		                l1 = world.getPrecipitationHeight(j1, k1);
		
		                if (world.canLightningStrikeAt(j1, l1, k1))
		                {
		                	EntityLightningBoltNew parEnt = (new EntityLightningBoltNew(world, (double)j1, (double)l1, (double)k1));
		                    world.addWeatherEffect(parEnt);
		                    
		                    NBTTagCompound nbt = new NBTTagCompound();
							nbt.setInteger("posX", MathHelper.floor_double(parEnt.posX/* * 32.0D*/));
							nbt.setInteger("posY", MathHelper.floor_double(parEnt.posY/* * 32.0D*/));
							nbt.setInteger("posZ", MathHelper.floor_double(parEnt.posZ/* * 32.0D*/));
							nbt.setInteger("entityID", parEnt.entityId);
							//nbt.setInteger("dimID", parEnt.worldObj.provider.dimensionId);
							
							NBTTagCompound data = new NBTTagCompound();
							data.setTag("data", nbt);
							data.setInteger("dimID", parEnt.worldObj.provider.dimensionId);
							data.setString("packetType", "lightningNew");
							
							PacketDispatcher.sendPacketToAllInDimension(ModPacketHandler.createPacketForServerToClientSerialization("mymodData", data), world.provider.dimensionId);
		                }
	            	}
	            }
	        }
	        
	        /**
	         * Gravity code
	         */
	         
	        for (Object obj : world.loadedEntityList) {
	        	if (obj instanceof EntityLivingBase && !(obj instanceof EntityBat || obj instanceof EntityDragon || obj instanceof EntityWither || obj instanceof EntityGhast)) {
	        		EntityLivingBase ent = (EntityLivingBase) obj;
	        		if (ent.worldObj.getHeightValue(MathHelper.floor_double(ent.posX), MathHelper.floor_double(ent.posZ)) > 0) {
	        			applyGravity(ent);
	        		}
	        	}
	        }
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public void tickClient() {
    
    	if (!worldObj.isRemote && (Minecraft.getMinecraft().currentScreen == null || !Minecraft.getMinecraft().currentScreen.doesGuiPauseGame())) {
    		World world = Minecraft.getMinecraft().theWorld;
    		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    		if (player != null) {
    			if (!player.capabilities.isFlying) {
	    			if (player.worldObj.provider.dimensionId == dimensionId) {
		    			if (gravityAddition != 0) {
		    				if (player.worldObj.getHeightValue(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posZ)) > 0) {
		    					applyGravity(player);
		    				}
		    			}
	    			}
    			}
    		}
    		
    		/*if (world != null) {
	    		for (Object obj : world.loadedEntityList) {
		        	if (obj instanceof EntityLivingBase) {
		        		EntityLivingBase ent = (EntityLivingBase) obj;
		        		applyGravity(ent);
		        	}
		        }
    		}*/
    			
    	}
    	
    }
    
    public void applyGravity(EntityLivingBase ent) {
    	if (ent.motionY > 0.8F) {
    		//System.out.println("motionY: " + ent.motionY);
    	} else {
	    	if (!ent.onGround) {
	    		ent.motionY -= gravityAddition;
	    	}
	    	if (gravityAddition < 0) {
	    		ent.fallDistance = 0;
	    	}
    	}
    	
    }
    
    @Override
    public boolean canDoLightning(Chunk chunk) {
    	/*// TODO Auto-generated method stub
    	return super.canDoLightning(chunk);*/
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 drawClouds(float partialTicks) {
    	float f1 = worldObj.getCelestialAngle(partialTicks);
        float f2 = MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        float f3 = (float)(cloudColor >> 16 & 255L) / 255.0F;
        float f4 = (float)(cloudColor >> 8 & 255L) / 255.0F;
        float f5 = (float)(cloudColor & 255L) / 255.0F;
        float f6 = worldObj.getRainStrength(partialTicks);
        float f7;
        float f8;

        if (f6 > 0.0F)
        {
            f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
            f8 = 1.0F - f6 * 0.95F;
            f3 = f3 * f8 + f7 * (1.0F - f8);
            f4 = f4 * f8 + f7 * (1.0F - f8);
            f5 = f5 * f8 + f7 * (1.0F - f8);
        }

        f3 *= f2 * 0.9F + 0.1F;
        f4 *= f2 * 0.9F + 0.1F;
        f5 *= f2 * 0.85F + 0.15F;
        f7 = worldObj.getWeightedThunderStrength(partialTicks);

        if (f7 > 0.0F)
        {
            f8 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
            float f9 = 1.0F - f7 * 0.95F;
            f3 = f3 * f9 + f8 * (1.0F - f9);
            f4 = f4 * f9 + f8 * (1.0F - f9);
            f5 = f5 * f9 + f8 * (1.0F - f9);
        }

        return worldObj.getWorldVec3Pool().getVecFromPool((double)f3, (double)f4, (double)f5);
    }
    
    @Override
    public ChunkCoordinates getSpawnPoint() {
    	WorldInfo info = worldObj.getWorldInfo();
    	return getSafePoint(info.getSpawnX(), info.getSpawnZ());
    }
}