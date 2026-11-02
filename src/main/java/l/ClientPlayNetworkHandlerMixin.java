package l;

import a.CombatClientInitializer;
import a.VanishAlertsModule;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientPlayNetworkHandler.class})
public abstract class ClientPlayNetworkHandlerMixin {
   @Inject(
      method = {"onPlayerList"},
      at = {@At("HEAD")}
   )
   private void zhvCapturePlayerList(PlayerListS2CPacket packet, CallbackInfo ci) {
      VanishAlertsModule.a(packet);
   }

   @Inject(
      method = {"onPlayerRemove"},
      at = {@At("HEAD")}
   )
   private void zhvCapturePlayerRemove(PlayerRemoveS2CPacket packet, CallbackInfo ci) {
      VanishAlertsModule.a(packet);
   }

   @Inject(
      method = {"onEntityTrackerUpdate"},
      at = {@At("HEAD")}
   )
   private void zhvCaptureEntityMetadata(EntityTrackerUpdateS2CPacket packet, CallbackInfo ci) {
      VanishAlertsModule.a(packet);
   }

   @Inject(
      method = {"onGameMessage"},
      at = {@At("HEAD")}
   )
   private void zhvCaptureChat(GameMessageS2CPacket packet, CallbackInfo ci) {
      VanishAlertsModule.a(packet);
   }

   @Inject(
      method = {"onPlayerPositionLook"},
      at = {@At("HEAD")}
   )
   private void zhvCaptureTeleport(PlayerPositionLookS2CPacket packet, CallbackInfo ci) {
      CombatClientInitializer.a.b();
   }
}
