package edu.stts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class LaporanActivity extends Fragment {

    private RecyclerView rview;
    private LaporanAdapter adapter;
    private ArrayList<Laporan> laporanlist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_laporan, container, false);


        laporanlist = new ArrayList<Laporan>();
        laporanlist.add(new Laporan("10-08-2018","12 Dari 20 Orang"));
        laporanlist.add(new Laporan("17-08-2019","12 Dari 20 Orang"));
        laporanlist.add(new Laporan("18-08-2019","12 Dari 20 Orang"));
        laporanlist.add(new Laporan("20-08-2019","12 Dari 20 Orang"));
        laporanlist.add(new Laporan("21-08-2019","12 Dari 20 Orang"));
        laporanlist.add(new Laporan("22-08-2019","12 Dari 20 Orang"));
        laporanlist.add(new Laporan("30-08-2019","12 Dari 20 Orang"));
        laporanlist.add(new Laporan("31-08-2019","12 Dari 20 Orang"));

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
