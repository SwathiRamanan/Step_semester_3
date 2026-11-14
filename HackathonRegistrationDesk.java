public class HackathonRegistrationDesk {

    static class Team {
        String name;
        int size;
        Team(String name, int size) { this.name = name; this.size = size; }
        @Override
        public String toString() { return name + "(" + size + ")"; }
    }

    
    static void sortTeams(Team[] teams) {
        for (int i = 1; i < teams.length; i++) {
            Team key = teams[i];
            int j = i - 1;
            while (j >= 0 && teams[j].size > key.size) { 
                teams[j + 1] = teams[j];                 
                j--;
            }
            teams[j + 1] = key;                         
        }
    }

    static String str(Team[] teams) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < teams.length; i++) {
            sb.append(teams[i]);
            if (i < teams.length - 1) sb.append(", ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Team[] teams = {
            new Team("Pixel", 4), new Team("Byte", 2), new Team("Nova", 4),
            new Team("Echo", 3), new Team("Atom", 2)
        };
        sortTeams(teams);
        System.out.println(str(teams)); 
    }


}