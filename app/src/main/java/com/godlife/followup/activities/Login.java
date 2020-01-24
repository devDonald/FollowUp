package com.godlife.followup.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.godlife.followup.R;
import com.godlife.followup.validators.EmailValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.valdesekamdem.library.mdtoast.MDToast;

public class Login extends AppCompatActivity {

    private EditText uemail;
    private EditText upassword;
    private Button signin;
    private TextView resetPassword;
    private FirebaseAuth auth;
    private Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=getApplicationContext();
        if(isNetworkAvailable()){
            // do network operation here
        }else{
            MDToast.makeText(getApplicationContext(),
                    "No network availabile, please try again!",
                    MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

        }

        auth = FirebaseAuth.getInstance();


        //check if user is currently logged in
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);

        uemail= findViewById(R.id.etLoginEmail);
        upassword= findViewById(R.id.etLoginPassword);
        signin = findViewById(R.id.btnLogin);
        resetPassword = findViewById(R.id.tvRestPassword);


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ResetPassword.class);
                startActivity(intent);
            }
        });



        //redirects user to main activity
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(Login.this);
                mDialog.setMessage("Signing in......");
                String email = uemail.getText().toString().trim();
                String password = upassword.getText().toString().trim();

                if(email.isEmpty() || !EmailValidator.getInstance().validate(email)){
                    MDToast.makeText(getApplicationContext(),"Invalid email! pls try again",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (TextUtils.isEmpty(password)){
                    MDToast.makeText(getApplicationContext(),"password Incorrect! pls try again",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else {

                    mDialog.show();
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.

                                    mDialog.dismiss();

                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        MDToast.makeText(Login.this,
                                                "incorrect email/password", MDToast.LENGTH_LONG,
                                                MDToast.TYPE_ERROR).show();
                                    } else {
                                        MDToast.makeText(getApplicationContext(),"Sign in successful",
                                                MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();

                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

    }

    //check network availability
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}
