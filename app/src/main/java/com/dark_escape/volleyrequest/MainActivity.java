package com.dark_escape.volleyrequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private EditText et;
    private Button bt;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.txt);
        et= (EditText) findViewById(R.id.img_url);
        bt= (Button) findViewById(R.id.go);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et.getText().toString().isEmpty()) {
                    request("56985acd-2182-4468-994c-3bfcec560b30",et.getText().toString());
                }
            }
        });

    }
    public void request(final String apiKey, final String url) {
        StringRequest request=new StringRequest(
                Request.Method.POST,
                "https://api.havenondemand.com/1/api/sync/ocrdocument/v1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "onResponse: "+response);
                            JSONObject jOb=new JSONObject(response);
                            String data=jOb.getJSONArray("text_block")
                                    .getJSONObject(0)
                                    .getString("text");
                            tv.setText(data);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("apikey",apiKey);
                map.put("url",url);
                return map;
            }
        };

        Volley.newRequestQueue(MainActivity.this).add(request);
    }
}
