package com.godlife.followup.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.godlife.followup.R;
import com.godlife.followup.models.MembersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;
import java.util.Map;

public class Members extends AppCompatActivity {

    private RecyclerView allMembers_RV;
    private DatabaseReference membersDb;
    private Context context;
    private String month,tag;
    //private int num;
    private FirebaseRecyclerAdapter<MembersModel, MembersViewHolder> firebaseRecyclerAdapter;
    private Dialog popDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberss);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.member_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_josmembers) {
            Intent join = new Intent(Members.this, JoinChurch.class);
            startActivity(join);
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MembersViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TextView person_name, birthday, birth_call, birth_text,birth_chat,birth_like;
        public ImageView birth_image;
        public CardView birth_view;


        public MembersViewHolder(View view) {
            super(view);

//            person_name= view.findViewById(R.id.birth_person_name);
//            birthday = view.findViewById(R.id.birth_person_date);
//            birth_call= view.findViewById(R.id.birth_person_phone);
//            birth_text= view.findViewById(R.id.birth_person_text);
//            birth_image= view.findViewById(R.id.birth_person_image);
//            //birth_chat =view.findViewById(R.id.birth_person_chat);
//            birth_like = view.findViewById(R.id.birth_person_like);
//            birth_view = view.findViewById(R.id.birth_parent);
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

        Query firebaseSearchQuery = membersDb.orderByChild("dd");

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<MembersModel, MembersViewHolder>(
                MembersModel.class,
                R.layout.layout_members,
                MembersViewHolder.class,
                firebaseSearchQuery
        ) {

            @Override
            protected void populateViewHolder(final MembersViewHolder viewHolder, final MembersModel model, final int position) {
                try {
                    viewHolder.person_name.setText(model.getSurname()+" "+model.getFirstName());


                    viewHolder.birthday.setText(model.getDD()+" / "+model.getMM());



                    Glide.with(Members.this).load(model.getPhoto()).into(viewHolder.birth_image);


                    int num = model.getLikes();
                    viewHolder.birth_like.setText(""+num);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public MembersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                MembersViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                return viewHolder;
            }
        };
        allMembers_RV.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        showBirthdays();
    }
}
