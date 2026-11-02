package a;

import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

public class AimAssistModule extends ToggleableModule {
   public final ChoiceSetting a = this.a("Mode", List.of("Smooth", "Blatant"), 0);
   public final SliderSetting b = this.a("Speed", (double)1.0F, (double)10.0F, (double)5.5F, (double)0.5F, "");
   public final SliderSetting c = this.a("Acceleration", 0.01, (double)1.0F, 0.35, 0.01, "");
   public final SliderSetting d = this.a("Range", (double)3.0F, (double)30.0F, (double)4.5F, (double)0.5F, "");
   public final SliderSetting e = this.a("FOV", (double)15.0F, (double)360.0F, (double)140.0F, (double)5.0F, "°");
   public final SliderSetting f = this.a("Aim height", (double)0.0F, (double)1.0F, 0.55, 0.05, "");
   public final BooleanSetting g = this.a("Multipoint", false);
   public final BooleanSetting h = this.a("Elytra tracking", true);
   public final BooleanSetting i = this.a("Through walls", false);
   public final BooleanSetting j = this.a("Target lock", true);
   public final BooleanSetting k = this.a("Only while attacking", true);
   public final BooleanSetting l = this.a("Hostile mobs", true);
   public final BooleanSetting m = this.a("Passive mobs", false);
   public final BooleanSetting n = this.a("Players", true);
   private final AimAssistController w = new AimAssistController(this);

   public AimAssistModule() {
      super("Motion", "Motion", "Smooths camera movement.", 71);
   }

   float a() {
      return (float)this.b.e;
   }

   float b() {
      return (float)this.c.e;
   }

   float c() {
      return (float)this.d.e;
   }

   float d() {
      return (float)this.e.e;
   }

   float e() {
      return (float)this.f.e;
   }

   public LivingEntity f() {
      return this.p() ? this.w.b() : null;
   }

   public double g() {
      LivingEntity var1 = this.f();
      MinecraftClient var2 = MinecraftClient.getInstance();
      if (var1 != null && var2.player != null) {
         float[] var3 = a(var2.player.getEyePos(), var1.getPos().add((double)0.0F, (double)var1.getHeight() * 0.65, (double)0.0F));
         return Math.hypot((double)MathHelper.wrapDegrees(var3[0] - var2.player.getYaw()), (double)(var3[1] - var2.player.getPitch()));
      } else {
         return (double)-1.0F;
      }
   }

   public double h() {
      return this.w.c();
   }

   public boolean i() {
      return this.w.d();
   }

   public void a_() {
      super.a_();
      this.w.a(this.p());
   }

   public void a(boolean on) {
      super.a(on);
      this.w.a(on);
   }

   public void a(MinecraftClient mc) {
      this.w.a(mc);
   }

   public void a(MinecraftClient mc, float dt) {
      this.w.a(mc, mc.getTickDelta());
   }

   private static float[] a(Vec3d from, Vec3d to) {
      double var2 = to.x - from.x;
      double var4 = to.y - from.y;
      double var6 = to.z - from.z;
      float var8 = (float)(Math.toDegrees(Math.atan2(var6, var2)) - (double)90.0F);
      float var9 = (float)(-Math.toDegrees(Math.atan2(var4, Math.hypot(var2, var6))));
      return new float[]{MathHelper.wrapDegrees(var8), var9};
   }
}
