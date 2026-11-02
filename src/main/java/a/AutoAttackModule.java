package a;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class AutoAttackModule extends ToggleableModule {
   public final BooleanSetting a = this.a("Skip while hurt", true);
   public final BooleanSetting b = this.a("Hit while eating", false);
   public final ChoiceSetting c = this.a("Target", List.of("All", "Players", "Mobs"), 0);
   public final BooleanSetting d = this.a("Target lock", false);
   public final BooleanSetting e = this.a("Slot lock", false);
   public final BooleanSetting f = this.a("Move check", false);
   public final SliderSetting g = this.a("Delay min", (double)0.0F, (double)1000.0F, (double)585.0F, (double)5.0F, "ms");
   public final SliderSetting h = this.a("Delay max", (double)0.0F, (double)1000.0F, (double)600.0F, (double)5.0F, "ms");
   public final SliderSetting i = this.a("Locked slot", (double)1.0F, (double)9.0F, (double)1.0F, (double)1.0F, "");
   public final SliderSetting j = this.a("Re-enable delay", (double)1.0F, (double)10.0F, (double)1.0F, (double)0.5F, "s");
   private static final long k = 60L;
   private long l;
   private long m;
   private long n;
   private final Random w = new Random();

   public AutoAttackModule() {
      super("Attacks", "Attacks", "Melee attack timing.", 82);
   }

   public long a() {
      return this.n;
   }

   public void a(boolean on) {
      super.a(on);
      if (on) {
         this.l = 0L;
         this.m = 0L;
      }

   }

   public void b() {
      if (this.p() && this.f.a) {
         if (this.j.e >= (double)10.0F) {
            this.a(false);
            ModConfigStore.a();
         } else {
            this.m = System.currentTimeMillis() + (long)(this.j.e * (double)1000.0F);
         }
      }
   }

   public void a(MinecraftClient mc) {
      if (mc.player != null && mc.world != null && mc.interactionManager != null) {
         if (mc.currentScreen == null && mc.player.isAlive() && !mc.player.isSpectator()) {
            long var2 = System.currentTimeMillis();
            if (var2 >= this.m) {
               if (!this.a.a || !CombatReachHelper.b(mc)) {
                  Entity var4 = this.b(mc);
                  if (var4 != null) {
                     while(mc.options.attackKey.wasPressed()) {
                     }

                     if (!this.e.a || mc.player.getInventory().selectedSlot == this.d()) {
                        if (this.d.a) {
                           LivingEntity var5 = mc.player.getAttacking();
                           if (var5 != null && var5 != var4) {
                              return;
                           }
                        }

                        if (var2 >= this.l) {
                           if (this.b.a || !mc.player.isUsingItem() || mc.player.isFallFlying() && mc.player.getMainHandStack().isOf(Items.FIREWORK_ROCKET)) {
                              if (var4 instanceof LivingEntity) {
                                 LivingEntity var6 = (LivingEntity)var4;
                                 if (var6.hurtTime > 0) {
                                    return;
                                 }
                              }

                              if (CombatReachHelper.a(mc, var4)) {
                                 mc.interactionManager.attackEntity(mc.player, var4);
                                 mc.player.swingHand(Hand.MAIN_HAND);
                                 this.l = var2 + this.c();
                                 this.n = var2;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private long c() {
      int var1 = (int)Math.round(this.g.e);
      int var2 = (int)Math.round(Math.max((double)var1, this.h.e));
      long var3 = (long)var1 + (long)(this.w.nextDouble() * (double)(var2 - var1 + 1));
      return Math.max(60L, var3);
   }

   private int d() {
      return Math.max(0, Math.min(8, (int)this.i.e - 1));
   }

   private Entity b(MinecraftClient mc) {
      ClientPlayerEntity var2 = mc.player;
      double var3 = CombatReachHelper.a(mc);
      Vec3d var5 = var2.getEyePos();
      Vec3d var6 = var5.add(var2.getRotationVec(1.0F).multiply(var3));
      BlockHitResult var7 = mc.world.raycast(new RaycastContext(var5, var6, ShapeType.COLLIDER, FluidHandling.NONE, var2));
      double var8 = var7.getType() == Type.MISS ? Double.MAX_VALUE : var5.squaredDistanceTo(var7.getPos());
      Entity var10 = null;
      double var11 = Double.MAX_VALUE;
      Box var13 = var2.getBoundingBox().expand(var3 + (double)1.0F);

      for(Entity var15 : mc.world.getOtherEntities(var2, var13)) {
         if (CombatTargetFilter.a(var2, var15, this.c.b) && CombatReachHelper.a(mc, var15)) {
            Optional var16 = var15.getBoundingBox().expand(0.06).raycast(var5, var6);
            if (!var16.isEmpty()) {
               double var17 = var5.squaredDistanceTo((Vec3d)var16.get());
               if (!(var17 > var8) && var17 < var11) {
                  var11 = var17;
                  var10 = var15;
               }
            }
         }
      }

      return var10;
   }
}
