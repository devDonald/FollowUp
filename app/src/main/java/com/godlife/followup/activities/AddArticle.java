package com.godlife.followup.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.godlife.followup.R;
import com.godlife.followup.models.ArticleModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddArticle extends AppCompatActivity {

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String title, content, author,uid, artTime, artDate;
    private EditText art_title, art_content, art_written_by;
    private Button submit_article;
    private KProgressHUD hud;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference articlesDB;
    private FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        artDate= currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        artTime= currentTime.format(calendar.getTime());


        art_title = findViewById(R.id.add_art_title);
        art_content = findViewById(R.id.add_art_body);
        art_written_by = findViewById(R.id.add_art_written_by);
        submit_article =findViewById(R.id.add_art_submit);

        hud = KProgressHUD.create(AddArticle.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setDetailsLabel("Posting Article")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        db = FirebaseDatabase.getInstance();
        articlesDB = db.getReference("Articles");


        submit_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = art_title.getText().toString().trim();
                content= art_content.getText().toString().trim();
                author = art_written_by.getText().toString().trim();


                if (!title.isEmpty() && !content.isEmpty() && !author.isEmpty()){

                    hud.show();

                    String id = articlesDB.push().getKey();

                    ArticleModel articleModel = new ArticleModel(title,content,author,artDate, artTime, id);

                    articlesDB.child(id).setValue(articleModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            hud.dismiss();
                            MDToast.makeText(AddArticle.this,"Article Successfully Posted",MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
                            Intent toAdmin = new Intent(AddArticle.this, MainActivity.class);
                            startActivity(toAdmin);
                            finish();
                        }
                    });


                }
            }
        });


    }
}

