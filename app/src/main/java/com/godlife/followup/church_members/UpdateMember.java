package com.godlife.followup.church_members;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.godlife.followup.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.valdesekamdem.library.mdtoast.MDToast;

public class UpdateMember extends AppCompatActivity {

    private EditText uname;
    private EditText udob;
    private EditText ugender;
    private EditText uaddress;
    private EditText uemail;
    private EditText uphone;
    private EditText umarital;
    private EditText uoccupation;
    private EditText unationality;
    private EditText ustateOfOrigin;
    private EditText ulocation;
    private Button submitUpdate;

    private FirebaseDatabase database;
    private ImageView member_image;
    private DatabaseReference membersReference;
    private String id;
    private StorageReference membersStorage;
    private static final int GALLERY_REQUEST =70;
    private static final int CAMERA_REQUEST_CODE = 1;
    private Uri imageUri =null;
    private ProgressDialog mProgress;
    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_member);

        uname= findViewById(R.id.um_person_name);
        udob= findViewById(R.id.um_person_dob);
        ugender= findViewById(R.id.um_gender);
        uaddress= findViewById(R.id.um_person_address);
        uemail= findViewById(R.id.um_person_email);
        uphone= findViewById(R.id.um_person_phone);
        umarital= findViewById(R.id.um_marital_status);
        uoccupation= findViewById(R.id.um_person_occupation);
        unationality= findViewById(R.id.um_person_nationality);
        ustateOfOrigin= findViewById(R.id.um_person_stateOfOrigin);
        ulocation= findViewById(R.id.um_person_location);
        submitUpdate= findViewById(R.id.um_update_button);

        member_image= findViewById(R.id.um_person_image);

        member_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galIntent, "Choose picture"), GALLERY_REQUEST);
            }
        });

        database= FirebaseDatabase.getInstance();
        membersReference=database.getReference("Members");
        membersStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);


        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            position = extras.getString("position");
            if (position!=null){
                DatabaseReference userRef = membersReference.child(position);

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
                        Picasso.with(UpdateMember.this).load(profile_image).into(member_image);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                userRef.addListenerForSingleValueEvent(valueEventListener);


            }



        }

        submitUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.setMessage("Updating Member......");
                final String name = uname.getText().toString().trim();
                final String dob = udob.getText().toString().trim();
                final String gender=ugender.getText().toString().trim();
                final String address=uaddress.getText().toString().trim();
                final String email = uemail.getText().toString().trim();
                final String phone =uphone.getText().toString().trim();
                final String marital_status = umarital.getText().toString().trim();
                final String occupation = uoccupation.getText().toString().trim();
                final String nationality = unationality.getText().toString().trim();
                final String state = ustateOfOrigin.getText().toString().trim();
                final String location=ulocation.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    MDToast.makeText(UpdateMember.this,"Name field cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (TextUtils.isEmpty(dob)){
                    MDToast.makeText(UpdateMember.this,"Date of birth cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (TextUtils.isEmpty(address)){
                    MDToast.makeText(UpdateMember.this,"Address cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (TextUtils.isEmpty(phone)){
                    MDToast.makeText(UpdateMember.this,"Phone Number cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                } else if (TextUtils.isEmpty(occupation)){
                    MDToast.makeText(UpdateMember.this,"Occupation field cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                } else if (TextUtils.isEmpty(nationality)){
                    MDToast.makeText(UpdateMember.this,"Nationality cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (TextUtils.isEmpty(state)){
                    MDToast.makeText(UpdateMember.this,"State field cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();


                }else if (TextUtils.isEmpty(location)){
                    MDToast.makeText(UpdateMember.this,"Location field cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (gender.isEmpty()){
                    MDToast.makeText(UpdateMember.this," Pls Select a valid gender",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (marital_status.isEmpty()){
                    MDToast.makeText(UpdateMember.this,"Pls Select a valid Marital Status",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                } else {

                    mProgress.show();
                    if (imageUri!=null){
                        StorageReference filePath = membersStorage.child("images").child(imageUri.getLastPathSegment());
                        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Uri downloadUri = taskSnapshot.getDownloadUrl();
                                id = membersReference.push().getKey();
                                membersReference.child(position).child("name").setValue(name);
                                membersReference.child(position).child("dob").setValue(dob);
                                membersReference.child(position).child("gender").setValue(gender);
                                membersReference.child(position).child("address").setValue(address);
                                membersReference.child(position).child("email").setValue(email);
                                membersReference.child(position).child("phone").setValue(phone);
                                membersReference.child(position).child("marital").setValue(marital_status);
                                membersReference.child(position).child("occupation").setValue(occupation);
                                membersReference.child(position).child("nationality").setValue(nationality);
                                membersReference.child(position).child("stateOfOrigin").setValue(state);
                                membersReference.child(position).child("location").setValue(location);
                                membersReference.child(position).child("image").setValue(downloadUri.toString());

                                MDToast mdToast = MDToast.makeText(getApplicationContext(),
                                        "Member Updated successfully!",
                                        MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS);
                                mdToast.show();
                                mProgress.dismiss();
                                Intent payIntent = new Intent(UpdateMember.this, Members.class);
                                payIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(payIntent);

                            }
                        });

                    } else {
                        membersReference.child(position).child("name").setValue(name);
                        membersReference.child(position).child("dob").setValue(dob);
                        membersReference.child(position).child("gender").setValue(gender);
                        membersReference.child(position).child("address").setValue(address);
                        membersReference.child(position).child("email").setValue(email);
                        membersReference.child(position).child("phone").setValue(phone);
                        membersReference.child(position).child("marital").setValue(marital_status);
                        membersReference.child(position).child("occupation").setValue(occupation);
                        membersReference.child(position).child("nationality").setValue(nationality);
                        membersReference.child(position).child("stateOfOrigin").setValue(state);
                        membersReference.child(position).child("location").setValue(location);

                        MDToast mdToast = MDToast.makeText(getApplicationContext(),
                                "Member Updated successfully!",
                                MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS);
                        mdToast.show();
                        mProgress.dismiss();
                        Intent payIntent = new Intent(UpdateMember.this, Members.class);
                        payIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(payIntent);
                    }



                }

            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            imageUri = data.getData();

            try {
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
