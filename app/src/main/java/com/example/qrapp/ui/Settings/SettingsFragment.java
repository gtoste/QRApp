package com.example.qrapp.ui.Settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrapp.R;
import com.example.qrapp.databinding.FragmentQrcodeBinding;
import com.example.qrapp.databinding.FragmentSettingsBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    String theme;
    Integer imgWidth;
    String imgSrc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        SharedPreferences settings = getActivity().getSharedPreferences("settings", MODE_PRIVATE);

        this.theme = settings.getString("theme", null);
        this.imgWidth = settings.getInt("img_width", 350);
        this.imgSrc = settings.getString("img_src", null);


        root.setBackgroundColor(this.theme.equals("light") ? Color.WHITE : Color.DKGRAY);
        binding.etSettings.setText(imgSrc);
        binding.size.setText(String.valueOf(imgWidth));
        binding.themeBtn.setText(theme);

        binding.themeBtn.setOnClickListener((view -> {
            this.theme = this.theme.equals("light") ? "dark" : "light";
            binding.themeBtn.setText(theme);
            binding.getRoot().setBackgroundColor(this.theme.equals("light") ? Color.WHITE : Color.DKGRAY);
        }));


        binding.save.setOnClickListener((view -> {
            String src = binding.etSettings.getText().toString().trim();
            String str_width = binding.size.getText().toString().trim();
            File img_folder = new File(src);
            if(!str_width.equals(""))
            {
                this.imgWidth = Integer.parseInt(str_width);
            }

            SharedPreferences.Editor editor = settings.edit();
            editor.putString("theme", this.theme);
            editor.putInt("img_width", this.imgWidth);
            editor.putString("img_src", img_folder.getPath());
            editor.apply();

            Toast.makeText(getContext(), "Saved!", Toast.LENGTH_LONG).show();
        }));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}