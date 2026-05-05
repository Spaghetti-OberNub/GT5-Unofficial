package gregtech.api.items.armor.behaviors;

import com.gtnewhorizons.angelica.dynamiclights.DynamicLights;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class LightHandler {
    private static AngelicaLightHelper helper;
    private static boolean isArmorEquipped = false;

    public static void updateArmorStatus(boolean equipped) {
        isArmorEquipped = equipped;
        if (!equipped && helper != null) {
            helper.angelica$resetDynamicLight();
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || !isArmorEquipped) return;

        if (helper == null) {
            helper = new AngelicaLightHelper();
            com.gtnewhorizons.angelica.dynamiclights.DynamicLights.get().addLightSource(helper);
        }

        helper.update(mc.thePlayer, event.renderTickTime);
    }
}
