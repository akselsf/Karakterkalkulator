package Karakter.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class GradeComparator implements Comparator<Grade> {
    private String sort;
    private final ArrayList<String> validSortStrings = new ArrayList<String>(Arrays.asList("g+", "g-", "a+", "g-"));

    public GradeComparator(String sort) {
        // g+ = stigende
        // g- = synkende
        // a+ = emnekode stigene
        // a- = emnekode synkende
        if (validSortStrings.contains(sort)) {
            this.sort = sort;
        } else {
            this.sort = validSortStrings.get(0);
        }

    }

    @Override
    public int compare(Grade arg0, Grade arg1) {
        if (sort.equals("g-")) {
            return (arg0.getTallKarakter() <= arg1.getTallKarakter()) ? 1 : -1;
        } else if (sort.equals("g+")) {
            return (arg0.getTallKarakter() >= arg1.getTallKarakter()) ? 1 : -1;
        } else if (sort.equals("a+")) {
            return (arg0.getEmnekode().compareTo(arg1.getEmnekode()));

        } else if (sort.equals("a-")) {

            return (arg0.getEmnekode().compareTo(arg1.getEmnekode()))
                    * (-1);

        } else {
            return 0;
        }

    }

}
