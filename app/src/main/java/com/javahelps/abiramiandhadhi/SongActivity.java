package com.javahelps.abiramiandhadhi;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.javahelps.abiramiandhadhi.db.DatabaseAccess;

public class SongActivity extends AppCompatActivity {
    private int currentSongNumber;
    private Song currentSong;
    private DatabaseAccess databaseAccess;
    private TextView txtSong;
    private TextView txtDescription;
    private TextView txtSongTitle;
    private TextView txtDescriptionTitle;
    private ImageView imgBookmark;

    private SimpleGestureFilter detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        this.databaseAccess = DatabaseAccess.getInstance(this);

        this.txtSong = (TextView) findViewById(R.id.txtSong);
        this.txtDescription = (TextView) findViewById(R.id.txtDescription);
        this.txtSongTitle = (TextView) findViewById(R.id.txtSongTitle);
        this.txtDescriptionTitle = (TextView) findViewById(R.id.txtDescriptionTitle);
        this.imgBookmark = (ImageView) findViewById(R.id.imgBookmark);

        // Detect touched area
        detector = new SimpleGestureFilter(this, new SwipeListener());


        this.imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSong.isBookmarked()) {
                    // Remove the bookmark
                    databaseAccess.removeSookmark(currentSongNumber);
                } else {
                    // Add the bookmark
                    databaseAccess.addSookmark(currentSongNumber);
                }
                loadSong();
            }
        });

        Typeface typefaceText = Typeface.createFromAsset(getAssets(), "fonts/baamini.ttf");

        this.txtSong.setTypeface(typefaceText);
        this.txtDescription.setTypeface(typefaceText);
        this.txtSongTitle.setTypeface(typefaceText, Typeface.BOLD);
        this.txtDescriptionTitle.setTypeface(typefaceText, Typeface.BOLD);

        this.txtSong.setTextSize(20);
        this.txtDescription.setTextSize(20);
        this.txtSongTitle.setTextSize(20);
        this.txtDescriptionTitle.setTextSize(20);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            currentSongNumber = bundle.getInt("SONG_NUMBER");
        } else {
            currentSongNumber = savedInstanceState.getInt("CURRENT_SONG_NUMBER");
        }
        databaseAccess.open();

        loadSong();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("CURRENT_SONG_NUMBER", currentSongNumber);
    }

    private void loadSong() {
        currentSong = databaseAccess.getSong(currentSongNumber);
        this.txtSong.setText(currentSong.getSong());
        this.txtDescription.setText(currentSong.getDescription());
        if (currentSongNumber == 0) {
            this.txtSongTitle.setText("fhg;G");
            this.imgBookmark.setVisibility(View.INVISIBLE);
        } else if (currentSongNumber == 101) {
            this.txtSongTitle.setText("Ehw;gad;");
            this.imgBookmark.setVisibility(View.INVISIBLE);
        } else {
            this.txtSongTitle.setText("ghly; - " + currentSongNumber);
            this.imgBookmark.setVisibility(View.VISIBLE);
            if (currentSong.isBookmarked()) {
                this.imgBookmark.setImageResource(R.drawable.img_bookmark_enabled);
            } else {
                this.imgBookmark.setImageResource(R.drawable.img_bookmark_disabled);
            }
        }
    }

    @Override
    protected void onStop() {
        databaseAccess.close();
        super.onStop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    private class SwipeListener implements SimpleGestureFilter.SimpleGestureListener {
        @Override
        public void onSwipe(int direction) {
            String str = "";

            switch (direction) {

                case SimpleGestureFilter.SWIPE_RIGHT:
                    if (currentSongNumber > 1 && currentSongNumber <= 100) {
                        currentSongNumber--;
                        loadSong();
                    }
                    break;
                case SimpleGestureFilter.SWIPE_LEFT:
                    if (currentSongNumber >= 1 && currentSongNumber < 100) {
                        currentSongNumber++;
                        loadSong();
                    }
                    break;

            }
        }

        @Override
        public void onDoubleTap() {
            // Do nothing
        }
    }
}
