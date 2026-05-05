package gregtech.api.items.armor.behaviors;

import com.gtnewhorizons.angelica.dynamiclights.IDynamicLightSource;
import com.gtnewhorizons.angelica.dynamiclights.IDynamicLightWorldRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

public class AngelicaLightHelper implements IDynamicLightSource {
    private static double lightReach = 20.0D;

    private boolean isLightActive = false;
    private double lightX, lightY, lightZ;

    private double lastLightX, lastLightY, lastLightZ;

    @Override
    public void angelica$resetDynamicLight() {
        this.isLightActive = false;
        this.lightX = 0;
        this.lightY = 0;
        this.lightZ = 0;
    }

    public void update(EntityPlayer player, float partialTicks) {
        double eyeX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
        double eyeY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks + player.getEyeHeight();
        double eyeZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
        Vec3 start = Vec3.createVectorHelper(eyeX, eyeY, eyeZ);

        Vec3 look = player.getLook(partialTicks);
        Vec3 end = start.addVector(look.xCoord * lightReach, look.yCoord * lightReach, look.zCoord * lightReach);

        MovingObjectPosition mop = player.worldObj.rayTraceBlocks(start, end, false);

        if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            this.lightX = mop.hitVec.xCoord - (look.xCoord * 0.1);
            this.lightY = mop.hitVec.yCoord - (look.yCoord * 0.1);
            this.lightZ = mop.hitVec.zCoord - (look.zCoord * 0.1);
            this.isLightActive = true;
        } else {
            this.lightX = eyeX;
            this.lightY = eyeY;
            this.lightZ = eyeZ;
            this.isLightActive = true;
        }
    }

    @Override
    public boolean angelica$isDynamicLightEnabled() {
        return isLightActive;
    }

    @Override
    public int angelica$getLuminance() {
        return 15;
    }

    @Override
    public void angelica$dynamicLightTick() {}

    @Override
    public boolean angelica$updateDynamicLight(@NotNull IDynamicLightWorldRenderer renderer) {
        if (!isLightActive) return false;

        double dx = lightX - lastLightX;
        double dy = lightY - lastLightY;
        double dz = lightZ - lastLightZ;

        if (dx * dx + dy * dy + dz * dz > 0.01) {
            this.angelica$scheduleTrackedChunksRebuild(renderer);
            this.lastLightX = lightX;
            this.lastLightY = lightY;
            this.lastLightZ = lightZ;
            return true;
        }

        return false;
    }

    @Override
    public void angelica$scheduleTrackedChunksRebuild(@NotNull IDynamicLightWorldRenderer renderer) {
        if (isLightActive) {
            int chunkX = ((int) lightX) >> 4;
            int chunkY = ((int) lightY) >> 4;
            int chunkZ = ((int) lightZ) >> 4;

            renderer.scheduleRebuildForChunk(chunkX, chunkY, chunkZ, false);
        }
    }

    @Override
    public double angelica$getDynamicLightX() { return lightX; }

    @Override
    public double angelica$getDynamicLightY() { return lightY; }

    @Override
    public double angelica$getDynamicLightZ() { return lightZ; }
}
