package a;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

public abstract class ToggleableModule {
   public final String o;
   public final String p;
   public final String q;
   public final List<Setting> r;
   public int s;
   public boolean t;
   public String u;
   public boolean v;
   private boolean a;

   protected ToggleableModule(String name, String description, int defaultKey) {
      this(name, name, description, defaultKey);
   }

   protected ToggleableModule(String configKey, String name, String description, int defaultKey) {
      this.r = new ArrayList();
      this.u = "COMBAT";
      this.v = true;
      this.o = configKey;
      this.p = name;
      this.q = description;
      this.s = defaultKey;
   }

   protected BooleanSetting a(String n, boolean def) {
      BooleanSetting var3 = new BooleanSetting(n, def);
      this.r.add(var3);
      return var3;
   }

   protected SliderSetting a(String n, double min, double max, double def, double step, String unit) {
      SliderSetting var11 = new SliderSetting(n, min, max, def, step, unit);
      this.r.add(var11);
      return var11;
   }

   protected ChoiceSetting a(String n, List<String> options, int def) {
      ChoiceSetting var4 = new ChoiceSetting(n, options, def);
      this.r.add(var4);
      return var4;
   }

   protected LicenseKeySetting b(String n, String def) {
      LicenseKeySetting var3 = new LicenseKeySetting(n, def);
      this.r.add(var3);
      return var3;
   }

   public boolean p() {
      return this.a;
   }

   public void a(boolean on) {
      this.a = on;
   }

   public void a_() {
      MinecraftClient var1 = MinecraftClient.getInstance();
      if (!this.a && !LicenseModule.b()) {
         if (var1.player != null) {
            String var2 = LicenseModule.e() == LicenseStateValidator.State.g ? "\u00a7c[Revoked] This license key was revoked by the vendor" : "\u00a7c[Licensed] Paste your licence in MISC > License first";
            var1.player.sendMessage(Text.literal(var2), false);
         }

      } else {
         this.a = !this.a;
         ModConfigStore.a();
      }
   }

   public void a(MinecraftClient mc) {
   }

   public void a(MinecraftClient mc, float dt) {
   }
}
