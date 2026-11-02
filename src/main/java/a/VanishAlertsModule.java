package a;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.TextContent;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Action;

public class VanishAlertsModule extends ToggleableModule {
   public static VanishAlertsModule a;
   public final SliderSetting b = this.a("Sensor range", (double)8.0F, (double)128.0F, (double)48.0F, (double)4.0F, "m");
   public final LicenseKeySetting c = new LicenseKeySetting("staff-ranks", "Owner|Helper|Admin|SrMod|Mod|SrHelper|Developer");
   private static final String[][] d = new String[][]{{"srhelper", "SrHelper"}, {"srmod", "SrMod"}, {"owner", "Owner"}, {"developer", "Developer"}, {"admin", "Admin"}, {"mod", "Mod"}, {"helper", "Helper"}};
   private String[][] e;
   private static final int f = 20;
   private static final long g = 60000L;
   private static final long h = 5000L;
   private static final long i = 20000L;
   private static final long j = 5000L;
   private static final long k = 8000L;
   private static final int l = 256;
   private final ConcurrentLinkedQueue<StaffEntry> m = new ConcurrentLinkedQueue();
   private final Map<UUID, String> n = new HashMap();
   private final Map<UUID, String> w = new HashMap();
   private final Map<UUID, Long> x = new HashMap();
   private final Map<UUID, Long> y = new HashMap();
   private final Map<UUID, Long> z = new HashMap();
   private final Deque<GameEntry> A = new ArrayDeque();
   private final Deque<NameEntry> B = new ArrayDeque();
   private int C;
   private String D = "";
   private String E = "";
   private long F;

   public VanishAlertsModule() {
      super("VanishAlerts", "Staff watching you: vanished, spectator or invisible nearby.", 88);
      this.u = "COMBAT";
      a = this;
      this.e = a(this.c.a);
   }

   static String[][] a(String list) {
      if (list != null && !list.isBlank()) {
         ArrayList var1 = new ArrayList();

         for(String var5 : list.split("\\|")) {
            String var6 = var5.trim().toLowerCase(Locale.ROOT);
            if (!var6.isEmpty()) {
               for(String[] var10 : d) {
                  if (var10[0].equals(var6) || var10[1].equals(var6)) {
                     var1.add(var10);
                     break;
                  }
               }
            }
         }

         return var1.isEmpty() ? d : (String[][])var1.toArray(new String[0][]);
      } else {
         return d;
      }
   }

   public String a() {
      return this.D;
   }

   public String b() {
      return this.E;
   }

   public long c() {
      return this.F;
   }

   public static void a(PlayerListS2CPacket packet) {
      VanishAlertsModule var1 = a;
      if (var1 != null) {
         int var2 = 0;
         if (packet.getActions().contains(Action.UPDATE_LISTED)) {
            for(PlayerListS2CPacket.Entry var4 : packet.getEntries()) {
               if (var4 != null && !var4.listed()) {
                  ++var2;
               }
            }
         }

         boolean var7 = var2 > 0 && var2 < 4;
         boolean var8 = packet.getActions().contains(Action.UPDATE_GAME_MODE);

         for(PlayerListS2CPacket.Entry var6 : packet.getEntries()) {
            if (var6 != null) {
               if (var6.displayName() != null) {
                  var1.m.add(VanishAlertsModule.StaffEntry.b(var6.profileId(), var6.displayName().getString()));
               }

               if (var8 && var6.gameMode() != null) {
                  var1.m.add(VanishAlertsModule.StaffEntry.a(var6.profileId(), var6.gameMode().getName()));
               }

               if (var7 && !var6.listed()) {
                  var1.m.add(VanishAlertsModule.StaffEntry.b(var6.profileId()));
               }
            }
         }

      }
   }

   public static void a(PlayerRemoveS2CPacket packet) {
      VanishAlertsModule var1 = a;
      if (var1 != null) {
         List<UUID> var2 = packet.profileIds();
         if (var2 != null && var2.size() < 4) {
            for(UUID var4 : var2) {
               var1.m.add(VanishAlertsModule.StaffEntry.a(var4));
            }

         }
      }
   }

