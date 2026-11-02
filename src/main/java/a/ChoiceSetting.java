package a;

import java.util.List;

public class ChoiceSetting extends Setting {
   public final List<String> a;
   public int b;

   public ChoiceSetting(String name, List<String> options, int def) {
      super(name);
      this.a = options;
      this.a(def);
   }

   public void a(int i) {
      this.b = Math.max(0, Math.min(this.a.size() - 1, i));
   }

   public void a() {
      this.a((this.b + 1) % this.a.size());
   }

   public String b() {
      return (String)this.a.get(this.b);
   }
}
