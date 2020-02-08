package com.godlife.followup.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.godlife.followup.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddEvents extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 27;
    private String title, details,eventDate;
    private EditText event_title, event_body;
    private ImageView addImage;
    private Button submit_event;
    private KProgressHUD hud;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore eventsDB;
    private FirebaseFirestoreSettings settings;
    private Uri imageUri;
    private StorageReference eventPics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        event_title = findViewById(R.id.add_event_title);
        event_body = findViewById(R.id.add_event_details);
        addImage = findViewById(R.id.event_photo);
        submit_event = findViewById(R.id.add_event_submit);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galIntent, "Choose picture"), GALLERY_REQUEST);
            }
        });


        eventPics= FirebaseStorage.getInstance().getReference();

        eventsDB = FirebaseFirestore.getInstance();

        submit_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();

            }
        });


        hud = KProgressHUD.create(AddEvents.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setDetailsLabel("Adding a Meeting")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            imageUri = data.getData();

            addImage.setImageURI(imageUri);



        }

    }

    public void uploadData() {

        hud.show();

        if (imageUri!=null){
            final StorageReference file = eventPics.child("EventPics").child(imageUri.getLastPathSegment());

//            addImage.setDrawingCacheEnabled(true);
//            addImage.buildDrawingCache();
//            Bitmap bitmap = addImage.getDrawingCache();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] data = baos.toByteArray();

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
                        title = event_title.getText().toString().trim();
                        details = event_body.getText().toString().trim();
                        Map<String, Object> events = new HashMap<>();

                        events.put("title", title);
                        events.put("details",details);
                        events.put("image",downloadUrl.toString());

                        eventsDB.collection("Meetings")
                                .add(events)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                        MDToast.makeText(AddEvents.this,"Meeting Successfully Added",MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
                                        Intent toAdmin = new Intent(AddEvents.this, MainActivity.class);
                                        toAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        toAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(toAdmin);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        MDToast.makeText(AddEvents.this,"Meeting failed to add",MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                                    }
                                });


                    }

                }


            });

        } else {

            title = event_title.getText().toString().trim();
            details = event_body.getText().toString().trim();
            Map<String, Object> events = new HashMap<>();

            events.put("title", title);
            events.put("details",details);

            eventsDB.collection("Meetings")
                    .add(events)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            MDToast.makeText(AddEvents.this,"Meeting Successfully Added",MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
                            Intent toAdmin = new Intent(AddEvents.this, MainActivity.class);
                            toAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            toAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(toAdmin);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            MDToast.makeText(AddEvents.this,"Meeting failed to add",MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                        }
                    });

        }
    }


}
