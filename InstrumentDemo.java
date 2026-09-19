abstract class Instrument {

    public abstract String play();
}

class StringInstrument extends Instrument {

    @Override
    public String play() {
        return "Strumming the strings";
    }
}

class Violin extends StringInstrument {

    @Override
    public String play() {
        return super.play() + ", with a bow drawn across four strings";
    }
}

public class InstrumentDemo {

    public static void main(String[] args) {

        StringInstrument s = new StringInstrument();
        Violin v = new Violin();

        System.out.println(s.play());
        System.out.println(v.play());
    }
}
