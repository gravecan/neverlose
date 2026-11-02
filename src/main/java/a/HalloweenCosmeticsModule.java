package a;

public class HalloweenCosmeticsModule extends ToggleableModule {
   public static HalloweenCosmeticsModule a;
   public final BooleanSetting b = this.a("Witch hat", true);
   public final BooleanSetting c = this.a("Bat wings", true);
   public final SliderSetting d = this.a("Wing flap speed", (double)0.0F, (double)3.0F, (double)1.0F, 0.1, "x");

   public HalloweenCosmeticsModule() {
      super("Cosmetics", "Halloween cosmetics for your player.", -1);
      this.u = "MISC";
      a = this;
   }

   public static boolean a() {
      HalloweenCosmeticsModule var0 = a;
      return var0 != null && var0.p() && var0.b.a && LicenseModule.b();
   }

   public static boolean b() {
      HalloweenCosmeticsModule var0 = a;
      return var0 != null && var0.p() && var0.c.a && LicenseModule.b();
   }

   public static float c() {
      return a != null ? (float)a.d.e : 1.0F;
   }
}
