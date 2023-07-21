package sg.edu.rp.c346.id22035802.myndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity {

    private EditText etTitle, etSinger, etReleasedYear, etRating;
    private Button btnInsert, btnShowList;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etTitle = findViewById(R.id.et_title);
        etSinger = findViewById(R.id.et_singer);
        etReleasedYear = findViewById(R.id.et_released_year);
        etRating = findViewById(R.id.et_rating);
        btnInsert = findViewById(R.id.btn_insert);
        btnShowList = findViewById(R.id.btn_show_list);

        db = new DatabaseHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertSong();
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSongList();
            }
        });
    }

    private void insertSong() {
        String title = etTitle.getText().toString().trim();
        String singer = etSinger.getText().toString().trim();
        int releasedYear = Integer.parseInt(etReleasedYear.getText().toString().trim());
        int rating = Integer.parseInt(etRating.getText().toString().trim());

        if (title.isEmpty() || singer.isEmpty() || etReleasedYear.getText().toString().trim().isEmpty() ||
                etRating.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Calendar instance for the current date
        Calendar calendar = Calendar.getInstance();

        // Set the released year and month (January is 0, so subtract 1)
        calendar.set(Calendar.YEAR, releasedYear);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);

        // Create a new ToDoItem with the input data
        ToDoItem song = new ToDoItem(title, singer, calendar, rating);

        // Insert the song into the database
        boolean isInserted = db.insertSong(song);

        if (isInserted) {
            Toast.makeText(this, "Song inserted successfully", Toast.LENGTH_SHORT).show();
            clearInputFields();
        } else {
            Toast.makeText(this, "Failed to insert song", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSongList() {
        // Start the SecondActivity to show the list of songs
        // (Implement this in the SecondActivity class)
    }

    private void clearInputFields() {
        etTitle.setText("");
        etSinger.setText("");
        etReleasedYear.setText("");
        etRating.setText("");
    }
}
    }
}