package a;

import java.util.List;

public final class HalloweenColorTheme {
   public static final Entry a = new Entry("Halloween", -7643914, -4160260);
   public static final List<Entry> b;

   private HalloweenColorTheme() {
   }

   public static Entry a() {
      return a;
   }

   public static int b() {
      if (!CombatClientInitializer.c.a.a) {
         return a.b();
      } else {
         float var0 = (float)((Math.sin((double)System.currentTimeMillis() / (double)800.0F) + (double)1.0F) / (double)2.0F);
         return a(a.b(), a.c(), var0 * 0.85F);
      }
   }

   public static int a(int col, int a) {
      return Math.max(0, Math.min(255, a)) << 24 | col & 16777215;
   }

   public static int a(int a, int b, float t) {
      t = Math.max(0.0F, Math.min(1.0F, t));
      int var3 = a >>> 24 & 255;
      int var4 = a >> 16 & 255;
      int var5 = a >> 8 & 255;
      int var6 = a & 255;
      int var7 = b >>> 24 & 255;
      int var8 = b >> 16 & 255;
      int var9 = b >> 8 & 255;
      int var10 = b & 255;
      return (int)((float)var3 + (float)(var7 - var3) * t) << 24 | (int)((float)var4 + (float)(var8 - var4) * t) << 16 | (int)((float)var5 + (float)(var9 - var5) * t) << 8 | (int)((float)var6 + (float)(var10 - var6) * t);
   }

   static {
      b = List.of(a);
   }

   public record Entry(String a, int b, int c) {}
}
