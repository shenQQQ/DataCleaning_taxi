package algorithm;

import config.Configuration;
import model.RoadPoint;
import model.Route;
import utils.InputUtils;
import utils.RoadPointUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllCommonSubString {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        InputUtils utils = new InputUtils();
        Route r1 = utils.getRoute(0);
        Route r2 = utils.getRoute(1);
        long endTime = System.currentTimeMillis();
        System.out.println("读取运行时间：" + (endTime - startTime) + "ms");
        startTime = System.currentTimeMillis();
        Map<Integer,List<List<RoadPoint>>> map = maxSub(r1,r2);
        List<List<RoadPoint>> l1 = map.get(r1.getId());
        List<List<RoadPoint>> l2 = map.get(r2.getId());
        for (int i = 0; i < l1.size(); i++) {
            List<RoadPoint> route1 = l1.get(i);
            List<RoadPoint> route2 = l2.get(i);
            for (int j = 0; j < route1.size(); j++) {
                System.out.println(route1.get(j) +" " +route2.get(j));
            }
            System.out.println();
        }
        System.out.println(l1.size());
        endTime = System.currentTimeMillis();
        System.out.println("运算运行时间：" + (endTime - startTime) + "ms");
    }

    public static Map<Integer,List<List<RoadPoint>>> maxSub(Route r1, Route r2){
        Map<Integer,List<List<RoadPoint>>> ans = new HashMap();

        ArrayList<RoadPoint> list1 = r1.getRoute();
        ArrayList<RoadPoint> list2 = r2.getRoute();
        int sLength = list1.size();
        int tLength = list2.size();
        ArrayList<RoadPoint> max = new ArrayList();
        ArrayList<RoadPoint> min = new ArrayList();

        max = (sLength > tLength) ? list1 : list2;
        int id1 = (sLength > tLength) ? r1.getId() : r2.getId();
        min = (sLength > tLength) ? list2 : list1;
        int id2 = (sLength > tLength) ? r2.getId() : r1.getId();

        ans.put(id1,new ArrayList());
        ans.put(id2,new ArrayList());
        while (true){
            Map<Integer, List<RoadPoint>> same = LongestCommonSubString(max,min,id1,id2);
            List<RoadPoint> x = same.get(id1);
            List<RoadPoint> y = same.get(id2);
//            if(x.get(x.size() - 1).getTime().getTime() - x.get(0).getTime().getTime() < Configuration.MIN_ROUTE_TimeDelta * 60 * 1000
//                    && y.get(x.size() - 1).getTime().getTime() - y.get(0).getTime().getTime() < Configuration.MIN_ROUTE_TimeDelta * 60 * 1000){
//                return ans;
//            }
            if(RoadPointUtils.getMeterDelta(x.get(x.size() - 1),x.get(0))<Configuration.MIN_ROUTE_METERDelta
                && RoadPointUtils.getMeterDelta(y.get(x.size() - 1),y.get(0))<Configuration.MIN_ROUTE_METERDelta){
//                System.out.println(x.get(x.size() - 1));
//                System.out.println(x.get(0));
//                System.out.println(y.get(x.size() - 1));
//                System.out.println(y.get(0));
//                System.out.println(RoadPointUtils.getMeterDelta(x.get(x.size() - 1),x.get(0)));
//                System.out.println(RoadPointUtils.getMeterDelta(y.get(x.size() - 1),y.get(0)));
                return ans;
            }
            ans.get(id1).add(x);
            ans.get(id2).add(y);
            max.removeAll(x);
            min.removeAll(y);
        }
    }

    public static Map<Integer, List<RoadPoint>> LongestCommonSubString(ArrayList<RoadPoint> list1, ArrayList<RoadPoint> list2 ,int id1,int id2) {
        int result = 0;
        int sLength = list1.size();
        int tLength = list2.size();

        List<RoadPoint> ansX = new ArrayList<>();
        List<RoadPoint> ansY = new ArrayList<>();

        int[][] dp = new int[sLength][tLength];
        for (int i = 0; i < sLength; i++) {
            for (int k = 0; k < tLength; k++) {
                RoadPoint c1 = list1.get(i);
                RoadPoint c2 = list2.get(k);
                if (RoadPointUtils.isSimilar(c1, c2)) {
                    if (i == 0 || k == 0) {
                        dp[i][k] = 1;
                    } else {
                        dp[i][k] = dp[i - 1][k - 1] + 1;
                    }
                    if (dp[i][k] > result) {
                        result = Math.max(dp[i][k], result);
                        ansX = getSubRoute(list1, i, result);
                        ansY = getSubRoute(list2, k, result);
                    }
                } else {
                    dp[i][k] = 0;
                }
            }
        }

        Map<Integer, List<RoadPoint>> map = new HashMap();
        map.put(id1, ansX);
        map.put(id2, ansY);
        return map;
    }

    public static List<RoadPoint> getSubRoute(ArrayList<RoadPoint> list, int a, int b) {
        List<RoadPoint> ans = new ArrayList();
        for (int i = a - b + 1; i <= a ; i++) {
            ans.add(list.get(i));
        }
        return ans;
    }
}
