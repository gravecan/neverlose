package a;

import java.util.List;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

public class CombatClientInitializer implements ClientModInitializer {
   public static final AutoAttackModule a = new AutoAttackModule();
   public static final AimAssistModule b = new AimAssistModule();
   public static final VisualEffectsModule c = new VisualEffectsModule();
   public static final HalloweenCosmeticsModule d = new HalloweenCosmeticsModule();
   public static final LicenseModule e = new LicenseModule();
   public static final List<ToggleableModule> f;
   public static int g;
   private boolean h;
   private boolean i;
   private long j = System.nanoTime();

   public void onInitializeClient() {
      HardwareIdProvider.a();
      LicenseRevocationManager.a(FabricLoader.getInstance().getConfigDir().resolve("viabackward.revoked"), System.getProperty("viabackward.feed", System.getProperty("zhuravel.feed", "")));
      ModConfigStore.a(f);
      if (!LicenseModule.b()) {
         for(ToggleableModule var2 : f) {
            if (var2.v) {
               var2.a(false);
            }
         }
      }

      ClientLifecycleEvents.CLIENT_STOPPING.register((ClientLifecycleEvents.ClientStopping)(mc) -> ModConfigStore.b());
      ClientPlayConnectionEvents.JOIN.register((ClientPlayConnectionEvents.Join)(handler, sender, client) -> {
         this.i = true;
         LicenseRevocationManager.a();
      });
      ClientTickEvents.START_CLIENT_TICK.register((ClientTickEvents.StartTick)(mc) -> {
         if (LicenseModule.b()) {
            if (a.p()) {
               a.a(mc);
            }

         }
      });
      ClientTickEvents.END_CLIENT_TICK.register((ClientTickEvents.EndTick)(mc) -> {
         LicenseRevocationManager.a();
         boolean var2 = LicenseModule.b();
         if (this.i && mc.player != null) {
            this.i = false;
            if (!var2) {
               if (LicenseModule.e() == LicenseStateValidator.State.g) {
                  mc.player.sendMessage(Text.literal("\u00a7c[ViaBackward] \u00a7fThis licence was revoked \u2014 the modules stay locked."), false);
               } else if (LicenseModule.e() == LicenseStateValidator.State.f) {
                  mc.player.sendMessage(Text.literal("\u00a7c[ViaBackward] \u00a7fThe mod files do not match the signed build \u2014 modules stay locked."), false);
               } else {
                  mc.player.sendMessage(Text.literal("\u00a76[ViaBackward] \u00a7fNo licence active \u2014 paste your token in \u00a77MISC > License\u00a7f to unlock the modules."), false);
               }
            }
         }

         if (!var2) {
            for(ToggleableModule var6 : f) {
               if (var6.v && var6.p()) {
                  var6.a(false);
               }
            }

         } else {
            for(ToggleableModule var4 : f) {
               if (var4 != a && var4.v && var4.p()) {
                  var4.a(mc);
               }
            }

         }
      });
      HudRenderCallback.EVENT.register(CombatHudRenderer::a);
      LivingEntityFeatureRendererRegistrationCallback.EVENT.register((LivingEntityFeatureRendererRegistrationCallback)(entityType, renderer, helper, context) -> {
         if (renderer instanceof PlayerEntityRenderer var4) {
            helper.register(new HalloweenCosmeticsFeatureRenderer(var4));
         }

      });
      WorldRenderEvents.START.register((WorldRenderEvents.Start)(ctx) -> {
         long var2 = System.nanoTime();
         float var4 = Math.min((float)(var2 - this.j) / 1.0E9F, 0.1F);
         this.j = var2;
         MinecraftClient var5 = MinecraftClient.getInstance();
         long var6 = var5.getWindow().getHandle();
         boolean var8 = g > 0 && InputUtil.isKeyPressed(var6, g);
         if (var8 && !this.h && var5.currentScreen == null && var5.player != null) {
            var5.setScreen(new ModuleSettingsScreen());
         }

         this.h = var8;
         boolean var9 = LicenseModule.b();

         for(ToggleableModule var11 : f) {
            if (var11.v) {
               boolean var12 = var11.s > 0 && InputUtil.isKeyPressed(var6, var11.s);
               if (var12 && !var11.t && var5.currentScreen == null) {
                  var11.a_();
               }

               var11.t = var12;
               if (var9 && var11.p()) {
                  var11.a(var5, var4);
               }
            }
         }

      });
   }

   static {
      f = List.of(a, b, c, d, e);
      g = 344;
   }
}
