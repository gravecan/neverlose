package a;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import org.lwjgl.glfw.GLFW;

final class AimAssistController {
   static final String a = "Smooth";
   static final String b = "Blatant";
   private static final float c = 0.92F;
   private final AimAssistModule d;
   private int e = -1;
   private boolean f;
   private boolean g;
   private boolean h;
   private float i;
   private float j;
   private float k;
   private float l;
   private float m;
   private float n;
   private long o;
   private long p;
   private float q;
   private float r;
   private float s;
   private long t;
   private LivingEntity u;
   private LivingEntity v;
   private float w;
   private boolean x;
   private int y;
   private boolean z;
   private double A;
   private long B;

   AimAssistController(AimAssistModule cfg) {
      this.d = cfg;
   }

   void a(boolean enabled) {
      this.a();
      this.g = enabled;
   }

   void a() {
      this.e = -1;
      this.f = false;
      this.h = false;
      this.u = null;
      this.v = null;
      this.w = 0.0F;
      this.i = 0.0F;
      this.j = 0.0F;
      this.k = 0.0F;
      this.l = 0.0F;
      this.m = 0.0F;
      this.n = 0.0F;
      this.o = 0L;
      this.p = 0L;
      this.q = 0.0F;
      this.r = 0.0F;
      this.s = 0.0F;
      this.t = System.nanoTime();
      this.x = false;
      this.y = 0;
      this.z = false;
      this.A = (double)0.0F;
      this.B = 0L;
      TargetAimPointPredictor.a(-1);
   }

   private void e() {
      this.e = -1;
      this.f = false;
      this.h = false;
      this.u = null;
      this.v = null;
      this.z = false;
      this.m = 0.0F;
      this.n = 0.0F;
      this.x = false;
      this.y = 0;
   }

   LivingEntity b() {
      return this.v != null && this.v.isAlive() && !this.v.isRemoved() ? this.v : null;
   }

   double c() {
      return this.A;
   }

   boolean d() {
      return this.A < (double)420.0F;
   }

   void a(MinecraftClient client) {
      if (!this.f()) {
         this.f = false;
         this.h = false;
      } else if (!b(client)) {
         this.f = false;
         this.h = false;
      } else {
         ClientPlayerEntity var2 = client.player;
         LivingEntity var3 = this.b(var2, client);
         if (var3 == null) {
            this.e();
         } else {
            if (this.e != var3.getId()) {
               this.a(var2, var3);
            }

            this.e = var3.getId();
            if (this.d.j.a) {
               this.u = var3;
            }

            this.f = true;
            this.h = "Blatant".equals(this.d.a.b());
         }
      }

   }

   private boolean f() {
      if (!this.d.p()) {
         if (this.g) {
            this.a();
         }

         this.g = false;
         return false;
      } else {
         if (!this.g) {
            this.a();
            this.g = true;
         }

         return true;
      }
   }

   private static boolean b(MinecraftClient client) {
      return client.player != null && client.world != null && client.currentScreen == null;
   }

   void a(MinecraftClient client, float partialTick) {
      if (this.f && client.player != null && this.d.p()) {
         if (!b(client)) {
            this.w = 0.0F;
         } else {
            ClientPlayerEntity var3 = client.player;
            LivingEntity var4 = this.a(var3, client);
            if (var4 != null && var4.isAlive() && !var4.isRemoved()) {
               this.v = var4;
               if ("Blatant".equals(this.d.a.b())) {
                  this.b(var3, var4, partialTick, client);
               } else {
                  this.a(client, var3, var4, partialTick);
               }

               this.w = partialTick;
            } else {
               this.e();
               this.w = 0.0F;
            }
         }
      } else {
         this.w = 0.0F;
      }
   }

   private void a(MinecraftClient client, ClientPlayerEntity player, LivingEntity target, float partialTick) {
      if (this.d.k.a && !d(client)) {
         this.a(player);
      } else {
         float[] var5 = this.a(player, target, partialTick, client);
         this.i = var5[0];
         this.j = var5[1];
         float var6 = this.c(player, target, partialTick, client);
         if (var6 >= 0.92F && this.y > 4) {
            this.a(player, this.i, this.j, client);
         } else {
            this.a(player, this.i, this.j, client, var6);
         }

      }
   }

