package com.godlife.followup.birthdays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.godlife.followup.R;
import com.godlife.followup.church_members.FullDetails;
import com.godlife.followup.church_members.MembersModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Birthdays extends AppCompatActivity {
    private RecyclerView birthdays_RV;
    private DatabaseReference allMembersReference;
    private Context context=getBaseContext();

    private FirebaseRecyclerAdapter<MembersModel,MembersViewHolder> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdays);

        birthdays_RV= findViewById(R.id.birthdays_rv);
        allMembersReference= FirebaseDatabase.getInstance().getReference().child("Members");
        birthdays_RV.setHasFixedSize(true);
        birthdays_RV.setLayoutManager(new LinearLayoutManager(context));


    }

    @Override
    protected void onStart() {
        super.onStart();
        showBirthdays();
    }

    public static class MembersViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public MembersViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(view, getAdapterPosition());

                }
            });
        }
        public void setName(String names){
            TextView name= mView.findViewById(R.id.tv_birthday_name);
            name.setText(names);

        }
        public void setPhone(String phone){
            TextView phones = mView.findViewById(R.id.tv_birthday_phone);
            phones.setText(phone);
        }
        public void setDob(String dob){
            TextView tv_dob= mView.findViewById(R.id.tv_birthday_dob);
            tv_dob.setText(dob);
        }


        private MembersViewHolder.ClickListener mClickListener;

        public interface ClickListener{
            void onItemClick(View view, int position);
        }

        public void setOnClickListener(MembersViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }


    }

    public void showBirthdays(){

        Query firebaseSearchQuery = allMembersReference.orderByChild("dob");

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<MembersModel, MembersViewHolder>(
                MembersModel.class,
                R.layout.birthdays_layout,
                MembersViewHolder.class,
                firebaseSearchQuery
        ) {

            @Override
            protected void populateViewHolder(MembersViewHolder viewHolder, MembersModel model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setDob(model.getDob());
               }

            @Override
            public MembersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                MembersViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new MembersViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        //Toast.makeText(getApplicationContext(), "Item clicked at " + position, Toast.LENGTH_SHORT).show();

                        Intent wholeProfile=new Intent(Birthdays.this,FullDetails.class);
                        wholeProfile.putExtra("position",firebaseRecyclerAdapter.getRef(position).getKey());

                        startActivity(wholeProfile);

                    }

                });

                return viewHolder;
            }
        };
        birthdays_RV.setAdapter(firebaseRecyclerAdapter);
    }

}
