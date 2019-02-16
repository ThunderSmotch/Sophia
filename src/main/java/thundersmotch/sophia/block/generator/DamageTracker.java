package thundersmotch.sophia.block.generator;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

public class DamageTracker {

    public static final DamageTracker instance = new DamageTracker();

    private Map<Integer, Map<BlockPos, Set<UUID>>> tracking = new HashMap<>();

    public void reset(){
        tracking.clear();
    }

    /***
     * Registers a new entity in our damage tracker.
     * @param dimension The dimension of the entity
     * @param pos The nearby tileentity to this entity
     * @param entity The uuid of the entity
     */
    public void register(Integer dimension, BlockPos pos, UUID entity){
        if (!tracking.containsKey(dimension)){
            tracking.put(dimension, new HashMap<>());
        }
        Map<BlockPos, Set<UUID>> dimTracking = tracking.get(dimension);
        if (!dimTracking.containsKey(pos)){
            dimTracking.put(pos, new HashSet<>());
        }
        dimTracking.get(pos).add(entity);
    }

    //Remove the nearby tile entity tracker
    public void remove(Integer dimension, BlockPos pos){
        if (tracking.containsKey(dimension)){
            tracking.get(dimension).remove(pos);
        }
    }

    //Clear the list of nearby entities to the tile entity
    public void clear(Integer dimension, BlockPos pos){
        if (tracking.containsKey(dimension)){
            Map<BlockPos, Set<UUID>> dimTracking = tracking.get(dimension);
            if (dimTracking.containsKey(pos)){
                dimTracking.get(pos).clear();
            }
        }
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent event){
        float amount = event.getAmount();
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.world;
        int dimension = world.provider.getDimension();

        if (amount > 0 && tracking.containsKey(dimension)){
            Map<BlockPos, Set<UUID>> dimTracking = tracking.get(dimension);
            for (Map.Entry<BlockPos, Set<UUID>> entry: dimTracking.entrySet()){
                if (entry.getValue().contains(entity.getUniqueID())){
                    if (world.isBlockLoaded(entry.getKey())){
                        TileEntity te = world.getTileEntity(entry.getKey());
                        if (te instanceof TileGenerator){
                            ((TileGenerator) te).senseDamage(entity, amount);
                        }
                    }
                }
            }
        }
    }
}
