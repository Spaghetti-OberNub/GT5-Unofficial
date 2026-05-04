package gregtech.api.items.armor.behaviors;

import com.gtnewhorizon.gtnhlib.keybind.SyncedKeybind;
import cpw.mods.fml.common.Loader;
import gregtech.api.items.armor.ArmorContext;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

import static codechicken.nei.NEIClientConfig.world;

public class HeadlampBehavior implements IArmorBehavior {

    public static final HeadlampBehavior INSTANCE = new HeadlampBehavior();

    @Override
    public BehaviorName getName() {
        return BehaviorName.Headlamp;
    }

    @Override
    public void onArmorTick(@NotNull ArmorContext context) {
        EntityPlayer player = context.getPlayer();

        if (context.isRemote()) {
            LightHandler.updateHeadlamp(player);
        }
    }
}

