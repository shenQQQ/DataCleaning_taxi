package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
public class RoadPoint {
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    double longitude;
    double latitude;
    int speed;
    int direction;
    Date time;

    @Override
    public String toString() {
        return longitude +
                "," + latitude +
                "," + speed+
                "," + direction+
                "," + df.format(time);
    }
}
