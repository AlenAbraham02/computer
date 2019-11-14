package com.example.computer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    IntentIntegrator qrScan;
    String qrid;
    TextView name,mouse,ram,harddisk,keyboard;
    ImageView img;
    String a,b,c,d,e,f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.name);
        mouse=findViewById(R.id.mouse);
        ram=findViewById(R.id.ram);
        harddisk=findViewById(R.id.harddisk);
        keyboard=findViewById(R.id.keyboard);img=findViewById(R.id.imageView);
        qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                qrid = result.getContents();


            }


        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://gressorial-parts.000webhostapp.com/alen/qrscan.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//If we are getting success from server


                        Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_LONG).show();


                        try {


                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json_obj = jsonArray.getJSONObject(i);
                                a = json_obj.getString("devicename");
                                b = json_obj.getString("mouse");
                                c = json_obj.getString("keyboard");
                                d = json_obj.getString("harddisk");
                                e = json_obj.getString("ram");
                                f=json_obj.getString("image");

                                name.setText(a);
                                mouse.setText(b);
                                keyboard.setText(c);
                                harddisk.setText(d);
                                ram.setText(e);


                                Picasso.with(MainActivity.this).load("https://gressorial-parts.000webhostapp.com/alen/"+f).into(img);
                                // longitude=json_obj.getString("longitude");
                                //        Toast.makeText(login.this,no,Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();


                            try {


                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json_obj = jsonArray.getJSONObject(i);


                                    // longitude=json_obj.getString("longitude");
                                    //        Toast.makeText(login.this,no,Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }


                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//Adding parameters to request
                params.put("qrscan",qrid);


//returning parameter
                return params;
            }
        };

//Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);


    }


}
