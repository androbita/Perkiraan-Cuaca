package com.ngopidevteam.pranadana.perkiraancuaca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    Spinner spinner;
    TextView siang, malam, dini_hari, suhu, kelembapan;

    ArrayList<String> namaDaerah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namaDaerah = new ArrayList<>();
        spinner = findViewById(R.id.spin_kota);
        mQueue = Volley.newRequestQueue(this);
        Button btnAmbil = findViewById(R.id.btnAmbil);

        btnAmbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilCuaca();
            }
        });


    }

    private void ambilCuaca() {
        String URL = "http://papaside.com/data.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                siang = findViewById(R.id.isiSiang);
                malam = findViewById(R.id.isiMalam);
                dini_hari = findViewById(R.id.isi_dini_hari);
                suhu = findViewById(R.id.isiSuhu);
                kelembapan = findViewById(R.id.isiKelembapan);

                int kota = spinner.getSelectedItemPosition();
                try {
                    JSONObject cuaca = response.getJSONObject(kota);

                    String getSiang = cuaca.getString("siang");
                    String getMalam = cuaca.getString("malam");
                    String getDiniHari = cuaca.getString("dini_hari");
                    String getSuhu = cuaca.getString("suhu");
                    String getKelembapan = cuaca.getString("Kelembapan");

                    siang.setText(getSiang);
                    malam.setText(getMalam);
                    dini_hari.setText(getDiniHari);
                    suhu.setText(getSuhu);
                    kelembapan.setText(getKelembapan);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}
