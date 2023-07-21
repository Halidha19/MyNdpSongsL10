package sg.edu.rp.c346.id22035802.myndpsongs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
public class CustomAdapter extends ArrayAdapter<ToDoItem> {

    private Context context;
    private ArrayList<ToDoItem> songsList;

    public CustomAdapter(Context context, ArrayList<ToDoItem> songsList) {
        super(context, 0, songsList);
        this.context = context;
        this.songsList = songsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            viewHolder.tvSinger = convertView.findViewById(R.id.tv_singer);
            viewHolder.tvRating = convertView.findViewById(R.id.tv_rating);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ToDoItem song = songsList.get(position);

        viewHolder.tvTitle.setText(song.getTitle());
        viewHolder.tvSinger.setText(song.getSinger());
        viewHolder.tvRating.setText(String.valueOf(song.getRating()));

        return convertView;
    }

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvSinger;
        TextView tvRating;
    }
}
