package q007.maze;

import static java.lang.Character.toUpperCase;

public enum MazeNodeType {
    WALL_RIGHTDOWN('*', false),
    WALL_LEFTRIGHT_BORDER('|', false),
    WALL_TOPDOWN_BORDER('-', false),
    WALL('X', false),
    AISLE(' ', true),
    START('S', true),
    END('E', true);

    private final char indicator;
    private final boolean canMove;

    MazeNodeType(char _indicator, boolean _canMove) {
        this.indicator = _indicator;
        this.canMove = _canMove;
    }

    public static MazeNodeType from(int c) {
        for (MazeNodeType v : values()) {
            if (v.indicator == toUpperCase(c)) {
                return v;
            }
        }
        throw new RuntimeException("Undefined indicator : '" + c + "'");
    }

    public boolean canMove() {
        return canMove;
    }

    public boolean isStart() {
        return this == START;
    }

    public boolean isEnd() {
        return this == END;
    }
}
