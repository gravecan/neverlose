package a;

import net.minecraft.client.gui.DrawContext;

public final class RoundedRectangleRenderer {
   private RoundedRectangleRenderer() {
   }

   public static void a(DrawContext ctx, int x1, int y1, int x2, int y2, int r, int col) {
      if (col >>> 24 != 0) {
         a(ctx, x1, y1, x2, y2, r, r, r, r, col, col);
      }
   }

   public static void a(DrawContext ctx, int x1, int y1, int x2, int y2, int r, int top, int bottom) {
      a(ctx, x1, y1, x2, y2, r, r, r, r, top, bottom);
   }

   public static void a(DrawContext ctx, int x1, int y1, int x2, int y2, int rTL, int rTR, int rBR, int rBL, int top, int bottom) {
      int var11 = x2 - x1;
      int var12 = y2 - y1;
      if (var11 > 0 && var12 > 0) {
         int var13 = Math.min(var11, var12) / 2;
         rTL = Math.max(0, Math.min(rTL, var13));
         rTR = Math.max(0, Math.min(rTR, var13));
         rBR = Math.max(0, Math.min(rBR, var13));
         rBL = Math.max(0, Math.min(rBL, var13));
         float var14 = var12 > 1 ? 1.0F / (float)(var12 - 1) : 0.0F;
         int var15 = -1;

         for(int var16 = y1; var16 < y2; ++var16) {
            float var17 = Math.max(a((float)var16 + 0.5F - (float)y1, rTL), a((float)y2 - ((float)var16 + 0.5F), rBL));
            float var18 = Math.max(a((float)var16 + 0.5F - (float)y1, rTR), a((float)y2 - ((float)var16 + 0.5F), rBR));
            if (var17 <= 0.01F && var18 <= 0.01F) {
               if (var15 < 0) {
                  var15 = var16;
               }
            } else {
               if (var15 >= 0) {
                  a(ctx, x1, x2, var15, var16, top, bottom, y1, var14);
                  var15 = -1;
               }

               a(ctx, x1, x2, var16, var17, var18, a(top, bottom, ((float)var16 + 0.5F - (float)y1) * var14));
            }
         }

         if (var15 >= 0) {
            a(ctx, x1, x2, var15, y2, top, bottom, y1, var14);
         }

      }
   }

   private static void a(DrawContext ctx, int x1, int x2, int ya, int yb, int top, int bottom, int y0, float invH) {
      int var9 = a(top, bottom, ((float)ya + 0.5F - (float)y0) * invH);
      int var10 = a(top, bottom, ((float)yb - 0.5F - (float)y0) * invH);
      BatchedQuadRenderer.a(ctx, (float)x1, (float)ya, (float)x2, (float)yb, var9, var10);
   }

   private static float a(float dy, int r) {
      if (r > 0 && !(dy >= (float)r)) {
         if (dy <= 0.0F) {
            return (float)r;
         } else {
            float var2 = (float)r - dy;
            return (float)r - (float)Math.sqrt((double)Math.max(0.0F, (float)(r * r) - var2 * var2));
         }
      } else {
         return 0.0F;
      }
   }

   private static void a(DrawContext ctx, int x1, int x2, int y, float li, float ri, int col) {
      float var7 = (float)x1 + li;
      float var8 = (float)x2 - ri;
      if (!(var8 - var7 < 0.02F)) {
         int var9 = (int)Math.ceil((double)var7);
         int var10 = (int)Math.floor((double)var8);
         if (var10 > var9) {
            BatchedQuadRenderer.a(ctx, (float)var9, (float)y, (float)var10, (float)(y + 1), col);
            float var11 = (float)var9 - var7;
            if (var11 > 0.02F && var11 < 0.98F) {
               BatchedQuadRenderer.a(ctx, (float)(var9 - 1), (float)y, (float)var9, (float)(y + 1), a(col, var11));
            }

            float var12 = var8 - (float)var10;
            if (var12 > 0.02F && var12 < 0.98F) {
               BatchedQuadRenderer.a(ctx, (float)var10, (float)y, (float)(var10 + 1), (float)(y + 1), a(col, var12));
            }
         } else {
            float var13 = Math.min(1.0F, var8 - var7);
            if (var13 > 0.02F) {
               BatchedQuadRenderer.a(ctx, (float)var9, (float)y, (float)(var9 + 1), (float)(y + 1), a(col, var13));
            }
         }

      }
   }

