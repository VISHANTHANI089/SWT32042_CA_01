package com.example.trobleshooting_app;

import java.util.ArrayList;
import java.util.List;

public class FaultManager {
    // Shared list to hold faults across all activities during runtime
    public static List<FaultItem> faultList = new ArrayList<>();

    public static class FaultItem {
        public int id;
        public String title, location, device, priority;

        public FaultItem(int id, String title, String location, String device, String priority) {
            this.id = id;
            this.title = title;
            this.location = location;
            this.device = device;
            this.priority = priority;
        }
    }
}