package sg.edu.rp.c346.id22035802.myndpsongs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
public class ThirdActivity extends AppCompatActivity {
    private EditText etTitle, etSinger, etReleasedYear, etRating;
    private Button btnSave, btnDelete;
    private DatabaseHelper db;
    private ToDoItem selectedSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etTitle = findViewById(R.id.et_title);
        etSinger = findViewById(R.id.et_singer);
        etReleasedYear = findViewById(R.id.et_released_year);
        etRating = findViewById(R.id.et_rating);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);

        db = new DatabaseHelper(this);

        // Retrieve the selected song from the intent extras
        selectedSong = (ToDoItem) getIntent().getSerializableExtra("selectedSong");

        // Populate the EditText fields with the song details
        etTitle.setText(selectedSong.getTitle());
        etSinger.setText(selectedSong.getSinger());
        etReleasedYear.setText(String.valueOf(selectedSong.getDate().get(Calendar.YEAR)));
        etRating.setText(String.valueOf(selectedSong.getRating()));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSong();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSong();
            }
        });
    }

    private void updateSong() {
        String title = etTitle.getText().toString().trim();
        String singer = etSinger.getText().toString().trim();
        int releasedYear = Integer.parseInt(etReleasedYear.getText().toString().trim());
        int rating = Integer.parseInt(etRating.getText().toString().trim());

        if (title.isEmpty() || singer.isEmpty() || etReleasedYear.getText().toString().trim().isEmpty() ||
                etRating.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Calendar instance for the updated released year
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, releasedYear);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);

        // Update the selected song details
        selectedSong.setTitle(title);
        selectedSong.setSinger(singer);
        selectedSong.setDate(calendar);
        selectedSong.setRating(rating);

        // Update the song in the database
        boolean isUpdated = db.updateSong(selectedSong);

        if (isUpdated) {
            Toast.makeText(this, "Song updated successfully", Toast.LENGTH_SHORT).show();
            finish(); // Return to the SecondActivity after updating
        } else {
            Toast.makeText(this, "Failed to update song", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteSong() {
        // Delete the selected song from the database
        boolean isDeleted = db.deleteSong(selectedSong.getId());

        if (isDeleted) {
            Toast.makeText(this, "Song deleted successfully", Toast.LENGTH_SHORT).show();
            finish(); // Return to the SecondActivity after deleting
        } else {
            Toast.makeText(this, "Failed to delete song", Toast.LENGTH_SHORT).show();
        }
    }
}

