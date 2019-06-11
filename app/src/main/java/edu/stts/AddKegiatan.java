package edu.stts;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class AddKegiatan extends Fragment {

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private SimpleDateFormat dateFormatter,timeFormatter;
    private EditText btDatePicker,btTimePicker,etTgl,etAlamat;
    private TextView tvstatus,etLeaderName;
    private Button btn,addKegiatan;
    private RadioGroup jenis;
    private RadioButton selectedRadioButton;
    private DomainConfig domainConfig;
    private List<String> ListAnggota;
    private RecyclerView rvListAnggota;
    RequestQueue requestQueue;
    SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        domainConfig = new DomainConfig();
        requestQueue = Volley.newRequestQueue(getContext());
        View view = inflater.inflate(R.layout.activity_add_kegiatan, container, false);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.US);
        btDatePicker = (EditText) view.findViewById(R.id.etTanggal);
        btTimePicker = (EditText) view.findViewById(R.id.etWaktu);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        btTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHourDialog();
            }
        });
        addKegiatan = view.findViewById(R.id.btn_addkegiatan);
        jenis = (RadioGroup) view.findViewById(R.id.radioGroup);
        sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        ListAnggota = new ArrayList<>();
            ListAnggota.add("Budi Rahman s");
        ListAnggota.add("Ani Yudhoyono");
        ListAnggota.add("Moses Kurniawan");
        rvListAnggota = (RecyclerView) view.findViewById(R.id.rvAnggota);
        rvListAnggota.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListAnggota.setAdapter(new ListAnggotaAdapter(getActivity(), ListAnggota));
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, domainConfig.getDomain_local() + "/Laporan_API/jabatan", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject resJSON = new JSONObject(response);
//                    String message = resJSON.getString("message").toString();
//                    etLeaderName = getView().findViewById(R.id.namaLeader);
//                    etLeaderName.setText(message);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("error", error.getMessage());
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("id", sp.getString("id", "Dummy"));
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
        etLeaderName = view.findViewById(R.id.namaLeader);
        etLeaderName.setText(sp.getString("nama", "Dummy"));
        btn=view.findViewById(R.id.btn_addkegiatan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn.getText().toString().equalsIgnoreCase("BUAT LAPORAN")){
                    Toast.makeText(getActivity(),"Add New Laporan",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Save Update",Toast.LENGTH_SHORT).show();
                }

            }
        });
        etTgl=(EditText)view.findViewById(R.id.etTanggal);
        etAlamat=(EditText)view.findViewById(R.id.etAlamat);
        tvstatus=view.findViewById(R.id.tv_judul_laporan);
        if(getArguments()!=null){
            Date mydate=null;
            try {
                mydate=dateFormatter.parse(getArguments().getString("Tgl"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            etTgl.setText(dateFormatter.format(mydate));
            etAlamat.setText(String.valueOf(getArguments().getString("Alamat")));
            tvstatus.setText("Edit Laporan Komsel");
            btn.setText("Save");
        }
        sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);

        addKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Response : ","jalan gan");
                if(jenis.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getActivity(), "Isi Jenis Kegiatan!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // get selected radio button from radioGroup
                    int selectedId = jenis.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    selectedRadioButton = (RadioButton) getView().findViewById(selectedId);
                    final String komsel_id = sp.getString("id_komsel", "Dummy");
                    final String jenis = selectedRadioButton.getText().toString();
                    final String waktu = etTgl.getText().toString();
                    Toast.makeText(getActivity(), waktu, Toast.LENGTH_SHORT).show();
                    final String alamat = etAlamat.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, domainConfig.getDomain_local() + "/Laporan_API/addLaporan", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject resJSON = new JSONObject(response);
                                String message = resJSON.getString("message").toString();
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                            params.put("komsel_id", komsel_id);
                            params.put("jenis", jenis);
                            params.put("waktu", waktu);
                            params.put("alamat", alamat);
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });

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
    private void showHourDialog(){
        Calendar newCalendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(hourOfDay, minute);
                btTimePicker.setText(timeFormatter.format(newDate.getTimeInMillis()));
            }
        },newCalendar.get(Calendar.HOUR),newCalendar.get(Calendar.MINUTE),true);

       timePickerDialog.show();
    }
}