   private void a(ClientPlayerEntity player, LivingEntity target) {
      long var3 = System.currentTimeMillis();
      this.o = var3;
      this.p = var3;
      this.k = player.getYaw();
      this.l = player.getPitch();
      this.m = 0.0F;
      this.n = 0.0F;
      this.x = false;
      this.y = 0;
      this.q = 0.0F;
      this.r = 0.0F;
      this.s = 0.0F;
      this.t = System.nanoTime() ^ (long)target.getId();
      this.z = true;
      TargetAimPointPredictor.a(target.getId());
      float[] var5 = a(a(player, g(), 1.0F), this.d(player, target, 1.0F, g()));
      this.i = var5[0];
      this.j = var5[1];
   }

   private float[] a(ClientPlayerEntity player, LivingEntity target, float partialTick, MinecraftClient client) {
      Vec3d var5 = a(player, client, partialTick);
      Vec3d var6 = this.e(player, target, partialTick, client);
      float[] var7 = a(var5, var6);
      float var8 = MathHelper.clamp(this.d.b(), 0.01F, 1.0F);
      float var9 = 0.18F + var8 * 0.38F;
      if (this.b(player, target)) {
         var9 *= TargetAimPointPredictor.a();
      }

      if (!this.z) {
         float[] var10 = a(client, player);
         this.k = var10[0];
         this.l = var10[1];
         this.z = true;
      }

      float var12 = MathHelper.wrapDegrees(var7[0] - this.k);
      float var11 = var7[1] - this.l;
      this.k = MathHelper.wrapDegrees(this.k + var12 * var9);
      this.l += var11 * var9 * 0.9F;
      this.l = MathHelper.clamp(this.l, -90.0F, 90.0F);
      this.i = this.k;
      this.j = this.l;
      return new float[]{this.k, this.l};
   }

   private void a(ClientPlayerEntity player, float targetYaw, float targetPitch, MinecraftClient client, float lockAmount) {
      this.x = false;
      float var6 = player.getYaw();
      float var7 = player.getPitch();
      float var8 = MathHelper.clamp(this.d.b(), 0.01F, 1.0F);
      float var9 = MathHelper.clamp(this.d.a(), 1.0F, 10.0F) / 10.0F;
      float var10 = 0.08F + var8 * 0.42F;
      var10 *= 0.65F + var9 * 0.55F;
      var10 *= 0.55F + lockAmount * 0.55F;
      if (this.b(player, this.v)) {
         var10 *= TargetAimPointPredictor.b(player, (PlayerEntity)this.v);
      }

      if (this.o > 0L) {
         long var11 = System.currentTimeMillis() - this.o;
         if (var11 < 220L) {
            float var13 = (float)var11 / 220.0F;
            var10 *= 0.35F + var13 * 0.65F;
         } else {
            this.o = 0L;
         }
      }

      float var22 = MathHelper.wrapDegrees(targetYaw - var6);
      float var12 = targetPitch - var7;
      this.m = this.m * 0.72F + var22 * var10 * 0.28F;
      this.n = this.n * 0.72F + var12 * var10 * 0.24F;
      float var23 = a(this.t, 1) * 0.06F * (1.0F - lockAmount);
      float var14 = a(this.t, 2) * 0.04F * (1.0F - lockAmount);
      float var15 = this.m + var23;
      float var16 = this.n + var14;
      double var17 = c(client);
      var15 = (float)a((double)var15, var17);
      var16 = (float)a((double)var16, var17);
      if (this.b(player, this.v)) {
         float[] var19 = TargetAimPointPredictor.a(var15, var16);
         var15 = var19[0];
         var16 = var19[1];
      }

      if (!((double)Math.abs(var15) < var17 * 0.35) || !((double)Math.abs(var16) < var17 * 0.35)) {
         player.setYaw(var6 + var15);
         player.setPitch(MathHelper.clamp(var7 + var16, -90.0F, 90.0F));
         this.a(var15, var16);
      }
   }

