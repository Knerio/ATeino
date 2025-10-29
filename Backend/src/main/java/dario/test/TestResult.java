package dario.test;

import java.util.ArrayList;
import java.util.List;

public class TestResult {

    private List<Integer> success = new ArrayList<>();
    private List<Integer> fail = new ArrayList<>();


    public List<Integer> getFail() {
        return fail;
    }

    public void incrementSuccess(int index) {
        this.success.add(index);
    }
    public void incrementFail(int index) {
        this.fail.add(index);
    }

    public boolean isSuccess() {
        return fail.isEmpty();
    }
}
