package a;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.text.Text;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;

public class ModuleSettingsScreen extends Screen {
   private static final int c = 620;
   private static final int d = 432;
   private static final float e = 0.8F;
   private static final int f = 46;
   private static final int g = 152;
   private static final int h = 34;
   private static final int i = 18;
   private static final int j = 170;
   private static final int k = 432;
   private static final int l = 12;
   private static final int m = 210;
   private static final int n = 210;
   private static final int o = 116;
   private static final int p = 272;
   private static final int q = 32;
   private static final int r = -1185543;
   private static final int s = -6582600;
   private static final int t = -10069633;
   private static final int u = -13951425;
   private static final int v = -12373921;
   private static final int w = -15397087;
   private static final int x = -16054509;
   private static final int y = -15594468;
   private static final int z = -16120303;
   private static final int A = -15594212;
   private static final int B = -15265499;
   private static final int C = -15922923;
   private static final int D = -12656479;
   private static final int E = -23990;
   private static final int F = -37243;
   private static final int G = -30147;
   public static float a = Float.NaN;
   public static float b = Float.NaN;
   private static final int H = 0;
   private static final int I = 1;
   private static final int J = 2;
   private static final int K = 3;
   private static final int L = 4;
   private static final int M = 6;
   private static final int N = 7;
   private static final int O = 8;
   private static final int P = 64;
   private final List<Hitbox> Q = new ArrayList();
   private final List<Pointer> R = new ArrayList();
   private float S;
   private float T;
   private float U;
   private float V;
   private boolean W;
   private float X;
   private float Y;
   private float Z;
   private float aa = 0.8F;
   private float ab = 1.0F;
   private float ac;
   private float ad = 1.0F;
   private boolean ae;
   private int af;
   private long ag = System.nanoTime();
   private long ah = System.nanoTime();
   private Object ai;
   private LicenseKeySetting aj;
   private String ak = "";
   private Hitbox al;
   private double am;
   private double an;
   private double ao;
   private double ap;
   private float aq;
   private float ar;
   private float as;
   private float at;
   private float au;
   private float av;
   private int[] aw = new int[0];
   private String[] ax = new String[0];
   private int[] ay = new int[0];
   private float[] az;
   private float[] aA;
   private float[] aB;
   private float[] aC;
   private float[] aD;
   private float[] aE;
   private float aF;
   private float[] aG;
   private float[] aH;
   private float[] aI;
   private float[] aJ;
   private long aK = 12648430L;
   private int aL;
   private int aM;
   private int aN;

   public ModuleSettingsScreen() {
      super(Text.literal("Neverlose"));
   }

   protected void init() {
      List var1 = CombatClientInitializer.f;
      int var2 = var1.size();
      this.az = new float[var2];
      this.aA = new float[var2];
      this.aB = new float[var2];
      this.aC = new float[var2 * 64];
      this.aE = new float[var2 * 64];
      this.aD = new float[var2 * 64];
      this.aw = new int[var2];
      ArrayList var3 = new ArrayList();
      ArrayList var4 = new ArrayList();
      int var5 = 62;
      String var6 = null;

      for(int var7 = 0; var7 < var2; ++var7) {
         ToggleableModule var8 = (ToggleableModule)var1.get(var7);
         if (!var8.u.equals(var6)) {
            if (var6 != null) {
               var5 += 8;
            }

            var3.add(var8.u);
            var4.add(var5);
            var5 += 16;
            var6 = var8.u;
         }

         this.aw[var7] = var5;
         var5 += 28;
      }

      this.ax = (String[])var3.toArray(new String[0]);
      this.ay = new int[var3.size()];

      for(int var12 = 0; var12 < this.ay.length; ++var12) {
         this.ay[var12] = (Integer)var4.get(var12);
      }

      this.aF = this.aw.length == 0 ? 0.0F : (float)this.aw[Math.min(this.af, this.aw.length - 1)];
      this.aG = new float[16];
      this.aH = new float[16];
      this.aI = new float[16];
      this.aJ = new float[16];
      float var13 = 12.34F;

      for(int var16 = 0; var16 < 16; ++var16) {
         float var14 = var13 * 9301.0F + 49297.0F;
         float var9 = var14 % 233280.0F / 233280.0F;
         float var15 = var14 * 9301.0F + 49297.0F;
         float var10 = var15 % 233280.0F / 233280.0F;
         var13 = var15 * 9301.0F + 49297.0F;
         float var11 = var13 % 233280.0F / 233280.0F;
         this.aG[var16] = var9 * (float)this.width;
         this.aH[var16] = var10 * (float)this.height;
         this.aI[var16] = 4.0F + var11 * 10.0F;
         this.aJ[var16] = var9 * 6.28F;
      }

      float var17 = Float.isNaN(a) ? 0.0F : a;
      float var18 = Float.isNaN(b) ? 0.0F : b;
      this.S = this.U = (float)(this.width - 620) / 2.0F + var17;
      this.T = this.V = (float)(this.height - 432) / 2.0F + var18;
      this.f();
      this.S = this.U;
      this.T = this.V;
      this.ag = System.nanoTime();
   }

   public void render(DrawContext ctx, int rawX, int rawY, float delta) {
      long var5 = System.nanoTime();
      this.ac = Math.min((float)(var5 - this.ag) / 1.0E9F, 0.08F);
      this.ag = var5;
      this.am = (double)rawX;
      this.an = (double)rawY;
      this.Z = MathHelper.clamp(this.Z + (this.ae ? -this.ac : this.ac) / 0.22F, 0.0F, 1.0F);
      if (this.ae && this.Z <= 0.0F) {
         BatchedQuadRenderer.b();
         ModConfigStore.b(CombatClientInitializer.f);
         this.client.setScreen((Screen)null);
      } else {
         float var7 = 1.0F - (1.0F - this.Z) * (1.0F - this.Z) * (1.0F - this.Z);
         this.ab = var7;
         this.aa = (0.965F + 0.035F * var7) * 0.8F;
         this.ad = Math.min(1.0F, this.ad + this.ac * 6.5F);
         this.aL = HalloweenColorTheme.b();
         this.aM = HalloweenColorTheme.a.c();
         this.aN = HalloweenColorTheme.a(this.aL, -16054509, 0.55F);
         float var8 = this.W ? 30.0F : 16.0F;
         float var9 = 1.0F - (float)Math.exp((double)(-this.ac * var8));
         this.S += (this.U - this.S) * var9;
         this.T += (this.V - this.T) * var9;
         this.f();
         float var10 = this.S + 310.0F;
         float var11 = this.T + 216.0F;
         this.ao = (double)(((float)rawX - var10) / this.aa + 310.0F);
         this.ap = (double)(((float)rawY - var11) / this.aa + 216.0F);
         this.Q.clear();
         this.e();
         this.a(ctx);
         this.b(ctx);
         if (CombatClientInitializer.c.g.e > (double)0.01F) {
            int var12 = (int)(CombatClientInitializer.c.g.e * (double)34.0F * (double)this.ab);
            RoundedRectangleRenderer.a(ctx, Math.round(var10), Math.round(var11 + 14.0F), 250, var12 << 24 | this.aL & 16777215);
         }

         MatrixStack var13 = ctx.getMatrices();
         var13.push();
         var13.translate(var10, var11, 0.0F);
         var13.scale(this.aa, this.aa, 1.0F);
         var13.translate(-var10, -var11, 0.0F);
         var13.translate(this.S, this.T, 0.0F);
         this.c(ctx);
         this.d(ctx);
         this.e(ctx);
         this.f(ctx);
         this.h(ctx);
         this.g(ctx);
         BatchedQuadRenderer.a();
         var13.pop();
      }
   }

