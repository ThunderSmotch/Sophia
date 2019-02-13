package thundersmotch.sophia.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thundersmotch.sophia.Sophia;
import thundersmotch.sophia.tools.IEnergyContainer;

public class PacketSyncPower implements IMessage {

    private int energy;

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        energy = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(energy);
    }

    //This constructor is needed!
    public PacketSyncPower(){}

    public PacketSyncPower(int energy){
        this.energy = energy;
    }

    public static class Handler implements IMessageHandler<PacketSyncPower, IMessage>{

        @Override
        public IMessage onMessage(PacketSyncPower packetSyncPower, MessageContext messageContext) {
            Sophia.proxy.addScheduledTaskClient(() -> handle(packetSyncPower, messageContext));
            return null;
        }

        private void handle(PacketSyncPower message, MessageContext ctx){
            EntityPlayer player = Sophia.proxy.getClientPlayer();
            if(player.openContainer instanceof IEnergyContainer){
                ((IEnergyContainer) player.openContainer).syncPower(message.energy);
            }
        }

    }
}
