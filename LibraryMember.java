public class LibraryMember {
 
    // only this class can touch the pin
    private String membershipPin;
 
    // reachable only from other classes in the SAME package (no modifier = default/package-private)
    String branchCode;
 
    // also package-only for now (full subclass rules are handled in Problem 2)
    String finesOwed;
 
    // reachable from anywhere
    public String displayName;
 
    public LibraryMember(String membershipPin, String branchCode, String finesOwed, String displayName) {
        this.membershipPin = membershipPin;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }
}