   private void a(DrawContext ctx) {
      BatchedQuadRenderer.a();
      ctx.fillGradient(0, 0, this.width, this.height, this.c(-435156452), this.c(-201129466));
      int var2 = (int)(30.0F * this.ab);
      if (var2 > 2) {
         ctx.fillGradient(0, 0, this.width, 46, var2 << 24, 0);
         ctx.fillGradient(0, this.height - 46, this.width, this.height, 0, var2 << 24);
      }

   }

   private void b(DrawContext ctx) {
      if (CombatClientInitializer.c.b.a && !(this.ab < 0.05F)) {
         float var2 = (float)(System.currentTimeMillis() % 100000L) / 1000.0F;
         int var3 = (int)(26.0F * this.ab);

         for(int var4 = 0; var4 < this.aG.length; ++var4) {
            float[] var10000 = this.aH;
            var10000[var4] -= this.aI[var4] * this.ac;
            var10000 = this.aG;
            var10000[var4] += (float)Math.sin((double)(var2 * 0.5F + this.aJ[var4])) * 3.0F * this.ac;
            if (this.aH[var4] < -2.0F) {
               this.aH[var4] = (float)(this.height + 2);
               this.aG[var4] = (this.aG[var4] * 37.0F + 91.0F) % (float)Math.max(1, this.width);
            }

            BatchedQuadRenderer.a(ctx, (float)((int)this.aG[var4]), (float)((int)this.aH[var4]), (float)((int)this.aG[var4] + 1), (float)((int)this.aH[var4] + 1), var3 << 24 | 12167167);
         }

      }
   }

   private void c(DrawContext ctx) {
      for(int var2 = 3; var2 >= 1; --var2) {
         int var3 = 10 + (3 - var2) * 7;
         this.b(ctx, -var2, -var2 + 4, 620 + var2, 432 + var2 + 4, 16 + var2 * 2, var3 << 24);
      }

      this.b(ctx, -1, -1, 621, 433, 16, a(-13951425, this.aL, 0.35F));
      RoundedRectangleRenderer.a(ctx, 0, 0, 620, 432, 15, this.c(-15397087), this.c(-16054509));
      RoundedRectangleRenderer.a(ctx, 1, 1, 619, 150, 15, 15, 0, 0, this.c(478895350), this.c(9133302));
      RoundedRectangleRenderer.a(ctx, 8, 1, 612, 80, 15, 15, 0, 0, this.c(318767103), this.c(16777215));
      this.a(ctx, 15, 1, 605, 2, 352321535);
      this.a(ctx, 24, 2, 596, 3, 218103807);
   }

   private void d(DrawContext ctx) {
      byte var2 = 11;
      byte var3 = 16;
      byte var4 = 24;
      this.b(ctx, var3, var2, var3 + var4, var2 + var4, 9, a(this.aN, this.aL, 0.85F));
      this.a(ctx, var3 + 1, var2 + 1, var3 + var4 - 1, var2 + var4 - 1, a(this.aL, -1, 0.12F), this.aM);
      Text var5 = b("NL");
      float var6 = 0.82F;
      int var7 = Math.round((float)this.textRenderer.getWidth(var5) * var6);
      this.a(ctx, var5, var3 + (var4 - var7) / 2, var2 + 7, var6, -1);
      int var8 = var3 + var4 + 11;
      Text var9 = b("NEVER");
      Text var10 = b("LOSE");
      int var11 = Math.round((float)this.textRenderer.getWidth(var9) * 1.14F);
      this.a(ctx, var9, var8, var2 + 8, 1.14F, -1185543);
      this.a(ctx, var10, var8 + var11 + 1, var2 + 8, 1.14F, a(this.aL, -1185543, 0.2F));
      short var12 = 586;
      byte var13 = 13;
      boolean var14 = this.a(var12, var13, var12 + 20, var13 + 20);
      this.b(ctx, var12, var13, var12 + 20, var13 + 20, 8, var14 ? 620756991 : 285212671);
      int var15 = var14 ? -1185543 : -6582600;
      float var16 = (float)(var12 + 10);
      float var17 = (float)(var13 + 10);
      this.a(ctx, var16, var17, ((float)Math.PI / 4F), 4.2F, 1.0F, var15);
      this.a(ctx, var16, var17, (-(float)Math.PI / 4F), 4.2F, 1.0F, var15);
      this.Q.add(new Hitbox(var12, var13, var12 + 20, var13 + 20, 8, (Object)null));
      boolean var18 = LicenseModule.b();
      String var19 = var18 ? "ACTIVE" : "NO LICENSE";
      Text var20 = a(var19);
      int var21 = Math.round((float)this.textRenderer.getWidth(var20) * 0.72F);
      int var22 = var21 + 26;
      int var23 = var12 - 8;
      int var24 = var23 - var22;
      int var25 = var18 ? -12656479 : -37243;
      this.b(ctx, var24, 15, var23, 31, 8, 352321535);
      this.b(ctx, var24 + 8, 20, var24 + 14, 26, 3, a(var25, -1, 0.15F));
      this.a(ctx, var20, var24 + 18, 20, 0.72F, var25);
   }

