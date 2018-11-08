
public class Person implements IObserver{

	private double preferedTemp;
	private double acceptableDif;
	private String name;
	
	public Person(String name, double preferedTemp, double acceptableDif) {
		this.name = name;
		this.preferedTemp = preferedTemp;
		this.acceptableDif = acceptableDif;
	}

	@Override
	public void actionDone(Subject subject) {
		Heizung hzng=(Heizung)subject;
		String output="Person \""+name+"\" sagt";

		if(hzng.getTemperature()>preferedTemp+acceptableDif) {
			output+=" zu warm";
		}else if(hzng.getTemperature()<preferedTemp-acceptableDif) {
			output+=" zu kalt";
		}else {
			output+="Temperatur passt";
		}
		System.out.println(output);
	}

}
