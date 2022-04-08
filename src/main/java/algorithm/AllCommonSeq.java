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

//错误代码
public class AllCommonSeq {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        InputUtils utils = new InputUtils();
        Route r1 = utils.getRoute(0);
        Route r2 = utils.getRoute(1);
        long endTime = System.currentTimeMillis();
        System.out.println("读取运行时间：" + (endTime - startTime) + "ms");
        startTime = System.currentTimeMillis();
        Map<Integer,List<List<RoadPoint>>> map = exactAllCommonSeq(r1,r2);
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


    public static Map<Integer,List<List<RoadPoint>>> exactAllCommonSeq(Route r1,Route r2) {
        ArrayList<RoadPoint> list1 = r1.getRoute();
        ArrayList<RoadPoint> list2 = r2.getRoute();
        int[][] temp = new int[list1.size()][list2.size()];
        for (int i = 0; i < list2.size(); i++) {
            temp[0][i] = (RoadPointUtils.isSimilar(list1.get(0),list2.get(i))) ? 1 : 0;
        }
        for (int j = 0; j < list1.size(); j++) {
            temp[j][0] = (RoadPointUtils.isSimilar(list2.get(0),list1.get(j))) ? 1 : 0;
        }
        for (int i = 1; i < list1.size(); i++) {
            for (int j = 1; j < list2.size(); j++) {
                if (RoadPointUtils.isSimilar(list1.get(i),list2.get(j))) {
                    temp[i][j] = temp[i - 1][j - 1] + 1;
                } else {
                    temp[i][j] = 0;
                }
            }
        }

        List<Map<String, Integer>> list = new ArrayList<>();
        //依次遍历对角矩阵的对角线
        for (int i = 0; i <list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                Map<String, Integer> map = new HashMap<>();
                if (temp[i][j] != 0 && temp[i][j] != 1) {
                    if (temp[i - 1][j - 1] == temp[i][j] - 1) {
                        if (i - 1 > 0 && j - 1 > 0 && i + 1 < list1.size() && j + 1 < list2.size() && temp[i - 1][j - 1] != 0 && temp[i + 1][j + 1] == 0) {
                            map.put("row", i);
                            map.put("column", j);
                            map.put("length", temp[i][j]);
                            list.add(map);
                        } else if ((i + 1 == list1.size() || j + 1 == list2.size())) {
                            map.put("row", i);
                            map.put("column", j);
                            map.put("length", temp[i][j]);
                            list.add(map);
                        }
                    }
                }
            }
        }
        List<List<RoadPoint>> resultListX = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Map<String, Integer> map : list) {
                if (map.get("length") > Configuration.MIN_ROUTE_TimeDelta) {
                    List<RoadPoint> l = getSubRoute(r1, map.get("row"), map.get("length"));
                    resultListX.add(l);
                }
            }
        }
        List<List<RoadPoint>> resultListY = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Map<String, Integer> map : list) {
                if (map.get("length") > Configuration.MIN_ROUTE_TimeDelta) {
                    List<RoadPoint> l = getSubRoute(r2, map.get("row"), map.get("length"));
                    resultListY.add(l);
                }
            }
        }
        Map<Integer,List<List<RoadPoint>>> ans = new HashMap<>();
        ans.put(r1.getId(),resultListX);
        ans.put(r2.getId(),resultListY);
        return ans;
    }

    public static List<RoadPoint> getSubRoute(Route r, int a, int b) {
        ArrayList<RoadPoint> list = r.getRoute();
        List<RoadPoint> ans = new ArrayList<>();
        for (int i = a - b + 1; i <= a+1; i++) {
            ans.add(list.get(i));
        }
        return ans;
    }

}
