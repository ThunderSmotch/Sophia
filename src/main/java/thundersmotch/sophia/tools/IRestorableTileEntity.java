package thundersmotch.sophia.tools;

import net.minecraft.nbt.NBTTagCompound;

public interface IRestorableTileEntity {

    void readRestorableFromNBT(NBTTagCompound compound);

    void writeRestorableToNBT(NBTTagCompound compound);
}