   private void e(DrawContext ctx) {
      RoundedRectangleRenderer.a(ctx, 1, 46, 152, 431, 0, 0, 0, 14, this.c(-15594468), this.c(-16120303));
      this.a(ctx, 152, 46, 153, 431, -13951425);

      for(int var2 = 0; var2 < this.ax.length; ++var2) {
         this.b(ctx, 20, this.ay[var2] + 4, 22, this.ay[var2] + 10, 1, this.a());
         this.a(ctx, a(CustomFontTextFactory.c(this.ax[var2])), 28, this.ay[var2] + 1, 0.7F, -10069633);
      }

      int var19 = this.af < this.aw.length ? this.aw[this.af] : 0;
      this.aF = this.a(this.aF, (float)var19, 15.0F);
      int var3 = Math.round(this.aF);
      this.b(ctx, 10, var3, 142, var3 + 28, 9, a(-15594468, this.aL, 0.2F));
      this.b(ctx, 10, var3, 142, var3 + 28, 9, 301989888);
      this.a(ctx, 10, var3 + 7, 12, var3 + 21, this.aL);
      RoundedRectangleRenderer.a(ctx, 12, var3 + 14, 16, 436207616 | this.aL & 16777215);
      List var4 = CombatClientInitializer.f;

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         ToggleableModule var6 = (ToggleableModule)var4.get(var5);
         int var7 = this.aw[var5];
         boolean var8 = this.a(10, var7, 142, var7 + 28);
         this.az[var5] = this.a(this.az[var5], var8 ? 1.0F : 0.0F, 18.0F);
         this.aA[var5] = this.a(this.aA[var5], var6.p() ? 1.0F : 0.0F, 14.0F);
         float var9 = this.az[var5];
         float var10 = this.aA[var5];
         if (var9 > 0.02F) {
            this.b(ctx, 10, var7, 142, var7 + 28, 9, (int)(10.0F * var9) << 24 | 16777215);
         }

         byte var11 = 16;
         byte var12 = 19;
         int var13 = var7 + 6;
         float var14 = var5 == this.af ? 1.0F : 0.0F;
         int var15 = a(-15594468, var5 == this.af ? this.aN : -12373921, 0.55F);
         this.b(ctx, var12, var13, var12 + var11, var13 + var11, 6, var15);
         this.a(ctx, var6.o, var12 + var11 / 2, var13 + var11 / 2, a(-10069633, var5 == this.af ? -1185543 : -6582600, Math.max(var9, var14)), var15);
         int var16 = a(-6582600, -1185543, Math.max(var9, var14));
         this.a(ctx, a(var6.p), 41, var7 + 10, 0.92F, var16);
         if (var6.v) {
            short var17 = 130;
            int var18 = var7 + 13;
            this.b(ctx, var17 - 4, var18 - 4, var17 + 8, var18 + 8, 7, (int)(46.0F * var10) << 24 | this.aL & 16777215);
            this.b(ctx, var17, var18, var17 + 4, var18 + 4, 2, a(-10069633, this.aL, var10));
         }

         this.Q.add(new Hitbox(10, var7, 142, var7 + 28, 0, var5));
      }

