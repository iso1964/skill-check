package q007.maze;

public class MazeNode extends MazeNodeKey {
    private final MazeNodeType nodeType;

    private boolean done = false;
    private int cost = -1;
    private int previousNodeKey = -1;

    public MazeNode(int _x, int _y, MazeNodeType _nodeType) {
        super(_x, _y);
        nodeType = _nodeType;
    }

    public boolean isStart() {
        return nodeType.isStart();
    }

    public boolean isEnd() {
        return nodeType.isEnd();
    }

    public boolean canMove() {
        return nodeType.canMove();
    }

    public void cost(int _cost) {
        cost = _cost;
    }

    public int cost() {
        return cost;
    }

    public void previousNodeKey(int _previousNodeKey) {
        previousNodeKey = _previousNodeKey;
    }

    public int previousNodeKey() {
        return previousNodeKey;
    }

    public void done() {
        done = true;
    }

    public boolean isNotDone() {
        return !done;
    }

    @Override
    public String toString() {
        return getY() + ":" + getX();
    }
}
