package classes;

//This class contains data about a specific nutrition and its quantity in a meal or an ingredient
public class Nutrient {
	private int nutrientId;
	private String nutrientName; // like PROTEIN (CDF database uses uppercase)
	private String unit;
	private double quantity;
	
	public Nutrient (int nutrientId) {
		this.nutrientId = nutrientId;
	}
	
	public int getNutrientId() {
		return this.nutrientId;
	}
	
	public void setNutrientName(String nutrientName) {
		this.nutrientName = nutrientName;
	}
	
	public String getNutrientName() {
		return this.nutrientName;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getUnit() {
		return this.unit;
	}
	
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public double getQuantity() {
		return this.quantity;
	}

}
