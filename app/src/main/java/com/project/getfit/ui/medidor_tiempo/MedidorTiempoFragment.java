package com.project.getfit.ui.medidor_tiempo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.getfit.R;

public class MedidorTiempoFragment extends Fragment {

    private MedidorTiempoViewModel medidorTiempoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        medidorTiempoViewModel =
                new ViewModelProvider(this).get(MedidorTiempoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_medidor_tiempo, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        medidorTiempoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}