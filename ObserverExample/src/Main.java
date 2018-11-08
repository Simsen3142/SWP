
public class Main {
	public static void main(String[] args) {
		Heizung heizung=new Heizung();
		
		Person person=new Person("Max Mustermann",25,0);
		Staat staat=new Staat();
		
		heizung.addObserver(person);
		heizung.addObserver(staat);
		
		double temp=Math.random()*10.0+20.0;
		heizung.setTemperature(temp);
		System.out.println("TEMPERATUR: "+String.format("%,.2f", temp)+"°C");
		heizung.notifyObservers();
	}
}
