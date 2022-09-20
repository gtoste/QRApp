package com.example.qrapp.ui.BarCodeUI;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrapp.databinding.FragmentBarcodeBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarCodeFragment extends Fragment {

    private FragmentBarcodeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BarCodeViewModel galleryViewModel =
                new ViewModelProvider(this).get(BarCodeViewModel.class);

        binding = FragmentBarcodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.generateButton.setOnClickListener((view -> {
            String sText = binding.etInput.getText().toString().trim();

            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(sText, BarcodeFormat.CODABAR, 400, 170,null);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);
                binding.barcodeoutputImageview.setImageBitmap(bitmap);
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