package gregtech.api.items.armor.behaviors;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.player.EntityPlayer;

public class LightHandler {
    public static void updateHeadlamp(EntityPlayer player) {
        if (Loader.isModLoaded("angelica")) {
            AngelicaLightHelper.applyLight(player);
        }
    }
}
