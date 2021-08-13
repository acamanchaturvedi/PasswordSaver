package com.example.passwordsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class AddActivity extends AppCompatActivity {

    EditText name_input, username_input, password_input;
    Button add_button,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name_input = findViewById(R.id.name_input);
        username_input = findViewById(R.id.username_input);
        password_input = findViewById(R.id.password_input);
        add_button = findViewById(R.id.add_button);
        button2 = findViewById(R.id.button2);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addPassword(name_input.getText().toString().trim(),
                        username_input.getText().toString().trim(),
                        password_input.getText().toString().trim());

                //Refresh Activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result=geekPassword(10);
                while (!mine(result)) {
                    result=geekPassword(10);
                }
                password_input.setText(result);
            }
        });

    }

    static String geekPassword(int len) {
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "`~!@#$%^&*()_+-={}|[]\\:;<>?,./'\"";
        Random random_method = new Random();
        Set<Character> set = new LinkedHashSet<>();
        for (int i = 1; i <= 100; i++) {
            int ran=random_method.nextInt(4)+1;
            switch (ran) {
                case 1:set.add(Capital_chars.charAt(random_method.nextInt(Capital_chars.length())));
                    break;
                case 2:set.add(Small_chars.charAt(random_method.nextInt(Small_chars.length())));
                    break;
                case 3:set.add(numbers.charAt(random_method.nextInt(numbers.length())));
                    break;
                case 4:set.add(symbols.charAt(random_method.nextInt(symbols.length())));
                    break;
            }
        }
        StringBuilder ans= new StringBuilder();
        for(char i:set) {
            ans.append(i);
        }
        ans = new StringBuilder(ans.substring(0, len));

        return ans.toString();
    }

    public static boolean mine(String in) {
        Set<Character> set1=new HashSet<>();
        for(int i=0;i<in.length();i++) {
            set1.add(in.charAt(i));
        }
        StringBuilder input1= new StringBuilder();
        for(Character c:set1) {
            input1.append(c);
        }
        String input=new String(input1);
        int n = input.length();
        boolean hasLower = false, hasUpper = false, hasDigit = false, specialChar = false;
        Set<Character> set = new HashSet<>(Arrays.asList('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+'));
        for (char i : input.toCharArray()) {
            if (Character.isLowerCase(i))
                hasLower = true;
            if (Character.isUpperCase(i))
                hasUpper = true;
            if (Character.isDigit(i))
                hasDigit = true;
            if (set.contains(i))
                specialChar = true;
        }
        return hasDigit && hasLower && hasUpper && specialChar && (n >= 8);
    }

}