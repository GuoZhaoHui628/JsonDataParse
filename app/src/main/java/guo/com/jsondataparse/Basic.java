package guo.com.jsondataparse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${GuoZhaoHui} on 2017/3/10.
 * email:guozhaohui628@gmail.com
 */

public class Basic {

    @SerializedName("lat")
    public String lat;

    @SerializedName("lon")
    public String log;


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "Basic{" +
                "lat='" + lat + '\'' +
                ", log='" + log + '\'' +
                '}';
    }
}
