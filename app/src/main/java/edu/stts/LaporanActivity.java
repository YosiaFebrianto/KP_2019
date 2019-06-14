package edu.stts;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class LaporanActivity extends Fragment {

    private RecyclerView rview;
    private LaporanAdapter adapter;
    private DomainConfig domainConfig;
    private ArrayList<Laporan> laporanlist;
    SharedPreferences sp;
    RequestQueue requestQueue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_laporan, container, false);

        sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        domainConfig = new DomainConfig();
        requestQueue = Volley.newRequestQueue(getContext());
        laporanlist = new ArrayList<Laporan>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, domainConfig.getDomain_local() + "/Laporan_API/DataLaporan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject data = new JSONObject(response);
                    JSONArray subjects = data.getJSONArray("data");
                    for(int i = 0; i < subjects.length(); i++){
                        Toast.makeText(getActivity(), subjects.getJSONObject(i).getString("LOKASI_KEGIATAN"), Toast.LENGTH_SHORT).show();
                        laporanlist.add(new Laporan(subjects.getJSONObject(i).getString("WAKTU_KEGIATAN"),subjects.getJSONObject(i).getString("LOKASI_KEGIATAN")));
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "GAGAL", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", sp.getString("id", "Dummy"));
                return params;
            }
        };
        laporanlist.add(new Laporan("21-09-2019","asd"));
        requestQueue.add(stringRequest);

        rview=view.findViewById(R.id.rviewlaporan);
        //anonymus inner class
        adapter=new LaporanAdapter(laporanlist, new RVClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int posisi) {
                Toast.makeText(getActivity(),"posisi :"+posisi,Toast.LENGTH_SHORT).show();
                Fragment fragment = new AddKegiatan();
                Bundle b=new Bundle();
                b.putString("Tgl",laporanlist.get(posisi).getTgl());
                b.putString("Alamat",laporanlist.get(posisi).getJumhadir());
                fragment.setArguments(b);
                ((MainActivity)getActivity()).showAddLaporan();
                getFragmentManager().beginTransaction().add(R.id.fl_container,fragment).commit();

            }
        });
        RecyclerView.LayoutManager lm =new LinearLayoutManager(getActivity());
        rview.setLayoutManager(lm);
        rview.setAdapter(adapter);

        return view;
    }
}
