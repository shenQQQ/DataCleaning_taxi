package config;

public class Configuration {
    public static int DELTA_METER_MAX = 150; //距离150内判读交互
    public static int DELTA_TIME_SEC_MAX = 21600;
    public static int DELTA_TIME_SEC_MIN = 30; //轨迹要时差30s到6h，30设定为传输等时延
    //最短时间差
    public static int MIN_ROUTE_TimeDelta = 2;  //最小交互时间2min
    public static int MIN_ROUTE_METERDelta =1000;   //或者最少交互距离1km,目前选的1km
}
