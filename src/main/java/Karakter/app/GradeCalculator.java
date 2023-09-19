package Karakter.app;

import java.util.ArrayList;

public class GradeCalculator {
    private ArrayList<Grade> grades;

    public GradeCalculator(ArrayList<Grade> grades) {
        this.grades = grades;
    };

    public double getAverage() {
        double sum = 0.0;
        for (Grade g : grades) {
            sum += g.getTallKarakter() * 100;
        }
        return 0.01 * Math.round((long) (sum / grades.size()));

    }

    public double getMedian() {
        if (grades.size() <= 1) {
            return (grades.size() == 1) ? grades.get(0).getTallKarakter() : 0.0;
        }
        grades.sort(new GradeComparator("g+"));
        if (grades.size() % 2 == 1) {
            return grades.get((int) Math.floor(grades.size() / 2)).getTallKarakter();
        } else {
            return 0.1 * Math.floor((double) (grades.get((int) Math.floor(grades.size() / 2)).getTallKarakter() * 10
                    + grades.get((int) Math.floor(grades.size() / 2) - 1).getTallKarakter() * 10) / 2);
        }
    }

    public String getWorst() {
        if (grades.size() == 0) {
            return "";
        }
        grades.sort(new GradeComparator("g+"));
        return grades.get(0).getEmnekode() + String.format(" (%s)", grades.get(0).getBokstavKarakter());
    }

    public String getBest() {
        if (grades.size() == 0) {
            return "";
        }
        grades.sort(new GradeComparator("g-"));
        return grades.get(0).getEmnekode() + String.format(" (%s)", grades.get(0).getBokstavKarakter());
    }
}
