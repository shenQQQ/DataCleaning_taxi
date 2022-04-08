package model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Route {
    int id;
    ArrayList<RoadPoint> route = new ArrayList<>();

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", route=" + route +
                '}';
    }
}
