package com.example.android_displyjancode.ui.home;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.android_displyjancode.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.ITFWriter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    // バーコードの各種設定
    String targetData = "";       //バーコードに変換する対象データ
    int    width      = 400;      //作成するバーコードの幅
    int    height     = 200;      //作成するバーコードの高さ

    // 選択肢
    private String[] spinnerItems = {"CODABAR", "CODE_128", "ITF"};
    public TextView SelectItem;
    public View root = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        Button button = (Button)getActivity().findViewById(R.id.AddButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageView imageView = root.findViewById(R.id.imageView);
                EditText editText = root.findViewById(R.id.inputNumber);
                TextView textView = root.findViewById(R.id.NumberDisplayView);
                int[] pix;

                try {
                    // 入力データをバーコードに変換する対象データに代入
                    targetData = editText.getText().toString();

                    // バーコード表示処理
                    pix = DisplayBarcode();
                    DisplayBitmapImage(pix);

                    // バーコード表示を表示状態にする。
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
                catch (Exception e)
                {
                    Log.e("ERROR","例外発生" + e);
                }
            }
        });

        Button ClearButton = (Button)getActivity().findViewById(R.id.ClearButton);
        ClearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageView imageView = root.findViewById(R.id.imageView);
                EditText editText = root.findViewById(R.id.inputNumber);
                TextView textView = root.findViewById(R.id.NumberDisplayView);

                try {
                    // 入力データを消去
                    editText.setText("");

                    // バーコード表示を非表示状態にする。
                    imageView.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                }
                catch (Exception e)
                {
                    Log.e("ERROR","例外発生" + e);
                }
            }
        });


    }

    public int[] DisplayBarcode()
    {
        int[] pixels = null;
        //Spinner spinner = root.findViewById(R.id.SelectFormat);
        try
        {
            BitMatrix bitMatrix = null;

            CodaBarWriter writer = new CodaBarWriter();
            bitMatrix = writer.encode(targetData, BarcodeFormat.CODABAR, width, height);

            // BitMatrixのデータが「true」の時は「黒」を設定し、「false」の時は「白」を設定する
            pixels = new int[width * height];
            for (int y = 0; y < height; y++)
            {
                int offset = y * width;
                for (int x = 0; x < width; x++)
                {
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
        }
        catch (WriterException e)
        {
            Log.e("ERROR","例外発生" + e);
        }
        return pixels;
    }

    public void DisplayBitmapImage(int[] pixels)
    {
        ImageView imageView = root.findViewById(R.id.imageView);
        TextView textView = root.findViewById(R.id.NumberDisplayView);

        try {
            // ビットマップ形式に変換する
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            // イメージビューに表示する
            imageView.setImageBitmap(bitmap);

            // 取得したテキストを TextView に張り付ける
            textView.setText(targetData);
        }
        catch (Exception e)
        {
            Log.e("ERROR","例外発生" + e);
        }
    }


}