   public static void a(EntityTrackerUpdateS2CPacket packet) {
      VanishAlertsModule var1 = a;
      if (var1 != null) {
         var1.m.add(VanishAlertsModule.StaffEntry.a(packet.id()));
      }
   }

   public static void a(GameMessageS2CPacket packet) {
      VanishAlertsModule var1 = a;
      if (var1 != null) {
         String var2 = packet.content() == null ? "" : packet.content().getString();
         if (!var2.isBlank()) {
            var1.m.add(VanishAlertsModule.StaffEntry.a(var2));
            String var3 = a(packet.content());
            if (!var3.isBlank()) {
               var1.m.add(VanishAlertsModule.StaffEntry.b(var3));
            }

         }
      }
   }

   public void a(MinecraftClient mc) {
      if (mc.player != null && mc.world != null) {
         ++this.C;

         for(int var2 = 0; var2 < 256; ++var2) {
            StaffEntry var3 = (StaffEntry)this.m.poll();
            if (var3 == null) {
               break;
            }

            switch (var3.a) {
               case a:
                  this.a(mc, var3.b);
                  break;
               case b:
                  this.b(mc, var3.b);
                  break;
               case c:
                  this.a(mc, var3);
                  break;
               case d:
                  if (var3.b != null) {
                     String var4 = this.b(var3.d);
                     if (var4 != null) {
                        this.w.put(var3.b, var4);
                     }
                  }
                  break;
               case e:
                  this.a(mc, var3.c);
                  break;
               case f:
                  this.c(var3.d);
                  break;
               case g:
                  this.d(var3.d);
            }
         }

         if (this.C % 10 == 0) {
            this.b(mc);
         }

         if (this.C % 5 == 0) {
            this.d(mc);
         }

         this.c(mc);
         this.d();
      } else {
         this.e();
      }
   }

   private void b(MinecraftClient mc) {
      if (mc.getNetworkHandler() != null && mc.player != null) {
         for(PlayerListEntry var3 : mc.getNetworkHandler().getListedPlayerListEntries()) {
            if (var3 != null && var3.getProfile() != null && var3.getProfile().getId() != null) {
               UUID var4 = var3.getProfile().getId();
               if (!var4.equals(mc.player.getUuid())) {
                  String var5 = var3.getProfile().getName();
                  if (var5 != null && !var5.isBlank()) {
                     this.n.put(var4, var5);
                  }

                  String var6 = this.b(var3.getDisplayName() == null ? null : var3.getDisplayName().getString());
                  if (var6 != null) {
                     this.w.put(var4, var6);
                  }
               }
            }
         }

         if (this.n.size() > 2048) {
            HashSet var7 = new HashSet();

            for(PlayerListEntry var9 : mc.getNetworkHandler().getPlayerList()) {
               if (var9 != null && var9.getProfile() != null) {
                  var7.add(var9.getProfile().getId());
               }
            }

            this.n.keySet().retainAll(var7);
            this.w.keySet().retainAll(var7);
         }

      }
   }

   private void a(MinecraftClient mc, StaffEntry o) {
      if (o.b != null) {
         if ("spectator".equals(o.d)) {
            this.x.put(o.b, System.currentTimeMillis());
            this.b(mc, o.b);
         } else {
            this.x.remove(o.b);
         }

      }
   }

   private void a(MinecraftClient mc, UUID uuid) {
      if (uuid != null && mc.player != null && !uuid.equals(mc.player.getUuid())) {
         this.A.addLast(new GameEntry(uuid, this.C + 20));
      }
   }

