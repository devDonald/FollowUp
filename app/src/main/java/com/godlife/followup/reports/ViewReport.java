package com.godlife.followup.reports;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.godlife.followup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class ViewReport extends AppCompatActivity {
    private static final String TAG = "ViewJournals";
    private DatabaseReference mReportsDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String uID;
    private TextView mTitle, mReporter, mDate;
    private JustifyTextView mContent;
    private String position;
    private String postUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        mTitle = (TextView)findViewById(R.id.report_full_title);
        mContent = (JustifyTextView) findViewById(R.id.report_full_content);
        mReporter = (TextView) findViewById(R.id.report_full_reporter);
        mDate = (TextView)findViewById(R.id.full_date);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        uID = mUser.getUid();
        mReportsDatabase = FirebaseDatabase.getInstance().getReference("Reports");


        try {
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
                            mContent.setText(dataSnapshot.child("content").getValue(String.class));
                            mReporter.setText(dataSnapshot.child("reporter").getValue(String.class));
                            mDate.setText(dataSnapshot.child("date").getValue(String.class));
                            postUID = dataSnapshot.child("id").getValue(String.class);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadJournals:onCancelled", databaseError.toException());

                        }
                    };
                    userRef.addListenerForSingleValueEvent(valueEventListener);


                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (uID.matches(postUID)){
            getMenuInflater().inflate(R.menu.report_menu,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update_report) {
            Intent updateReport=new Intent(ViewReport.this,UpdateReport.class);
            updateReport.putExtra("position",position);

            startActivity(updateReport);
            finish();

        } else if (id==R.id.action_delete_report){
            mReportsDatabase.child(position).removeValue();
            Intent delete = new Intent(ViewReport.this, Reports.class);
            delete.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            delete.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            delete.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(delete);

        } else if(id==R.id.action_done_report){
            Intent ok = new Intent(ViewReport.this, Reports.class);
            ok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ok.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ok);

        }

        return super.onOptionsItemSelected(item);
    }
}
