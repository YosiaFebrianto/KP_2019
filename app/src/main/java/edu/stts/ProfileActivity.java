package edu.stts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ProfileActivity extends Fragment {
    EditText passwordLama,passwordBaru,confpasswordBaru;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        passwordLama = view.findViewById(R.id.etPasswordLama);
        passwordBaru = view.findViewById(R.id.etPasswordBaru);
        confpasswordBaru = view.findViewById(R.id.etCPasswordBaru);
        return view;
    }
//    public void changePassword(View v){
//        if()
//    }
}
