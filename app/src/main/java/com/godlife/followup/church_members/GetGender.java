package com.godlife.followup.church_members;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.godlife.followup.MainActivity;
import com.godlife.followup.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.valdesekamdem.library.mdtoast.MDToast;

public class GetGender extends AppCompatActivity {
    private RecyclerView search_members_RV;
    private DatabaseReference membersDatabase;
    private Context context=getBaseContext();
    private SearchableSpinner mSearchField;
    private ImageButton mSearchBtn;

    private FirebaseRecyclerAdapter<MembersModel,AllMembersViewHolder> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_gender);

        search_members_RV= findViewById(R.id.gender_members_rvs);
        membersDatabase= FirebaseDatabase.getInstance().getReference().child("Members");
        search_members_RV.setHasFixedSize(true);
        search_members_RV.setLayoutManager(new LinearLayoutManager(context));
        mSearchField = findViewById(R.id.gender_search);
        mSearchBtn = findViewById(R.id.gender_search_btn);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchField.getItemAtPosition(mSearchField.getSelectedItemPosition()).toString();
                if (searchText.equalsIgnoreCase("Gender")){
                    MDToast.makeText(GetGender.this,"Select a gender",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else{
                    showMembers(searchText);
                }

            }
        });

    }

    public static class AllMembersViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public AllMembersViewHolder(View itemView) {
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
            TextView name= mView.findViewById(R.id.tv_searchperson_name);
            name.setText(names);

        }
        public void setPhone(String phone){
            TextView phones = mView.findViewById(R.id.tv_searchperson_phone);
            phones.setText(phone);
        }
        public void setLocation(String location){
            TextView locations= mView.findViewById(R.id.tv_searchperson_location);
            locations.setText(location);
        }
        public void setMarital(String marital){
            TextView maritals= mView.findViewById(R.id.tv_searchperson_mstatus);
            maritals.setText(marital);
        }

        public void setOccupation(String occupation){
            TextView occupations= mView.findViewById(R.id.tv_searchperson_occupation);
            occupations.setText(occupation);
        }

        private AllMembersViewHolder.ClickListener mClickListener;

        public interface ClickListener{
            void onItemClick(View view, int position);
        }

        public void setOnClickListener(AllMembersViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }


    }

    public void showMembers(String searchText){

        Query firebaseSearchQuery = membersDatabase.orderByChild("gender").startAt(searchText).endAt(searchText + "\uf8ff");

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<MembersModel, AllMembersViewHolder>(
                MembersModel.class,
                R.layout.search_members,
                AllMembersViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(AllMembersViewHolder viewHolder, MembersModel model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setMarital(model.getMarital());
                viewHolder.setOccupation(model.getOccupation());
            }

            @Override
            public AllMembersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                AllMembersViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new AllMembersViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        //Toast.makeText(getApplicationContext(), "Item clicked at " + position, Toast.LENGTH_SHORT).show();

                        Intent wholeProfile=new Intent(GetGender.this,FullDetails.class);
                        wholeProfile.putExtra("position",firebaseRecyclerAdapter.getRef(position).getKey());

                        startActivity(wholeProfile);

                    }

                });

                return viewHolder;
            }
        };
        search_members_RV.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ok = new Intent(GetGender.this, MainActivity.class);
        ok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ok.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ok);
    }

}
