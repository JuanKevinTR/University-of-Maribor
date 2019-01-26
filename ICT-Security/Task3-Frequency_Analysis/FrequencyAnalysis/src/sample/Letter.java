package sample;

public class Letter {

    private char letter;
    private int frecuency;

    public Letter(char let, int fre) {
        letter = let;
        frecuency = fre;
    }

    public char getLetter() {
        return letter;
    }

    public int getFrecuency() {
        return frecuency;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

}
