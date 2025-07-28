package decorator;

import classes.SwapSuggestion;
import dao.FoodSwapDAO;

public class CholesterolImpactDecorator implements ImpactDecorator {
         private final SwapSuggestion suggestion;
        private final int userId;
        private final String date;
        private final String mealType;
        private final FoodSwapDAO dao = new FoodSwapDAO();

        public CholesterolImpactDecorator(SwapSuggestion suggestion, int userId, String date, String mealType) {
            this.suggestion = suggestion;
            this.userId = userId;
            this.date = date;
            this.mealType = mealType;
        }

        public String getDecoratedInfo() {
            try {
                String measure = dao.getMeasureDescription(suggestion.getOriginalFood(), userId, date, mealType);
                if (measure == null) return "Calorie impact: unavailable";

                double originalCalories = dao.getNutrientAmount(suggestion.getOriginalFood(), measure, "CHOLESTEROL");
                double recommendedCalories = dao.getCalorieAmount(suggestion.getRecommendedFood(), measure, "CHOLESTEROL");

                double difference = recommendedCalories - originalCalories;
                double percentage = (Math.abs(difference)/originalCalories)*100;
                String symbol = difference > 0 ? "+" : "-";

                return "Calorie impact: " + symbol + String.format("%.2f", Math.abs(difference)) + " mg. Percentage change: " + symbol + String.format("%.2f", percentage) + " % mg";
            } catch (Exception e) {
                return "Cholesterol impact: unavailable";
            }
        }

}
