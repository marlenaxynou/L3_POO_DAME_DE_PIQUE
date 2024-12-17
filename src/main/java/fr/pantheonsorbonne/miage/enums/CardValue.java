package fr.pantheonsorbonne.miage.enums;


public enum CardValue {
    ACE("1", 14),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13),
    JOKER("Joker", 1);

    private final String symbol;
    private final int value;
    CardValue(String symbol, int value) {
        this.symbol = symbol;
        this.value = value;
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
