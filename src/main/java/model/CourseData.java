package model;

public class CourseData {
	
	private String name;
	private String duration;
	private String rating;
	
	public CourseData(String name, String duration, String rating) {
		this.name=name;
		this.duration=duration;
		this.rating=rating;
	}
	
	public  String getName() {
		
		return name;
		
	}
	
	public String getDuration() {
		return duration;
	}
	
	public String getRating() {
		return rating;
	}

	@Override
	public String toString() {
		return "CourseData{name='"+name+"', duration='"+duration+"', rating='"+rating+"'}";
	}
}
