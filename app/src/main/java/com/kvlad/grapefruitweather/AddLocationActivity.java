package com.kvlad.grapefruitweather;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class AddLocationActivity extends AppCompatActivity {
    private EditText edtLocationName;
    private EditText edtLocationAddress;
    private DatabaseHelper db;

    // If we recreate a previously destroyed instance, restore the values.
    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        edtLocationName.setText(savedInstanceState.getString("LOCATION_NAME"));
        edtLocationAddress.setText(savedInstanceState.getString("LOCATION_ADDRESS"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.coordLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtLocationName = findViewById(R.id.edtLocationName);
        edtLocationAddress = findViewById(R.id.edtLocationAddress);
        db = new DatabaseHelper(this);

        MaterialToolbar mToolbar = findViewById(R.id.mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int result;
                String name = edtLocationName.getText().toString();
                String address = edtLocationAddress.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(AddLocationActivity.this, "Please enter location name!", Toast.LENGTH_SHORT).show();
                } else if (address.isEmpty()) {
                    Toast.makeText(AddLocationActivity.this, "Please enter location address!", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.addLocation(name, address) != -1) {
                        result = RESULT_OK;
                    } else result = RESULT_CANCELED;
                    setResult(result);
                    finish();
                    return result == RESULT_OK;
                }
                return false;
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        // Save the current state of the activity.
        outState.putString("LOCATION_NAME", edtLocationName.getText().toString());
        outState.putString("LOCATION_ADDRESS", edtLocationAddress.getText().toString());

        // Save the view hierarchy state.
        super.onSaveInstanceState(outState, outPersistentState);
    }
}