   private void c(MinecraftClient mc) {
      while(!this.A.isEmpty() && ((GameEntry)this.A.peekFirst()).b <= this.C) {
         GameEntry var2 = (GameEntry)this.A.removeFirst();
         if (mc.getNetworkHandler() != null && mc.world != null && mc.getNetworkHandler().getPlayerListEntry(var2.a) == null) {
            long var3 = System.currentTimeMillis();
            if ((Long)this.y.getOrDefault(var2.a, 0L) <= var3 && !this.e(this.c(mc, var2.a))) {
               PlayerEntity var5 = mc.world.getPlayerByUuid(var2.a);
               if (var5 != null && !var5.isRemoved()) {
                  this.b(mc, var2.a);
               }
            }
         }
      }

   }

   private void b(MinecraftClient mc, UUID uuid) {
      if (uuid != null && mc.player != null && mc.world != null) {
         if (!uuid.equals(mc.player.getUuid())) {
            PlayerEntity var3 = mc.world.getPlayerByUuid(uuid);
            if (var3 != null && !var3.isRemoved()) {
               if (!(var3.squaredDistanceTo(mc.player) > this.b.e * this.b.e)) {
                  String var4 = (String)this.w.get(uuid);
                  if (var4 != null) {
                     this.a(uuid, this.c(mc, uuid), var4);
                  }
               }
            }
         }
      }
   }

   private void d(MinecraftClient mc) {
      if (mc.player != null && mc.world != null) {
         long var2 = System.currentTimeMillis();
         double var4 = this.b.e * this.b.e;

         for(PlayerEntity var7 : mc.world.getPlayers()) {
            if (var7 != null && var7 != mc.player && !(var7.squaredDistanceTo(mc.player) > var4)) {
               UUID var8 = var7.getUuid();
               if (this.w.containsKey(var8)) {
                  Long var9 = (Long)this.x.get(var8);
                  boolean var10 = var9 != null && var2 - var9 <= 60000L;
                  if (var10 || var7.isInvisible()) {
                     this.a(var8, this.c(mc, var8), (String)this.w.get(var8));
                  }
               }
            }
         }

      }
   }

   private void a(MinecraftClient mc, int entityId) {
      if (mc.player != null && mc.world != null) {
         Entity var3 = mc.world.getEntityById(entityId);
         if (var3 instanceof PlayerEntity) {
            PlayerEntity var4 = (PlayerEntity)var3;
            if (var4 != mc.player && var4.isInvisible()) {
               if (var4.squaredDistanceTo(mc.player) > this.b.e * this.b.e) {
                  return;
               }

               if (!this.w.containsKey(var4.getUuid())) {
                  return;
               }

               this.a(var4.getUuid(), this.c(mc, var4.getUuid()), (String)this.w.get(var4.getUuid()));
               return;
            }
         }

      }
   }

   private void a(UUID uuid, String name, String rank) {
      if (uuid != null && name != null && !name.isBlank()) {
         long var4 = System.currentTimeMillis();
         if (name.equals(this.D) && var4 < this.F + 2000L) {
            this.F = var4 + 5000L;
         } else {
            Long var6 = (Long)this.z.get(uuid);
            if (var6 == null || var4 - var6 >= 20000L) {
               this.z.put(uuid, var4);
               this.D = name;
               this.E = rank != null ? rank : "";
               this.F = var4 + 5000L;
            }
         }
      }
   }

   private String c(MinecraftClient mc, UUID uuid) {
      if (uuid == null) {
         return "";
      } else {
         String var3 = (String)this.n.get(uuid);
         if (var3 != null && !var3.isBlank()) {
            return var3;
         } else {
            if (mc.getNetworkHandler() != null) {
               PlayerListEntry var4 = mc.getNetworkHandler().getPlayerListEntry(uuid);
               if (var4 != null && var4.getProfile() != null && var4.getProfile().getName() != null) {
                  String var8 = var4.getProfile().getName();
                  this.n.put(uuid, var8);
                  return var8;
               }
            }

            PlayerEntity var7 = mc.world == null ? null : mc.world.getPlayerByUuid(uuid);
            if (var7 instanceof AbstractClientPlayerEntity) {
               AbstractClientPlayerEntity var5 = (AbstractClientPlayerEntity)var7;
               if (var5.getGameProfile() != null && var5.getGameProfile().getName() != null) {
                  String var6 = var5.getGameProfile().getName();
                  this.n.put(uuid, var6);
                  return var6;
               }
            }

            return "";
         }
      }
   }

