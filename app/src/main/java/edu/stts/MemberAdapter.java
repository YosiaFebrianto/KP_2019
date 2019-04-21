package edu.stts;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{
    private static RVClickListener mylistener;
    private ArrayList<Member> menulist;
    private Activity activity;
    private int posisi;
    public int getPosisi() { return posisi;}
    public void setPosisi(int posisi) {this.posisi = posisi; }

    //Membuat constructor dari MenuAdapter yang menerima data ArrayList
    public MemberAdapter (ArrayList<Member>menulist,RVClickListener rvcl)
    {
        this.menulist=menulist;
        mylistener=rvcl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Menentukan layout untuk 1 row dari RecyclerView
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v= inflater.inflate(R.layout.rowmember,viewGroup,false);

        return new ViewHolder(v);//generate ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //mengisi widget dengan data dari ArrayList
        viewHolder.tvNama.setText(menulist.get(i).getNama());
        viewHolder.tvTgl.setText(menulist.get(i).getTgl());
        viewHolder.tvNohp.setText(""+menulist.get(i).getNohp());
    }

    @Override
    public int getItemCount() {
        //menampilkan jumlah data yang mau ditampilkan dari ArrayList
        return (menulist!=null)?menulist.size():0;
        //return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama,tvTgl,tvNohp;
        private ImageView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //find view by id
            tvNama=itemView.findViewById(R.id.tvNama);
            tvTgl=itemView.findViewById(R.id.tvTanggal);
            tvNohp=itemView.findViewById(R.id.tvNoHp);
            iv=itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mylistener.recyclerViewListClicked(v,ViewHolder.this.getLayoutPosition());
                    //getLayoutPosition() mendapatkan posisi ViewHolder yg di click
                }
            });
        }
    }
}
