package com.example.passwordsaver;

import static androidx.core.content.ContextCompat.getSystemService;
import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList password_id, password_name, password_username, password_password;

    CustomAdapter(Activity activity, Context context, ArrayList password_id, ArrayList password_name, ArrayList password_username,
                  ArrayList password_password){
        this.activity = activity;
        this.context = context;
        this.password_id = password_id;
        this.password_name = password_name;
        this.password_username = password_username;
        this.password_password = password_password;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.password_id_text.setText(String.valueOf(password_id.get(holder.getAdapterPosition())));
        holder.password_name_text.setText(String.valueOf(password_name.get(holder.getAdapterPosition())));
        holder.password_username_text.setText(String.valueOf(password_username.get(holder.getAdapterPosition())));
        holder.password_password_text.setText(String.valueOf(password_password.get(holder.getAdapterPosition())));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(password_id.get(holder.getAdapterPosition())));
                intent.putExtra("name", String.valueOf(password_name.get(holder.getAdapterPosition())));
                intent.putExtra("username", String.valueOf(password_username.get(holder.getAdapterPosition())));
                intent.putExtra("password", String.valueOf(password_password.get(holder.getAdapterPosition())));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.copy_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", holder.password_username_text.getText());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(context, "Username Copied", Toast.LENGTH_SHORT).show();
            }
        });

        holder.copy_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", holder.password_password_text.getText());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(context, "Password Copied", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return password_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView password_id_text, password_name_text, password_username_text, password_password_text;
        LinearLayout mainLayout;
        ImageButton copy_one,copy_two;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            password_id_text = itemView.findViewById(R.id.idTextView);
            password_name_text = itemView.findViewById(R.id.nameTextView);
            password_username_text = itemView.findViewById(R.id.usernameTextView);
            password_password_text = itemView.findViewById(R.id.passwordTextView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            copy_one=itemView.findViewById(R.id.imageButton1);
            copy_two=itemView.findViewById(R.id.imageButton2);
        }

    }

}
