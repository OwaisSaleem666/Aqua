package com.example.aqua;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class shops extends AppCompatActivity {
    ListView listView;
    ArrayList<Shopsrecord> lst = new ArrayList<>();
    ShopsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        fetchshops();
        listView = findViewById(R.id.shopslist);

        adapter = new ShopsAdapter(lst,this);
        listView.setAdapter(adapter);

    }

    public void fetchshops() {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                "http://192.168.10.7/aqua/public/api/shops",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("ShopId");
                                String txtshopname = jsonObject.getString("Slogan");
                                String shopdesc = jsonObject.getString("Description");
                                lst.add(new Shopsrecord(id, txtshopname, null, shopdesc, null, null));
                            } catch (Exception e) {
                                Toast.makeText(shops.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(shops.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        Volley.newRequestQueue(this).add(request);

    }

    class ShopsAdapter extends BaseAdapter {
        ArrayList<Shopsrecord> lst;
        Context context;
        LayoutInflater inflater;

        public ShopsAdapter(ArrayList<Shopsrecord> lst, Context context) {
            this.lst = lst;
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return lst.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = inflater.inflate(R.layout.shops_layout, null);
            ImageView imghsop = v.findViewById(R.id.imgshop);
            TextView txtshop = v.findViewById(R.id.txtshop);
            TextView txtdescshop = v.findViewById(R.id.txtdescshop);
            Button btnvisit = v.findViewById(R.id.btnvisitprods);
            Shopsrecord obj = lst.get(position);
            txtshop.setText(obj.Slogan);
            txtdescshop.setText(obj.Description);
            return v;
        }
    }
}