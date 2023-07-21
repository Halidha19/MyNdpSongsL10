package sg.edu.rp.c346.id22035802.myndpsongs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity  extends AppCompatActivity {
    private ListView listView;
    private Button btnFilter;
    private DatabaseHelper db;
    private CustomAdapter adapter;
    private ArrayList<ToDoItem> songsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.listView);
        btnFilter = findViewById(R.id.btn_filter);
        db = new DatabaseHelper(this);
        songsList = new ArrayList<>();

        // Populate the list of songs from the database
        songsList = db.getAllSongs();

        // Initialize the adapter with the list of songs
        adapter = new CustomAdapter(this, songsList);
        listView.setAdapter(adapter);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Filter songs with 5-star rating and update the list view
                filterFiveStarSongs();
            }
        });

        // Handle item click to navigate to the ThirdActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoItem selectedSong = songsList.get(position);
                openThirdActivity(selectedSong);
            }
        });
    }

    private void filterFiveStarSongs() {
        // Create a new list to store 5-star rated songs
        ArrayList<ToDoItem> fiveStarSongs = new ArrayList<>();

        // Filter songs with 5-star rating
        for (ToDoItem song : songsList) {
            if (song.getRating() == 5) {
                fiveStarSongs.add(song);
            }
        }

        // Update the list view with 5-star rated songs
        adapter = new CustomAdapter(this, fiveStarSongs);
        listView.setAdapter(adapter);
    }

    private void openThirdActivity(ToDoItem song) {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra("selectedSong", song);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list view when returning from the ThirdActivity
        songsList = db.getAllSongs();
        adapter = new CustomAdapter(this, songsList);
        listView.setAdapter(adapter);
    }
}
}
