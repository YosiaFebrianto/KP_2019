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
import android.widget.Button;
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

import static android.content.Context.MODE_PRIVATE;

public class ProfileActivity extends Fragment {
    EditText passwordLama,passwordBaru,confpasswordBaru;
    private DomainConfig domainConfig;
    Button submitPassword,submitLogout;
    RequestQueue requestQueue;
    SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        domainConfig = new DomainConfig();
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        passwordLama = view.findViewById(R.id.etPasswordLama);
        passwordBaru = view.findViewById(R.id.etPasswordBaru);
        confpasswordBaru = view.findViewById(R.id.etCPasswordBaru);
        requestQueue = Volley.newRequestQueue(getContext());
        submitPassword = view.findViewById(R.id.btn_changepass);

        submitLogout = view.findViewById(R.id.btn_logout);

        submitLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);

        submitPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Response : ","jalan gan");
                final String username = sp.getString("username", "Dummy");
                final String password = passwordLama.getText().toString();
                final String tempPasswordBaru = passwordBaru.getText().toString();
                String confpassword = confpasswordBaru.getText().toString();
                if(tempPasswordBaru.equals(confpassword)){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, domainConfig.getDomain_local() + "/changepassword_api/changepassword", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject resJSON = new JSONObject(response);
                                String message = resJSON.getString("message").toString();
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
                            params.put("password_lama", password);
                            params.put("password_baru", tempPasswordBaru);

                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(getActivity(), "Password Baru dan Confirm Password harus sama", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        return view;
    }

    private void signOut() {
        //sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        //sp.edit().clear().apply();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
