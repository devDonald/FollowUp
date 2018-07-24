package com.godlife.followup.reports;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.godlife.followup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateReport extends AppCompatActivity {
    private static final String TAG ="EditJournal" ;
    private EditText mReportContent, mTitle;
    private DatabaseReference mReportsDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String uID;
    private String position;
    private TextView mDate, mReporter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_report);
        mReportContent = (EditText) findViewById(R.id.edit_content);
        mReporter = (TextView) findViewById(R.id.edit_reporter);
        mDate = (TextView) findViewById(R.id.edit_date);
        mTitle = (EditText) findViewById(R.id.edit_title);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        uID = mUser.getUid();
        mReportsDatabase = FirebaseDatabase.getInstance().getReference("Reports");

        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            position = extras.getString("position");
            if (position != null) {
                DatabaseReference userRef = mReportsDatabase.child(position);

                Log.d("userref", "" + userRef);
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Log.d("ds",""+ds);
                        mTitle.setText(dataSnapshot.child("title").getValue(String.class));
                        mReportContent.setText(dataSnapshot.child("content").getValue(String.class));
                        mReporter.setText(dataSnapshot.child("reporter").getValue(String.class));
                        mDate.setText(dataSnapshot.child("date").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "loadReports:onCancelled", databaseError.toException());

                    }
                };
                userRef.addListenerForSingleValueEvent(valueEventListener);


            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_report_done) {
            updateReport();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateReport() {
        String updateTitle = mTitle.getText().toString().trim();
        String updateContent = mReportContent.getText().toString().trim();
        if (!TextUtils.isEmpty(updateContent)){

            mReportsDatabase.child(position).child("title").setValue(updateTitle);
            mReportsDatabase.child(position).child("content").setValue(updateContent);

            Intent update = new Intent(UpdateReport.this, Reports.class);
            startActivity(update);
            finish();
        }
    }

}
