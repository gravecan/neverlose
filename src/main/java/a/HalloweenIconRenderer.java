package a;

import net.minecraft.client.gui.DrawContext;

public final class HalloweenIconRenderer {
   public static final String[] a = new String[]{"X.......X", "XX.X.X.XX", "XXXXXXXXX", ".X.XXX.X.", "........."};
   public static final String[] b = new String[]{".........", ".X.XXX.X.", "XXXXXXXXX", "XX.X.X.XX", "X.......X"};
   public static final String[] c = new String[]{"X..XXX..X", ".X.XXX.X.", "..XXXXX..", ".X.XXX.X.", "X..X.X..X"};
   public static final String[] d = new String[]{"....G....", "..OOGOO..", ".OOOOOOO.", "OOKOOOKOO", "OOOOOOOOO", "OKOKOKOKO", ".OOKKKOO.", "..OOOOO.."};

   private HalloweenIconRenderer() {
   }

   public static int a(int col, float a) {
      int var2 = (int)((float)(col >>> 24 & 255) * Math.max(0.0F, Math.min(1.0F, a)));
      return var2 << 24 | col & 16777215;
   }

   public static void a(DrawContext ctx, String[] rows, int x, int y, int scale, int color) {
      for(int var6 = 0; var6 < rows.length; ++var6) {
         for(int var7 = 0; var7 < rows[var6].length(); ++var7) {
            if (rows[var6].charAt(var7) != '.') {
               BatchedQuadRenderer.a(ctx, (float)(x + var7 * scale), (float)(y + var6 * scale), (float)(x + (var7 + 1) * scale), (float)(y + (var6 + 1) * scale), color);
            }
         }
      }

   }

   public static void a(DrawContext ctx, int x, int y, int scale, float alpha) {
      for(int var5 = 0; var5 < d.length; ++var5) {
         for(int var6 = 0; var6 < d[var5].length(); ++var6) {
            char var7 = d[var5].charAt(var6);
            int var8;
            switch (var7) {
               case 'G':
                  var8 = -12808913;
                  break;
               case 'K':
                  var8 = -15070422;
                  break;
               case 'O':
                  var8 = -34278;
                  break;
               default:
                  continue;
            }

            BatchedQuadRenderer.a(ctx, (float)(x + var6 * scale), (float)(y + var5 * scale), (float)(x + (var6 + 1) * scale), (float)(y + (var5 + 1) * scale), a(var8, alpha));
         }
      }

   }

   public static void a(DrawContext ctx, int x0, int y0, int x1, int y1, int color) {
      int var6 = Math.abs(x1 - x0);
      int var7 = -Math.abs(y1 - y0);
      int var8 = x0 < x1 ? 1 : -1;
      int var9 = y0 < y1 ? 1 : -1;
      int var10 = var6 + var7;

      while(true) {
         BatchedQuadRenderer.a(ctx, (float)x0, (float)y0, (float)(x0 + 1), (float)(y0 + 1), color);
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

   public static void a(DrawContext ctx, int cx, int cy, int dirX, int dirY, int size, int color) {
      byte var7 = 6;
      int[] var8 = new int[var7];
      int[] var9 = new int[var7];

      for(int var10 = 0; var10 < var7; ++var10) {
         double var11 = Math.toRadians((double)var10 * (double)90.0F / (double)(var7 - 1));
         var8[var10] = cx + (int)Math.round((double)dirX * Math.cos(var11) * (double)size);
         var9[var10] = cy + (int)Math.round((double)dirY * Math.sin(var11) * (double)size);
         a(ctx, cx, cy, var8[var10], var9[var10], color);
      }

      double[] var23 = new double[]{0.38, 0.65, 0.92};

      for(double var14 : var23) {
         int var16 = 0;
         int var17 = 0;

         for(int var18 = 0; var18 < var7; ++var18) {
            double var19 = Math.toRadians((double)var18 * (double)90.0F / (double)(var7 - 1));
            int var21 = cx + (int)Math.round((double)dirX * Math.cos(var19) * (double)size * var14);
            int var22 = cy + (int)Math.round((double)dirY * Math.sin(var19) * (double)size * var14);
            if (var18 > 0) {
               a(ctx, var16, var17, var21, var22, color);
            }

            var16 = var21;
            var17 = var22;
         }
      }

   }
}
