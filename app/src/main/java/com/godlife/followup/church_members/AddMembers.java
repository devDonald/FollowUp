package com.godlife.followup.church_members;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.godlife.followup.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.valdesekamdem.library.mdtoast.MDToast;

public class AddMembers extends AppCompatActivity {

    private EditText uname;
    private EditText udob;
    private Spinner ugender;
    private EditText uaddress;
    private EditText uemail;
    private EditText uphone;
    private Spinner umarital;
    private EditText uoccupation;
    private EditText unationality;
    private Spinner ustateOfOrigin;
    private Spinner ulocation;
    private Button submitButton;

    private FirebaseDatabase database;
    private ImageButton member_image;
    private DatabaseReference membersReference;
    private String id;
    private StorageReference membersStorage;
    private static final int GALLERY_REQUEST =78;
    private static final int CAMERA_REQUEST_CODE = 1;
    private Uri imageUri =null;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members);
        getSupportActionBar().setTitle("Add Members");
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        uname= findViewById(R.id.et_person_name);
        udob= findViewById(R.id.et_person_dob);
        ugender= findViewById(R.id.gender);
        uaddress= findViewById(R.id.et_person_address);
        uemail= findViewById(R.id.et_person_email);
        uphone= findViewById(R.id.et_person_phone);
        umarital= findViewById(R.id.marital_status);
        uoccupation= findViewById(R.id.et_person_occupation);
        unationality= findViewById(R.id.et_person_nationality);
        ustateOfOrigin= findViewById(R.id.et_person_stateOfOrigin);
        ulocation= findViewById(R.id.et_person_location);
        submitButton= findViewById(R.id.submit_button);

        member_image= findViewById(R.id.ib_person_image);

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


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.setMessage("Adding Member......");
                final String name = uname.getText().toString().trim();
                final String dob = udob.getText().toString().trim();
                final String gender=ugender.getItemAtPosition(
                        ugender.getSelectedItemPosition()).toString().trim();
                final String address=uaddress.getText().toString().trim();
                final String email = uemail.getText().toString().trim();
                final String phone =uphone.getText().toString().trim();
                final String marital_status = umarital.getItemAtPosition(
                        umarital.getSelectedItemPosition()).toString().trim();
                final String occupation = uoccupation.getText().toString().trim();
                final String nationality = unationality.getText().toString().trim();
                final String state = ustateOfOrigin.getItemAtPosition(
                        ustateOfOrigin.getSelectedItemPosition()).toString().trim();
                final String location=ulocation.getItemAtPosition(
                        ulocation.getSelectedItemPosition()).toString().trim();

                if (TextUtils.isEmpty(name)){
                    MDToast.makeText(AddMembers.this,"Name field cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (TextUtils.isEmpty(dob)){
                    MDToast.makeText(AddMembers.this,"Date of birth cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (TextUtils.isEmpty(address)){
                    MDToast.makeText(AddMembers.this,"Address cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (TextUtils.isEmpty(phone)){
                    MDToast.makeText(AddMembers.this,"Phone Number cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                } else if (TextUtils.isEmpty(occupation)){
                    MDToast.makeText(AddMembers.this,"Occupation field cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                } else if (TextUtils.isEmpty(nationality)){
                    MDToast.makeText(AddMembers.this,"Nationality cannot be Empty",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (state.equalsIgnoreCase("State of Origin")){
                    MDToast.makeText(AddMembers.this,"Select A valid State",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();


                }else if (location.equalsIgnoreCase("Location Tag")){
                    MDToast.makeText(AddMembers.this,"Select Valid Location Tag",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (gender.equalsIgnoreCase("Gender")){
                    MDToast.makeText(AddMembers.this," Pls Select a valid gender",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                }else if (marital_status.equalsIgnoreCase("Marital Status")){
                    MDToast.makeText(AddMembers.this,"Pls Select a valid Marital Status",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                } else {

                    mProgress.show();
                    StorageReference filePath = membersStorage.child("images").child(imageUri.getLastPathSegment());
                    filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            id = membersReference.push().getKey();
                            MembersModel members = new MembersModel(name,dob,gender,address, email, phone,
                                    marital_status, occupation,nationality,state,location);
                            membersReference.child(id).setValue(members);
                            membersReference.child(id).child("image").setValue(downloadUri.toString());

                            MDToast mdToast = MDToast.makeText(getApplicationContext(),
                                    "Member added successfully!",
                                    MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS);
                            mdToast.show();
                            mProgress.dismiss();
                            Intent payIntent = new Intent(AddMembers.this, Members.class);
                            payIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(payIntent);

                        }
                    });

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

    @Override
    protected void onStart() {
        super.onStart();
        unationality.setText("Nigerian");
    }
}
