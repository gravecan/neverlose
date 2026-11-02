package a;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ClientPlayerEntity;

final class TargetAimPointPredictor {
   private static final double a = 0.065;
   private static final double b = 0.32;
   private static final double c = 0.99;
   private static final double d = 0.08;
   private static final double e = 0.6;
   private static Vec3d f;
   private static int g;
   private static boolean h;
   private static Vec3d i;

   private TargetAimPointPredictor() {
   }

   static void a(int targetId) {
      g = targetId;
      h = false;
      i = Vec3d.ZERO;
   }

   static boolean a(ClientPlayerEntity player, PlayerEntity target) {
      return player != null && target != null && target.isFallFlying();
   }

   static Vec3d a(MinecraftClient client, ClientPlayerEntity player, PlayerEntity target, float aimHeightNorm, float partialTick) {
      if (g != target.getId()) {
         a(target.getId());
      }

      Vec3d var5 = b(client, player, target, aimHeightNorm, partialTick);
      if (!h) {
         f = var5;
         h = true;
         return f;
      } else {
         f = f.lerp(var5, 0.55);
         return f;
      }
   }

   private static Vec3d b(MinecraftClient client, ClientPlayerEntity player, PlayerEntity target, float aimHeightNorm, float partialTick) {
      Vec3d var5 = target.getLerpedPos(partialTick);
      Vec3d var6 = player.getCameraPosVec(partialTick);
      Vec3d var7 = target.getVelocity();
      Vec3d var8;
      double var9;
      if (target.isFallFlying()) {
         double var11 = Math.sqrt(var7.x * var7.x + var7.z * var7.z);
         double var13 = Math.atan2(-var7.y, Math.max(var11, 0.001));
         double var15 = 0.3;
         double var17 = Math.sin(var13) * var15;
         var9 = var5.y + var15 - var17 * 0.55;
         var8 = new Vec3d(var5.x, var9, var5.z);
      } else {
         var9 = var5.y + (double)target.getHeight() * (double)0.5F;
         var8 = var5.add((double)0.0F, (double)target.getHeight() * (double)0.5F, (double)0.0F);
      }

      Vec3d var43 = new Vec3d(target.getX() - target.prevX, target.getY() - target.prevY, target.getZ() - target.prevZ);
      Vec3d var12 = var7.subtract(var43);
      double var44 = var12.lengthSquared();
      double var45 = var44 > 0.065 ? 0.65 : 0.18;
      i = i.lerp(var12, var45);
      float var46 = a(var6, var8, var7);
      float var18 = b(var6, var8, var7);
      double var19 = a(client, player, target, var7, i);
      var19 *= MathHelper.lerp((double)1.0F, 0.55, (double)var46);
      double var21 = var19 * var19;
      double var23 = var5.x + var7.x * var19 + i.x * var21 * (double)0.5F;
      double var25 = var5.z + var7.z * var19 + i.z * var21 * (double)0.5F;
      double var27 = Math.pow(0.99, var19 * (double)0.5F);
      double var29 = var7.y * var19 * var27 - 0.08 * var19 * (double)0.5F;
      double var31 = var9 + var29;
      double var33 = target.isFallFlying() ? 0.6 : (double)target.getHeight();
      double var35 = MathHelper.lerp(-0.15, 0.15, (double)MathHelper.clamp(aimHeightNorm, 0.0F, 1.0F));
      var31 += var35 * var33;
      Vec3d var37 = new Vec3d(var7.x, (double)0.0F, var7.z);
      double var38 = var37.length();
      Vec3d var40 = Vec3d.ZERO;
      if (var38 > 0.05 && (double)var18 > 0.08) {
         double var41 = Math.min(0.32, var38 * 0.14 + 0.05) * (double)var18;
         var40 = var37.normalize().multiply(var41);
      }

      if ((double)var46 > 0.12) {
         Vec3d var49 = var6.subtract(var8);
         Vec3d var42 = new Vec3d(var49.x, (double)0.0F, var49.z);
         if (var42.lengthSquared() > 1.0E-4) {
            var40 = var40.add(var42.normalize().multiply(0.12 * (double)var46));
         }
      }

      return new Vec3d(var23 + var40.x, var31, var25 + var40.z);
   }

   private static float a(Vec3d eye, Vec3d targetCenter, Vec3d velocity) {
      Vec3d var3 = new Vec3d(targetCenter.x - eye.x, (double)0.0F, targetCenter.z - eye.z);
      if (var3.length() < 0.15) {
         return 0.0F;
      } else {
         Vec3d var4 = new Vec3d(velocity.x, (double)0.0F, velocity.z);
         if (var4.lengthSquared() < 0.0025) {
            return 0.0F;
         } else {
            double var5 = var3.normalize().dotProduct(var4.normalize());
            return MathHelper.clamp((float)((var5 - 0.2) / 0.55), 0.0F, 1.0F);
         }
      }
   }

   private static float b(Vec3d eye, Vec3d targetCenter, Vec3d velocity) {
      Vec3d var3 = new Vec3d(targetCenter.x - eye.x, (double)0.0F, targetCenter.z - eye.z);
      if (var3.length() < 0.15) {
         return 0.0F;
      } else {
         Vec3d var4 = new Vec3d(velocity.x, (double)0.0F, velocity.z);
         if (var4.lengthSquared() < 0.0025) {
            return 0.0F;
         } else {
            double var5 = var3.normalize().dotProduct(var4.normalize());
            return MathHelper.clamp((float)((-var5 - 0.2) / 0.55), 0.0F, 1.0F);
         }
      }
   }

   static float b(ClientPlayerEntity player, PlayerEntity target) {
      if (!a(player, target)) {
         return 1.0F;
      } else {
         Vec3d var2 = target.getVelocity().subtract(player.getVelocity());
         double var3 = var2.length();
         return MathHelper.clamp(1.14F + (float)Math.min(var3 * 0.22, 0.22), 1.14F, 1.32F);
      }
   }

   static float[] a(float yawStep, float pitchStep) {
      return new float[]{MathHelper.clamp(yawStep, -14.5F, 14.5F), MathHelper.clamp(pitchStep, -11.0F, 11.0F)};
   }

   static float a() {
      return 1.12F;
   }

   private static double a(MinecraftClient client, ClientPlayerEntity player, PlayerEntity target, Vec3d velocity, Vec3d accel) {
      double var5 = 0.05;
      if (client.getNetworkHandler() != null) {
         PlayerListEntry var7 = client.getNetworkHandler().getPlayerListEntry(player.getUuid());
         if (var7 != null) {
            var5 = (double)var7.getLatency() / (double)1000.0F;
         }
      }

      double var11 = (double)player.distanceTo(target);
      double var9 = var5 * (double)20.0F + MathHelper.clamp(var11 * 0.055, (double)0.0F, (double)2.0F);
      if (accel.lengthSquared() > 0.065) {
         var9 *= 1.35;
      } else if (velocity.lengthSquared() > (double)0.5F) {
         var9 *= 1.12;
      }

      return MathHelper.clamp(var9, 0.35, (double)4.5F);
   }

   static {
      f = Vec3d.ZERO;
      g = -1;
      i = Vec3d.ZERO;
   }
}
