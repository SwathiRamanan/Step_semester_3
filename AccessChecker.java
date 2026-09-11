public class AccessChecker {
 
    // Decides if access is ALLOWED or DENIED based on Java's real visibility rules
    static String classifyAccess(String fieldModifier, String accessorContext) {
 
        if (fieldModifier.equals("public")) {
            // public is always reachable
            return "ALLOWED";
        }
 
        if (fieldModifier.equals("private")) {
            // private is only reachable from the same class
            if (accessorContext.equals("SAME_CLASS")) {
                return "ALLOWED";
            }
            return "DENIED";
        }
 
        if (fieldModifier.equals("default")) {
            // default (package-private) works in same class or same package only
            if (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }
 
        if (fieldModifier.equals("protected")) {
            // protected works in same class, same package,
            // and in a subclass (different package) ONLY through the subclass's own type
            if (accessorContext.equals("SAME_CLASS")) {
                return "ALLOWED";
            }
            if (accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            if (accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                return "ALLOWED";
            }
            // DIFFERENT_PACKAGE and SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE are both denied
            return "DENIED";
        }
 
        // Just in case an unknown modifier is passed
        return "DENIED";
    }
 
    // Problem 1: groups results by modifier, not as one flat total
    static String summarizeByModifier(String[][] attempts) {
 
        // Always show all four modifiers, even with zero attempts
        String[] modifiers = {"private", "default", "protected", "public"};
        int[] allowedCount = new int[4];
        int[] deniedCount = new int[4];
 
        for (int i = 0; i < attempts.length; i++) {
            String modifier = attempts[i][0];
            String context = attempts[i][1];
            String result = classifyAccess(modifier, context);
 
            // find which slot this modifier belongs to
            for (int j = 0; j < modifiers.length; j++) {
                if (modifiers[j].equals(modifier)) {
                    if (result.equals("ALLOWED")) {
                        allowedCount[j]++;
                    } else {
                        deniedCount[j]++;
                    }
                    break;
                }
            }
        }
 
        // build the final string piece by piece
        String output = "";
        for (int j = 0; j < modifiers.length; j++) {
            output = output + modifiers[j] + ": " + allowedCount[j] + " allowed / " + deniedCount[j] + " denied";
            if (j != modifiers.length - 1) {
                output = output + " | ";
            }
        }
 
        return output;
    }
 
    // Problem 2: scans in order and stops at the FIRST denied attempt (early exit)
    static String firstDeniedAttempt(String[][] attempts) {
 
        for (int i = 0; i < attempts.length; i++) {
            String modifier = attempts[i][0];
            String context = attempts[i][1];
            String result = classifyAccess(modifier, context);
 
            if (result.equals("DENIED")) {
                int attemptNumber = i + 1; // attempts are 1-based in the output
                return modifier + " via " + context + " (attempt #" + attemptNumber + ")";
            }
        }
 
        return "None Denied";
    }
 
    // quick manual test
    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS"));       // ALLOWED
        System.out.println(classifyAccess("protected", "DIFFERENT_PACKAGE")); // DENIED
 
        String[][] batch = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(summarizeByModifier(batch));
 
        String[][] attempts = {
            {"public", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"},
            {"protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"},
            {"protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"}
        };
        System.out.println(firstDeniedAttempt(attempts));
    }
}