   private void a(ClientPlayerEntity player, float targetYaw, float targetPitch, MinecraftClient client) {
      this.x = true;
      float var5 = player.getYaw();
      float var6 = player.getPitch();
      float var7 = MathHelper.clamp(this.d.b(), 0.01F, 1.0F);
      float var8 = MathHelper.clamp(this.d.a(), 1.0F, 10.0F) / 10.0F;
      float var9 = 0.14F + var7 * 0.28F;
      var9 *= 0.7F + var8 * 0.35F;
      if (this.b(player, this.v)) {
         var9 *= TargetAimPointPredictor.b(player, (PlayerEntity)this.v);
      }

      float var10 = MathHelper.wrapDegrees(targetYaw - var5);
      float var11 = targetPitch - var6;
      float var12 = var10 * var9;
      float var13 = var11 * var9 * 0.88F;
      double var14 = c(client);
      var12 = (float)a((double)var12, var14);
      var13 = (float)a((double)var13, var14);
      if (this.b(player, this.v)) {
         float[] var16 = TargetAimPointPredictor.a(var12, var13);
         var12 = var16[0];
         var13 = var16[1];
      }

      if (!((double)Math.abs(var12) < var14 * 0.4) || !((double)Math.abs(var13) < var14 * 0.4)) {
         player.setYaw(var5 + var12);
         player.setPitch(MathHelper.clamp(var6 + var13, -90.0F, 90.0F));
         this.a(var12, var13);
      }
   }

   private void b(ClientPlayerEntity player, LivingEntity target, float partialTick, MinecraftClient client) {
      if (!this.h) {
         this.h = true;
      }

      Vec3d var5 = a(player, client, partialTick);
      Vec3d var6 = this.d(player, target, partialTick, client);
      if (this.d.g.a) {
         this.a(partialTick);
         var6 = var6.add((double)this.q, (double)this.r, (double)this.s);
      }

      double var7 = var5.distanceTo(var6);
      float[] var9 = a(var5, var6);
      float var10 = MathHelper.wrapDegrees(var9[0] - player.getYaw());
      float var11 = MathHelper.wrapDegrees(var9[1] - player.getPitch());
      float var12 = MathHelper.clamp(this.d.a(), 1.0F, 10.0F) / 10.0F;
      float var13 = 1.0F;
      if (var7 > (double)2.5F) {
         var13 = 1.0F + (float)MathHelper.clamp((var7 - (double)2.5F) / (double)1.0F, (double)0.0F, (double)1.0F);
      }

      float var14 = Math.min(1.0F, var12 * var13);
      if (this.b(player, target)) {
         var14 = Math.min(1.0F, var14 * TargetAimPointPredictor.b(player, (PlayerEntity)target));
      }

      float var15 = var10 * var14;
      float var16 = var11 * var14;
      double var17 = c(client);
      var15 = (float)a((double)var15, var17);
      var16 = (float)a((double)var16, var17);
      if (this.b(player, target)) {
         float[] var19 = TargetAimPointPredictor.a(var15, var16);
         var15 = var19[0];
         var16 = var19[1];
      }

      if (!((double)Math.abs(var15) < var17 * (double)0.5F) || !((double)Math.abs(var16) < var17 * (double)0.5F)) {
         player.setYaw(player.getYaw() + var15);
         player.setPitch(MathHelper.clamp(player.getPitch() + var16, -90.0F, 90.0F));
         this.a(var15, var16);
      }
   }

   private void a(float partialTick) {
      this.t += 17L;
      float var2 = 0.045F + this.d.a() * 0.004F;
      this.q += (a(this.t, 3) - 0.5F) * var2 * partialTick;
      this.r += (a(this.t, 4) - 0.5F) * var2 * 0.35F * partialTick;
      this.s += (a(this.t, 5) - 0.5F) * var2 * partialTick;
      this.q = MathHelper.clamp(this.q, -0.22F, 0.22F);
      this.r = MathHelper.clamp(this.r, -0.08F, 0.08F);
      this.s = MathHelper.clamp(this.s, -0.22F, 0.22F);
      this.q *= 0.94F;
      this.r *= 0.92F;
      this.s *= 0.94F;
   }

   private void a(float yawStep, float pitchStep) {
      long var3 = System.nanoTime();
      float var5 = this.B == 0L ? 0.016666668F : (float)(var3 - this.B) / 1.0E9F;
      this.B = var3;
      double var6 = Math.hypot((double)yawStep, (double)pitchStep) / Math.max((double)var5, 1.0E-4);
      this.A = this.A * 0.6 + var6 * 0.4;
   }

