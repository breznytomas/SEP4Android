package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sep4android.R;
import com.example.sep4android.Repository.MessageRepository;

public class MainActivity extends AppCompatActivity {
    private MessageRepository messageRepository;
    private TextView textView;
    private String printIt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.HelloWorldText);
        messageRepository = MessageRepository.getInstance();

        messageRepository.getMessage(7);


    }
}