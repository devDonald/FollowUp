package com.godlife.followup.reports;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.godlife.followup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Reports extends AppCompatActivity {

    private RecyclerView mReportsRecycler;
    private DatabaseReference mReportssDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String uID;
    private static final String TAG = Reports.class.getSimpleName();
    private static final int REQUEST_INVITE = 0;
    private List<ReportModel> list;
    private FirebaseRecyclerAdapter<ReportModel,RecyclerAdapter> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mReportsRecycler = findViewById(R.id.all_reports_recycler);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        uID = mUser.getUid();
        mReportssDatabase = FirebaseDatabase.getInstance().getReference("Reports");

        RecyclerView.LayoutManager manager = new LinearLayoutManager(Reports.this);
        mReportsRecycler.setLayoutManager(manager);
        mReportsRecycler.setItemAnimator( new DefaultItemAnimator());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_report = new Intent(Reports.this, AddReports.class);
                startActivity(add_report);
            }
        });
    }
    public void fetchReports(){
        try {
            firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<ReportModel, RecyclerAdapter>(
                    ReportModel.class,
                    R.layout.layout_all_reports,
                    RecyclerAdapter.class,
                    mReportssDatabase
            ) {
                @Override
                protected void populateViewHolder(RecyclerAdapter viewHolder,ReportModel model, int position) {
                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setReporter(model.getReporter());
                    viewHolder.setDate(model.getDate());
                }

                @Override
                public RecyclerAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
                    RecyclerAdapter viewHolder = super.onCreateViewHolder(parent, viewType);
                    viewHolder.setOnClickListener(new RecyclerAdapter.ClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            //Toast.makeText(getApplicationContext(), "Item clicked at " + position, Toast.LENGTH_SHORT).show();

                            try {
                                Intent wholeProfile=new Intent(Reports.this,ViewReport.class);
                                wholeProfile.putExtra("position",firebaseRecyclerAdapter.getRef(position).getKey());

                                startActivity(wholeProfile);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    });

                    return viewHolder;
                }
            };
            mReportsRecycler.setAdapter(firebaseRecyclerAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            fetchReports();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
