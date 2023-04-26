package com.example.kupengfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreviewLoginActivity extends AppCompatActivity {
    Button tosign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_login);

        tosign = (Button) findViewById(R.id.btntosign);

        tosign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(PreviewLoginActivity.this, SignInActivity.class);
                startActivity(loginIntent);
            }
        });
}
}
