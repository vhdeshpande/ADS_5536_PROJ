/**
 * Red-black tree node color enum class
 */
public enum Color {

    RED("Red"),
    BLACK("Black");

    /**
     * Node color
     */
    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String toString() {
        return color;
    }
}
