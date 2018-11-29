package model;

import java.util.List;

public class Pizza implements Product {
	
	public enum PizzaSize{
		SMALL(25), LARGE(30), FAMILY(45), CALZONE(30);
		
		PizzaSize(int size) {
			this.SIZE=size;
		}
		
		public final int SIZE;
	}
	
	private PizzaSize size;
	private List<Object> ingredients;
	private long bakeTime;
	private int bakeTemperature;
	private String name;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the size
	 */
	public PizzaSize getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(PizzaSize size) {
		this.size = size;
	}

	/**
	 * @return the ingredients
	 */
	public List<Object> getIngredients() {
		return ingredients;
	}
	/**
	 * @param ingredients the ingredients to set
	 */
	public void setIngredients(List<Object> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * @return the bakeTime
	 */
	public long getBakeTime() {
		return bakeTime;
	}
	/**
	 * @param bakeTime the bakeTime to set
	 */
	public void setBakeTime(long bakeTime) {
		this.bakeTime = bakeTime;
	}

	/**
	 * @return the bakeTemperature
	 */
	public int getBakeTemperature() {
		return bakeTemperature;
	}
	/**
	 * @param bakeTemperature the bakeTemperature to set
	 */
	public void setBakeTemperature(int bakeTemperature) {
		this.bakeTemperature = bakeTemperature;
	}

	public Pizza(String name, PizzaSize size) {
		this.size = size;
		this.name=name;
	}
	
	public void belegPizza(List<Object> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void bake(long bakeTime, int bakeTemperature) {
		this.bakeTime = bakeTime;
		this.bakeTemperature = bakeTemperature;
		try {
			Thread.sleep(bakeTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