   private void a(ClientPlayerEntity player) {
      this.m *= 0.55F;
      this.n *= 0.55F;
      this.k = player.getYaw();
      this.l = player.getPitch();
      this.y = 0;
      this.x = false;
   }

   private float c(ClientPlayerEntity player, LivingEntity target, float partialTick, MinecraftClient client) {
      Vec3d var5 = a(player, client, partialTick);
      Vec3d var6 = this.e(player, target, partialTick, client);
      float[] var7 = a(var5, var6);
      float var8 = Math.abs(MathHelper.wrapDegrees(var7[0] - player.getYaw()));
      float var9 = Math.abs(var7[1] - player.getPitch());
      float var10 = var8 + var9 * 0.55F;
      float var11 = MathHelper.clamp(this.d.d() * 0.35F, 12.0F, 55.0F);
      float var12 = 1.0F - MathHelper.clamp(var10 / var11, 0.0F, 1.0F);
      if (var12 >= 0.92F) {
         ++this.y;
      } else {
         this.y = 0;
         this.x = false;
      }

      return var12;
   }

   private Vec3d d(ClientPlayerEntity player, LivingEntity target, float partialTick, MinecraftClient client) {
      return this.b(player, target) ? TargetAimPointPredictor.a(client, player, (PlayerEntity)target, this.d.e(), partialTick) : a(target, this.d.e(), partialTick);
   }

   private Vec3d e(ClientPlayerEntity player, LivingEntity target, float partialTick, MinecraftClient client) {
      if (this.b(player, target)) {
         return TargetAimPointPredictor.a(client, player, (PlayerEntity)target, this.d.e(), partialTick);
      } else {
         Vec3d var5 = a(target, this.d.e(), partialTick);
         Vec3d var6 = target.getVelocity();
         double var7 = (double)target.distanceTo(player);
         double var9 = 1.2 + (double)MathHelper.clamp((float)(var7 * 0.08), 0.0F, 2.5F);
         float var11 = MathHelper.clamp(this.d.b(), 0.01F, 1.0F);
         var9 *= (double)0.75F + (double)var11 * 0.45;
         return var5.add(var6.x * var9 * (double)0.5F, var6.y * var9 * 0.35, var6.z * var9 * (double)0.5F);
      }
   }

