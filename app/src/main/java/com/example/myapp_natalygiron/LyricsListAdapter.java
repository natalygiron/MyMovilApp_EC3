package com.example.myapp_natalygiron;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp_natalygiron.model.Lyrics;

import java.util.ArrayList;
import java.util.List;

public class LyricsListAdapter extends RecyclerView.Adapter<LyricsListAdapter.ViewHolder> {

    private ArrayList<Lyrics> dataset;

    public LyricsListAdapter() {
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyric_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lyrics lyrics = dataset.get(position);
        holder.quoteView.setText('"' + lyrics.getQuote() + '"');
        holder.songView.setText("- " +lyrics.getSong());
        holder.albumView.setText(lyrics.getAlbum());
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addLyricsList(List<Lyrics> lyricsList) {
        dataset.addAll(lyricsList);
        notifyDataSetChanged();
    }

    public void removeData() {
        dataset.clear();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
       // private ImageView photoImageView;
        private TextView quoteView;
        private TextView songView;
        private TextView albumView;

        public ViewHolder(View itemView) {
            super(itemView);

            //photoImageView = (ImageView) itemView.findViewById(R.id.photo_image_view);
            quoteView = (TextView) itemView.findViewById(R.id.txt_quote);
            songView = (TextView) itemView.findViewById(R.id.txt_song);
            albumView = (TextView) itemView.findViewById(R.id.txt_album);

        }
    }
}
