package a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public final class CombatHudRenderer {
   private static final int a = -804581088;
   private static final int b = -536013788;
   private static final int c = -2563339;
   private static final int d = -9470567;
   private static final int e = -14997696;
   private static final Map<String, Float> f = new HashMap();
   private static float g = -1.0F;
   private static long h = System.nanoTime();

   private CombatHudRenderer() {
   }

   private static Text a(String s) {
      return CustomFontTextFactory.a(s);
   }

   private static Text b(String s) {
      return CustomFontTextFactory.b(s);
   }

   public static void a(DrawContext ctx, float tickDelta) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      if (var2.player != null && !var2.options.hudHidden) {
         long var3 = System.nanoTime();
         float var5 = Math.min((float)(var3 - h) / 1.0E9F, 0.1F);
         h = var3;
         VisualEffectsModule var6 = CombatClientInitializer.c;
         int var7 = HalloweenColorTheme.b();
         int var8 = HalloweenColorTheme.a.c();
         TextRenderer var9 = var2.textRenderer;
         if (var6.d.a) {
            a(ctx, var9, var2, var7, var8);
         }

         if (var6.e.a) {
            a(ctx, var9, var2, var5, var7, var8);
         }

         if (var6.f.a) {
            b(ctx, var9, var2, var5, var7, var8);
         }

         BatchedQuadRenderer.a();
      }
   }

   private static void a(DrawContext ctx, TextRenderer tr, Text t, int x, int y, int col) {
      BatchedQuadRenderer.a();
      ctx.drawText(tr, t, x, y, col, false);
   }

   private static void a(DrawContext ctx, int x1, int y1, int x2, int y2, int top, int bottom) {
      BatchedQuadRenderer.a();
      ctx.fillGradient(x1, y1, x2, y2, top, bottom);
   }

   private static void a(DrawContext ctx, TextRenderer tr, MinecraftClient mc, int acc, int acc2) {
      Text var5 = b("NEVER");
      Text var6 = b("LOSE");
      Text var7 = a(mc.getSession().getUsername());
      int var8 = tr.getWidth(var5);
      int var9 = tr.getWidth(var6);
      int var10 = tr.getWidth(var7);
      boolean var11 = CombatClientInitializer.c.c.a;
      int var12 = var11 ? 14 : 0;
      int var13 = 12 + var12 + var8 + var9 + 12 + 1 + 12 + var10 + 12;
      byte var14 = 8;
      byte var15 = 8;
      byte var16 = 20;
      RoundedRectangleRenderer.a(ctx, var14, var15 + 1, var14 + var13, var15 + var16 + 2, 6, 1073741824);
      RoundedRectangleRenderer.a(ctx, var14, var15, var14 + var13, var15 + var16, 5, HalloweenColorTheme.a(-804581088, acc, 0.22F));
      RoundedRectangleRenderer.a(ctx, var14 + 1, var15 + 1, var14 + var13 - 1, var15 + var16 - 1, 4, -804581088);
      a(ctx, var14 + 5, var15 + 1, var14 + var13 - 5, var15 + var16 - 1, 352321535, 16777215);
      int var17 = var14 + 12 + var12;
      if (var11) {
         HalloweenIconRenderer.a(ctx, var14 + 7, var15 + 6, 1, 1.0F);
      }

      RoundedRectangleRenderer.a(ctx, var17 - 1, var15 + 6, var17 + 7, var15 + 14, 2, acc);
      RoundedRectangleRenderer.a(ctx, var17 + 1, var15 + 8, var17 + 5, var15 + 12, 1, HalloweenColorTheme.a(acc, -1, 0.65F));
      a(ctx, tr, var5, var17 + 12, var15 + 6, -2563339);
      a(ctx, tr, var6, var17 + 12 + var8, var15 + 6, acc);
      int var18 = var17 + 12 + var8 + var9 + 12;
      BatchedQuadRenderer.a(ctx, (float)var18, (float)(var15 + 6), (float)(var18 + 1), (float)(var15 + var16 - 6), -14010796);
      a(ctx, tr, var7, var18 + 12, var15 + 6, -9470567);
      long var19 = System.currentTimeMillis();
      int var21 = var13 - 10;
      int var22 = (int)(var19 / 6L % (long)(var21 + 40)) - 20;

      for(int var23 = 0; var23 < var21; var23 += 2) {
         float var24 = 1.0F - Math.min(1.0F, (float)Math.abs(var23 - var22) / 40.0F);
         int var25 = HalloweenColorTheme.a(acc, acc2, (float)var23 / (float)var21);
         int var26 = (int)(60.0F + 150.0F * var24);
         BatchedQuadRenderer.a(ctx, (float)(var14 + 5 + var23), (float)(var15 + var16 - 1), (float)(var14 + 5 + Math.min(var23 + 2, var21)), (float)(var15 + var16), var26 << 24 | var25 & 16777215);
      }

   }

   private static void a(DrawContext ctx, TextRenderer tr, MinecraftClient mc, float dt, int acc, int acc2) {
      ArrayList<Object[]> var6 = new ArrayList<>();

      for(ToggleableModule var8 : CombatClientInitializer.f) {
         if (var8.v) {
            float var9 = (Float)f.getOrDefault(var8.p, 0.0F);
            var9 += ((var8.p() ? 1.0F : 0.0F) - var9) * Math.min(1.0F, dt * 12.0F);
            f.put(var8.p, var9);
            if (var9 > 0.02F) {
               var6.add(new Object[]{a(var8.p), var9});
            }
         }
      }

      var6.sort((a, b) -> tr.getWidth((Text)b[0]) - tr.getWidth((Text)a[0]));
      int var21 = mc.getWindow().getScaledWidth();
      int var22 = 8;

      for(Object[] var10 : var6) {
         Text var11 = (Text)var10[0];
         float var12 = (Float)var10[1];
         float var13 = 1.0F - (1.0F - var12) * (1.0F - var12);
         int var14 = tr.getWidth(var11) + 20;
         byte var15 = 16;
         int var16 = var21 - 8 + Math.round((1.0F - var13) * (float)(var14 + 14));
         int var17 = var16 - var14;
         int var18 = (int)(208.0F * Math.min(1.0F, var13 * 1.4F));
         RoundedRectangleRenderer.a(ctx, var17, var22 + 1, var16, var22 + var15 + 2, 4, 805306368);
         RoundedRectangleRenderer.a(ctx, var17, var22, var16, var22 + var15, 4, var18 << 24 | 725280);
         a(ctx, var17 + 4, var22 + 1, var16 - 4, var22 + var15 - 1, 285212671, 16777215);
         int var19 = (int)(255.0F * Math.min(1.0F, var13));
         RoundedRectangleRenderer.a(ctx, var16 - 3, var22 + 2, var16, var22 + var15 - 2, 1, var19 << 24 | acc & 16777215);
         RoundedRectangleRenderer.a(ctx, var16 - 5, var22 + 3, var16 + 1, var22 + var15 - 3, 2, (int)(50.0F * var13) << 24 | acc & 16777215);
         int var20 = Math.max(1, Math.round(2.0F * var13));
         BatchedQuadRenderer.a(ctx, (float)(var17 + 8), (float)(var22 + var15 / 2 - var20), (float)(var17 + 8 + var20 * 2), (float)(var22 + var15 / 2 + var20), HalloweenColorTheme.a(acc, acc2, 0.5F));
         a(ctx, tr, var11, var17 + 15, var22 + 4, var19 << 24 | 14213877);
         var22 += var15 + 3;
      }

   }

   private static void b(DrawContext ctx, TextRenderer tr, MinecraftClient mc, float dt, int acc, int acc2) {
      LivingEntity var6 = CombatClientInitializer.b.f();
      if (var6 == null) {
         g = -1.0F;
      } else {
         float var7 = Math.max(0.0F, Math.min(1.0F, var6.getHealth() / Math.max(1.0F, var6.getMaxHealth())));
         g = g < 0.0F ? var7 : g + (var7 - g) * Math.min(1.0F, dt * 10.0F);
         int var8 = mc.getWindow().getScaledWidth() / 2 + 34;
         int var9 = mc.getWindow().getScaledHeight() / 2 + 22;
         short var10 = 132;
         byte var11 = 40;
         RoundedRectangleRenderer.a(ctx, var8, var9 + 1, var8 + var10, var9 + var11 + 2, 6, 1073741824);
         RoundedRectangleRenderer.a(ctx, var8, var9, var8 + var10, var9 + var11, 6, HalloweenColorTheme.a(-536013788, acc, 0.18F));
         RoundedRectangleRenderer.a(ctx, var8 + 1, var9 + 1, var8 + var10 - 1, var9 + var11 - 1, 5, -536013788);
         a(ctx, var8 + 5, var9 + 1, var8 + var10 - 5, var9 + var11 - 1, 352321535, 16777215);
         BatchedQuadRenderer.a(ctx, (float)(var8 + 8), (float)(var9 + 8), (float)(var8 + 9), (float)(var9 + 18), acc);
         a(ctx, tr, a(var6.getName().getString()), var8 + 14, var9 + 7, -2563339);
         Text var12 = a(String.format("%.1fm", mc.player.distanceTo(var6)));
         a(ctx, tr, var12, var8 + var10 - 11 - tr.getWidth(var12), var9 + 7, -9470567);
         int var13 = var8 + 11;
         int var14 = var9 + 27;
         int var15 = var10 - 22;
         byte var16 = 5;
         RoundedRectangleRenderer.a(ctx, var13, var14, var13 + var15, var14 + var16, 2, -14997696);
         int var17 = Math.max(3, (int)((float)var15 * g));
         int var18 = HalloweenColorTheme.a(-45730, -13315175, g);
         RoundedRectangleRenderer.a(ctx, var13, var14, var13 + var17, var14 + var16, 2, var18);
         RoundedRectangleRenderer.a(ctx, var13, var14, var13 + var17, var14 + 2, 1, HalloweenColorTheme.a(var18, -1, 0.35F));
         BatchedQuadRenderer.a(ctx, (float)(var13 + var17), (float)(var14 + 1), (float)(var13 + var17 + 2), (float)(var14 + var16 - 1), 1728053247);

         for(int var19 = 1; var19 < 4; ++var19) {
            int var20 = var13 + Math.round((float)(var15 * var19) / 4.0F);
            BatchedQuadRenderer.a(ctx, (float)var20, (float)var14, (float)(var20 + 1), (float)(var14 + var16), 1426063360);
         }

      }
   }
}
