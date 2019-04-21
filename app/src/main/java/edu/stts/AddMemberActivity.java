package edu.stts;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMemberActivity extends Fragment {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText btDatePicker;
    private TextView etNama,etTgl,etNohp,etstatus;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_member, container, false);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        btDatePicker = (EditText) view.findViewById(R.id.etTgl);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        etNama=view.findViewById(R.id.etNama);
        etTgl=view.findViewById(R.id.etTanggal);
        etNohp=view.findViewById(R.id.etNoHp);
        etstatus=view.findViewById(R.id.tv_ket_AddMember);
        btn=view.findViewById(R.id.btn_addmember);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn.getText().toString().equalsIgnoreCase("ADD")){
                    Toast.makeText(getActivity(),"Add New",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Save",Toast.LENGTH_SHORT).show();
                }

            }
        });
        if(getArguments()!=null){
            String tanggal=String.valueOf(getArguments().getString("Tgl"));
            //int hari=Integer.parseInt(tanggal.substring(0,2));
            //int bulan=Integer.parseInt(tanggal.substring(3,2));
            //int tahun=Integer.parseInt(tanggal.substring(6,4));
            etNama.setText(String.valueOf(getArguments().getString("Nama")));
            //etTgl.setText("12-10-1999");
            etNohp.setText(String.valueOf(getArguments  ().getString("Nohp")));
            etstatus.setText("Edit Member");
            btn.setText("Save");

        }

        return view;
    }
    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                btDatePicker.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
