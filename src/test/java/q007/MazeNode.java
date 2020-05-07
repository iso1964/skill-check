package q007;

import q007.maze.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MazeNodeTest {

    @Test
    void isStart() {
        var mn = new MazeNode(1, 1, MazeNodeType.from('E'));
        assertFalse(mn.isStart());
        //assertTrue(mn.isStart());
    }
}

