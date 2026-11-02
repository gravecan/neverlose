package a;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;

public final class BatchedQuadRenderer {
   private static float[] a = new float[6144];
   private static int[] b = new int[2048];
   private static int c;

   private BatchedQuadRenderer() {
   }

   public static void a(DrawContext ctx, float x1, float y1, float x2, float y2, int color) {
      if (!(x2 <= x1) && !(y2 <= y1) && color >>> 24 != 0) {
         a(4);
         float[] var6 = a;
         int var7 = c * 3;
         a(ctx, x1, y1, var7);
         a(ctx, x1, y2, var7 + 3);
         a(ctx, x2, y2, var7 + 6);
         a(ctx, x2, y1, var7 + 9);
         int var8 = c;
         b[var8] = color;
         b[var8 + 1] = color;
         b[var8 + 2] = color;
         b[var8 + 3] = color;
         c += 4;
      }
   }

   public static void a(DrawContext ctx, float x1, float y1, float x2, float y2, int top, int bottom) {
      if (!(x2 <= x1) && !(y2 <= y1)) {
         a(4);
         int var7 = c * 3;
         a(ctx, x1, y1, var7);
         a(ctx, x1, y2, var7 + 3);
         a(ctx, x2, y2, var7 + 6);
         a(ctx, x2, y1, var7 + 9);
         int var8 = c;
         b[var8] = top;
         b[var8 + 1] = bottom;
         b[var8 + 2] = bottom;
         b[var8 + 3] = top;
         c += 4;
      }
   }

   public static void a() {
      if (c != 0) {
         BufferBuilder var0 = Tessellator.getInstance().getBuffer();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShader(GameRenderer::getPositionColorProgram);
         var0.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);

         for(int var1 = 0; var1 < c; ++var1) {
            int var2 = var1 * 3;
            var0.vertex((double)a[var2], (double)a[var2 + 1], (double)a[var2 + 2]).color(b[var1]).next();
         }

         BufferRenderer.drawWithGlobalProgram(var0.end());
         RenderSystem.disableBlend();
         c = 0;
      }
   }

   public static void b() {
      c = 0;
   }

   private static void a(DrawContext ctx, float x, float y, int at) {
      Matrix4f var4 = ctx.getMatrices().peek().getPositionMatrix();
      a[at] = var4.m00() * x + var4.m10() * y + var4.m30();
      a[at + 1] = var4.m01() * x + var4.m11() * y + var4.m31();
      a[at + 2] = var4.m02() * x + var4.m12() * y + var4.m32();
   }

   private static void a(int vertices) {
      int var1 = c + vertices;
      if (var1 > b.length) {
         int var2 = Math.max(var1, b.length * 2);
         float[] var3 = new float[var2 * 3];
         int[] var4 = new int[var2];
         System.arraycopy(a, 0, var3, 0, c * 3);
         System.arraycopy(b, 0, var4, 0, c);
         a = var3;
         b = var4;
      }
   }
}
