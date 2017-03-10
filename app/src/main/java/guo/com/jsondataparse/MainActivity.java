package guo.com.jsondataparse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listView;
    private List<Province> provinceList;
    private List<String> stringList = new ArrayList<>();
    private static final String URL1 = "http://guolin.tech/api/china";
    private static final String URL2 = "http://guolin.tech/api/weather?cityid=CN101250101&key=be458932f635499eab9874ee9f07056b";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) this.findViewById(R.id.listview);
        this.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        sendOkHttpRequest(URL1, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                provinceList = parseJsonProvince(response.body().string());
                                for(Province province:provinceList){
                                    stringList.add(province.getName());
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, stringList);
                                        listView.setAdapter(arrayAdapter);
                                    }
                                });
                            }
                        });
                    }
                }.start();
            }
        });


        this.findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        sendOkHttpRequest(URL2, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final Basic basic = parseJsonBasic(response.body().string());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this,"经纬度-->"+basic.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                }.start();
            }
        });
    }


    /**
     * 从json数据解析成对象
     * @param response
     * @return
     */
    public List<Province> parseJsonProvince(String response){

        try {
            JSONArray jsonArray = new JSONArray(response);
            List<Province> provinceList = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Province province = new Province();
                province.setId(jsonObject.getInt("id"));
                province.setName(jsonObject.getString("name"));
                provinceList.add(province);
            }
            return provinceList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return null;
    }


    /**
     * 解析天气json数据中的lat和log两个字段,并将解析出来的数据存到Basic对象中
     * @param response
     * @return
     */
    public Basic parseJsonBasic(String response){
        try {
            Basic basic = new Basic();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray =  jsonObject.getJSONArray("HeWeather");

            //GSON解析方法
//            String jsonWeather = jsonArray.getJSONObject(0).toString();
//            Weather weather = new Gson().fromJson(jsonWeather,Weather.class);
//            basic = weather.basic;

            JSONObject  jsonWeather =  jsonArray.getJSONObject(0);
            JSONObject jsonBasic =  jsonWeather.getJSONObject("basic");

            basic.setLat(jsonBasic.getString("lat"));
            basic.setLog(jsonBasic.getString("lon"));
            Log.i(TAG, basic.toString());
            return basic;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 用okhttp发送请求
     * @param address
     * @param callback
     */
    public  void sendOkHttpRequest(String address,okhttp3.Callback callback){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);

    }

}
