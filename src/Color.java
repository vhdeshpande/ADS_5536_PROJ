public enum Color {

    RED("Red"),
    BLACK("Black");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String toString() {
        return color;
    }
}