   private void c(String text) {
      long var2 = System.currentTimeMillis();
      this.B.addLast(new NameEntry(text, var2));

      while(!this.B.isEmpty() && (var2 - ((NameEntry)this.B.peekFirst()).b > 8000L || this.B.size() > 64)) {
         this.B.removeFirst();
      }

   }

   private void d(String displayed) {
      if (displayed != null && !displayed.isBlank()) {
         long var2 = System.currentTimeMillis();

         for(Map.Entry var5 : this.n.entrySet()) {
            if (a(displayed, (String)var5.getValue())) {
               this.y.put((UUID)var5.getKey(), var2 + 5000L);
               UUID var6 = (UUID)var5.getKey();
               this.A.removeIf((p) -> p.a.equals(var6));
            }
         }

      }
   }

   private boolean e(String name) {
      if (name != null && !name.isBlank()) {
         long var2 = System.currentTimeMillis();

         for(NameEntry var5 : this.B) {
            if (var2 - var5.b <= 8000L && c(var5.a, name)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void d() {
      long var1 = System.currentTimeMillis();
      this.y.entrySet().removeIf((e) -> (Long)e.getValue() <= var1);
      this.z.entrySet().removeIf((e) -> var1 - (Long)e.getValue() > 120000L);
      this.x.entrySet().removeIf((e) -> var1 - (Long)e.getValue() > 60000L);

      while(!this.B.isEmpty() && var1 - ((NameEntry)this.B.peekFirst()).b > 8000L) {
         this.B.removeFirst();
      }

   }

   private void e() {
      this.m.clear();
      this.n.clear();
      this.w.clear();
      this.x.clear();
      this.y.clear();
      this.z.clear();
      this.A.clear();
      this.B.clear();
      this.C = 0;
      this.D = "";
      this.F = 0L;
   }

   String b(String display) {
      if (display != null && !display.isBlank()) {
         String var2 = f(display).toLowerCase(Locale.ROOT);

         for(String[] var6 : this.e) {
            if (d(var2, var6[0])) {
               return var6[1];
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static boolean d(String text, String token) {
      int var3;
      for(int var2 = 0; var2 <= text.length() - token.length(); var2 = var3 + 1) {
         var3 = text.indexOf(token, var2);
         if (var3 < 0) {
            return false;
         }

         int var4 = var3 + token.length();
         boolean var5 = var3 == 0 || !Character.isLetterOrDigit(text.charAt(var3 - 1));
         boolean var6 = var4 == text.length() || !Character.isLetterOrDigit(text.charAt(var4));
         if (var5 && var6) {
            return true;
         }
      }

      return false;
   }

   static String a(Text content) {
      if (content == null) {
         return "";
      } else {
         TextContent var2 = content.getContent();
         if (var2 instanceof TranslatableTextContent) {
            TranslatableTextContent var1 = (TranslatableTextContent)var2;
            if ("multiplayer.player.left".equals(var1.getKey())) {
               Object[] var5 = var1.getArgs();
               if (var5.length != 0 && var5[0] != null) {
                  Object var4 = var5[0];
                  String var10000;
                  if (var4 instanceof Text) {
                     Text var3 = (Text)var4;
                     var10000 = var3.getString();
                  } else {
                     var10000 = String.valueOf(var5[0]);
                  }

                  return var10000;
               }
            }
         }

         return "";
      }
   }

   static boolean a(String displayed, String playerName) {
      String var2 = f(displayed == null ? "" : displayed).toLowerCase(Locale.ROOT);
      String var3 = f(playerName == null ? "" : playerName).trim().toLowerCase(Locale.ROOT);
      if (var3.isEmpty()) {
         return false;
      } else if (!var3.matches("[a-z0-9_]{1,16}")) {
         return var2.contains(var3);
      } else {
         int var5;
         for(int var4 = 0; var4 <= var2.length() - var3.length(); var4 = var5 + 1) {
            var5 = var2.indexOf(var3, var4);
            if (var5 < 0) {
               return false;
            }

            int var6 = var5 + var3.length();
            boolean var7 = var5 == 0 || !a(var2.charAt(var5 - 1));
            boolean var8 = var6 == var2.length() || !a(var2.charAt(var6));
            if (var7 && var8) {
               return true;
            }
         }

         return false;
      }
   }

   static boolean c(String message, String playerName) {
      if (!a(message, playerName)) {
         return false;
      } else {
         String var10000 = f(message);
         String var2 = " " + var10000.toLowerCase(Locale.ROOT) + " ";
         var10000 = playerName.trim();
         String var3 = " " + var10000.toLowerCase(Locale.ROOT) + " ";
         return var2.contains(var3 + "left the game ") || var2.contains(var3 + "left ") || var2.contains(var3 + "has left ") || var2.contains(var3 + "quit ") || var2.contains(var3 + "has quit ") || var2.contains(var3 + "disconnected ") || var2.contains(var3 + "logged out ");
      }
   }

   private static boolean a(char c) {
      return c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '_';
   }

   private static String f(String text) {
      if (text != null && !text.isEmpty()) {
         StringBuilder var1 = new StringBuilder(text.length());

         for(int var2 = 0; var2 < text.length(); ++var2) {
            char var3 = text.charAt(var2);
            if (var3 == 167 && var2 + 1 < text.length()) {
               char var4 = Character.toLowerCase(text.charAt(var2 + 1));
               if ("0123456789abcdefklmnorx".indexOf(var4) >= 0) {
                  ++var2;
                  continue;
               }
            }

            var1.append(var3);
         }

         return var1.toString();
      } else {
         return "";
      }
   }

   static final class GameEntry {
      final UUID a;
      final int b;

      GameEntry(UUID uuid, int dueTick) {
         this.a = uuid;
         this.b = dueTick;
      }
   }

   static final class NameEntry {
      final String a;
      final long b;

      NameEntry(String text, long atMs) {
         this.a = text;
         this.b = atMs;
      }
   }

   static enum Rank {
      a,
      b,
      c,
      d,
      e,
      f,
      g;

      // $FF: synthetic method
      private static Rank[] a() {
         return new Rank[]{a, b, c, d, e, f, g};
      }
   }

   static final class StaffEntry {
      final Rank a;
      final UUID b;
      final int c;
      final String d;

      StaffEntry(Rank type, UUID uuid, int entityId, String detail) {
         this.a = type;
         this.b = uuid;
         this.c = entityId;
         this.d = detail;
      }

      static StaffEntry a(UUID id) {
         return new StaffEntry(VanishAlertsModule.Rank.a, id, -1, "");
      }

      static StaffEntry b(UUID id) {
         return new StaffEntry(VanishAlertsModule.Rank.b, id, -1, "");
      }

      static StaffEntry a(UUID id, String mode) {
         return new StaffEntry(VanishAlertsModule.Rank.c, id, -1, mode);
      }

      static StaffEntry b(UUID id, String text) {
         return new StaffEntry(VanishAlertsModule.Rank.d, id, -1, text);
      }

      static StaffEntry a(int id) {
         return new StaffEntry(VanishAlertsModule.Rank.e, (UUID)null, id, "");
      }

      static StaffEntry a(String text) {
         return new StaffEntry(VanishAlertsModule.Rank.f, (UUID)null, -1, text);
      }

      static StaffEntry b(String name) {
         return new StaffEntry(VanishAlertsModule.Rank.g, (UUID)null, -1, name);
      }
   }
}
