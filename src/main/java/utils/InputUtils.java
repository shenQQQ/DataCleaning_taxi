package utils;

import model.RoadPoint;
import model.Route;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InputUtils {
    public Route getRoute(int id){
        File file = new File("E:\\cardata\\120car_1/0904-708/" + id + ".txt"); // sql数据文件
        Route route = new Route();
        route.setId(id);
        ArrayList<RoadPoint> list = new ArrayList<>();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                    String[] attribute = line.split(",");
                    list.add(new RoadPoint(
                            Double.parseDouble(attribute[1]),
                            Double.parseDouble(attribute[2]),
                            Integer.parseInt(attribute[3]),
                            Integer.parseInt(attribute[4]),
                            ft.parse(attribute[5])));

            }
            route.setRoute(list);
        }catch (ParseException e){
            e.printStackTrace();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return route;
    }
}
