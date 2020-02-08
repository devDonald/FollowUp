package com.godlife.followup.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.godlife.followup.R;
import com.godlife.followup.models.LyricsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;
import java.util.Map;

public class AddLyrics extends AppCompatActivity {
    private String songtitle, fullLyrics, songauthor;
    private EditText song_title, song_lyrics, written_by;
    private Button submit_lyrics;
    private KProgressHUD hud;
    private DatabaseReference songsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lyrics);



        song_title = findViewById(R.id.add_song_title);
        song_lyrics = findViewById(R.id.add_song_body);
        written_by = findViewById(R.id.add_song_written_by);
        submit_lyrics =findViewById(R.id.add_songs_submit);

        hud = KProgressHUD.create(AddLyrics.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setDetailsLabel("Adding Lyrics")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);


        submit_lyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songtitle = song_title.getText().toString().trim();
                fullLyrics= song_lyrics.getText().toString().trim();
                songauthor = written_by.getText().toString().trim();

                songsDB = FirebaseDatabase.getInstance().getReference().child("Lyrics");


                if (!songtitle.isEmpty() && !fullLyrics.isEmpty() && !songauthor.isEmpty()){

                    hud.show();

                    try {
                        String id = songsDB.push().getKey();

                       LyricsModel model = new LyricsModel(songtitle,fullLyrics,songauthor,id);

                       songsDB.child(id).setValue(model)
                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       hud.dismiss();
                                       if (task.isSuccessful()){
                                           MDToast.makeText(AddLyrics.this,"Lyrics Successfully Added",MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
                                           Intent toAdmin = new Intent(AddLyrics.this, MainActivity.class);
                                           startActivity(toAdmin);
                                           finish();
                                       }
                                   }
                               });

                    } catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }
        });


    }
}
