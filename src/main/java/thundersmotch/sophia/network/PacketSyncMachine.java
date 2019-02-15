package thundersmotch.sophia.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thundersmotch.sophia.Sophia;
import thundersmotch.sophia.tools.IMachineStateContainer;

public class PacketSyncMachine implements IMessage {

    private int energy;
    private int progress; //Stored in client as percentage

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        energy = byteBuf.readInt();
        progress = byteBuf.readByte();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(energy);
        byteBuf.writeByte(progress);
    }

    //This constructor is needed!
    public PacketSyncMachine(){}

    public PacketSyncMachine(int energy, int progress){
        this.energy = energy;
        this.progress = progress;
    }

    public static class Handler implements IMessageHandler<PacketSyncMachine, IMessage>{

        @Override
        public IMessage onMessage(PacketSyncMachine packetSyncMachine, MessageContext messageContext) {
            Sophia.proxy.addScheduledTaskClient(() -> handle(packetSyncMachine, messageContext));
            return null;
        }

        private void handle(PacketSyncMachine message, MessageContext ctx){
            EntityPlayer player = Sophia.proxy.getClientPlayer();
            if(player.openContainer instanceof IMachineStateContainer){
                ((IMachineStateContainer) player.openContainer).sync(message.energy, message.progress);
            }
        }

    }
}
