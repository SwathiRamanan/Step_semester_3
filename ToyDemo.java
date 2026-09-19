abstract class Toy {
    private static int counter = 1000;
    private final String toyId;

    protected String name;

    public Toy(String name) {
        this.name = name;
        counter++;
        this.toyId = "TOY-" + counter;
    }

    public abstract String makeSound();

    public String getToyId() {
        return toyId;
    }
}

class ToyCar extends Toy {

    public ToyCar(String name) {
        super(name);
    }

    @Override
    public String makeSound() {
        return name + ": Vroom vroom!";
    }
}

class ToyRobot extends Toy {

    public ToyRobot(String name) {
        super(name);
    }

    @Override
    public String makeSound() {
        return name + ": Beep boop!";
    }
}

public class ToyDemo {
    public static void main(String[] args) {

        ToyCar c = new ToyCar("Speedster");
        ToyRobot r = new ToyRobot("Bolt");

        System.out.println(c.makeSound());
        System.out.println(r.makeSound());

        System.out.println(c.getToyId());
        System.out.println(r.getToyId());

        // This will NOT compile:
        // Toy t = new Toy("Generic");
    }
}
    

