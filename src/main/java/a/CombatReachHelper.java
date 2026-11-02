package a;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.network.ClientPlayerEntity;

final class CombatReachHelper {
   private static final double a = 2.95;
   private static final double b = 2.72;
   private static final double c = 0.12;
   private static final double d = (double)2.5F;

   private CombatReachHelper() {
   }

   static double a(MinecraftClient mc) {
      ClientPlayerEntity var1 = mc.player;
      if (var1 == null) {
         return 2.95;
      } else {
         double var2 = (var1.isFallFlying() ? 2.72 : 2.95) - var1.getVelocity().horizontalLength() * 0.12;
         return Math.max((double)2.5F, var2);
      }
   }

   static boolean a(MinecraftClient mc, Entity target) {
      ClientPlayerEntity var2 = mc.player;
      if (var2 != null && target != null) {
         Box var3 = target.getBoundingBox();
         Vec3d var4 = var2.getEyePos();
         Vec3d var5 = new Vec3d(MathHelper.clamp(var4.x, var3.minX, var3.maxX), MathHelper.clamp(var4.y, var3.minY, var3.maxY), MathHelper.clamp(var4.z, var3.minZ, var3.maxZ));
         double var6 = a(mc);
         return var4.squaredDistanceTo(var5) <= var6 * var6;
      } else {
         return false;
      }
   }

   static boolean b(MinecraftClient mc) {
      return mc.player != null && mc.player.hurtTime > 0;
   }

   static boolean c(MinecraftClient mc) {
      ClientPlayerEntity var1 = mc.player;
      if (var1 == null) {
         return false;
      } else if (!var1.isOnGround() && !var1.isClimbing() && !var1.isTouchingWater()) {
         if (!var1.hasStatusEffect(StatusEffects.BLINDNESS) && !var1.hasVehicle()) {
            return var1.fallDistance > 0.0F || var1.getVelocity().y < -0.05;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
