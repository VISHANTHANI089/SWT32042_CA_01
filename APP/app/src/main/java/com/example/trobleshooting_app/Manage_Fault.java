package com.example.trobleshooting_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Manage_Fault extends AppCompatActivity {


    private LinearLayout faultsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_fault);

        faultsContainer = findViewById(R.id.faultsContainer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFaults();
    }

    private void loadFaults() {
        // Clear previous views to avoid duplicate lists when refreshing
        faultsContainer.removeAllViews();

        if (FaultManager.faultList.isEmpty()) {
            TextView Empty = new TextView(this);
            Empty.setText("No unresolved Faults found.");
            Empty.setTextColor(0xFF4D0404);
            Empty.setTextSize(16);
            faultsContainer.addView(Empty);
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(this);

        // Loop through each item in memory and create a card view
        for (int i = 0; i < FaultManager.faultList.size(); i++) {
            FaultManager.FaultItem fault = FaultManager.faultList.get(i);
            final int index = i;

            // Inflate our new item_fault.xml card template
            View itemView = inflater.inflate(R.layout.item_fault, faultsContainer, false);

            TextView Faulttitle = itemView.findViewById(R.id.ItemTitle);
            TextView Details = itemView.findViewById(R.id.ItemDetails);
            TextView Priority = itemView.findViewById(R.id.ItemPriority);
            Button Edit = itemView.findViewById(R.id.EditFault);
            Button Delete = itemView.findViewById(R.id.DeleteFault);

            // Set data to views
            Faulttitle.setText(fault.title);
            Details.setText("Location: " + fault.location + " | Device: " + fault.device);
            Priority.setText("Priority: " + fault.priority);

            // Edit button opens Add_Edit_page with already filled details
            Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Manage_Fault.this, Add_Edit_page.class);
                    intent.putExtra("FAULT_ID", fault.id);
                    intent.putExtra("TITLE", fault.title);
                    intent.putExtra("LOCATION", fault.location);
                    intent.putExtra("DEVICE", fault.device);
                    intent.putExtra("PRIORITY", fault.priority);
                    startActivity(intent);

                }
            });


            // Delete button removes it from memory and refreshes screen
            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FaultManager.faultList.remove(index);
                    loadFaults();
                    Toast.makeText(Manage_Fault.this, "Fault resolved and removed", Toast.LENGTH_SHORT).show();
                }
            });

            // Add the card to your main scroll container
            faultsContainer.addView(itemView);
        }
    }
}