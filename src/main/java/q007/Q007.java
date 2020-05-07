package q007;

import q007.maze.*;

import java.io.IOException;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * q007 最短経路探索
 *
 * 壁を 'X' 通路を ' ' 開始を 'S' ゴールを 'E' で表現された迷路で、最短経路を通った場合に
 * 何歩でゴールまでたどり着くかを出力するプログラムを実装してください。
 * もし、ゴールまで辿り着くルートが無かった場合は -1 を出力してください。
 * なお、1歩は上下左右のいずれかにしか動くことはできません（斜めはNG）。
 *
 * 迷路データは MazeInputStream から取得してください。
 * 迷路の横幅と高さは毎回異なりますが、必ず長方形（あるいは正方形）となっており、外壁は全て'X'で埋まっています。
 * 空行が迷路データの終了です。
 *
[迷路の例]
XXXXXXXXX
XSX    EX
X XXX X X
X   X X X
X X XXX X
X X     X
XXXXXXXXX
[答え]
14
 */

public class Q007 {
    public static void main(String[] args) {
        doDijkstra(args);
        //doDijkstraN(100);  // デバッグ用 100回コール
    }

    private static void doDijkstra(String[] args) {
        var mazeData = makeMazeData(args.length > 0 ? args[0] : null);

        mazeData.dijkstra();
        System.out.println("    → 最短コスト: " + mazeData.minCost());
    }

    private static MazeData makeMazeData(String filepath) {
        if (filepath == null) {  // MazeInputStreamでランダム生成
            return new MazeData(null);
        }

        if ("/".equals(filepath.substring(0, 1))) {  // ファイル名指定（絶対パス）
            return new MazeData(filepath);
        }

        // ファイル名指定
        return new MazeData(System.getProperty("user.dir") + "/" + filepath);
    }

    private static void doDijkstraN(int loop) {
        ArrayList<String> lines = new ArrayList<>();
        long minMs = Long.MAX_VALUE;
        long maxMs = -1;

        try {
            PrintStream sysOut = System.out;
            FileOutputStream fos = new FileOutputStream("out.txt");
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);

            for (int i = 0; i < loop; i++) {
                System.out.println("\n=== #" + (i + 1));

                var mazeData = makeMazeData(null);
                mazeData.dijkstra();
                System.out.println("    → 最短コスト: " + mazeData.minCost());

                if (mazeData.minCost() < 0) {
                    String line = "#" + (i + 1) + " 経路なし";

                    System.out.println(line);
                    lines.add(line);
                } else {
                    long elapsedMs = mazeData.elapsedMs();
                    minMs = Math.min(minMs, elapsedMs);
                    maxMs = Math.max(maxMs, elapsedMs);
                }
                ps.flush();
            }

            ps.close();
            fos.close();
            System.setOut(sysOut);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }

        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println(loop + "回実施: " + minMs + "ms〜" + maxMs + "ms");
    }

}
