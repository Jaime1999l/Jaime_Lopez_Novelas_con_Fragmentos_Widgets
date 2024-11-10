package com.example.jaime_lopez_novelas_con_fragmentos_widgets.ui.favoritesNovel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaime_lopez_novelas_con_fragmentos_widgets.R;
import com.example.jaime_lopez_novelas_con_fragmentos_widgets.domain.Novel;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.NovelHolder> {
    private List<Novel> novels = new ArrayList<>();

    @NonNull
    @Override
    public NovelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.novel_item_favorites, parent, false);
        return new NovelHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelHolder holder, int position) {
        Novel currentNovel = novels.get(position);
        holder.textViewTitle.setText(currentNovel.getTitle());
        holder.textViewAuthor.setText(currentNovel.getAuthor());
        holder.imageFavorite.setImageResource(
                currentNovel.isFavorite() ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24
        );
    }

    @Override
    public int getItemCount() {
        return novels.size();
    }

    public void setNovels(List<Novel> novels) {
        this.novels = novels;
        notifyDataSetChanged();
    }

    class NovelHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewAuthor;
        private final ImageView imageFavorite;

        public NovelHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAuthor = itemView.findViewById(R.id.text_view_author);
            imageFavorite = itemView.findViewById(R.id.image_favorite);
        }
    }
}
