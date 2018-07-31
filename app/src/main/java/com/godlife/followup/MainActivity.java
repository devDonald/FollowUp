package com.godlife.followup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.godlife.followup.birthdays.Birthdays;
import com.godlife.followup.church_members.GetGender;
import com.godlife.followup.church_members.GetLocation;
import com.godlife.followup.church_members.Members;
import com.godlife.followup.first_timers.FirstTimers;
import com.godlife.followup.reports.Reports;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private CardView churchMembers, firstTimers, reports, location, birthdays,
            forum, gender, exit;
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

        firstTimers =findViewById(R.id.card2);
        firstTimers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent two = new Intent(MainActivity.this, FirstTimers.class);
                startActivity(two);
            }
        });

        reports = findViewById(R.id.card3);
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent three = new Intent(MainActivity.this, Reports.class);
                startActivity(three);
            }
        });
        location = findViewById(R.id.card4);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent four = new Intent(MainActivity.this, GetLocation.class);
                startActivity(four);
            }
        });

        birthdays = findViewById(R.id.card5);
        birthdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent five = new Intent(MainActivity.this, Birthdays.class);
                startActivity(five);
            }
        });

        gender = findViewById(R.id.card7);
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seven = new Intent(MainActivity.this, GetGender.class);
                startActivity(seven);
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
