import java.io.Serializable;

/**
 * Created by magichuang on 17-3-9.
 */
public class Task implements Serializable, Loadable {
    private int count;

    public Task(int count) {
        this.count = count;
    }

    public void lalala() {
        System.out.println(count);
    }

    @Override
    public void doTask() {
        lalala();
    }

    @Override
    public int getLength() {
        return 1;
    }
}
