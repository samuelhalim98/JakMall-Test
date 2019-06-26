package com.example.jakmall2;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    Button click;
    public static TextView data;
    private static final String urldata ="https://api.icndb.com/jokes/random/10";
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private  List<ListActivity> listActivities;


    protected View onCreate(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        recyclerView =(RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listActivities=new ArrayList<>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                LoadRecyclerViewData();
            }
        });

        return rootView;
    }


    @Override
    public void onRefresh() {

        // Fetching data from server
        LoadRecyclerViewData();
    }





    private void LoadRecyclerViewData()
    {
        mSwipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urldata,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array  = jsonObject.getJSONArray("value");

                            for(int i = 0;i<array.length();i++)
                            {
                                JSONObject obj = array.getJSONObject(i);
                                ListActivity list = new ListActivity(
                                        obj.getString("id"),
                                        obj.getString("joke"),
                                        obj.getString("categories")
                                );
                                listActivities.add(list);
                            }
                            adapter = new AdapterActivity(listActivities,getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);


                    }
                }, new Response.ErrorListener() {
                     @Override
                    public void onErrorResponse(VolleyError error) {
                         Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                         mSwipeRefreshLayout.setRefreshing(false);

            }
        }


        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
