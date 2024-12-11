package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private TextInputEditText email_text,password_text;
    private Button login_button;
    private String email,password;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView nav_register;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent it = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(it);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth=FirebaseAuth.getInstance();
        email_text = findViewById(R.id.email);
        login_button=findViewById(R.id.registerbut);
        password_text = findViewById(R.id.password);
        progressBar=findViewById(R.id.progress1);
        nav_register=findViewById(R.id.textView1);
        login_button.setOnClickListener(v -> login());
        nav_register.setOnClickListener(v -> loginpage());
    }
    private void login(){
        progressBar.setVisibility(View.VISIBLE);
        email=String.valueOf(email_text.getText());
        password=String.valueOf(password_text.getText());
        if(!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Registration Succesful.", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(it);
                        finish();
                    } else {
                        Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{Toast.makeText(Register.this,"Email or Password field can't be empty",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }
    private void loginpage(){
        Intent it=new Intent(getApplicationContext(), Login.class);
        startActivity(it);
        finish();
    }
}