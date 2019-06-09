package edu.stts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    TextView tvResultNama;
    String resultNama;
    Boolean dash,member,addlaporan,laporan,profile,memberDetail,laporanDetail;
    AddMemberActivity addmember;
    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initComponents();
        dash=true;member=false;addlaporan=false;laporan=false;profile=false;memberDetail=false;laporanDetail=false;
        loadFragment(new DashboardActivity());

        bottomNavigationView = findViewById(R.id.bn_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setItemHorizontalTranslationEnabled(false);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        // untuk mendapatkan data dari activity sebelumnya, yaitu activity login.
        //Bundle extras = getIntent().getExtras();
        //if (extras != null) resultNama = extras.getString("result_nama");
        //tvResultNama.setText(resultNama);
    }
    private void initComponents(){
        //tvResultNama = (TextView) findViewById(R.id.test);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(member==true||addlaporan==true||laporan==true||profile==true){
            member=false;addlaporan=false;laporan=false;profile=false;
            dash=true;
            finish();
            startActivity(getIntent());
        }else if(memberDetail==true){
            loadFragment(new MemberActivity());
            memberDetail=false;
            member=true;
        }else if(laporanDetail==true){
            loadFragment(new LaporanActivity());
            laporanDetail=false;
            laporan=true;
        }
        else if(dash==true){
            member=false;addlaporan=false;laporan=false;profile=false;
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                fragment = new DashboardActivity();
                dash=true;
                break;
            case R.id.navigation_member:
                fragment = new MemberActivity();
                member=true;
                dash=false;
                break;
            case R.id.navigation_addlaporan:
                fragment = new AddKegiatan();
                addlaporan=true;
                dash=false;
                break;
            case R.id.navigation_laporan:
                fragment = new LaporanActivity();
                laporan=true;
                dash=false;
                break;
            case R.id.navigation_profile:
                fragment = new ProfileActivity();
                profile=true;
                dash=false;
                break;
        }
        return loadFragment(fragment);
    }

    public void showAddMember() {
        memberDetail = true;
        member=false;addlaporan=false;laporan=false;profile=false;
    }
    public void showAddLaporan() {
        laporanDetail = true;
        member=false;addlaporan=false;laporan=false;profile=false;
    }
}
