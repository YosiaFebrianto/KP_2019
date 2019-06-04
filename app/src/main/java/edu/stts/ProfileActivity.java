package edu.stts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends Fragment {
    EditText passwordLama,passwordBaru,confpasswordBaru;
    private DomainConfig domainConfig;
    RequestQueue requestQueue;
    SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        passwordLama = view.findViewById(R.id.etPasswordLama);
        passwordBaru = view.findViewById(R.id.etPasswordBaru);
        confpasswordBaru = view.findViewById(R.id.etCPasswordBaru);
        requestQueue = Volley.newRequestQueue(getContext());
        sp = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        return view;
    }
    public void changePassword(View v){
        Log.e("Response : ","jalan gan");
        SharedPreferences sp = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String username = sp.getString("username", "Dummy");
        final String password = passwordBaru.getText().toString();
        String confpassword = confpasswordBaru.getText().toString();
        if(password.equals(confpassword)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, domainConfig.getDomain_local() + "/changepassword_api/changepassword", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject resJSON = new JSONObject(response);
                        String message = resJSON.getString("status").toString();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error", error.getMessage());
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(getActivity(), "Password Baru dan Confirm Password harus sama", Toast.LENGTH_SHORT).show();
        }
    }
}