   public static void a(DrawContext ctx, int cx, int cy, int radius, int col) {
      int var5 = col >>> 24 & 255;
      if (var5 != 0 && radius > 0) {
         int var6 = col & 16777215;

         for(int var7 = -radius; var7 < radius; var7 += 2) {
            double var8 = (double)var7 / (double)radius;
            double var10 = (double)1.0F - var8 * var8;
            if (!(var10 <= (double)0.0F)) {
               int var12 = (int)Math.round((double)radius * Math.sqrt(var10));
               double var13 = (double)1.0F - Math.abs(var8);
               int var15 = (int)((double)var5 * var13 * var13);
               if (var15 >= 2 && var12 > 0) {
                  int var16 = Math.max(0, (int)((double)var12 * 0.55));
                  BatchedQuadRenderer.a(ctx, (float)(cx - var16), (float)(cy + var7), (float)(cx + var16), (float)(cy + var7 + 2), var15 << 24 | var6);
                  int var17 = (int)((double)var15 * 0.42);
                  if (var17 >= 2 && var12 > var16) {
                     BatchedQuadRenderer.a(ctx, (float)(cx - var12), (float)(cy + var7), (float)(cx - var16), (float)(cy + var7 + 2), var17 << 24 | var6);
                     BatchedQuadRenderer.a(ctx, (float)(cx + var16), (float)(cy + var7), (float)(cx + var12), (float)(cy + var7 + 2), var17 << 24 | var6);
                  }
               }
            }
         }

      }
   }

   public static void a(DrawContext ctx, int cx, int cy, int radius, int thickness, int col, int x1, int y1, int x2, int y2) {
      if (col >>> 24 != 0 && radius > 0) {
         int var10 = Math.max(20, Math.min(64, radius * 3));
         int var11 = Math.max(1, thickness / 2);

         for(int var12 = 0; var12 < var10; ++var12) {
            double var13 = (double)var12 * (Math.PI * 2D) / (double)var10;
            int var15 = cx + (int)Math.round(Math.cos(var13) * (double)radius);
            int var16 = cy + (int)Math.round(Math.sin(var13) * (double)radius);
            if (var15 >= x1 && var15 < x2 && var16 >= y1 && var16 < y2) {
               BatchedQuadRenderer.a(ctx, (float)(var15 - var11), (float)(var16 - var11), (float)(var15 - var11 + thickness), (float)(var16 - var11 + thickness), col);
            }
         }

      }
   }

   public static void a(DrawContext ctx, int x0, int y0, int x1, int y1, int col) {
      int var6 = Math.abs(x1 - x0);
      int var7 = -Math.abs(y1 - y0);
      int var8 = x0 < x1 ? 1 : -1;
      int var9 = y0 < y1 ? 1 : -1;
      int var10 = var6 + var7;

      while(true) {
         BatchedQuadRenderer.a(ctx, (float)x0, (float)y0, (float)(x0 + 1), (float)(y0 + 1), col);
         if (x0 == x1 && y0 == y1) {
            return;
         }

         int var11 = 2 * var10;
         if (var11 >= var7) {
            var10 += var7;
            x0 += var8;
         }

         if (var11 <= var6) {
            var10 += var6;
            y0 += var9;
         }
      }
   }

   private static int a(int col, float f) {
      int var2 = (int)((float)(col >>> 24 & 255) * Math.max(0.0F, Math.min(1.0F, f)));
      return var2 << 24 | col & 16777215;
   }

   private static int a(int a, int b, float t) {
      if (t <= 0.0F) {
         return a;
      } else if (t >= 1.0F) {
         return b;
      } else {
         int var3 = a >>> 24;
         int var4 = a >> 16 & 255;
         int var5 = a >> 8 & 255;
         int var6 = a & 255;
         int var7 = b >>> 24;
         int var8 = b >> 16 & 255;
         int var9 = b >> 8 & 255;
         int var10 = b & 255;
         return (int)((float)var3 + (float)(var7 - var3) * t) << 24 | (int)((float)var4 + (float)(var8 - var4) * t) << 16 | (int)((float)var5 + (float)(var9 - var5) * t) << 8 | (int)((float)var6 + (float)(var10 - var6) * t);
      }
   }
}
