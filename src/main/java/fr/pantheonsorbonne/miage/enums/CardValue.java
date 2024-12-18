package fr.pantheonsorbonne.miage.enums;


public enum CardValue {
    ACE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    JOKER("Joker");

    private final String symbol;

    CardValue(String symbol) {
        this.symbol = symbol;
    }

    public static CardValue fromSymbol(String symbol) {
        for (CardValue value : CardValue.values()) {
            if (value.symbol.equals(symbol)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum constant with symbol " + symbol);
    }

    public String getSymbol() {
        return this.symbol;
    }

    public boolean endsWith(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endsWith'");
    }
}
