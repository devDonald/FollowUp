package com.godlife.followup.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.godlife.followup.R;
import com.godlife.followup.first_timers.FirstTimers;
import com.godlife.followup.first_timers.TimersHome;
import com.godlife.followup.reports.ReportModel;
import com.godlife.followup.reports.Reports;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button service, article,lyrics,notices,love,praise,members,meetings,report, firstTimers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        service = findViewById(R.id.bt_service_text);
        article = findViewById(R.id.bt_articles_text);
        lyrics = findViewById(R.id.bt_lyrics);
        meetings = findViewById(R.id.bt_upcoming_text);
        love = findViewById(R.id.bt_lovenote_text);
        praise = findViewById(R.id.bt_praise_report_text);
        notices=findViewById(R.id.bt_add_notices);
        members = findViewById(R.id.bt_members);
        report = findViewById(R.id.bt_report);
        firstTimers = findViewById(R.id.bt_first_timers);

        firstTimers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent first = new Intent(MainActivity.this, TimersHome.class);
                startActivity(first);
            }
        });


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewReport = new Intent(MainActivity.this, Reports.class);
                startActivity(viewReport);
            }
        });

        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewMembers = new Intent(MainActivity.this, Members.class);
                startActivity(viewMembers);
            }
        });

        notices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addnote = new Intent(MainActivity.this, AddNotices.class);
                startActivity(addnote);
            }
        });


        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addJournal = new Intent(MainActivity.this, ServiceSummary.class);
                startActivity(addJournal);

            }
        });
        article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addArticle = new Intent(MainActivity.this, AddArticle.class);
                startActivity(addArticle);

            }
        });
        lyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addLyrics = new Intent(MainActivity.this, AddLyrics.class);
                startActivity(addLyrics);

            }
        });
        meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addNotices = new Intent(MainActivity.this, AddEvents.class);
                startActivity(addNotices);

            }
        });


        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addlove = new Intent(MainActivity.this, AddLove.class);
                startActivity(addlove);
            }
        });

        praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addlove = new Intent(MainActivity.this, AddPraiseReport.class);
                startActivity(addlove);
            }
        });
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        Intent toLogin = new Intent(MainActivity.this, Login.class);
        toLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        toLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(toLogin);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
          signOut();
        }

        return super.onOptionsItemSelected(item);
    }
}
