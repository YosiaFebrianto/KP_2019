package edu.stts;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class DashboardActivity extends Fragment {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateStart,tvDateEnd, profileName;

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

        SharedPreferences sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        profileName.setText(sp.getString("nama", null));
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

}
