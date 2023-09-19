package Karakter.app;

import java.util.Iterator;

public class GradeManagerIterator implements Iterator<Grade> {
    private GradeManager gm;
    private int count;

    public GradeManagerIterator(GradeManager gm) {
        this.gm = gm;
        count = -1;
    }

    @Override
    public boolean hasNext() {
        return count < gm.getGrades().size() - 1;
    }

    @Override
    public Grade next() {
        count++;
        return gm.getGrades().get(count);
    }

}
