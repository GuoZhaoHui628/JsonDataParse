package guo.com.jsondataparse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${GuoZhaoHui} on 2017/3/9.
 * email:guozhaohui628@gmail.com
 */

public class Province {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
