package q007.maze;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.LocalDateTime;

public class MazeData extends MazeProperties {
    private static final int INITIAL_COST = 0;
    private static final int INCREMENTAL_COST = 1;

    private List<MazeNode> nodes = new ArrayList<>();

    public MazeData(String filepath) {
        makeNodes(getLines(filepath));
        validate();
    }

    // 入力されたMAZEデータ
    private ArrayList<String> lines = new ArrayList<>();

    public ArrayList<String> getMazeLines() {
        return lines;
    }

    // 時刻情報
    private LocalDateTime startDateTime = null;
    private long elapsedMs = -1;
    public long elapsedMs() {
        return elapsedMs;
    }

    // 最短コスト
    private Integer minCost = -1;

    public int minCost() {
        return minCost;
    }

    // ダイクストラ (Dijkstra) 法ベースの探索
    public void dijkstra() {
        startDateTime = LocalDateTime.now();

        long startTime = System.currentTimeMillis();
        System.out.println(" // 最短コスト探索 start!  " + startDateTime);

        // 最初のノードに、コスト0設定(到着ノードから遡る)
        nodes.stream()
                .filter(MazeNode::isEnd)
                .forEach(node -> node.cost(INITIAL_COST));

        // 最短経路が見つかるか、全探索終了するまでループする
        while (true) {

            // 次の処理ノードを求める
            MazeNode processNode = nodes.stream()
                    .filter(node -> node.isNotDone()  // 未確定、
                            && 0 <= node.cost())     // かつ、コスト設定済み
                    .min(Comparator.comparing(MazeNode::cost))
                    .orElse(null);

            if (processNode == null) {  // 処理中ノードがなくなった → 全探索終了
                System.out.print(" // No Route.");
                break;
            }

            System.out.print(String.format("    #%d\t%-8s", processNode.cost(), processNode.toString()));

            // この処理中ノードを「確定済み」にする。（そこに至るまでの経路として一番コストが小さいことが、ここまでで計算されているので「確定」。）
            processNode.done();

            if (processNode.isStart()) {  // 到達した → これが最短コスト。（同じコストのルートが他にもある可能性はある）
                System.out.print(" // Done.");
                minCost = processNode.cost();  // 最短コスト
                break;
            }

            // この処理中ノードから隣接ノードを求めて、次の処理対象ノードとしてコスト設定（ないし更新）する
            int[] adjacentNodeKeys = {
                    processNode.getNodeKeyUp(),
                    processNode.getNodeKeyDown(),
                    processNode.getNodeKeyLeft(),
                    processNode.getNodeKeyRight()
            };
            Arrays.sort(adjacentNodeKeys);

            int netxCost = processNode.cost() + INCREMENTAL_COST;

            nodes.stream()
                    .filter(node -> Arrays.binarySearch(adjacentNodeKeys, node.getNodeKey()) >= 0
                            && node.isNotDone()               // かつ、未確定
                            && node.canMove()                 // かつ、壁でない(ここに移動できる)
                            && (node.cost() < 0               // かつ、（コスト未設定、
                                 || netxCost < node.cost()))  //        ないし、今回ルートで少ないコストに更新できる）
                    .forEach(node -> {
                        node.cost(netxCost);
                        node.previousNodeKey(processNode.getNodeKey());
                        System.out.print(" [ → " + node.toString() + " ]");
                    });

            System.out.println(" ,");
        }

        elapsedMs = (System.currentTimeMillis() - startTime);
        System.out.println(" (" + elapsedMs + "ms)");
    }

    private ArrayList<String> getLines(String filepath) {

        String line;
        var lineCnt = 0;
        var lineSize = 0;

        System.out.println(filepath == null ? "Maze Created By MazeInputStream" : "filepath=" + filepath);

        try {
            var is = filepath == null ? (new MazeInputStream()) : (new FileInputStream(filepath));
            var ir = new InputStreamReader(is);
            var br = new BufferedReader(ir);
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {  // 空行skip
                    continue;
                }
                ++lineCnt;
                if (maxLineSize < line.length()) {  // 行サイズ超過
                    System.out.println("line#" + lineCnt + " : Line size exceeded " + maxLineSize + ".");
                    throw new RuntimeException();
                }
                if (lineSize == 0) {
                    lineSize = line.length();
                } else if (lineSize != line.length()) {  // 行サイズが揃っていない
                    System.out.println("line#" + lineCnt + " : Misalighnment of data line size.");
                    throw new RuntimeException();
                }

                lines.add(line);
            }
            br.close();
            ir.close();
            is.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }

        return lines;
    }

    private void makeNodes(ArrayList<String> lines) {
        AtomicInteger y = new AtomicInteger();
        AtomicInteger x = new AtomicInteger();

        System.out.println("       " + "----+----1----+----2----+----4----+----5 …");
        y.set(0);
        for (String line : lines) {
            //line = line.replaceAll("[-*|]", "X");

            System.out.println(" " + String.format("%4d", y.incrementAndGet()) + ": " + line);

            x.set(0);
            line.chars().forEach(c -> {
                nodes.add(new MazeNode(x.incrementAndGet(), y.intValue(), MazeNodeType.from(c)));
            });
        }
        System.out.println("       ");
    }

    private void validate() {
        long startNodeCount = nodes.stream()
                .filter(MazeNode::isStart)
                .count();
        if (startNodeCount != 1) {
            throw new RuntimeException("[S]tart duplicated or not defined in data.");
        }

        long endNodeCount = nodes.stream()
                .filter(MazeNode::isEnd)
                .count();
        if (endNodeCount != 1) {
            throw new RuntimeException("[E]nd duplicated or not defined in data.");
        }
    }
}
