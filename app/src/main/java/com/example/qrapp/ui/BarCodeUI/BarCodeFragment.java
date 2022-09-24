package com.example.qrapp.ui.BarCodeUI;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrapp.Image;
import com.example.qrapp.databinding.FragmentBarcodeBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class BarCodeFragment extends Fragment {

    private FragmentBarcodeBinding binding;

    private String theme;
    private Integer imgWidth;
    private String imgSrc;
    private Bitmap bitmap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BarCodeViewModel galleryViewModel =
                new ViewModelProvider(this).get(BarCodeViewModel.class);

        binding = FragmentBarcodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences settings = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        this.theme = settings.getString("theme", null);
        this.imgWidth = settings.getInt("img_width", 350);
        this.imgSrc = settings.getString("img_src", null);
        binding.getRoot().setBackgroundColor(this.theme.equals("light") ? Color.WHITE : Color.DKGRAY);

        Image img = new Image(this.imgSrc);


        binding.generateButton.setOnClickListener((view -> {
            String sText = binding.etInput.getText().toString().trim();

            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(sText, BarcodeFormat.CODABAR, this.imgWidth, this.imgWidth / 2,null);
                BarcodeEncoder encoder = new BarcodeEncoder();
                bitmap = encoder.createBitmap(matrix);
                binding.barcodeoutputImageview.setImageBitmap(bitmap);
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(binding.etInput.getWindowToken(),0);

                binding.barcodeoutputImageview.setOnClickListener((view2)->{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Saving image");
                    builder.setMessage("Are you sure, saving this image at location " + imgSrc);
                    builder.setPositiveButton("Yes", (dialog, id) -> {
                        img.saveToInternalStorage(bitmap);
                        Toast.makeText(getContext(),"Saved", Toast.LENGTH_LONG).show();
                    });

                    builder.setNegativeButton("No", ((dialogInterface, id) -> {

                    }));
                    builder.show();
                });
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

    private void saveToInternalStorage(Bitmap bitmapImage){
        File directory = new File(this.imgSrc);

        if (! directory.exists()){
            directory.mkdir();
        }

        UUID uuid = UUID.randomUUID();
        File mypath = new File(directory, uuid.toString() + ".png");

        Log.d("hm", mypath.getAbsolutePath());
        Log.d("hm", directory.getAbsolutePath());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}