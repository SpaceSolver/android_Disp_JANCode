package com.example.android_displyjancode.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_displyjancode.MainActivity;
import com.example.android_displyjancode.R;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    // バーコードの各種設定
    String targetData = "";       //バーコードに変換する対象データ
    int    width      = 400;      //作成するバーコードの幅
    int    height     = 200;      //作成するバーコードの高さ

    public View root = null;
    private Object MatrixToImageWriter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // ViewModel生成
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // Acitvityを操作できるようにする
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
                    MainActivity.DisplayBarcode(targetData,width,height);

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

    /*
    public void DisplayBarcode()
    {
        int[] pixels = null;
        try
        {
            BitMatrix bitData  = null;

            EAN13Writer writer = new EAN13Writer();

            //EAN13Writer#encode()には以下の情報を渡す
            // (1)エンコード対象の文字列、バーコードシンボルに埋め込みたい情報
            // (2)出力するバーコードの書式
            // (3)イメージの幅
            // (4)イメージの高さ
            bitData  = writer.encode(targetData, BarcodeFormat.EAN_13, width, height);
            //エンコードで得られたイメージを画像ファイルに出力する
            FileOutputStream output = new FileOutputStream("mycode.png");
            MatrixToImageWriter.writeToStream(bitData, "png", output);

        }
        catch (WriterException | FileNotFoundException e)
        {
            Log.e("ERROR","例外発生" + e);
        }
    }
     */
}