   private boolean b(ClientPlayerEntity player, LivingEntity target) {
      boolean var10000;
      if (this.d.h.a && target instanceof PlayerEntity var3) {
         if (TargetAimPointPredictor.a(player, var3)) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   private static Vec3d a(LivingEntity entity, float aimHeightNorm, float partialTicks) {
      Vec3d var3 = entity.getLerpedPos(partialTicks);
      double var4 = (double)entity.getHeight();
      if (entity.isFallFlying()) {
         return var3.add((double)0.0F, var4 * (double)0.5F, (double)0.0F);
      } else {
         double var6 = var4 * MathHelper.clamp((double)aimHeightNorm, 0.05, 0.95);
         return var3.add((double)0.0F, var6, (double)0.0F);
      }
   }

   private static Vec3d a(ClientPlayerEntity player, MinecraftClient client, float partialTick) {
      return client.options.getPerspective().isFirstPerson() ? player.getCameraPosVec(partialTick) : client.gameRenderer.getCamera().getPos();
   }

   private static float[] a(MinecraftClient client, ClientPlayerEntity player) {
      return client.options.getPerspective().isFirstPerson() ? new float[]{player.getYaw(), player.getPitch()} : new float[]{client.gameRenderer.getCamera().getYaw(), client.gameRenderer.getCamera().getPitch()};
   }

   private static float[] a(Vec3d eyePos, Vec3d target) {
      double var2 = target.x - eyePos.x;
      double var4 = target.y - eyePos.y;
      double var6 = target.z - eyePos.z;
      double var8 = Math.sqrt(var2 * var2 + var6 * var6);
      float var10 = (float)Math.toDegrees(Math.atan2(-var2, var6));
      float var11 = (float)(-Math.toDegrees(Math.atan2(var4, var8)));
      return new float[]{var10, var11};
   }

   private static double c(MinecraftClient client) {
      double var1 = (Double)client.options.getMouseSensitivity().getValue();
      double var3 = var1 * 0.6 + 0.2;
      return var3 * var3 * var3 * (double)8.0F * 0.15;
   }

   private static double a(double rotation, double gcd) {
      return gcd <= 1.0E-4 ? rotation : (double)Math.round(rotation / gcd) * gcd;
   }

   private static float a(long seed, int channel) {
      long var3 = seed * 6364136223846793005L + 1442695040888963407L + (long)channel * -7046029254386353131L;
      var3 ^= var3 >>> 33;
      var3 *= -49064778989728563L;
      var3 ^= var3 >>> 33;
      return (float)((double)(var3 & 65535L) / (double)65535.0F);
   }

   private static boolean d(MinecraftClient client) {
      if (client.player == null || client.getWindow() == null || GLFW.glfwGetMouseButton(client.getWindow().getHandle(), 0) != 1 && !client.options.attackKey.isPressed()) {
         return CombatClientInitializer.a.p() && System.currentTimeMillis() - CombatClientInitializer.a.a() < 200L;
      } else {
         return true;
      }
   }

   private LivingEntity a(ClientPlayerEntity player, MinecraftClient client) {
      if (this.e >= 0) {
         Entity var4 = player.getWorld().getEntityById(this.e);
         if (var4 instanceof LivingEntity) {
            LivingEntity var3 = (LivingEntity)var4;
            if (var3.isAlive() && !var3.isRemoved() && player.distanceTo(var3) <= MathHelper.clamp(this.d.c(), 1.0F, 50.0F)) {
               return var3;
            }
         }
      }

      return this.b(player, client);
   }

   private LivingEntity b(ClientPlayerEntity player, MinecraftClient client) {
      if (this.u != null && (!this.u.isAlive() || this.u.isRemoved())) {
         this.u = null;
      }

      boolean var3 = this.d.j.a;
      LivingEntity var4 = var3 ? this.u : null;
      if (var4 == null && var3 && this.e >= 0) {
         Entity var6 = player.getWorld().getEntityById(this.e);
         if (var6 instanceof LivingEntity) {
            LivingEntity var5 = (LivingEntity)var6;
            if (var5.isAlive() && !var5.isRemoved()) {
               var4 = var5;
            }
         }
      }

      if (var3 && var4 != null && var4.isAlive() && !var4.isRemoved() && this.c(player, var4)) {
         return var4;
      } else {
         float var20 = MathHelper.clamp(this.d.c(), 1.0F, 50.0F);
         float var21 = MathHelper.clamp(this.d.d(), 15.0F, 360.0F);
         float var7 = a(client, player)[0];
         LivingEntity var8 = null;
         double var9 = Double.MAX_VALUE;

         for(LivingEntity var13 : player.getWorld().getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand((double)var20), (e) -> CombatTargetFilter.a(player, e, this.d.l.a, this.d.m.a, this.d.n.a))) {
            double var14 = (double)player.distanceTo(var13);
            if (!(var14 > (double)var20) && (this.d.i.a || player.canSee(var13))) {
               if (var21 < 360.0F) {
                  Vec3d var16 = player.getEyePos();
                  Vec3d var17 = var13.getLerpedPos(1.0F).add((double)0.0F, (double)(var13.getHeight() * this.d.e()), (double)0.0F);
                  Vec3d var18 = var17.subtract(var16);
                  float var19 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(var18.z, var18.x)) - (double)90.0F);
                  if (Math.abs(MathHelper.wrapDegrees(var19 - var7)) > var21 * 0.5F) {
                     continue;
                  }
               }

               if (var14 < var9) {
                  var9 = var14;
                  var8 = var13;
               }
            }
         }

         return var8;
      }
   }

   private boolean c(ClientPlayerEntity player, LivingEntity e) {
      return CombatTargetFilter.a(player, e, this.d.l.a, this.d.m.a, this.d.n.a) && player.squaredDistanceTo(e) <= (double)(this.d.c() * this.d.c()) && (this.d.i.a || player.canSee(e));
   }

   private static MinecraftClient g() {
      return MinecraftClient.getInstance();
   }
}
