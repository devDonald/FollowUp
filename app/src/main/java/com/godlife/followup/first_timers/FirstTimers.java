package com.godlife.followup.first_timers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.godlife.followup.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FirstTimers extends AppCompatActivity {
    private RecyclerView first_timers_RV;
    private DatabaseReference firstTimersReference;
    private Context context=getBaseContext();

    private FirebaseRecyclerAdapter<FirstTimersModel, FirstTimersViewHolder> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_timers);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        first_timers_RV= findViewById(R.id.first_timers_rv);
        firstTimersReference= FirebaseDatabase.getInstance().getReference().child("FirstTimers");
        first_timers_RV.setHasFixedSize(true);
        first_timers_RV.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    protected void onStart() {
        super.onStart();
        showFirstTimers();
    }


    public static class FirstTimersViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public FirstTimersViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

        }
        public void setName(String names){
            TextView name= mView.findViewById(R.id.tv_first_name);
            name.setText(names);

        }
        public void setPhone(String phone){
            TextView phones = mView.findViewById(R.id.tv_first_phone);
            phones.setText(phone);
        }
        public void setLocation(String location){
            TextView locations= mView.findViewById(R.id.tv_first_location);
            locations.setText(location);
        }
        public void setMarital(String marital){
            TextView maritals= mView.findViewById(R.id.tv_first_mstatus);
            maritals.setText(marital);
        }

        public void setOccupation(String occupation){
            TextView occupations= mView.findViewById(R.id.tv_first_occupation);
            occupations.setText(occupation);
        }

        public void setDOB(String dob){
            TextView tv_dob= mView.findViewById(R.id.tv_first_dob);
            tv_dob.setText("Date of Birth: "+dob);
        }
        public void setDOV(String dov){
            TextView tv_dov= mView.findViewById(R.id.tv_first_dov);
            tv_dov.setText("Date of Visit: "+dov);
        }

        public void setGender(String gender){
            TextView genders= mView.findViewById(R.id.tv_first_gender);
            genders.setText(gender);
        }
        public void setFilled(String filled){
            TextView filleds= mView.findViewById(R.id.tv_first_filled);
            filleds.setText("Are you Filled? "+filled);
        }


    }

    public void showFirstTimers(){

        Query firebaseSearchQuery = firstTimersReference.orderByChild("dateOfVisit");

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<FirstTimersModel, FirstTimersViewHolder>(
                FirstTimersModel.class,
                R.layout.layout_first_timers,
                FirstTimersViewHolder.class,
                firebaseSearchQuery
        ) {

            @Override
            protected void populateViewHolder(FirstTimersViewHolder viewHolder, FirstTimersModel model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setMarital(model.getMarital());
                viewHolder.setOccupation(model.getOccupation());
                viewHolder.setGender(model.getGender());
                viewHolder.setFilled(model.getFilled());
                viewHolder.setDOB(model.getDob());
                viewHolder.setDOV(model.getDateOfVisit());
                viewHolder.setLocation(model.getAddress());
            }

            @Override
            public FirstTimersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                FirstTimersViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);


                return viewHolder;
            }
        };
        first_timers_RV.setAdapter(firebaseRecyclerAdapter);
    }
}