      short var20 = 398;
      this.b(ctx, 10, var20, 142, var20 + 22, 8, 251658239);
      this.a(ctx, a("MENU"), 18, var20 + 7, 0.72F, -10069633);
      this.b(ctx, a("[" + b(CombatClientInitializer.g) + "]"), 134, var20 + 7, 0.72F, a(this.aL, -1185543, 0.4F));
   }

   private int a() {
      return a(this.aL, -1, 0.25F);
   }

   private void f(DrawContext ctx) {
      ToggleableModule var2 = (ToggleableModule)CombatClientInitializer.f.get(this.af);
      float var3 = this.ad;
      float var4 = (1.0F - var3) * 8.0F;
      float var5 = this.ab;
      this.ab = var5 * (0.25F + 0.75F * var3);
      byte var6 = 62;
      this.a(ctx, 170, var6 + 1, 173, var6 + 19, this.aL);
      this.a(ctx, 170, var6 + 1, 173, var6 + 9, a(this.aL, this.aM, 0.7F));
      this.a(ctx, b(var2.p), 181 + Math.round(var4), var6, 1.4F, -1185543);
      this.a(ctx, a(var2.q), 182 + Math.round(var4), var6 + 22, 0.78F, -6582600);
      if (var2.v) {
         boolean var7 = var2.p();
         float var8 = this.a(this.aB[this.af], var7 ? 1.0F : 0.0F, 16.0F);
         this.aB[this.af] = var8;
         byte var9 = 68;
         short var10 = 602;
         int var11 = var10 - var9;
         int var12 = var7 ? this.aL : -10069633;
         this.b(ctx, var11, var6, var10, var6 + 22, 8, a(-13951425, var12, 0.25F + 0.55F * var8));
         this.b(ctx, var11 + 1, var6 + 1, var10 - 1, var6 + 21, 7, a(-15922923, this.aN, var8 * 0.6F));
         this.b(ctx, var11 + 1, var6 + 1, var10 - 1, var6 + 21, 7, (int)(26.0F * var8) << 24 | var12 & 16777215);
         this.a(ctx, var11 + 9, var6 + 10, var11 + 13, var6 + 13, a(-10069633, -1, var8));
         Text var13 = a(var7 ? "ENABLED" : "DISABLED");
         this.a(ctx, var13, var11 + 18, var6 + 8, 0.72F, a(-6582600, -1, var8));
         this.Q.add(new Hitbox(var11, var6, var10, var6 + 22, 1, var2));
      }

      this.a(ctx, 170, 102, 602, 103, -13951425);
      boolean var23 = this.a(var2) || var2 == CombatClientInitializer.e;
      int var24 = var23 ? 210 : 432;
      short var25 = 210;
      this.a(ctx, 170, 116, var24, 272, "MAIN");
      if (var23) {
         this.a(ctx, 392, 116, var25, 272, "SETTINGS");
      }

      int var26 = var24 - 24;
      this.as = (float)this.b(var2);
      this.aq = this.a(this.aq, this.as, 232);
      short var27 = 148;
      short var28 = 382;
      this.a(ctx, 174, var27, 170 + var24 - 4, var28);
      this.au = (float)var27;
      this.av = (float)var28;
      short var29 = 182;
      int var14 = var27 + 2 - Math.round(this.aq);
      if (var2.v) {
         var14 = this.a(ctx, var29, var14, var26, "Enabled", var2.p(), this.af * 64, 1, var2);
         var14 = this.a(ctx, var29, var14, var26, "Keybind", var2, var2.s, this.af * 64 + 1);
      }

      int var15 = 2;

      for(Setting var17 : var2.r) {
         int var18 = this.af * 64 + (var15++ & 63);
         if (var17 instanceof BooleanSetting var19) {
            var14 = this.a(ctx, var29, var14, var26, var19.f, var19.a, var18, 2, var19);
         } else if (var17 instanceof ChoiceSetting var20) {
            var14 = this.a(ctx, var29, var14, var26, (ChoiceSetting)var20, var18);
         } else if (var17 instanceof LicenseKeySetting var21) {
            var14 = this.a(ctx, var29, var14, var26, (LicenseKeySetting)var21, var18);
         }
      }

      if (var2 == CombatClientInitializer.c) {
         this.a(ctx, var29, var14, var26, "Menu key", "gui", CombatClientInitializer.g, this.af * 64 + 40);
      }

      this.i(ctx);
      if (var23) {
         int var31 = var25 - 24;
         this.at = (float)this.c(var2);
         this.ar = this.a(this.ar, this.at, 232);
         short var32 = 404;
         this.a(ctx, 396, var27, 392 + var25 - 4, var28);
         this.au = (float)var27;
         this.av = (float)var28;
         int var33 = var27 + 2 - Math.round(this.ar);
         int var34 = 30;

         for(Setting var36 : var2.r) {
            if (var36 instanceof SliderSetting) {
               SliderSetting var22 = (SliderSetting)var36;
               var33 = this.a(ctx, var32, var33, var31, (SliderSetting)var22, this.af * 64 + (var34++ & 63));
            }
         }

         if (var2 == CombatClientInitializer.e) {
            this.a(ctx, var32, var33, var31);
         }

         this.i(ctx);
      }

      this.e();
      this.ab = var5;
   }

   private void a(DrawContext ctx, int x, int y, int w, int h, String head) {
      this.b(ctx, x, y + 2, x + w, y + h + 2, 12, 704643072);
      this.b(ctx, x, y, x + w, y + h, 12, a(-13951425, this.aL, 0.12F));
      this.b(ctx, x + 1, y + 1, x + w - 1, y + h - 1, 11, -15594212);
      RoundedRectangleRenderer.a(ctx, x + 2, y + 2, x + w - 2, y + 32 + 10, 10, 10, 0, 0, this.c(-15265499), this.c(-15594212));
      this.a(ctx, x + 12, y + 2, x + w - 12, y + 3, 285212671);
      this.a(ctx, x + 12, y + 13, x + 16, y + 17, this.aL);
      Text var7 = a(CustomFontTextFactory.c(head));
      this.a(ctx, var7, x + 22, y + 10, 0.7F, a(-6582600, -1185543, 0.55F));
      int var8 = Math.round((float)this.textRenderer.getWidth(var7) * 0.7F);
      int var9 = x + 28 + var8;
      if (var9 < x + w - 14) {
         this.a(ctx, var9, y + 15, x + w - 14, y + 16, -13951425);
      }

      this.a(ctx, x + 10, y + 32 - 1, x + w - 10, y + 32, -13951425);
   }

   private int a(DrawContext ctx, int x, int y, int w, String label, boolean on, int slot, int kind, Object target) {
      byte var10 = 26;
      boolean var11 = this.a(x - 6, y, x + w + 6, y + var10);
      this.aC[slot] = this.a(this.aC[slot], var11 ? 1.0F : 0.0F, 18.0F);
      float var12 = this.a(slot);
      this.aE[slot] = this.a(this.aE[slot], on ? 1.0F : 0.0F, 15.0F);
      float var13 = this.aE[slot];
      this.a(ctx, x - 6, y, w + 12, var10, this.aC[slot], var12);
      int var14 = a(-6582600, -1185543, Math.max(this.aC[slot], var13 * 0.4F));
      this.a(ctx, a(label), x + 2, y + 7, 0.92F, var14);
      byte var15 = 26;
      byte var16 = 14;
      int var17 = x + w - var15 - 2;
      int var18 = y + 6;
      this.b(ctx, var17, var18, var17 + var15, var18 + var16, 7, a(-12373921, this.aL, var13));
      this.b(ctx, var17 + 1, var18 + 1, var17 + var15 - 1, var18 + var16 - 1, 6, a(-15922923, this.aN, var13 * 0.8F));
      int var19 = var17 + 2 + Math.round((float)(var15 - 16) * var13);
      if (var13 > 0.04F) {
         RoundedRectangleRenderer.a(ctx, var19 + 6, var18 + 7, 12, (int)(40.0F * var13) << 24 | this.aL & 16777215);
      }

      this.b(ctx, var19, var18 + 2, var19 + 12, var18 + var16 - 2, 5, a(-6582600, -1, var13));
      this.a(x - 6, y, x + w + 6, y + var10, kind, target);
      return y + var10 + 3;
   }

   private int a(DrawContext ctx, int x, int y, int w, ChoiceSetting s, int slot) {
      byte var7 = 26;
      boolean var8 = this.a(x - 6, y, x + w + 6, y + var7);
      this.aC[slot] = this.a(this.aC[slot], var8 ? 1.0F : 0.0F, 18.0F);
      float var9 = this.a(slot);
      this.a(ctx, x - 6, y, w + 12, var7, this.aC[slot], var9);
      this.a(ctx, a(s.f), x + 2, y + 7, 0.92F, a(-6582600, -1185543, this.aC[slot]));
      Text var10 = a(s.b());
      int var11 = Math.round((float)this.textRenderer.getWidth(var10) * 0.74F) + 22;
      int var12 = x + w - var11;
      this.b(ctx, var12, y + 5, var12 + var11, y + 21, 7, a(-13951425, this.aL, this.aC[slot] * 0.7F));
      this.b(ctx, var12 + 1, y + 6, var12 + var11 - 1, y + 20, 6, -15922923);
      this.a(ctx, var10, var12 + 9, y + 9, 0.74F, a(-6582600, this.aL, Math.max(this.aC[slot], 0.3F)));
      int var13 = a(-10069633, this.aL, this.aC[slot]);
      this.a(ctx, (float)(var12 + var11) - 11.5F, (float)y + 11.5F, ((float)Math.PI / 4F), 2.1F, 0.7F, var13);
      this.a(ctx, (float)(var12 + var11) - 11.5F, (float)y + 14.5F, (-(float)Math.PI / 4F), 2.1F, 0.7F, var13);
      this.a(x - 6, y, x + w + 6, y + var7, 7, s);
      return y + var7 + 3;
   }

   private int a(DrawContext ctx, int x, int y, int w, String label, Object target, int key, int slot) {
      byte var9 = 26;
      boolean var10 = target != null && this.ai == target;
      boolean var11 = this.a(x - 6, y, x + w + 6, y + var9);
      this.aC[slot] = this.a(this.aC[slot], var11 ? 1.0F : 0.0F, 18.0F);
      this.aE[slot] = this.a(this.aE[slot], var10 ? 1.0F : 0.0F, 16.0F);
      float var12 = this.a(slot);
      this.a(ctx, x - 6, y, w + 12, var9, this.aC[slot], var12);
      this.a(ctx, a(label), x + 2, y + 7, 0.92F, a(-6582600, -1185543, Math.max(this.aC[slot], this.aE[slot])));
      Text var13 = a(var10 ? "PRESS A KEY" : b(key));
      int var14 = Math.round((float)this.textRenderer.getWidth(var13) * 0.76F) + 18;
      int var15 = x + w - 2;
      int var16 = var15 - var14;
      float var17 = Math.max(this.aE[slot], this.aC[slot] * 0.55F);
      this.b(ctx, var16, y + 5, var15, y + 21, 7, a(-13951425, this.aL, var17));
      this.b(ctx, var16 + 1, y + 6, var15 - 1, y + 20, 6, -15922923);
      if (this.aE[slot] > 0.03F) {
         this.a(ctx, var16 + 1, y + 19, var16 + 1 + Math.round((float)(var14 - 2) * this.aE[slot]), y + 20, this.aL);
      }

      this.c(ctx, var13, (var16 + var15) / 2, y + 9, 0.76F, a(-6582600, this.aL, var17));
      this.a(x - 6, y, x + w + 6, y + var9, 4, target == null ? "gui" : target);
      return y + var9 + 3;
   }

   private int a(DrawContext ctx, int x, int y, int w, SliderSetting n, int slot) {
      byte var7 = 40;
      boolean var8 = this.al != null && this.al.f() == n;
      boolean var9 = this.a(x - 6, y, x + w + 6, y + var7);
      this.aC[slot] = this.a(this.aC[slot], !var9 && !var8 ? 0.0F : 1.0F, 18.0F);
      float var10 = this.a(this.aE[slot], (float)n.a(), 22.0F);
      this.aE[slot] = var10;
      this.a(ctx, a(n.f), x + 2, y + 2, 0.92F, a(-6582600, -1185543, this.aC[slot]));
      this.b(ctx, a(n.b()), x + w - 2, y + 2, 0.76F, a(-6582600, this.aL, Math.max(this.aC[slot], var8 ? 1.0F : 0.25F)));
      int var11 = y + 22;
      byte var12 = 4;
      this.b(ctx, x, var11, x + w, var11 + var12, 2, -15922923);
      this.a(ctx, x + 1, var11 + 1, x + w - 1, var11 + 2, a(-15922923, -12373921, 0.7F));
      int var13 = Math.max(3, Math.round((float)(w - 1) * var10));
      if (var10 > 0.005F) {
         this.a(ctx, x, var11, x + var13, var11 + var12, a(this.aL, -1, 0.18F), this.aM);
         if (var8) {
            RoundedRectangleRenderer.a(ctx, x + var13, var11 + 2, 16, 738197504 | this.aL & 16777215);
         }
      }

      int var14 = x + var13;
      RoundedRectangleRenderer.a(ctx, var14, var11 + 2, var8 ? 14 : 9, (var8 ? 66 : 30) + (int)(40.0F * this.aC[slot]) << 24 | this.aL & 16777215);
      this.b(ctx, var14 - 4, var11 - 2, var14 + 5, var11 + var12 + 2, 6, -1);
      this.a(ctx, var14 - 2, var11 - 1, var14 + 3, var11 + var12 + 1, -791041);
      if (var8) {
         Text var15 = a(n.b());
         int var16 = Math.round((float)this.textRenderer.getWidth(var15) * 0.74F) + 14;
         int var17 = MathHelper.clamp(var14 - var16 / 2, x - 2, x + w - var16 + 2);
         this.b(ctx, var17, var11 - 23, var17 + var16, var11 - 7, 7, a(-13951425, this.aL, 0.6F));
         this.b(ctx, var17 + 1, var11 - 22, var17 + var16 - 1, var11 - 8, 6, -15922923);
         this.c(ctx, var15, var17 + var16 / 2, var11 - 20, 0.74F, this.aL);
      }

      this.a(x - 6, y, x + w + 6, y + var7, 3, n);
      return y + var7 + 2;
   }

   private int a(DrawContext ctx, int x, int y, int w, LicenseKeySetting ts, int slot) {
      boolean var7 = this.aj == ts;
      this.aE[slot] = this.a(this.aE[slot], var7 ? 1.0F : 0.0F, 16.0F);
      boolean var8 = this.a(x - 6, y, x + w + 6, y + 40);
      this.aC[slot] = this.a(this.aC[slot], var8 ? 1.0F : 0.0F, 18.0F);
      this.a(ctx, a(ts.f), x + 2, y + 1, 0.9F, -1185543);
      int var9 = y + 16;
      byte var10 = 22;
      float var11 = Math.max(this.aE[slot], this.aC[slot] * 0.5F);
      this.b(ctx, x, var9, x + w, var9 + var10, 8, a(-13951425, this.aL, var11));
      this.b(ctx, x + 1, var9 + 1, x + w - 1, var9 + var10 - 1, 7, -15922923);
      if (this.aE[slot] > 0.02F) {
         this.a(ctx, x + 1, var9 + var10 - 2, x + 1 + Math.round((float)(w - 2) * this.aE[slot]), var9 + var10 - 1, this.aL);
      }

      int var13 = -1185543;
      String var12;
      if (var7) {
         String var10000 = a(LicenseTokenUtils.d(this.ak), 34);
         var12 = var10000 + this.d();
      } else if (ts.a.isEmpty()) {
         var12 = "click to paste your licence";
         var13 = -10069633;
      } else if (!LicenseTokenUtils.b(ts.a)) {
         var12 = "invalid licence - click to fix";
         var13 = -37243;
      } else {
         var12 = ts.b();
      }

      this.a(ctx, a(var12), x + 9, var9 + 7, 0.78F, var13);
      this.a(x - 6, y, x + w + 6, var9 + var10, 6, ts);
      return y + 40 + 7;
   }

   private int a(DrawContext ctx, int x, int y, int w) {
      LicenseStateValidator.State var5 = LicenseModule.e();
      boolean var6 = var5 == LicenseStateValidator.State.c;
      boolean var7 = var5 == LicenseStateValidator.State.d;
      boolean var8 = var5 == LicenseStateValidator.State.e;
      boolean var9 = var5 == LicenseStateValidator.State.g;
      int var10 = var6 ? -12656479 : (var7 ? -23990 : -37243);
      this.a(ctx, a("Status"), x + 2, y + 6, 0.9F, -6582600);
      Text var11 = a(LicenseModule.g());
      int var12 = Math.round((float)this.textRenderer.getWidth(var11) * 0.74F) + 16;
      int var13 = x + w - 2;
      int var14 = var13 - var12;
      float var15 = var6 ? 0.7F + 0.3F * (float)Math.sin((double)System.currentTimeMillis() / (double)420.0F) : 1.0F;
      this.b(ctx, var14, y + 3, var13, y + 21, 7, a(-13951425, var10, 0.55F * var15));
      this.b(ctx, var14 + 1, y + 4, var13 - 1, y + 20, 6, -15922923);
      this.c(ctx, var11, (var14 + var13) / 2, y + 8, 0.74F, var10);
      y += 28;
      if (var9) {
         y = this.a(ctx, x, y, w, "Revoked by the vendor. This key can never be activated again.");
         int var16 = y + 3;
         byte var17 = 22;
         boolean var18 = this.a(x - 2, var16, x + w + 2, var16 + var17);
         this.b(ctx, x - 2, var16, x + w + 2, var16 + var17, 8, a(-13951425, this.aL, var18 ? 0.9F : 0.5F));
         this.b(ctx, x - 1, var16 + 1, x + w + 1, var16 + var17 - 1, 7, a(-15922923, this.aN, var18 ? 0.55F : 0.25F));
         this.c(ctx, a("PASTE A NEW LICENCE"), x + w / 2, var16 + 7, 0.78F, a(-6582600, -1, var18 ? 1.0F : 0.4F));
         this.a(x - 2, var16, x + w + 2, var16 + var17, 6, LicenseModule.a.b);
         y = var16 + var17 + 4;
         y = this.a(ctx, x, y, w, "Only a new key unlocks the modules again.");
      }

      y = this.a(ctx, x, y, w, "Time left", var6 ? LicenseModule.j() : (var7 ? "0s" : "-"), var6 ? var10 : -10069633);
      y = this.a(ctx, x, y, w, "Granted", LicenseModule.k() > 0 ? LicenseModule.k() + " days" : "-", -1185543);
      y = this.a(ctx, x, y, w, "Machine", LicenseModule.n() ? "this PC" : (var8 ? "another PC" : (LicenseModule.a.b.a.isEmpty() ? "-" : "unbound")), var8 ? -37243 : (LicenseModule.n() ? var10 : -10069633));
      y = this.a(ctx, x, y, w, "Saved", LicenseModule.a.b.a.isEmpty() ? "-" : LicenseModule.a.b.b(), -1185543);
      y = this.a(ctx, x, y, w, "Hardware", LicenseModule.m(), -10069633);
      y = this.a(ctx, x, y, w, "Build", BuildWatermark.c() ? BuildWatermark.b() : "-", -10069633);
      y += 6;
      this.a(ctx, x, y, x + w, y + 1, -13951425);
      y += 10;
      y = this.a(ctx, x, y, w, "Paste with Ctrl+V, then Enter.");
      y = this.a(ctx, x, y, w, "A key locks to the first PC that activates it.");
      y = this.a(ctx, x, y, w, var9 ? "The revoked key stays dead; a new key starts a fresh term." : (LicenseModule.h() ? (LicenseModule.i() ? "Locked in: it can only be replaced once it expires." : "Locked in: clearing the field restores it.") : "A mistyped key can be corrected."));
      return y;
   }

   private int a(DrawContext ctx, int x, int y, int w, String label, String value, int col) {
      this.a(ctx, a(label), x + 2, y + 5, 0.86F, -6582600);
      this.b(ctx, a(value), x + w - 2, y + 5, 0.74F, col);
      return y + 21;
   }

   private int a(DrawContext ctx, int x, int y, int w, String s) {
      String var6 = s;

      float var7;
      int var8;
      for(var7 = 0.7F; var6.length() > 0 && (float)this.textRenderer.getWidth(a(var6)) * var7 > (float)(w - 6); var6 = var6.substring(var8 + 1)) {
         var8 = var6.lastIndexOf(32, var6.length() - 1);
         if (var8 <= 0) {
            break;
         }

         this.a(ctx, a(var6.substring(0, var8)), x + 2, y, var7, -10069633);
         y += 9;
      }

      if (var6.length() > 0) {
         this.a(ctx, a(var6), x + 2, y, var7, -10069633);
         y += 10;
      }

      return y;
   }

   private void a(DrawContext ctx, int x, int y, int w, int h, float hv, float press) {
      if (hv > 0.02F) {
         this.b(ctx, x, y, x + w, y + h, 7, (int)(9.0F * hv) << 24 | 16777215);
      }

      if (press > 0.02F) {
         this.b(ctx, x, y, x + w, y + h, 7, (int)(16.0F * press) << 24 | this.aL & 16777215);
      }

      if (hv > 0.02F) {
         this.a(ctx, x, y + 6, x + 2, y + h - 6, (int)(170.0F * hv) << 24 | this.aL & 16777215);
      }

   }

   private boolean a(ToggleableModule m) {
      for(Setting var3 : m.r) {
         if (var3 instanceof SliderSetting) {
            return true;
         }
      }

      return false;
   }

   private int b(ToggleableModule m) {
      int var2 = 0;
      if (m.v) {
         var2 += 58;
      }

      for(Setting var4 : m.r) {
         if (!(var4 instanceof BooleanSetting) && !(var4 instanceof ChoiceSetting)) {
            if (var4 instanceof LicenseKeySetting) {
               var2 += 47;
            }
         } else {
            var2 += 29;
         }
      }

      if (m == CombatClientInitializer.c) {
         var2 += 29;
      }

      return var2;
   }

   private int c(ToggleableModule m) {
      int var2 = 0;

      for(Setting var4 : m.r) {
         if (var4 instanceof SliderSetting) {
            var2 += 42;
         }
      }

      if (m == CombatClientInitializer.e) {
         var2 += 210;
         if (LicenseModule.d()) {
            var2 += 70;
         }
      }

      return var2;
   }

   private float a(float scroll, float content, int view) {
      float var4 = Math.max(0.0F, content - (float)view);
      return Math.max(0.0F, Math.min(scroll, var4));
   }

   public boolean mouseClicked(double rawX, double rawY, int button) {
      if (this.ae) {
         return true;
      } else {
         double var6 = (rawX - (double)(this.S + 310.0F)) / (double)this.aa + (double)310.0F;
         double var8 = (rawY - (double)(this.T + 216.0F)) / (double)this.aa + (double)216.0F;
         if (this.ai != null) {
            this.ai = null;
            return true;
         } else {
            if (this.aj != null) {
               this.b();
            }

            if (button == 0) {
               this.R.add(new Pointer((float)var6, (float)var8, System.currentTimeMillis()));

               for(int var10 = this.Q.size() - 1; var10 >= 0; --var10) {
                  Hitbox var11 = (Hitbox)this.Q.get(var10);
                  if (var11.a(var6, var8)) {
                     switch (var11.e()) {
                        case 0:
                           int var12 = (Integer)var11.f();
                           if (this.af != var12) {
                              this.af = var12;
                              this.ad = 0.0F;
                              this.aq = 0.0F;
                              this.ar = 0.0F;
                           }
                           break;
                        case 1:
                           ((ToggleableModule)var11.f()).a_();
                           break;
                        case 2:
                           ((BooleanSetting)var11.f()).a();
                           break;
                        case 3:
                           this.al = var11;
                           this.a(var6);
                           break;
                        case 4:
                           this.ai = var11.f();
                        case 5:
                        default:
                           break;
                        case 6:
                           this.a((LicenseKeySetting)var11.f());
                           break;
                        case 7:
                           ((ChoiceSetting)var11.f()).a();
                           break;
                        case 8:
                           this.close();
                     }

                     if (var11.e() == 1 || var11.e() == 2 || var11.e() == 7) {
                        this.a(var11.f());
                     }

                     if (var11.e() != 3 && var11.e() != 6 && var11.e() != 8) {
                        ModConfigStore.a();
                     }

                     return true;
                  }
               }

               if (var6 >= (double)0.0F && var6 < (double)620.0F && var8 >= (double)0.0F && var8 < (double)432.0F) {
                  this.W = true;
                  this.X = (float)rawX - this.S;
                  this.Y = (float)rawY - this.T;
                  return true;
               }
            }

            return super.mouseClicked(rawX, rawY, button);
         }
      }
   }

   private void a(Object target) {
      for(int var2 = 0; var2 < CombatClientInitializer.f.size(); ++var2) {
         ToggleableModule var3 = (ToggleableModule)CombatClientInitializer.f.get(var2);
         if (target == var3) {
            this.aD[var2 * 64] = 1.0F;
            return;
         }

         int var4 = 2;

         for(Setting var6 : var3.r) {
            if (var6 == target) {
               this.aD[var2 * 64 + (var4 & 63)] = 1.0F;
               return;
            }

            ++var4;
         }
      }

   }

   public boolean mouseDragged(double rawX, double rawY, int button, double dx, double dy) {
      if (this.W) {
         this.U = (float)rawX - this.X;
         this.V = (float)rawY - this.Y;
         this.f();
         return true;
      } else if (this.al != null) {
         this.a((rawX - (double)(this.S + 310.0F)) / (double)this.aa + (double)310.0F);
         return true;
      } else {
         return super.mouseDragged(rawX, rawY, button, dx, dy);
      }
   }

   public boolean mouseReleased(double rawX, double rawY, int button) {
      if (this.W) {
         this.W = false;
         a = this.U - (float)(this.width - 620) / 2.0F;
         b = this.V - (float)(this.height - 432) / 2.0F;
         ModConfigStore.a();
         return true;
      } else {
         if (this.al != null) {
            this.al = null;
            ModConfigStore.a();
         }

         return super.mouseReleased(rawX, rawY, button);
      }
   }

   public boolean mouseScrolled(double rawX, double rawY, double amount) {
      double var7 = (rawX - (double)(this.S + 310.0F)) / (double)this.aa + (double)310.0F;
      if (var7 > (double)386.0F) {
         this.ar = this.a(this.ar - (float)amount * 16.0F, this.at, 232);
      } else {
         this.aq = this.a(this.aq - (float)amount * 16.0F, this.as, 232);
      }

      return true;
   }

   private void a(double mx) {
      Object var4 = this.al.f();
      if (var4 instanceof SliderSetting var3) {
         int var6 = this.al.a() + 6;
         int var5 = Math.max(1, this.al.c() - this.al.a() - 12);
         var3.b((mx - (double)var6) / (double)var5);
      }
   }

   private void a(LicenseKeySetting ts) {
      this.aj = ts;
      this.ak = LicenseModule.d() && ts == LicenseModule.a.b ? "" : LicenseTokenUtils.a(ts.a);
   }

   private void b() {
      if (this.aj != null) {
         String var1 = LicenseTokenUtils.a(this.aj.a);
         String var2 = LicenseTokenUtils.a(LicenseModule.a(this.ak));
         this.aj.a(var2);
         if (!var1.equals(var2)) {
            LicenseModule.b(var2);
         }

         this.aj = null;
         this.ak = "";
         ModConfigStore.a();
      }
   }

   private void c() {
      this.aj = null;
      this.ak = "";
   }

   public boolean charTyped(char chr, int modifiers) {
      if (this.aj != null) {
         if (chr != '\r' && chr != '\n') {
            String var3 = LicenseTokenUtils.a(String.valueOf(chr));
            if (!var3.isEmpty() && this.ak.length() < 512) {
               this.ak = this.ak + var3;
            }

            return true;
         } else {
            this.b();
            return true;
         }
      } else {
         return super.charTyped(chr, modifiers);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.aj != null) {
         if (keyCode == 256) {
            this.c();
         } else if (keyCode != 257 && keyCode != 335) {
            if (keyCode != 259 && keyCode != 261) {
               if ((modifiers & 2) != 0 && keyCode == 86) {
                  String var8 = this.client == null ? "" : this.client.keyboard.getClipboard();
                  String var9 = LicenseTokenUtils.a(var8);
                  if (!var9.isEmpty()) {
                     this.ak = !this.ak.isEmpty() && var9.length() < 32 ? this.ak + var9 : var9;
                     if (this.ak.length() > 512) {
                        this.ak = this.ak.substring(0, 512);
                     }
                  }
               }
            } else if ((modifiers & 2) != 0) {
               this.ak = "";
            } else {
               this.ak = this.ak.length() > 0 ? this.ak.substring(0, this.ak.length() - 1) : this.ak;
            }
         } else {
            this.b();
         }

         return true;
      } else if (this.ai == null) {
         boolean var7 = System.nanoTime() - this.ah < 300000000L;
         if (keyCode == 256) {
            this.close();
            return true;
         } else if (CombatClientInitializer.g > 0 && keyCode == CombatClientInitializer.g && !var7) {
            this.close();
            return true;
         } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
         }
      } else {
         int var4 = keyCode;
         if (keyCode == 256) {
            var4 = Integer.MIN_VALUE;
         } else if (keyCode == 259 || keyCode == 261) {
            var4 = -1;
         }

         if (var4 != Integer.MIN_VALUE) {
            Object var6 = this.ai;
            if (var6 instanceof ToggleableModule) {
               ToggleableModule var5 = (ToggleableModule)var6;
               var5.s = var4;
            } else {
               CombatClientInitializer.g = var4;
            }
         }

         this.ai = null;
         ModConfigStore.a();
         return true;
      }
   }

   public void close() {
      if (this.aj != null) {
         this.b();
      }

      this.ae = true;
   }

   public boolean shouldPause() {
      return false;
   }

   private void g(DrawContext ctx) {
      if (!this.R.isEmpty() && !(this.Z < 0.9F)) {
         long var2 = System.currentTimeMillis();
         this.a(ctx, 15, 15, 605, 417);
         Iterator var4 = this.R.iterator();

         while(var4.hasNext()) {
            Pointer var5 = (Pointer)var4.next();
            float var6 = (float)(var2 - var5.c()) / 460.0F;
            if (var6 >= 1.0F) {
               var4.remove();
            } else {
               float var7 = 1.0F - (1.0F - var6) * (1.0F - var6);
               int var8 = (int)(6.0F + 30.0F * var7);
               int var9 = (int)(90.0F * (1.0F - var6) * this.ab);
               if (var9 > 3) {
                  RoundedRectangleRenderer.a(ctx, Math.round(var5.a()), Math.round(var5.b()), var8, 2, var9 << 24 | this.aL & 16777215, 15, 15, 605, 417);
               }
            }
         }

         this.i(ctx);
      }
   }

   private void h(DrawContext ctx) {
      short var2 = 402;
      this.a(ctx, 8, 398, 612, 399, -13951425);
      Text var3 = a("MENU");
      Text var4 = a("[" + b(CombatClientInitializer.g) + "]");
      int var5 = Math.round((float)this.textRenderer.getWidth(var3) * 0.72F);
      int var6 = Math.round((float)this.textRenderer.getWidth(var4) * 0.72F);
      this.b(ctx, 170, var2, 170 + var5 + var6 + 24, var2 + 18, 7, 218103807);
      this.a(ctx, var3, 178, var2 + 5, 0.72F, -10069633);
      this.b(ctx, var4, 170 + var5 + var6 + 16, var2 + 5, 0.72F, a(this.aL, -1185543, 0.35F));
      Text var7 = a("v1.0.0");
      int var8 = Math.round((float)this.textRenderer.getWidth(var7) * 0.72F);
      this.b(ctx, var7, 602, var2 + 5, 0.72F, -10069633);
      Text var9 = a(LicenseModule.b() ? "LICENSED" : "UNLICENSED");
      int var10 = Math.round((float)this.textRenderer.getWidth(var9) * 0.72F);
      int var11 = 602 - var8 - 14 - var10;
      this.a(ctx, var9, var11, var2 + 5, 0.72F, LicenseModule.b() ? -12656479 : -37243);
      if (BuildWatermark.c()) {
         Text var12 = a(BuildWatermark.b());
         int var13 = Math.round((float)this.textRenderer.getWidth(var12) * 0.72F);
         this.a(ctx, var12, var11 - 10 - var13, var2 + 5, 0.72F, 1728053247);
      }

      if (CombatClientInitializer.c.c.a) {
         HalloweenIconRenderer.a(ctx, var11 - 26, var2 + 1, 2, 0.9F * this.ab);
      }

   }

   private String d() {
      return System.currentTimeMillis() / 450L % 2L == 0L ? "_" : "";
   }

   private static String a(String s, int n) {
      if (s == null) {
         return "";
      } else {
         return s.length() <= n ? s : ".." + s.substring(s.length() - n);
      }
   }

   private void a(DrawContext ctx, float cx, float cy, float angle, float halfLen, float halfW, int col) {
      MatrixStack var8 = ctx.getMatrices();
      var8.push();
      var8.translate(cx, cy, 0.0F);
      var8.multiply((new Quaternionf()).rotateZ(angle));
      BatchedQuadRenderer.a(ctx, -halfW, -halfLen, halfW, halfLen, this.c(col));
      var8.pop();
   }

   private void a(DrawContext ctx, String name, int cx, int cy, int col, int chip) {
      byte var8 = -1;
      switch (name.hashCode()) {
         case -655799047:
            if (name.equals("AutoClick")) {
               var8 = 0;
            }
            break;
         case 1350204731:
            if (name.equals("AimMotion")) {
               var8 = 1;
            }
            break;
         case 1539175268:
            if (name.equals("Cosmetics")) {
               var8 = 3;
            }
            break;
         case 1831410721:
            if (name.equals("License")) {
               var8 = 4;
            }
            break;
         case 2023991696:
            if (name.equals("Colors")) {
               var8 = 2;
            }
      }

      switch (var8) {
         case 0:
            this.b(ctx, cx - 4, cy - 5, cx + 4, cy + 5, 4, col);
            this.b(ctx, cx - 3, cy - 4, cx + 3, cy + 4, 3, chip);
            this.b(ctx, cx - 1, cy - 3, cx + 1, cy, 1, col);
            break;
         case 1:
            this.b(ctx, cx - 5, cy - 5, cx + 5, cy + 5, 5, col);
            this.b(ctx, cx - 3, cy - 3, cx + 3, cy + 3, 3, chip);
            this.b(ctx, cx - 1, cy - 1, cx + 1, cy + 1, 1, col);
            break;
         case 2:
            this.b(ctx, cx - 5, cy - 1, cx - 3, cy + 5, 1, col);
            this.b(ctx, cx - 1, cy - 5, cx + 1, cy + 5, 1, col);
            this.b(ctx, cx + 3, cy - 3, cx + 5, cy + 5, 1, col);
            break;
         case 3:
            this.b(ctx, cx - 1, cy - 5, cx + 1, cy + 1, 1, col);
            this.b(ctx, cx - 3, cy - 1, cx + 3, cy + 2, 1, col);
            this.b(ctx, cx - 5, cy + 1, cx + 5, cy + 4, 2, col);
            break;
         case 4:
            this.b(ctx, cx - 5, cy - 3, cx + 5, cy + 4, 2, col);
            this.b(ctx, cx - 4, cy - 2, cx + 4, cy + 3, 1, chip);
            this.b(ctx, cx - 2, cy - 1, cx, cy + 1, 1, col);
            this.b(ctx, cx + 1, cy, cx + 3, cy + 1, 0, col);
            break;
         default:
            this.b(ctx, cx - 3, cy - 3, cx + 3, cy + 3, 2, col);
      }

   }

   private void e() {
      this.au = 0.0F;
      this.av = 432.0F;
   }

   private void a(int x1, int y1, int x2, int y2, int kind, Object target) {
      int var7 = Math.round(this.au);
      int var8 = Math.round(this.av);
      if (y2 > var7 && y1 < var8) {
         this.Q.add(new Hitbox(x1, Math.max(y1, var7), x2, Math.min(y2, var8), kind, target));
      }
   }

   private boolean a(int x1, int y1, int x2, int y2) {
      return !this.ae && this.ao >= (double)x1 && this.ao < (double)x2 && this.ap >= (double)y1 && this.ap < (double)y2;
   }

   private float a(float cur, float target, float rate) {
      return cur + (target - cur) * (1.0F - (float)Math.exp((double)(-this.ac * rate)));
   }

   private float a(int slot) {
      float var2 = this.aD[slot];
      if (var2 > 0.001F) {
         this.aD[slot] = Math.max(0.0F, var2 - this.ac * 4.5F);
      }

      return var2;
   }

   private void f() {
      float var1 = 620.0F * this.aa / 2.0F;
      float var2 = 432.0F * this.aa / 2.0F;
      float var3 = var1 - 310.0F + 4.0F;
      float var4 = (float)this.width - 310.0F - var1 - 4.0F;
      float var5 = var2 - 216.0F + 4.0F;
      float var6 = (float)this.height - 216.0F - var2 - 4.0F;
      this.U = MathHelper.clamp(this.U, Math.min(var3, var4), Math.max(var3, var4));
      this.V = MathHelper.clamp(this.V, Math.min(var5, var6), Math.max(var5, var6));
   }

   private void a(DrawContext ctx, int x1, int y1, int x2, int y2) {
      BatchedQuadRenderer.a();
      ctx.enableScissor(this.a((float)x1), this.b((float)y1), this.a((float)x2), this.b((float)y2));
   }

   private void i(DrawContext ctx) {
      BatchedQuadRenderer.a();
      ctx.disableScissor();
   }

   private int a(float lx) {
      return Math.round(this.S + 310.0F + (lx - 310.0F) * this.aa);
   }

   private int b(float ly) {
      return Math.round(this.T + 216.0F + (ly - 216.0F) * this.aa);
   }

   private static String b(int code) {
      return code <= 0 ? "NONE" : InputUtil.fromKeyCode(code, -1).getLocalizedText().getString().toUpperCase();
   }

   private static Text a(String s) {
      return CustomFontTextFactory.a(s);
   }

   private static Text b(String s) {
      return CustomFontTextFactory.b(s);
   }

   private int c(int col) {
      int var2 = (int)((float)(col >>> 24 & 255) * this.ab);
      return var2 << 24 | col & 16777215;
   }

   private void a(DrawContext ctx, int x1, int y1, int x2, int y2, int top, int bottom) {
      BatchedQuadRenderer.a(ctx, (float)x1, (float)y1, (float)x2, (float)y2, this.c(top), this.c(bottom));
   }

   private void a(DrawContext ctx, Text t, int x, int y, float sc, int col) {
      int var7 = this.c(col);
      if (var7 >>> 24 >= 6) {
         BatchedQuadRenderer.a();
         MatrixStack var8 = ctx.getMatrices();
         var8.push();
         var8.translate((float)x, (float)y, 0.0F);
         var8.scale(sc, sc, 1.0F);
         ctx.drawText(this.textRenderer, t, 0, 0, var7, false);
         var8.pop();
      }
   }

   private void b(DrawContext ctx, Text t, int rightX, int y, float sc, int col) {
      this.a(ctx, t, rightX - Math.round((float)this.textRenderer.getWidth(t) * sc), y, sc, col);
   }

   private void c(DrawContext ctx, Text t, int centerX, int y, float sc, int col) {
      this.a(ctx, t, centerX - Math.round((float)this.textRenderer.getWidth(t) * sc / 2.0F), y, sc, col);
   }

   private void a(DrawContext ctx, int x1, int y1, int x2, int y2, int col) {
      BatchedQuadRenderer.a(ctx, (float)x1, (float)y1, (float)x2, (float)y2, this.c(col));
   }

   private void b(DrawContext ctx, int x1, int y1, int x2, int y2, int r, int col) {
      RoundedRectangleRenderer.a(ctx, x1, y1, x2, y2, r, this.c(col));
   }

   private static int a(int a, int b, float t) {
      return HalloweenColorTheme.a(a, b, t);
   }

   record Hitbox(int a, int b, int c, int d, int e, Object f) {
      boolean a(double mx, double my) {
         return mx >= (double)this.a && mx < (double)this.c && my >= (double)this.b && my < (double)this.d;
      }

   }

   record Pointer(float a, float b, long c) {}
}
