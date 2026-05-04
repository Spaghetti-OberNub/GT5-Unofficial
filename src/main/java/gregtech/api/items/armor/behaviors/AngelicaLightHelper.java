package gregtech.api.items.armor.behaviors;

import net.minecraft.entity.player.EntityPlayer;

public class AngelicaLightHelper {
    private static int debugTimer = 0;

    public static void applyLight(EntityPlayer player) {
        debugTimer++;
        if (debugTimer >= 100) {
            System.out.println("[HEADLAMP-DEBUG] Angelica Helper is executing for player: " + player.getCommandSenderName());
            debugTimer = 0;
        }
        System.out.println("Headlamp: Angelica not detected, light disabled.");
    }
}
