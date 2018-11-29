package model;

import java.util.ArrayList;
import java.util.List;

import model.Pizza.PizzaSize;

public class Main {
	public static void main(String[] args) {
		PizzaFactory pzaFactory=new PizzaFactory();
		List<Pizza> pizzas=new ArrayList<>();
		
		pzaFactory.setLogging(true);
		
		pizzas.add(pzaFactory.createAmericana(PizzaSize.SMALL));
		pizzas.add(pzaFactory.createDiavolo(PizzaSize.LARGE));
		pizzas.add(pzaFactory.createMargherita(PizzaSize.LARGE));
		
		System.out.println("\n");
		printPizzaList(pizzas);
	}
	
	public static void printPizzaList(List<Pizza> pizzas) {
		for(Pizza pizza:pizzas) {
			System.out.println(pizza.getName()+" - "+pizza.getSize().name());
			for(Object o:pizza.getIngredients()) {
				System.out.println("\t"+o.toString());
			}
			System.out.println();
		}
	}
}
