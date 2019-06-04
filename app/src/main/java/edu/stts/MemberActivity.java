package edu.stts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MemberActivity extends Fragment {
    private DomainConfig domainConfig;
    private RecyclerView rview;
    private MemberAdapter adapter;
    private ArrayList<Member> memberlist;
    RequestQueue rq;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_member, container, false);
        domainConfig = new DomainConfig();
        rview=view.findViewById(R.id.rview);
        rq = Volley.newRequestQueue(getContext());

        final Button button = (Button) view.findViewById(R.id.btn_addmember);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_container, new AddMemberActivity() ); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();
                    Toast.makeText(getActivity(),"ADD",Toast.LENGTH_SHORT).show();


            }
        });
        memberlist = new ArrayList<Member>();

        //load member from database
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, domainConfig.getDomain_local() + "/ketua_api/get_Member", null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.e("Response : ", response.toString());
                    for (int i = 0 ; i < response.length() ; i++){
                        JSONObject data = null;
                        try {
                            data = response.getJSONObject(i);
                            memberlist.add(new Member(data.getString("name"), data.getString("birth_date"), data.getString("phone")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter=new MemberAdapter(memberlist, new RVClickListener() {
                        @Override
                        public void recyclerViewListClicked(View v, int posisi) {
                            Toast.makeText(getActivity(),"posisi :"+posisi,Toast.LENGTH_SHORT).show();
                            Fragment fragment = new AddMemberActivity();
                            Bundle b=new Bundle();
                            b.putString("Nama",memberlist.get(posisi).getNama());
                            b.putString("Tgl",memberlist.get(posisi).getTgl());
                            b.putString("Nohp",memberlist.get(posisi).getNohp());
                            fragment.setArguments(b);
                            getFragmentManager().beginTransaction().add(R.id.fl_container,fragment).commit();

                        }
                    });
                    RecyclerView.LayoutManager lm =new LinearLayoutManager(getActivity());
                    rview.setLayoutManager(lm);
                    rview.setAdapter(adapter);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
        rq.add(jar);



        //anonymus inner class

        return view;
    }
}
