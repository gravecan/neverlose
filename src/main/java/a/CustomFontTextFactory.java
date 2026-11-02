package a;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;

public final class CustomFontTextFactory {
   private static final Identifier a = new Identifier("chatpaches", "ui");
   private static final Identifier b = new Identifier("chatpaches", "title");
   private static final Map<String, Text> c = new LinkedHashMap<String, Text>(256, 0.75F, true) {
      protected boolean removeEldestEntry(Map.Entry<String, Text> eldest) {
         return this.size() > 384;
      }
   };

   private CustomFontTextFactory() {
   }

   public static Text a(String s) {
      return (Text)c.computeIfAbsent("u" + s, (k) -> Text.literal(s).setStyle(Style.EMPTY.withFont(a)));
   }

   public static Text b(String s) {
      return (Text)c.computeIfAbsent("t" + s, (k) -> Text.literal(s).setStyle(Style.EMPTY.withFont(b)));
   }

   public static String c(String s) {
      int var1 = s.length();
      if (var1 < 2) {
         return s;
      } else {
         StringBuilder var2 = new StringBuilder(var1 * 2);

         for(int var3 = 0; var3 < var1; ++var3) {
            if (var3 > 0) {
               var2.append(' ');
            }

            var2.append(s.charAt(var3));
         }

         return var2.toString();
      }
   }
}
