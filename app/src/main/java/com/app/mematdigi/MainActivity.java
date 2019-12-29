package com.app.mematdigi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Adapters.GeneralAdapter;
import Adapters.OfferAdapter;
import Adapters.RecyclerAdapter;
import Model.General;
import Model.Personal;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ViewPager homeOffers;
    TabLayout tabLayout;
    OfferAdapter adapter;
    GridView gridView;
    List<General> listData = new ArrayList<>();
    GeneralAdapter generalAdapter;
    CardView card;
    RecyclerView recycle;
    RecyclerAdapter recyclerAdapter;
    List<Personal> personalImg = new ArrayList<>();

    private static final String URL = "https://memat.herokuapp.com/getGeneral";
    private static final String URI = "https://memat.herokuapp.com/personal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeOffers = findViewById(R.id.homeOffers);
        tabLayout  = findViewById(R.id.tabLayout);
        gridView = findViewById(R.id.gridView);
        card = findViewById(R.id.card);
        recycle = findViewById(R.id.recycle);

        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        adapter = new OfferAdapter(MainActivity.this);

        tabLayout.setupWithViewPager(homeOffers,true);
        homeOffers.setAdapter(adapter);

        getPersonalImages();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(22, TimeUnit.SECONDS)
                .readTimeout(22, TimeUnit.SECONDS)
                .writeTimeout(22, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(URL).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            JSONArray jsonArray = new JSONArray(response.body().string());

                            if (jsonArray.length() == 0) {

                                Toast.makeText(getApplicationContext(),"Data not available",Toast.LENGTH_SHORT);

                            }

                            for (int i = 0; i < 4 ; i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                String str1 = object.getString("image");
                                String str2 = object.getString("name");
                                String str3 = object.getString("price");

                               // Log.d("Name",str1);

                                General model = new General(str1, str2, str3);

                                listData.add(model);
                            }

                            generalAdapter = new GeneralAdapter(MainActivity.this,R.layout.general_book,listData);
                            gridView.setAdapter(generalAdapter);


                        }catch (JSONException e) {

                            e.printStackTrace();

                        }catch (IOException e) {

                            e.printStackTrace();
                        }


                    }
                });
            }

            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });



    }

  private void getPersonalImages(){

      OkHttpClient client = new OkHttpClient.Builder()
              .connectTimeout(22, TimeUnit.SECONDS)
              .readTimeout(22,TimeUnit.SECONDS)
              .writeTimeout(22,TimeUnit.SECONDS)
              .build();

      Request request = new Request.Builder().url(URI).build();

      client.newCall(request).enqueue(new Callback() {

          @Override
          public void onResponse(Call call, final Response response) throws IOException {

              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {

                      try {

                          JSONArray jsonArray = new JSONArray(response.body().string());


                          for(int i = 0 ;i < jsonArray.length() ;i++){

                              JSONObject jsonObject = jsonArray.getJSONObject(i);

                              String str1 = jsonObject.getString("img");
                              String str2 = jsonObject.getString("title");

                              Log.d("name",str1);

                              Personal model = new Personal(str1,str2);
                              personalImg.add(model);
                          }

                          recyclerAdapter = new RecyclerAdapter(getApplicationContext(),personalImg);
                          recycle.setAdapter(recyclerAdapter);


                      } catch (JSONException e) {
                          e.printStackTrace();
                      } catch (IOException e) {
                          e.printStackTrace();
                      }

                  }
              });
          }

          @Override
          public void onFailure(Call call, final IOException e) {

              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {

                      Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                  }
              });
          }
      });
  }
}
