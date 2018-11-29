package model;

import java.util.Arrays;

import model.Pizza.PizzaSize;

public class PizzaFactory extends ProductFactory {
	private boolean logging=false;
	
	/**
	 * @return the logging
	 */
	public boolean isLogging() {
		return logging;
	}

	/**
	 * @param logging the logging to set
	 */
	public void setLogging(boolean logging) {
		this.logging = logging;
	}

	@Override
	protected Product createProduct(String productName, Object... parameters) {
		PizzaSize size=(PizzaSize)parameters[0];
		logIfLogging("Creating Pizza: "+productName+" - "+size.name());
		Pizza pizza=new Pizza(productName, size);
		
		logIfLogging("\tWird belegt");
		pizza.belegPizza(Arrays.asList(parameters).subList(1, parameters.length));
		logIfLogging("\tfertig");

		logIfLogging("\tWird gebacken:\n"+
				"\t\tBackzeit: "+5000+"\n"+
				"\t\tTemperatur: "+300);
		pizza.bake(5000, 300);
		logIfLogging("\tfertig");
		logIfLogging("fertig\n");

		return pizza;
	}
	
	public Pizza createAmericana(PizzaSize size) {
		return (Pizza)createProduct("Americana",size,"Tomaten","Käse","Salami");
	}
	
	public Pizza createDiavolo(PizzaSize size) {
		return (Pizza)createProduct("Diavolo",size,"Tomaten","Käse","scharfe Salami","Pfefferoni");
	}
	
	public Pizza createMargherita(PizzaSize size) {
		return (Pizza)createProduct("Diavolo",size,"Tomaten","Käse");
	}
	
	public void logIfLogging(String text) {
		if(logging) {
			System.out.println(text);
		}
	}
}
