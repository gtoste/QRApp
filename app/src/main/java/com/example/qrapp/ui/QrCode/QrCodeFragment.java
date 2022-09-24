package com.example.qrapp.ui.QrCode;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrapp.Image;
import com.example.qrapp.R;
import com.example.qrapp.databinding.FragmentQrcodeBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrCodeFragment extends Fragment {

    private FragmentQrcodeBinding binding;

    private String theme;
    private Integer imgWidth;
    private String imgSrc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QrCodeViewModel qrCodeViewModel =
                new ViewModelProvider(this).get(QrCodeViewModel.class);

        binding = FragmentQrcodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences settings = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        this.theme = settings.getString("theme", null);
        this.imgWidth = settings.getInt("img_width", 350);
        this.imgSrc = settings.getString("img_src", null);

        binding.getRoot().setBackgroundColor(this.theme.equals("light") ? Color.WHITE : Color.DKGRAY);
        Image image = new Image(imgSrc);


        binding.generateButton.setOnClickListener((view -> {
            String sText = binding.etInput.getText().toString().trim();

            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE, imgWidth, imgWidth);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);
                binding.qrcodeoutputImageview.setImageBitmap(bitmap);

                image.saveToInternalStorage(bitmap);

                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(binding.etInput.getWindowToken(),0);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}