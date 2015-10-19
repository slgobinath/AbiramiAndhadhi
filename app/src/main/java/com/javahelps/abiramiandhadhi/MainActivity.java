package com.javahelps.abiramiandhadhi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.javahelps.abiramiandhadhi.db.DatabaseAccess;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseAccess databaseAccess;
    private TextView btnFirst;
    private TextView btnLast;
    private TextView btnSongs;
    private ListView lstBookmarked;
    private TextView txtBookmarkTitle;
    private Typeface typeface;
    private List<Song> bookmarkedSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnFirst = (TextView) findViewById(R.id.btnFirst);
        this.btnLast = (TextView) findViewById(R.id.btnLast);
        this.btnSongs = (TextView) findViewById(R.id.btnSongs);
        this.lstBookmarked = (ListView) findViewById(R.id.lstBookmarked);
        this.txtBookmarkTitle = (TextView) findViewById(R.id.txtBookmarkTitle);

        this.databaseAccess = DatabaseAccess.getInstance(this);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/baamini.ttf");
        this.btnFirst.setTypeface(typeface);
        this.btnLast.setTypeface(typeface);
        this.btnSongs.setTypeface(typeface);
        this.txtBookmarkTitle.setTypeface(typeface);

        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSong(0);
            }
        });

        btnSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSong(1);
            }
        });

        lstBookmarked.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = bookmarkedSongs.get(position);
                showSong(song.getNumber());
            }
        });
        btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSong(101);
            }
        });
    }

    private void showSong(int no) {
        Intent intent = new Intent(MainActivity.this, SongActivity.class);
        intent.putExtra("SONG_NUMBER", no);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseAccess.open();
        bookmarkedSongs = databaseAccess.getBookmarkedSongs();
        ArrayAdapter<Song> adapter = new CustomAdapter(this, bookmarkedSongs);
        this.lstBookmarked.setAdapter(adapter);
        databaseAccess.close();
    }

    class CustomAdapter extends ArrayAdapter<Song> {
        List<Song> songs;

        public CustomAdapter(Context context, List<Song> objects) {
            super(context, 0, objects);
            this.songs = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_list, parent, false);
                TextView txtNumber = (TextView) convertView.findViewById(R.id.txtNumber);
                TextView txtSong = (TextView) convertView.findViewById(R.id.txtSong);

                txtNumber.setTypeface(typeface);
                txtSong.setTypeface(typeface);
            }

            TextView txtNumber = (TextView) convertView.findViewById(R.id.txtNumber);
            TextView txtSong = (TextView) convertView.findViewById(R.id.txtSong);

            Song song = songs.get(position);
            txtNumber.setText(String.format("%02d",song.getNumber()));
            txtSong.setText(song.toString());

            return convertView;
        }
    }
}
