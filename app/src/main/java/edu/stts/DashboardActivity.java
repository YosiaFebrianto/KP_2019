package edu.stts;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class DashboardActivity extends Fragment implements  AdapterView.OnItemSelectedListener {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateStart,tvDateEnd, profileName, tvJabatan, tvTotalMember, tvDataUltah;
    private ListView lvBirthday;
    private DomainConfig domainConfig;
    Spinner spinner;
    JSONArray komsel;
    RequestQueue rq;
    SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        tvDateStart = (TextView) view.findViewById(R.id.tv_startdate);
        tvDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog1();
            }
        });
        tvDateEnd = (TextView) view.findViewById(R.id.tv_enddate);
        tvDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog2();
            }
        });
        profileName = view.findViewById(R.id.tv_profile_nama);
        drawChart(view);
        tvTotalMember = view.findViewById(R.id.tv_totalmember);
        sp = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String nama = sp.getString("nama", "Dummy");
        profileName.setText(nama);
        tvJabatan = view.findViewById(R.id.tv_profile_jabatan);
        switch (sp.getString("jabatan", "0")){
            case "1" :
                tvJabatan.setText("Admin");
                break;
            case "2" :
                tvJabatan.setText("Penilik");
                break;
            case "3" :
                tvJabatan.setText("Ketua");
                break;
            case "4" :
                tvJabatan.setText("Anggota");
                break;
            default:
                break;
        }

        spinner = view.findViewById(R.id.spinnerListKomsel);
        spinner.setOnItemSelectedListener(this);
        rq = Volley.newRequestQueue(this.getContext());

        lvBirthday = view.findViewById(R.id.listViewBirthday);
        tvDataUltah = view.findViewById(R.id.tv_dataultah);
        domainConfig = new DomainConfig();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, domainConfig.getDomain_local() + "/ketua_api/dashboard", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resJSON = new JSONObject(response);
                    komsel = new JSONArray(resJSON.getString("komsel"));
                    JSONArray birthday = new JSONArray(resJSON.getString("birthday"));

                    tvTotalMember.setText(resJSON.getString("count"));
                    ArrayList<String> array = new ArrayList<>();
                    ArrayList<String> array_birthday = new ArrayList<>();
                    array.add("Semua Komsel");
                    for (int i = 0 ; i < komsel.length() ; i++){
                        JSONObject temp = komsel.getJSONObject(i);
                        array.add(temp.getString("komsel_nama"));
                    }

                    ArrayAdapter<String> spinAdapter= new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,array);
                    spinner.setAdapter(spinAdapter);

                    if(birthday.length() > 0){
                        for(int i = 0 ; i < birthday.length() ; i++){
                            JSONObject temp = birthday.getJSONObject(i);
                            array_birthday.add(temp.getString("user_nama") + ", " + temp.getString("tanggal_lahir"));
                        }
                        ArrayAdapter<String> listAdapater = new ArrayAdapter<>(getContext(), R.layout.list_birthday, R.id.textViewBirthdayDetail, array_birthday);
                        lvBirthday.setAdapter(listAdapater);
                        lvBirthday.setVisibility(View.VISIBLE);
                        tvDataUltah.setVisibility(View.GONE);
                    }else{
                        lvBirthday.setVisibility(View.GONE);
                        tvDataUltah.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", sp.getString("username", ""));
                return params;
            }
        };
        rq.add(stringRequest);


        return view;
    }

    private void showDateDialog1(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvDateStart.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showDateDialog2(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvDateEnd.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void drawChart(View view) {
        ArrayList<String> barLabels;
        BarChart chart = view.findViewById(R.id.barChart);
        barLabels = new ArrayList<String>();

        List<BarEntry> dataPemasukan = new ArrayList<BarEntry>();
        dataPemasukan.add(new BarEntry(2016, 1500000));
        dataPemasukan.add(new BarEntry(2017, 1430000));
        dataPemasukan.add(new BarEntry(2018, 1750000));
        dataPemasukan.add(new BarEntry(2019, 1390000));
        chart.getDescription().setEnabled(false);
        BarDataSet dataSet1 = new BarDataSet(dataPemasukan, "Pemasukan");
        dataSet1.setColor(ColorTemplate.JOYFUL_COLORS[0]);

        // Membuat Bar data yang akan di set ke Chart
        BarData barData = new BarData(dataSet1);
        chart.getXAxis().setValueFormatter(
                new IndexAxisValueFormatter(barLabels));

        chart.setData(barData);

        chart.animateY(3000);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, domainConfig.getDomain_local() + "/ketua_api/dashboard", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("sac", response);
                try {
                    JSONObject resJSON = new JSONObject(response);
                    komsel = new JSONArray(resJSON.getString("komsel"));
                    tvTotalMember.setText(resJSON.getString("count"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", sp.getString("username", ""));

                if(position > 0){
                    try {
                        JSONObject data = komsel.getJSONObject(position - 1);
                        params.put("komsel_id", data.getString("komsel_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return params;
            }
        };
        rq.add(stringRequest);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
