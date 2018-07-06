package com.godlife.followup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.godlife.followup.church_members.Members;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private CardView churchMembers, two, three, four, five, exit;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        churchMembers = findViewById(R.id.card1);
        churchMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent one = new Intent(MainActivity.this, Members.class);
                startActivity(one);
            }
        });

        exit= findViewById(R.id.card8);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                Intent exit = new Intent(MainActivity.this,Login.class);
                startActivity(exit);
                finish();
            }
        });

    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

    }

}
