package com.example.aqua;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUp_Activity extends AppCompatActivity {

EditText txtname,txtusername,txtemail,txtphone,txtpassword;

ArrayList<String> territorylist = new ArrayList<>();
ArrayAdapter<String> territoryadapter;
RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtname = findViewById(R.id.name);
        txtusername = findViewById(R.id.username);
        txtemail = findViewById(R.id.email);
        txtphone = findViewById(R.id.phone);
        txtpassword = findViewById(R.id.password);
        requestQueue = Volley.newRequestQueue(this);



    }

    public void navtosignin(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    public void signup(View view) {
        String name = txtname.getText().toString();
        String username = txtusername.getText().toString();
        String phone = txtphone.getText().toString();
        String email = txtemail.getText().toString();
        String password = txtpassword.getText().toString();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                "http://192.168.10.7/laravel/Aqua/public/api/users/submit",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(SignUp_Activity.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(SignUp_Activity.this, ""+response, Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(SignUp_Activity.this, MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp_Activity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("Name",name);
                params.put("Phone",phone);
                params.put("UserName",username);
                params.put("Email",email);
                params.put("Password",password);
                return params;

            }
        };
        Volley.newRequestQueue(this).add(request);

    }

    class GetTerrAdapter extends BaseAdapter
    {
        List<Territories> lst;
        Context context;
        LayoutInflater inflater;

        public GetTerrAdapter(List<Territories> lst, Context context) {
            this.lst = lst;
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
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
            return null;
        }
    }
}