package fr.pantheonsorbonne.miage.enums;

public enum CardColor {
    HEARTS("H", "\u2665"),   // ♥
    DIAMONDS("D", "\u2666"), // ♦
    CLUBS("C", "\u2663"),    // ♣
    SPADES("S", "\u2660"),   // ♠
    NONE("", "");            // Used for Joker

    private final String symbol;
    private final String unicodeSymbol;

    CardColor(String symbol, String unicodeSymbol) {
        this.symbol = symbol;
        this.unicodeSymbol = unicodeSymbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getUnicodeSymbol() {
        return unicodeSymbol;
    }

    public static CardColor fromSymbol(String symbol) {
        for (CardColor color : values()) {
            if (color.symbol.equals(symbol)) {
                return color;
            }
        }
        throw new IllegalArgumentException("Invalid card color symbol: " + symbol);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> a064f94c479c83cf03bb3b65836f9f18f23aac80
