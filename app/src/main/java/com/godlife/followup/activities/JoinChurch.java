package com.godlife.followup.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.godlife.followup.R;
import com.godlife.followup.models.MembersModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;


public class JoinChurch extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 10;
    private CountryCodePicker ccp;
    private String country = "Nigeria", state, surname, fname, oname, gender, marital, phone, address;
    private String email, occupation, d_day, d_month, uid, unit, my_location;
    private EditText etSurname, etFname, etOthernames, etPhone, etEmail, etOccupation,etAddress;
    private RadioGroup rgGender, rgMarital;
    private Spinner join_states,dob_day, dob_month, church_unit, location;
    private DatabaseReference userDb;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private TextView btDob;
    private ImageButton pick_image;
    private ImageView display_image;
    private Uri imageUri;
    private StorageReference userPics;
    private KProgressHUD hud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        setContentView(R.layout.activity_join_church);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ccp = findViewById(R.id.ccp);
        join_states = findViewById(R.id.join_state);
        etSurname =findViewById(R.id.join_surname);
        etFname =findViewById(R.id.join_first_name);
        etOthernames =findViewById(R.id.join_other_names);
        etPhone =findViewById(R.id.join_phone_no);
        etEmail =findViewById(R.id.join_email);
        etAddress =findViewById(R.id.join_address);
        etSurname =findViewById(R.id.join_surname);
        etOccupation =findViewById(R.id.join_occupation);
        dob_day = findViewById(R.id.join_pick_date_day);
        dob_month = findViewById(R.id.join_pick_date_month);
        pick_image =findViewById(R.id.pick_p_image);
        display_image=findViewById(R.id.display_pimage);
        church_unit = findViewById(R.id.join_units);
        location = findViewById(R.id.join_location);

        rgGender = findViewById(R.id.gender_group);
        rgMarital =findViewById(R.id.marital_group);

        hud = KProgressHUD.create(JoinChurch.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setDetailsLabel("Sending Your Details")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);


        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.gender_male){
                    gender="Male";
                } else if (i==R.id.gender_female){
                    gender="Female";
                }
                Log.d("gender",gender);

            }
        });

        rgMarital.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i==R.id.single){
                    marital="Single";
                } else if (i==R.id.married){
                    marital="Married";
                }
                Log.d("marital",marital);
            }
        });

        userDb = FirebaseDatabase.getInstance().getReference().child("Members");

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userPics= FirebaseStorage.getInstance().getReference();


        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                country = selectedCountry.getName().toString();

                if (!country.equalsIgnoreCase("Nigeria")){

                    join_states.setVisibility(View.GONE);

                } else {
                    join_states.setVisibility(View.VISIBLE);
                }
                Log.d("country",country);
            }
        });


        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galIntent, "Choose picture"), GALLERY_REQUEST);
            }
        });

//      try {
//
//      }catch (Exception e){
//          e.printStackTrace();
//      }int

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                surname=etSurname.getText().toString().trim();
                fname = etFname.getText().toString().trim();
                oname =etOthernames.getText().toString().trim();
                phone =etPhone.getText().toString().trim();
                address =etAddress.getText().toString().trim();
                email =etEmail.getText().toString().trim();
                state = join_states.getItemAtPosition(join_states.getSelectedItemPosition()).toString().trim();
                occupation = etOccupation.getText().toString().trim();
                d_day = dob_day.getItemAtPosition(dob_day.getSelectedItemPosition()).toString().trim();
                d_month = dob_month.getItemAtPosition(dob_month.getSelectedItemPosition()).toString().trim();
                my_location = location.getItemAtPosition(location.getSelectedItemPosition()).toString().trim();
                unit = church_unit.getItemAtPosition(church_unit.getSelectedItemPosition()).toString().trim();


                if (surname.isEmpty()){
                    etSurname.setError("field empty");
                } else if (fname.isEmpty()){
                    etFname.setError("field empty");
                } else if (phone.isEmpty()){
                    etPhone.setError("field empty");
                } else if (occupation.isEmpty()){
                    etOccupation.setError("field empty");
                } else if (address.isEmpty()){
                    etAddress.setError("field empty");
                } else if (!email.isEmpty() && !email.contains("@")|| !email.contains(".")){
                    etEmail.setError("email incorrect");
                } else if (gender.isEmpty()){
                    MDToast.makeText(JoinChurch.this, "Select a Gender",MDToast.TYPE_ERROR,MDToast.LENGTH_LONG).show();
                } else if (marital.isEmpty()){
                    MDToast.makeText(JoinChurch.this, "Select a marital status",MDToast.TYPE_ERROR,MDToast.LENGTH_LONG).show();

                } else if (my_location.matches("Location Tag")){
                    MDToast.makeText(JoinChurch.this, "Select a valid location tag",MDToast.TYPE_ERROR,MDToast.LENGTH_LONG).show();

                }else if (unit.matches("Select Church Department")){
                    MDToast.makeText(JoinChurch.this, "Select a valid church unit",MDToast.TYPE_ERROR,MDToast.LENGTH_LONG).show();

                }else {
                    try {

                        uploadData();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            imageUri = data.getData();

            display_image.setImageURI(imageUri);


        }

    }

    public void uploadData() {

            hud.show();

            if (imageUri!=null){
                final StorageReference file = userPics.child("ProfileImages").child(phone).child(imageUri.getLastPathSegment());

//                display_image.setDrawingCacheEnabled(true);
//                display_image.buildDrawingCache();
//                Bitmap bitmap = display_image.getDrawingCache();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] data = baos.toByteArray();

                UploadTask uploadTask = file.putFile(imageUri);


                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        hud.dismiss();
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return file.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        hud.dismiss();
                        if (task.isSuccessful()) {
                            Uri downloadUrl = task.getResult();
                            String id = userDb.push().getKey();

                            if (oname.isEmpty()){
                                MembersModel model = new MembersModel(fname,surname,phone,marital,gender,state,country,
                                        occupation,address,email,d_day,d_month,downloadUrl.toString(),uid, unit,my_location, 0);

                                userDb.child(d_month).child(id).setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                MDToast.makeText(JoinChurch.this,"Data Successfully Added",MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
                                                Intent toAdmin = new Intent(JoinChurch.this, MainActivity.class);
                                                toAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                toAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(toAdmin);
                                            }
                                        })

                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                MDToast.makeText(JoinChurch.this,"Data failed to add",MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                                            }
                                        });
                            } else {

                                MembersModel model = new MembersModel(fname,oname, surname,phone,marital,gender,state,country,
                                        occupation,address,email,d_day,d_month,downloadUrl.toString(),uid, unit,my_location, 0);

                                userDb.child(d_month).child(id).setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                MDToast.makeText(JoinChurch.this,"Data Successfully Added",MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
                                                Intent toAdmin = new Intent(JoinChurch.this, MainActivity.class);
                                                toAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                toAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(toAdmin);
                                            }
                                        })

                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                MDToast.makeText(JoinChurch.this,"Data failed to add",MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                                            }
                                        });
                            }



                        }

                    }


                });

            } else {
                MDToast.makeText(JoinChurch.this,"Failed",MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

            }
    }

}

