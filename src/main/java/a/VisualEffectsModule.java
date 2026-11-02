package a;

public class VisualEffectsModule extends ToggleableModule {
   public final BooleanSetting a = this.a("Animated accent", true);
   public final BooleanSetting b = this.a("Particles", true);
   public final BooleanSetting c = this.a("Spooky effects", true);
   public final BooleanSetting d = this.a("Watermark", true);
   public final BooleanSetting e = this.a("Module list", true);
   public final BooleanSetting f = this.a("Enemy info", true);
   public final SliderSetting g = this.a("Glow", (double)0.0F, (double)1.0F, 0.6, 0.05, "");

   public VisualEffectsModule() {
      super("Colors", "Halloween visuals and on-screen effects.", -1);
      this.u = "VISUALS";
      this.v = false;
   }
}
