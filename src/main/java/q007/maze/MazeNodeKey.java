package q007.maze;

public abstract class MazeNodeKey extends MazeProperties {
    private final int x;   // 1-
    private final int y;   // 1-

    MazeNodeKey(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // nodeKey: 縦横移動時において、nodeの一意化（x,yからのサーチ）で使用
    private int nodeKey(int _x, int _y) {
        if (_x < 0) {
            _x = 0;  // x=0のnodeは存在しない。存在しないキーを返送する。
        }
        if (_y < 0) {
            _y = 0;  // y=0のnodeは存在しない。存在しないキーを返送する。
        }
        return ((_y * maxLineSize) + _x);
    }

    public int getNodeKey() {
        return nodeKey(x, y);
    }

    public int getNodeKeyUp() {
        return nodeKey(x, y - 1);
    }

    public int getNodeKeyDown() {
        return nodeKey(x, y + 1);
    }

    public int getNodeKeyLeft() {
        return nodeKey(x - 1, y);
    }

    public int getNodeKeyRight() {
        return nodeKey(x + 1, y);
    }
}
