package utils;

import config.Configuration;
import model.RoadPoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class RoadPointUtils {
    private static final double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static int getTimeDelta(RoadPoint rd1, RoadPoint rd2) {
        Date startDate = rd1.getTime();
        Date endDate = rd2.getTime();
        long a = endDate.getTime();
        long b = startDate.getTime();
        int c = (int) ((a - b) / 1000);
        return Math.abs(c);
    }

    public static double getMeterDelta(RoadPoint rd1, RoadPoint rd2) {
        double long1 = rd1.getLongitude();
        double lat1 = rd1.getLatitude();
        double long2 = rd2.getLongitude();
        double lat2 = rd2.getLatitude();
        double a, b, d, sa2, sb2;
        lat1 = rad(lat1);
        lat2 = rad(lat2);
        a = lat1 - lat2;
        b = rad(long1 - long2);

        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * EARTH_RADIUS
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        d = d * 1000;
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }

    public static boolean isSimilar(RoadPoint c1 ,RoadPoint c2){
        if(RoadPointUtils.getMeterDelta(c1,c2) <= Configuration.DELTA_METER_MAX && RoadPointUtils.getTimeDelta(c1,c2) <= Configuration.DELTA_TIME_SEC_MAX && RoadPointUtils.getTimeDelta(c1,c2) >= Configuration.DELTA_TIME_SEC_MIN){
            return true;
        }
        return false;
    }
}
