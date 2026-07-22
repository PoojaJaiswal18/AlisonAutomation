package model;

public class CourseData {

    private String name;
    private String duration;
    private String learners;

    public CourseData(
            String name,
            String duration,
            String learners) {

        this.name = name;
        this.duration = duration;
        this.learners = learners;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getLearners() {
        return learners;
    }

    @Override
    public String toString() {

        return "CourseData{" +
                "name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", learners='" + learners + '\'' +
                '}';
    }
}