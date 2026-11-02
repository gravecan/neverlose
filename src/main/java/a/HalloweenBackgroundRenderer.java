package a;

import java.util.Random;
import net.minecraft.client.gui.DrawContext;

final class HalloweenBackgroundRenderer {
   private static final int a = 6;
   private final float[] b = new float[6];
   private final float[] c = new float[6];
   private final float[] d = new float[6];
   private final float[] e = new float[6];
   private final int[] f = new int[6];
   private final Random g = new Random();

   HalloweenBackgroundRenderer(int w, int h) {
      for(int var3 = 0; var3 < 6; ++var3) {
         this.b[var3] = this.g.nextFloat() * (float)w;
         this.c[var3] = 20.0F + this.g.nextFloat() * (float)h * 0.55F;
         this.d[var3] = 45.0F + this.g.nextFloat() * 75.0F;
         this.e[var3] = this.g.nextFloat() * 6.28F;
         this.f[var3] = this.g.nextFloat() < 0.3F ? 3 : 2;
      }

   }

   void a(DrawContext ctx, int w, int h, float dt, float time, float fade) {
      int var7 = Math.max(18, Math.min(w, h) / 9);
      int var8 = (int)((float)w * 0.83F);
      int var9 = (int)((float)h * 0.2F);

      for(int var10 = 3; var10 >= 1; --var10) {
         int var11 = var7 + var10 * 13;
         RoundedRectangleRenderer.a(ctx, var8 - var11, var9 - var11, var8 + var11, var9 + var11, var11, HalloweenIconRenderer.a(318732826, fade));
      }

      RoundedRectangleRenderer.a(ctx, var8 - var7, var9 - var7, var8 + var7, var9 + var7, var7, HalloweenIconRenderer.a(-19620, fade * 0.92F));
      int var15 = Math.max(2, var7 / 4);
      int var16 = Math.max(2, var7 / 6);
      RoundedRectangleRenderer.a(ctx, var8 - var7 / 2, var9 - var7 / 3, var8 - var7 / 2 + var15 * 2, var9 - var7 / 3 + var15 * 2, var15, HalloweenIconRenderer.a(1086936588, fade));
      RoundedRectangleRenderer.a(ctx, var8 + var7 / 5, var9 + var7 / 5, var8 + var7 / 5 + var16 * 2, var9 + var7 / 5 + var16 * 2, var16, HalloweenIconRenderer.a(1086936588, fade));

      for(int var12 = 0; var12 < 6; ++var12) {
         float[] var10000 = this.b;
         var10000[var12] += this.d[var12] * dt;
         if (this.b[var12] > (float)(w + 30)) {
            this.b[var12] = -30.0F;
            this.c[var12] = 20.0F + this.g.nextFloat() * (float)h * 0.55F;
         }

         int var13 = (int)((double)this.c[var12] + Math.sin((double)time * 2.1 + (double)this.e[var12]) * (double)14.0F);
         boolean var14 = (int)(time * 9.0F + this.e[var12] * 3.0F) % 2 == 0;
         HalloweenIconRenderer.a(ctx, var14 ? HalloweenIconRenderer.a : HalloweenIconRenderer.b, (int)this.b[var12], var13, this.f[var12], HalloweenIconRenderer.a(-13755822, fade * 0.95F));
      }

   }
}
