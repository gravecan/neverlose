package a;

public final class LicenseStateValidator {
   private LicenseStateValidator() {
   }

   public static Result a(SignedLicenseToken token, LicenseActivationState saved, boolean corrupt, String hwid, long now) {
      if (corrupt) {
         return new Result(LicenseStateValidator.State.f, 0L, saved);
      } else if (token == null) {
         return new Result(LicenseStateValidator.State.a, 0L, saved);
      } else if (saved == null) {
         return new Result(LicenseStateValidator.State.b, 0L, (LicenseActivationState)null);
      } else if (!saved.a.equals(token.b)) {
         return new Result(LicenseStateValidator.State.f, 0L, saved);
      } else if (!saved.d.equalsIgnoreCase(hwid)) {
         return new Result(LicenseStateValidator.State.e, 0L, saved);
      } else {
         long var6 = saved.a(now, token.f * 1000L);
         LicenseActivationState var8 = saved.a(now);
         return var6 > 0L ? new Result(LicenseStateValidator.State.c, var6, var8) : new Result(LicenseStateValidator.State.d, 0L, var8);
      }
   }

   public static final class Result {
      public final State a;
      public final long b;
      public final LicenseActivationState c;

      public Result(State status, long remainingMs, LicenseActivationState state) {
         this.a = status;
         this.b = remainingMs;
         this.c = state;
      }

      public boolean a() {
         return this.a == LicenseStateValidator.State.c;
      }
   }

   public static enum State {
      a,
      b,
      c,
      d,
      e,
      f,
      g;

      // $FF: synthetic method
      private static State[] a() {
         return new State[]{a, b, c, d, e, f, g};
      }
   }
}
