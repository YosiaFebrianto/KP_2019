package edu.stts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder>{
    private static RVClickListener mylistener;
    private ArrayList<Laporan> laporanlist;
    private SimpleDateFormat dateFormatter;
    //Membuat constructor dari MenuAdapter yang menerima data ArrayList
    public LaporanAdapter (ArrayList<Laporan>laporanlist,RVClickListener rvcl)
    {
        this.laporanlist=laporanlist;
        mylistener=rvcl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Menentukan layout untuk 1 row dari RecyclerView
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v= inflater.inflate(R.layout.rowlaporan,viewGroup,false);

        return new ViewHolder(v);//generate ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull final LaporanAdapter.ViewHolder viewHolder, int i) {
        //mengisi widget dengan data dari ArrayList
        Date mydate=null;
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        try {
            mydate=dateFormatter.parse(laporanlist.get(i).getTgl());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dayOfTheWeek = (String) DateFormat.format("EEEE",mydate);
        String day          = (String) DateFormat.format("dd",  mydate); // 20
        String monthString  = (String) DateFormat.format("MMMM",  mydate); // Jun
        String year         = (String) DateFormat.format("yyyy", mydate); // 2013
        viewHolder.tv_Tgl.setText(dayOfTheWeek+","+day+" "+monthString+" "+year);
        viewHolder.tv_jumlah.setText(laporanlist.get(i).getJumhadir());
    }

    @Override
    public int getItemCount() {
        //menampilkan jumlah data yang mau ditampilkan dari ArrayList
        return (laporanlist!=null)?laporanlist.size():0;
        //return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_Tgl,tv_jumlah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //find view by id
            tv_Tgl=itemView.findViewById(R.id.tv_Tgl);
            tv_jumlah=itemView.findViewById(R.id.tv_Jumlah);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mylistener.recyclerViewListClicked(v, LaporanAdapter.ViewHolder.this.getLayoutPosition());
                    //getLayoutPosition() mendapatkan posisi ViewHolder yg di click
                }
            });
        }
    }
}
