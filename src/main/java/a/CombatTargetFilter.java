package a;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.MerchantEntity;

final class CombatTargetFilter {
   static final int a = 0;
   static final int b = 1;
   static final int c = 2;

   private CombatTargetFilter() {
   }

   static boolean a(PlayerEntity self, Entity e, int mode) {
      if (e != self && e instanceof LivingEntity var3) {
         if (var3.isAlive() && !var3.isInvulnerable()) {
            if (e instanceof ArmorStandEntity) {
               return false;
            }

            if (mode != 1) {
               if (e instanceof TameableEntity) {
                  TameableEntity var4 = (TameableEntity)e;
                  if (var4.isTamed()) {
                     return false;
                  }
               }

               if (!(e instanceof MerchantEntity) && !(e instanceof GolemEntity)) {
                  if (mode == 2) {
                     return !(e instanceof PlayerEntity);
                  }

                  return true;
               }

               return false;
            }

            return e instanceof PlayerEntity || e instanceof ZombieEntity;
         }
      }

      return false;
   }

   static boolean a(PlayerEntity self, Entity e, boolean hostile, boolean passive, boolean players) {
      if (e != self && e instanceof LivingEntity var5) {
         if (var5.isAlive() && !var5.isInvulnerable()) {
            if (e instanceof ArmorStandEntity) {
               return false;
            }

            if (e instanceof PlayerEntity) {
               return players;
            }

            if (e instanceof Monster) {
               return hostile;
            }

            if (e instanceof TameableEntity) {
               TameableEntity var6 = (TameableEntity)e;
               if (var6.isTamed()) {
                  return false;
               }
            }

            if (!(e instanceof MerchantEntity) && !(e instanceof GolemEntity)) {
               return passive;
            }

            return false;
         }
      }

      return false;
   }
}
