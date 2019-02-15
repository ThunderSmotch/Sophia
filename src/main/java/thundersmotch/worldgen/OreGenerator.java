package thundersmotch.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import thundersmotch.sophia.Sophia;
import thundersmotch.sophia.block.ModBlocks;
import thundersmotch.sophia.block.ore.ConfigOres;
import thundersmotch.sophia.block.ore.IConfigOre;

import java.util.ArrayDeque;
import java.util.Random;

public class OreGenerator implements IWorldGenerator {

    public static final String RETRO_NAME = "SophiaOreGen";
    public static OreGenerator instance = new OreGenerator();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generateWorld(random, chunkX, chunkZ, world, true);
    }

    public void generateWorld(Random random, int chunkX, int chunkZ, World world, boolean newGen){
        //If it's not first gen or retrogen is disabled
        if (!newGen && !ConfigOres.RETROGEN){
            return;
        }

        if (world.provider.getDimension() == DimensionType.OVERWORLD.getId()){
            if (ConfigOres.CopperOre.GEN_OVERWORLD){
                addOreSpawn(ModBlocks.blockCopperOre, (byte) OreType.ORE_OVERWORLD.ordinal(), Blocks.STONE, world, random, chunkX * 16, chunkZ * 16, ConfigOres.copperOre);
            } else if(ConfigOres.CopperOre.GEN_NETHER){
                addOreSpawn(ModBlocks.blockCopperOre, (byte) OreType.ORE_NETHER.ordinal(), Blocks.NETHERRACK, world, random, chunkX * 16, chunkZ * 16, ConfigOres.copperOre);
            } else if(ConfigOres.CopperOre.GEN_END){
                addOreSpawn(ModBlocks.blockCopperOre, (byte) OreType.ORE_END.ordinal(), Blocks.END_STONE, world, random, chunkX * 16, chunkZ * 16, ConfigOres.copperOre);
            }
        }

        if (!newGen){
            world.getChunk(chunkX, chunkZ).markDirty();
        }
    }

    private void addOreSpawn(Block block, byte meta, Block targetBlock, World world, Random random, int blockX, int blockZ, IConfigOre c){
        WorldGenMinable minable = new WorldGenMinable(block.getStateFromMeta(meta), (c.MIN_VEIN_SIZE + random.nextInt(c.MAX_VEIN_SIZE-c.MIN_VEIN_SIZE+1)), BlockMatcher.forBlock(targetBlock));
        for (int i = 0; i < c.CHANCES_TO_SPAWN; i++){
            int posX = blockX + random.nextInt(16);
            int posZ = blockZ + random.nextInt(16);
            int posY = c.MIN_Y + random.nextInt(c.MAX_Y-c.MIN_Y);
            minable.generate(world, random, new BlockPos(posX, posY, posZ));
        }
    }

    //RETROGEN
    @SubscribeEvent
    public void handleChunkSaveEvent(ChunkDataEvent.Save event){
        NBTTagCompound tag = event.getData().getCompoundTag(RETRO_NAME);
        if (!tag.hasKey("generated")){
            //If key doesnt exist this is a new chunk were we dont need retrogen
            //Otherwise we save a chunk where retrogen is needed
            tag.setBoolean("generated", true);
        }
        event.getData().setTag(RETRO_NAME, tag);
    }

    @SubscribeEvent
    public void handleChunkLoadEvent(ChunkDataEvent.Load event){
        int dim = event.getWorld().provider.getDimension();

        boolean regen = false;
        NBTTagCompound tag = (NBTTagCompound) event.getData().getTag(RETRO_NAME);
        ChunkPos coord = event.getChunk().getPos();

        if (tag != null){
            boolean generate = ConfigOres.RETROGEN && !tag.hasKey("generated");
            if (generate){
                if (ConfigOres.VERBOSE){
                    Sophia.logger.log(Level.DEBUG, "Queueing Retrogen for chunk: " + coord.toString() + ".");
                }
                regen=true;
            }
        } else {
            regen = ConfigOres.RETROGEN;
        }

        if (regen){
            ArrayDeque<ChunkPos> chunks = WorldTickHandler.chunksToGen.get(dim);

            if (chunks == null){
                WorldTickHandler.chunksToGen.put(dim, new ArrayDeque<>(128));
                chunks = WorldTickHandler.chunksToGen.get(dim);
            }
            if (chunks != null){
                chunks.addLast(coord);
                WorldTickHandler.chunksToGen.put(dim, chunks);
            }
        }

    }
}
