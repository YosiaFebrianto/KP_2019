package edu.stts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.stts.apihelper.BaseApiService;
import edu.stts.apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private DomainConfig domainConfig;
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    ProgressDialog loading;

    Context mContext;
    BaseApiService mApiService;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        domainConfig = new DomainConfig();
        rq = Volley.newRequestQueue(this);
        initComponents();
    }

    private void initComponents() {
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin();
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void requestLogin(){
//        mApiService.loginRequest(etUsername.getText().toString(), etPassword.getText().toString())
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if (response.isSuccessful()){
//                            loading.dismiss();
//                            try {
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                if (jsonRESULTS.getString("error").equals("false")){
//                                    // Jika login berhasil maka data nama yang ada di response API
//                                    // akan diparsing ke activity selanjutnya.
//                                    Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//                                    String nama = jsonRESULTS.getJSONObject("user").getString("nama");
//                                    Intent intent = new Intent(mContext, MainActivity.class);
//                                    //intent.putExtra("result_nama", nama);
//                                    Log.e("Nama", nama);
//                                    SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
//                                    SharedPreferences.Editor Ed = sp.edit();
//                                    Ed.putString("nama", nama);
//                                    Ed.apply();
//
//                                    startActivity(intent);
//                                } else {
//                                    // Jika login gagal
//                                    String error_message = jsonRESULTS.getString("error_msg");
//                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            loading.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("debug", "onFailure: ERROR > " + t.toString());
//                        loading.dismiss();
//                        Toast.makeText(mContext, "Tidak Ada Jaringan Internet", Toast.LENGTH_SHORT).show();
//                    }
//                });
        StringRequest stringRequest = new StringRequest(Request.Method.POST, domainConfig.getDomain_local() + "/login_api/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resJSON = new JSONObject(response);
                    if(resJSON.getString("error").equals("false")){
                        JSONObject userJSON = new JSONObject(resJSON.getString("user"));

                        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor Ed = sp.edit();
                        Ed.putString("nama", userJSON.getString("nama"));
                        Ed.putString("jabatan", userJSON.getString("jabatan"));
                        Ed.putString("id_komsel", userJSON.getString("komsel"));
                        Ed.putString("username", etUsername.getText().toString());
                        Ed.apply();
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                    }else{
                        String error_message = "Error";
                        Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                    }
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
                params.put("username", etUsername.getText().toString());
                params.put("password", etPassword.getText().toString());

                return params;
            }
        };
        rq.add(stringRequest);
    }
}