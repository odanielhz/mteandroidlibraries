package com.mte.libraries;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.mte.mteframework.Debug.MTEDebugLogger;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MTEDebugLogger.Log(true,"MTE-LOG", "Aplicacion Inicializada");
    }
}