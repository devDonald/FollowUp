package com.godlife.followup.reports;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.godlife.followup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddReports extends AppCompatActivity {
    private DatabaseReference mReportsDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private EditText mReportContent;
    private EditText mReportTitle, mReporter;
    private Button mSelectDate;
    private String uID;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reports);


        mReportContent = (EditText) findViewById(R.id.report_content);
        mSelectDate = (Button) findViewById(R.id.pick_date);
        mReporter =  (EditText) findViewById(R.id.et_reporter);
        mReportTitle = (EditText) findViewById(R.id.report_title);

        mReportsDatabase = FirebaseDatabase.getInstance().getReference("Reports");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        uID = mCurrentUser.getUid();
        mSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddReports.this,
                        AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        mSelectDate.setText(dayOfMonth+ "-"+ (monthOfYear + 1)+ "-"+ year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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
            submitReport();
        }

        return super.onOptionsItemSelected(item);
    }
    private void submitReport(){
        String reportContent = mReportContent.getText().toString().trim();
        String reportDate = mSelectDate.getText().toString().trim();
        String reportTitle = mReportTitle.getText().toString().trim();
        String reporter = mReporter.getText().toString().trim();

        if (TextUtils.isEmpty(reportTitle)){
            mReportTitle.setError("Empty field");
        } else if (TextUtils.isEmpty(reportContent)){
            mReportContent.setError("Empty field");
        } else if (TextUtils.isEmpty(reportDate)|reportDate.equalsIgnoreCase("Select Date")){
            mSelectDate.setError("Empty field");

        } else if (TextUtils.isEmpty(reporter)){
            mReporter.setError("Empty field");

        } else{

            String id = mReportsDatabase.push().getKey();
            ReportModel journals = new ReportModel(uID, reportTitle,reportContent,reporter,reportDate);

            mReportsDatabase.child(id).setValue(journals);


            Intent addJournal = new Intent(AddReports.this, Reports.class);

            addJournal.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(addJournal);

            finish();

        }
    }

}
