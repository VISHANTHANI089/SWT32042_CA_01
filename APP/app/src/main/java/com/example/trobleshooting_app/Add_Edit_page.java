package com.example.trobleshooting_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Add_Edit_page extends AppCompatActivity {

    EditText FaultName, Location, Type;
    RadioGroup Priority;

    Button Save,cancelBtn;
    int editFaultId = -1; // -1 means edit entry

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_page);

        FaultName = findViewById(R.id.faultname);
        Location = findViewById(R.id.location);
        Type = findViewById(R.id.type);
        Priority = findViewById(R.id.priority);
        Save = findViewById(R.id.save);
        cancelBtn = findViewById(R.id.cancel);

        // Check if we passed data to edit an existing fault
        if (getIntent().hasExtra("FAULT_ID")) {
            editFaultId = getIntent().getIntExtra("FAULT_ID", -1);
            FaultName.setText(getIntent().getStringExtra("TITLE"));
            Location.setText(getIntent().getStringExtra("LOCATION"));
            Type.setText(getIntent().getStringExtra("DEVICE"));
            String priorityStr = getIntent().getStringExtra("PRIORITY");

            if ("High".equals(priorityStr)) Priority.check(R.id.High);
            else if ("Medium".equals(priorityStr)) Priority.check(R.id.Medium);
            else if ("Low".equals(priorityStr)) Priority.check(R.id.Low);

            Save.setText("Update Fault");
        }


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear all text inputs
                FaultName.setText("");
                Location.setText("");
                Type.setText("");
                Priority.clearCheck();


                Toast.makeText(Add_Edit_page.this, "Form cleared", Toast.LENGTH_SHORT).show();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = FaultName.getText().toString().trim();
                String location = Location.getText().toString().trim();
                String type = Type.getText().toString().trim();

                int selectedRadioId = Priority.getCheckedRadioButtonId();
                String priority = "Medium"; // Default fallback

                if (selectedRadioId == R.id.High) priority = "High";
                else if (selectedRadioId == R.id.Medium) priority = "Medium";
                else if (selectedRadioId == R.id.Low) priority = "Low";

                if (title.isEmpty() || location.isEmpty() || type.isEmpty()) {
                    Toast.makeText(Add_Edit_page.this, "Please fill out all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editFaultId == -1) {
                    // Add New
                    int newId = FaultManager.faultList.size() + 1;
                    FaultManager.faultList.add(new FaultManager.FaultItem(newId, title, location, type, priority));
                    Toast.makeText(Add_Edit_page.this, "Fault Saved Successfully!", Toast.LENGTH_SHORT).show();
                } else {

                    // Update Existing
                    for (FaultManager.FaultItem item : FaultManager.faultList) {
                        if (item.id == editFaultId) {
                            item.title = title;
                            item.location = location;
                            item.device = type;
                            item.priority = priority;
                            break;
                        }
                    }
                    Toast.makeText(Add_Edit_page.this, "Fault Updated Successfully!", Toast.LENGTH_SHORT).show();
                }

                finish(); // Return to dashboard screen

            }
        });



    }
}