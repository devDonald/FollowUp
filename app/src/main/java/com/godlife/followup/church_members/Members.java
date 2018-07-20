package com.godlife.followup.church_members;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.godlife.followup.MainActivity;
import com.godlife.followup.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Members extends AppCompatActivity {
    private RecyclerView allmembers_RV;
    private DatabaseReference allMembersReference;
    private Context context=getBaseContext();

    private FirebaseRecyclerAdapter<MembersModel,AllMembersViewHolder> firebaseRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMembers = new Intent(Members.this,AddMembers.class);
                startActivity(addMembers);
            }
        });

        allmembers_RV= findViewById(R.id.members_rv);
        allMembersReference= FirebaseDatabase.getInstance().getReference().child("Members");
        allmembers_RV.setHasFixedSize(true);
        allmembers_RV.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    protected void onStart() {
        super.onStart();

        showMembers();

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
            TextView name= mView.findViewById(R.id.tv_listperson_name);
            name.setText(names);

        }
        public void setPhone(String phone){
            TextView phones = mView.findViewById(R.id.tv_listperson_phone);
            phones.setText(phone);
        }
        public void setLocation(String location){
            TextView locations= mView.findViewById(R.id.tv_listperson_location);
            locations.setText(location);
        }
        public void setMarital(String marital){
            TextView maritals= mView.findViewById(R.id.tv_listperson_mstatus);
            maritals.setText(marital);
        }

        public void setOccupation(String occupation){
            TextView occupations= mView.findViewById(R.id.tv_listperson_occupation);
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

    public void showMembers(){

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<MembersModel, AllMembersViewHolder>(
                MembersModel.class,
                R.layout.all_members_layout,
                AllMembersViewHolder.class,
                allMembersReference
        ) {

            @Override
            protected void populateViewHolder(AllMembersViewHolder viewHolder,MembersModel model, int position) {
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

                        Intent wholeProfile=new Intent(Members.this,FullDetails.class);
                        wholeProfile.putExtra("position",firebaseRecyclerAdapter.getRef(position).getKey());

                        startActivity(wholeProfile);

                    }

                });

                return viewHolder;
            }
        };
        allmembers_RV.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ok = new Intent(Members.this, MainActivity.class);
        ok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ok.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ok);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent search=new Intent(Members.this,SearchMember.class);
            startActivity(search);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
