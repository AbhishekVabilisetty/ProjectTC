package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {
    private TextInputEditText email_text,password_text;
    private Button login_button;
    private String email,password;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView nav_register;
    private ImageButton google_icon;
    private static final int REQ_ONE_TAP = 2;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
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
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth=FirebaseAuth.getInstance();
        email_text = findViewById(R.id.email);
        password_text = findViewById(R.id.password);
        progressBar=findViewById(R.id.progress);
        nav_register=findViewById(R.id.textView);
        login_button=findViewById(R.id.loginbutton);
        google_icon=findViewById(R.id.google);
        login_button.setOnClickListener(v -> login());
        nav_register.setOnClickListener(v -> registerpage());
        google_icon.setOnClickListener(v ->googleauth());

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        gsc= GoogleSignIn.getClient(Login.this,gso);

    }
    private void login(){
        progressBar.setVisibility(View.VISIBLE);
        email=String.valueOf(email_text.getText());
        password=String.valueOf(password_text.getText());
        if(!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(it);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else{Toast.makeText(Login.this,"Email or Password field can't be empty",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }
    private void googleauth()  {
        Intent signInIntent=gsc.getSignInIntent();
        startActivityForResult(signInIntent,REQ_ONE_TAP);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuth(account.getIdToken());
                } catch (ApiException e) {
                    // ...
                }
                break;
        }
    }

    private void firebaseAuth(String idToken){
        AuthCredential credential=GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent it =new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(it);
                    finish();
                    Toast.makeText(Login.this,"Login Succesfull.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void registerpage(){
        Intent it=new Intent(getApplicationContext(), Register.class);
        startActivity(it);
        finish();
    }

}