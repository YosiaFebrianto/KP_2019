package edu.stts;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    TextView tvResultNama;
    String resultNama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initComponents();
        loadFragment(new LaporanActivity());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new LaporanActivity();
                break;
            case R.id.navigation_dashboard:
                fragment = new AddKegiatan();
                break;
            case R.id.navigation_notifications:
                fragment = new LaporanActivity();
                break;
        }
        return loadFragment(fragment);
    }
}
