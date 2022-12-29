package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidtracker.data.ApiData;
import com.example.covidtracker.retrofit.RetrofitInstance;
import com.example.covidtracker.retrofit.retrofitInterface;
import com.google.android.material.tabs.TabLayout;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    private TextView totalConfirm,totalRecovered,totalTest,totalDeath,totalActive,updateTime;
    List<ApiData> mydata;
    ArrayList<String> menulist;
    Spinner spinner;

   PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);






        totalConfirm=findViewById(R.id.totalConfirm);
        totalRecovered=findViewById(R.id.totalRecovered);
        totalTest=findViewById(R.id.totalTest);//?
        totalDeath=findViewById(R.id.totalDeath);
        totalActive=findViewById(R.id.totalActive);
      //  country=findViewById(R.id.country);
        updateTime=findViewById(R.id.updateTime);
        mPieChart= findViewById(R.id.piechart);
        mydata=new ArrayList<ApiData>();
        spinner=findViewById(R.id.spinner);

       retrofitInterface retrofitInterface= RetrofitInstance.getService();

        Call<List<ApiData>> call = retrofitInterface.getdata();

        call.enqueue(new Callback<List<ApiData>>() {
            @Override
            public void onResponse(Call<List<ApiData>> call, Response<List<ApiData>> response) {

               mydata=response.body();

               fun2();

            }



            @Override
            public void onFailure(Call<List<ApiData>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });





        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mPieChart.clearChart();
                ApiData obj=new ApiData();
                obj=mydata.get(position);

                //  country.setText(obj.getCountryText());
                updateTime.setText("Last updated on @ "+obj.getLastUpdate());
                totalConfirm.setText(obj.getTotalCasesText());
                totalRecovered.setText(obj.getTotalRecoveredText());
                totalDeath.setText(obj.getTotalDeathsText());
                totalTest.setText("N/A");

                if(fun(obj.getActiveCasesText())==0)
                    totalActive.setText("N/A");
                else
                    totalActive.setText(obj.getActiveCasesText());


                mPieChart.addPieSlice(new PieModel( "Confirm",fun(obj.getTotalCasesText()), Color.parseColor("#FBC233")));
                mPieChart.addPieSlice(new PieModel( "Active",fun(obj.getActiveCasesText()), Color.parseColor("#007afe")));
               mPieChart.addPieSlice(new PieModel( "Recovered",fun(obj.getTotalRecoveredText()), Color.parseColor("#08a045")));
               mPieChart.addPieSlice(new PieModel( "Death",fun(obj.getTotalDeathsText()), Color.parseColor("#F6404F")));
                mPieChart.startAnimation();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }

    private int fun(String s) {
         if(s=="N/A")
             return 0;
        int ans=0;
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)!=',' && s.charAt(i)!='+')
            ans=ans*10+(s.charAt(i)-'0');
        }

        return ans;
    }

   private void fun2()
   {
       menulist = new ArrayList<>();



        for(ApiData c: mydata)
        {
            menulist.add(c.getCountryText());
        }


       // Toast.makeText(MainActivity.this, mydata.get(5).getCountryText(), Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> spinnerAd =
              new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, menulist);
        spinner.setAdapter(spinnerAd);



   }
}

/*

   ApiData obj=new ApiData();
                obj=mydata.get(i);

                //  country.setText(obj.getCountryText());
                updateTime.setText("Last updated on @ "+obj.getLastUpdate());
                totalConfirm.setText(obj.getTotalCasesText());
                totalRecovered.setText(obj.getTotalRecoveredText());
                totalDeath.setText(obj.getTotalDeathsText());
                totalActive.setText(obj.getActiveCasesText());



                mPieChart.addPieSlice(new PieModel( "Confirm",fun(obj.getTotalCasesText()), Color.parseColor("#FBC233")));
                mPieChart.addPieSlice(new PieModel( "Active",fun(obj.getActiveCasesText()), Color.parseColor("#007afe")));
                mPieChart.addPieSlice(new PieModel( "Recovered",fun(obj.getTotalRecoveredText()), Color.parseColor("#08a045")));
                mPieChart.addPieSlice(new PieModel( "Death",fun(obj.getTotalDeathsText()), Color.parseColor("#F6404F")));
                mPieChart.startAnimation();
 */
