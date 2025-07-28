// package classes;

// import java.util.ArrayList;
// import java.util.List;

// public class FoodItem {
// 	private List<Nutrient> NutritionFacts;
// 	private String itemName;
// 	private String foodGroup;	// get from database through FoodDAO
// 	private int foodId;	// get from database  through FoodDAO
// 	private int quantity;
	
	
	
// 	public FoodItem(String itemName, int quantity) {
// 		this.itemName = itemName;
// 		this.quantity = quantity;
// 		this.NutritionFacts = new ArrayList<Nutrient>();
// 	}
	
	
	
// 	public String getItemName() {
// 		return this.itemName;
// 	}
	
// 	public int getQuantity() {
// 		return this.quantity;
// 	}
	
// 	public void setQuantity(int quantity) {
// 		this.quantity = quantity;
// 	}
	
// 	public List<Nutrient> getNutritionFacts() {
// 		return this.NutritionFacts;
// 	}
	
// 	public void addToNutritionFacts(Nutrient nutrient) {
// 		this.NutritionFacts.add(nutrient);
// 	}
	
// 	public void setFoodGroup(String foodGroup) {
// 		this.foodGroup = foodGroup;
// 	}
	
// 	public String getFoodGroup() {
// 		return this.foodGroup;
// 	}
	
// 	public void setFoodId(int foodId) {
// 		this.foodId = foodId;
// 	}
	
// 	public int getFoodId() {
// 		return this.foodId;
// 	}
// }
