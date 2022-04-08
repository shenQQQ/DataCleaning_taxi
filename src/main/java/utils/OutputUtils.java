package utils;

import config.Configuration;
import model.RoadPoint;
import model.Route;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static algorithm.AllCommonSubString.maxSub;

public class OutputUtils {
    public void outputResult(int id1, int id2) {
        InputUtils utils = new InputUtils();
        Route r1 = utils.getRoute(id1);
        Route r2 = utils.getRoute(id2);
        Map<Integer, List<List<RoadPoint>>> map = maxSub(r1, r2);
        List<List<RoadPoint>> l1 = map.get(r1.getId());
        List<List<RoadPoint>> l2 = map.get(r2.getId());

        // 前车小于后车时间，放到ans1中
        List<List<RoadPoint>> ansX1 = new ArrayList<>();
        List<List<RoadPoint>> ansY1 = new ArrayList<>();

        // 前车大于后车时间，放到ans2中
        List<List<RoadPoint>> ansX2 = new ArrayList<>();
        List<List<RoadPoint>> ansY2 = new ArrayList<>();
        for (int i = 0; i < l1.size(); i++) {
            if(l1.get(i).get(0).getTime().getTime() < l2.get(i).get(0).getTime().getTime()){
                ansX1.add(l1.get(i));
                ansY1.add(l2.get(i));
            }
            else {
                ansX2.add(l1.get(i));
                ansY2.add(l2.get(i));
            }

        }
//        System.out.println(ansX1);
//        System.out.println(ansX2);
//        System.out.println(ansY1);
//        System.out.println(ansY2);

        //如果相交长度大于0，再输出
        if (ansX1.size() > 0) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\cardata\\120car_1/0904-708/result/" + id1 + "_to_" + id2+ "_"+ansX1.size()+"time(s)"+ ".txt"));
                System.out.println(id1 +","+ id2 +"," + ansX1.size());
                bw.write(id1 + "," + id2+"," + ansX1.size());
                bw.newLine();
                for (int i = 0; i < ansX1.size(); i++) {
                    List<RoadPoint> route1 = ansX1.get(i);
                    List<RoadPoint> route2 = ansY1.get(i);
                    for (int j = 0; j < route1.size(); j++) {
                        String line = route1.get(j) + "---" + route2.get(j);
                        bw.write(line);
                        bw.newLine();
                        //System.out.println(line);
                    }
                    bw.newLine();
                    //System.out.println();
                }

                //刷新流
                bw.flush();

                //关闭资源
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (ansX2.size() > 0) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\cardata\\120car_1/0904-708/result/" + id2 + "_to_" + id1+ "_"+ansX2.size()+"time(s)"+".txt"));
                System.out.println(id2 +","+ id1 +","  + ansX2.size());
                bw.write(id2 + "," + id1+","  + ansX2.size() );
                bw.newLine();
                for (int i = 0; i < ansX2.size(); i++) {
                    List<RoadPoint> route1 = ansY2.get(i);
                    List<RoadPoint> route2 = ansX2.get(i);
                    for (int j = 0; j < route1.size(); j++) {
                        String line = route1.get(j) + "---" + route2.get(j);
                        bw.write(line);
                        bw.newLine();
                        //System.out.println(line);
                    }
                    bw.newLine();
                    //System.out.println();
                }

                //刷新流
                bw.flush();

                //关闭资源
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
