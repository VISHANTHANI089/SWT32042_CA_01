package com.example.trobleshooting_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

     TextView TotalFaults , HighCounter, MediumLowCounter;
     LinearLayout AddFault, ViewFault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        // Bind views BY  IDs
        TotalFaults = findViewById(R.id.TotalFaultCount);
        HighCounter = findViewById(R.id.HighPriorityCount);
        MediumLowCounter = findViewById(R.id.MediumLowCount);
        AddFault = findViewById(R.id.AddFault);
        ViewFault = findViewById(R.id.ViewFaults);


        //click add fault it redirect to add/edit screen

        AddFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Add_Edit_page.class);
                startActivity(intent);

            }
        });

//click view fault, its redirect to manage fault screen
        ViewFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Manage_Fault.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDashboardMetrics();
    }

    // Calculates and updates active fault totals from memory
    private void updateDashboardMetrics() {
        int totalTickets = FaultManager.faultList.size();
        int highPriorityCount = 0;

        for (FaultManager.FaultItem item : FaultManager.faultList) {
            if ("High".equals(item.priority)) {
                highPriorityCount++;
            }
        }
        int mediumLowCount = totalTickets - highPriorityCount;

        TotalFaults.setText(String.valueOf(totalTickets));
        HighCounter.setText(highPriorityCount + " High Priority");
        MediumLowCounter.setText(mediumLowCount + " Medium/Low");
    }
}