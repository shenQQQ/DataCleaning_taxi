package algorithm;

import model.RoadPoint;
import model.Route;
import utils.InputUtils;
import utils.RoadPointUtils;

import java.util.ArrayList;

//错误代码
public class LongestCommonSeq {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        InputUtils utils = new InputUtils();
        Route r1 = utils.getRoute(1568354669);
        Route r2 = utils.getRoute(1570086449);
        long endTime = System.currentTimeMillis();
        System.out.println("读取运行时间：" + (endTime - startTime) + "ms");
        startTime = System.currentTimeMillis();
        int length = LCS(r1,r2);
        System.out.println(length);
        endTime = System.currentTimeMillis();
        System.out.println("运算运行时间：" + (endTime - startTime) + "ms");
    }
    public static int LCS(Route route1, Route route2){
        int m = route1.getRoute().size();
        int n = route2.getRoute().size();
        int[][] dp = new int[m + 1][n + 1];
        ArrayList<RoadPoint> list1 = route1.getRoute();
        ArrayList<RoadPoint> list2 = route2.getRoute();
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (RoadPointUtils.isSimilar(list1.get(i-1),list2.get(j-1))) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

    ArrayList<RoadPoint> ansX = new ArrayList<>();
    ArrayList<RoadPoint> ansY = new ArrayList<>();
    while (m > 0 && n > 0) {
        RoadPoint c1 = route1.getRoute().get(m - 1);
        RoadPoint c2 = route2.getRoute().get(n - 1);
        if (RoadPointUtils.isSimilar(list1.get(m-1),list2.get(n-1))) {
            ansX.add(c1);
            ansY.add(c2);
            m--;
            n--;
        } else if (dp[m][n - 1] > dp[m - 1][n]) {
            n--;
        } else if (dp[m][n - 1] < dp[m - 1][n]) {
            m--;
        } else if (dp[m][n - 1] == dp[m - 1][n]) {
            n--;
        }
    }
      for (int i = 0; i < ansX.size(); i++) {
          System.out.println(ansX.get(i) +" " +ansY.get(i));
      }

        return dp[m][n];
    }
}
