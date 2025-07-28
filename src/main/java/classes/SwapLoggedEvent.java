package classes;

public class SwapLoggedEvent {
    private SwapSuggestion suggestion;
    private String nutrientName;

    public SwapLoggedEvent(SwapSuggestion suggestion, String nutrientName) {
        this.suggestion = suggestion;
        this.nutrientName = nutrientName;
    }

    public SwapSuggestion getSuggestion() {
        return suggestion;
    }

    public String getNutrientName() {
        return nutrientName;
    }
}

