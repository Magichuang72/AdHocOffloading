import java.io.Serializable;

/**
 * Created by magichuang on 17-3-14.
 */
public class NodeMessage implements Serializable {
    private int speed;
    private int cost;
    private int wantNode;

    public NodeMessage(int speed, int cost, int wantNode) {
        this.speed = speed;
        this.cost = cost;
        this.wantNode = wantNode;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getWantNode() {
        return wantNode;
    }

    public void setWantNode(int wantNode) {
        this.wantNode = wantNode;
    }
}
