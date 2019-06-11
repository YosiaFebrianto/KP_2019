package edu.stts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.HashMap;
import java.util.List;

public class ListAnggotaAdapter extends RecyclerView.Adapter<ListAnggotaAdapter.MyViewHolder> {
    private HashMap<Integer, Boolean> isChecked = new HashMap<>();
    private Context mContext;
    private LayoutInflater inflater;
    private List<String> ListAnggota;
    public ListAnggotaAdapter(Context context, List<String> ListAnggota){
        this.mContext = context;
        this.ListAnggota = ListAnggota;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ListAnggotaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = inflater.inflate(R.layout.list_anggota, viewGroup, false);

        MyViewHolder holder = new MyViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAnggotaAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.cbAnggota.setText(ListAnggota.get(i));
        if (isChecked.containsKey(i)){
            myViewHolder.cbAnggota.setChecked(isChecked.get(i));
        } else {
            myViewHolder.cbAnggota.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return ListAnggota.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbAnggota;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cbAnggota = (CheckBox) itemView.findViewById(R.id.cbAnggota);
            cbAnggota.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        // KONDISI PADA SAAT CEKLIS
                    } else {
                        // KONDISI PADA SAAT CEKLIS DIHILANGKAN
                    }
                }
            });
        }
    }
}
