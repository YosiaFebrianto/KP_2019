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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MemberActivity extends Fragment {
    private RecyclerView rview;
    private MemberAdapter adapter;
    private ArrayList<Member> menulist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_member, container, false);
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
        menulist = new ArrayList<Member>();
        menulist.add(new Member("Andre","11-05-1998","09112221"));
        menulist.add(new Member("Nafa","12-06-2001","091122211"));
        menulist.add(new Member("Juli","20-04-1981","091111"));
        menulist.add(new Member("Andre","11-05-1998","09112221"));
        menulist.add(new Member("Nafa","12-06-2001","091122211"));
        menulist.add(new Member("Juli","20-04-1981","091111"));
        menulist.add(new Member("Andre","11-05-1998","09112221"));
        menulist.add(new Member("Nafa","12-06-2001","091122211"));
        menulist.add(new Member("Juli","20-04-1981","091111"));
        menulist.add(new Member("Andre1","11-05-1998","09112221"));
        menulist.add(new Member("Nafa2","12-06-2001","091122211"));
        menulist.add(new Member("Juli3","20-04-1981","091111"));

        rview=view.findViewById(R.id.rview);
        //anonymus inner class
        adapter=new MemberAdapter(menulist, new RVClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int posisi) {
                Toast.makeText(getActivity(),"posisi :"+posisi,Toast.LENGTH_SHORT).show();
                Fragment fragment = new AddMemberActivity();
                Bundle b=new Bundle();
                b.putString("Nama",menulist.get(posisi).getNama());
                b.putString("Tgl",menulist.get(posisi).getTgl());
                b.putString("Nohp",menulist.get(posisi).getNohp());
                fragment.setArguments(b);
                getFragmentManager().beginTransaction().add(R.id.fl_container,fragment).commit();

            }
        });
        RecyclerView.LayoutManager lm =new LinearLayoutManager(getActivity());
        rview.setLayoutManager(lm);
        rview.setAdapter(adapter);
        return view;
    }
}
