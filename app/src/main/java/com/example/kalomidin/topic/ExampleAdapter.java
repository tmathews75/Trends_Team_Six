package com.example.kalomidin.topic;

import android.widget.Filterable;
import android.widget.Filter;
import java.util.ArrayList;

public class ExampleAdapter{
    int age;
    String color;

    int barking() {
        return age;
    }

    int hungry() {
        return 0;
    }

    void sleeping() {
        return;
    }
}