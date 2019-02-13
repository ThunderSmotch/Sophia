package thundersmotch.sophia.compat;

import mcp.mobius.waila.api.*;
import net.minecraft.item.ItemStack;
import thundersmotch.sophia.block.iron_furnace.BlockIronFurnace;

import javax.annotation.Nonnull;
import java.util.List;

@WailaPlugin
public class WailaSophia implements IWailaPlugin, IWailaDataProvider {

    @Override
    public void register(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(this, BlockIronFurnace.class);
    }

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getBlock() instanceof BlockIronFurnace)
            ((BlockIronFurnace) accessor.getBlock()).getWailaBody(tooltip, accessor);
        return tooltip;
    }

}
