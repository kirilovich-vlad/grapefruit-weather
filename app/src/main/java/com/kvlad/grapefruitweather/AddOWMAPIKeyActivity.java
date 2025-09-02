package com.kvlad.grapefruitweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Activity that asks user to input their OWM API key.
public class AddOWMAPIKeyActivity extends AppCompatActivity {
    EditText edtKey;
    TextView txtOWMURL;
    Button btnSubmit;
    SharedPreferences sharedPref;

    private class submitKey implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new Thread(() -> {
                try {
                    // If API key is valid, save it to shared preferences
                    if (NetworkHelper.checkOWMAPIKey(edtKey.getText().toString())) {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("OWM_API_KEY", edtKey.getText().toString());
                        editor.apply();
                        // Following intent data tells InitialLoadingActivity to progress to
                        // MainActivity (with RecyclerView of saved locations)
                        Intent resultIntent = new Intent().putExtra("task", "SET_API_KEY");
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(AddOWMAPIKeyActivity.this, "Wrong API Key", Toast.LENGTH_SHORT).show();
                        });
                    }
                } catch (Exception e) {
                    runOnUiThread(() -> {
                        Toast.makeText(AddOWMAPIKeyActivity.this, "Check your Internet connection!", Toast.LENGTH_SHORT).show();
                    });
                }
            }).start();
        }
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_owm_api_key);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPref = this.getSharedPreferences("preferences", MODE_PRIVATE);
        txtOWMURL = this.findViewById(R.id.txtOWMURL);
        txtOWMURL.setClickable(true);
        txtOWMURL.setMovementMethod(LinkMovementMethod.getInstance());
        edtKey = this.findViewById(R.id.edtKey);
        btnSubmit = this.findViewById(R.id.btnSubmit);

        View.OnClickListener submitKey = new submitKey();
        btnSubmit.setOnClickListener(submitKey);
    }
}