package a;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;

public class HalloweenCosmeticsFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
   private static final Identifier a = new Identifier("chatpaches", "textures/halloween/witch_hat.png");
   private static final Identifier b = new Identifier("chatpaches", "textures/halloween/bat_wings.png");
   private final ModelPart c = a().createModel();
   private final ModelPart d = b().createModel();

   public HalloweenCosmeticsFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
      super(context);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider consumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
      MinecraftClient var11 = MinecraftClient.getInstance();
      if (var11.player != null && var11.player == player && !player.isInvisible()) {
         if (HalloweenCosmeticsModule.a()) {
            matrices.push();
            ((PlayerEntityModel)this.getContextModel()).head.rotate(matrices);
            VertexConsumer var12 = consumers.getBuffer(RenderLayer.getEntityCutoutNoCull(a));
            this.c.render(matrices, var12, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
         }

         if (HalloweenCosmeticsModule.b()) {
            matrices.push();
            ((PlayerEntityModel)this.getContextModel()).body.rotate(matrices);
            float var16 = (float)Math.sin((double)(animationProgress * 0.35F * HalloweenCosmeticsModule.c())) * 0.3F + 0.15F + limbDistance * 0.6F;
            ModelPart var13 = this.d.getChild("left_wing");
            ModelPart var14 = this.d.getChild("right_wing");
            var13.roll = -var16;
            var14.roll = var16;
            var13.yaw = -0.4F;
            var14.yaw = 0.4F;
            VertexConsumer var15 = consumers.getBuffer(RenderLayer.getEntityCutoutNoCull(b));
            this.d.render(matrices, var15, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
         }

      }
   }

   private static TexturedModelData a() {
      ModelData var0 = new ModelData();
      ModelPartData var1 = var0.getRoot();
      ModelPartData var2 = var1.addChild("hat", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -9.2F, -5.0F, 10.0F, 1.0F, 10.0F).uv(0, 11).cuboid(-3.5F, -12.7F, -3.5F, 7.0F, 4.0F, 7.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
      ModelPartData var3 = var2.addChild("mid", ModelPartBuilder.create().uv(28, 11).cuboid(-2.5F, -3.9F, -2.5F, 5.0F, 4.0F, 5.0F), ModelTransform.of(0.0F, -12.5F, 0.0F, -0.2F, 0.0F, 0.0F));
      var3.addChild("tip", ModelPartBuilder.create().uv(0, 22).cuboid(-1.0F, -2.9F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.of(0.0F, -3.6F, 0.0F, -0.32F, 0.0F, 0.0F));
      return TexturedModelData.of(var0, 64, 32);
   }

   private static TexturedModelData b() {
      ModelData var0 = new ModelData();
      ModelPartData var1 = var0.getRoot();
      var1.addChild("left_wing", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -8.0F, -0.5F, 12.0F, 16.0F, 1.0F).uv(28, 0).cuboid(11.0F, -8.0F, -0.5F, 5.0F, 11.0F, 1.0F), ModelTransform.pivot(2.0F, 1.0F, 2.2F));
      var1.addChild("right_wing", ModelPartBuilder.create().mirrored(true).uv(0, 0).cuboid(-12.0F, -8.0F, -0.5F, 12.0F, 16.0F, 1.0F).uv(28, 0).cuboid(-16.0F, -8.0F, -0.5F, 5.0F, 11.0F, 1.0F), ModelTransform.pivot(-2.0F, 1.0F, 2.2F));
      return TexturedModelData.of(var0, 64, 32);
   }

}
