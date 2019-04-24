package edu.stts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{
    private static RVClickListener mylistener;
    private ArrayList<Member> memberlist;

    //Membuat constructor dari MenuAdapter yang menerima data ArrayList
    public MemberAdapter (ArrayList<Member>memberlist,RVClickListener rvcl)
    {
        this.memberlist=memberlist;
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
        viewHolder.tvNama.setText(memberlist.get(i).getNama());
        viewHolder.tvTgl.setText(memberlist.get(i).getTgl());
        viewHolder.tvNohp.setText(""+memberlist.get(i).getNohp());
    }

    @Override
    public int getItemCount() {
        //menampilkan jumlah data yang mau ditampilkan dari ArrayList
        return (memberlist!=null)?memberlist.size():0;
        //return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama,tvTgl,tvNohp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //find view by id
            tvNama=itemView.findViewById(R.id.tv_Tgl);
            tvTgl=itemView.findViewById(R.id.tv_Jumlah);
            tvNohp=itemView.findViewById(R.id.tvNoHp);
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
