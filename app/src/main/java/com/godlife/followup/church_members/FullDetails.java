package com.godlife.followup.church_members;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.godlife.followup.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FullDetails extends AppCompatActivity {
    private TextView uname;
    private TextView udob;
    private TextView ugender;
    private TextView uaddress;
    private TextView uemail;
    private TextView uphone;
    private TextView umarital;
    private TextView uoccupation;
    private TextView unationality;
    private TextView ustateOfOrigin;
    private TextView ulocation;
    private ImageView imageView;
    private DatabaseReference detailsRef;
    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details);

        detailsRef = FirebaseDatabase.getInstance().getReference().child("Members");
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        uname= findViewById(R.id.tv_fulldetails_name);
        udob= findViewById(R.id.tv_fulldetails_dob);
        ugender= findViewById(R.id.fulldetails_gender);
        uaddress= findViewById(R.id.tv_fulldetails_address);
        uemail= findViewById(R.id.tv_fulldetails_email);
        uphone= findViewById(R.id.tv_fulldetails_phone);
        umarital= findViewById(R.id.tv_fulldetails_marital_status);
        uoccupation= findViewById(R.id.tv_fulldetails_occupation);
        unationality= findViewById(R.id.tv_fulldetails_nationality);
        ustateOfOrigin= findViewById(R.id.tv_fulldetails_stateOfOrigin);
        ulocation= findViewById(R.id.tv_fulldetails_location);


        imageView= findViewById(R.id.iv_fulldetails_image);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
           position = extras.getString("position");
            if (position!=null){
                DatabaseReference userRef = detailsRef.child(position);

                Log.d("userref",""+userRef);
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                           // Log.d("ds",""+ds);

                            MembersModel member = dataSnapshot.getValue(MembersModel.class);
                            uname.setText(member.getName());
                            udob.setText(member.getDob());
                            uaddress.setText(member.getAddress());
                            uemail.setText(member.getEmail());
                            ugender.setText(member.getGender());
                            ulocation.setText(member.getLocation());
                            umarital.setText(member.getMarital());
                            unationality.setText(member.getNationality());
                            uoccupation.setText(member.getOccupation());
                            uphone.setText(member.getPhone());
                            ustateOfOrigin.setText(member.getStateOfOrigin());

                        String profile_image = dataSnapshot.child("image").getValue(String.class);
                        Picasso.with(FullDetails.this).load(profile_image).into(imageView);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                userRef.addListenerForSingleValueEvent(valueEventListener);


            }



        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.full_details_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            Intent updateMember=new Intent(FullDetails.this,UpdateMember.class);
            updateMember.putExtra("position",position);

            startActivity(updateMember);
            finish();

        } else if (id==R.id.action_delete){
            detailsRef.child(position).removeValue();
            Intent ok = new Intent(FullDetails.this, Members.class);
            ok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ok.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ok);

        } else if(id==R.id.action_done){
            Intent ok = new Intent(FullDetails.this, Members.class);
            ok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ok.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ok);

        }

        return super.onOptionsItemSelected(item);
    }
}
