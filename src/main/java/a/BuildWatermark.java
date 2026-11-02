package a;

public final class BuildWatermark {
   private static final String a = "MARKSLOT00000000";

   private BuildWatermark() {
   }

   public static String a() {
      String var0 = "\u0011\u0016\ufffd\ufffd\ufffd\ufffd\t\ufffd";
      if (var0 != null && var0.length() == 8) {
         for(int var1 = 0; var1 < 8; ++var1) {
            char var2 = var0.charAt(var1);
            if ((var2 < '0' || var2 > '9') && (var2 < 'A' || var2 > 'F')) {
               return "";
            }
         }

         return var0;
      } else {
         return "";
      }
   }

   public static String b() {
      String var0 = a();
      return var0.isEmpty() ? "" : "#" + var0;
   }

   public static boolean c() {
      return !a().isEmpty();
   }
}
