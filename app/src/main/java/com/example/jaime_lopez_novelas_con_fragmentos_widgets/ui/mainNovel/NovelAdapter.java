package com.example.jaime_lopez_novelas_con_fragmentos_widgets.ui.mainNovel;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaime_lopez_novelas_con_fragmentos_widgets.R;
import com.example.jaime_lopez_novelas_con_fragmentos_widgets.activity.ReviewActivity;
import com.example.jaime_lopez_novelas_con_fragmentos_widgets.domain.Novel;

import java.util.List;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.NovelHolder> {

    private List<Novel> novelList;

    public NovelAdapter(List<Novel> novels) {
        this.novelList = novels;
    }

    @NonNull
    @Override
    public NovelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.novel_item, parent, false);
        return new NovelHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelHolder holder, int position) {
        Novel currentNovel = novelList.get(position);
        holder.textViewTitle.setText(currentNovel.getTitle());
        holder.textViewAuthor.setText(currentNovel.getAuthor());

        if (currentNovel.getImageUri() != null && !currentNovel.getImageUri().isEmpty()) {
            holder.imageViewCover.setVisibility(View.VISIBLE);
            holder.imageViewCover.setImageURI(Uri.parse(currentNovel.getImageUri()));
        } else {
            holder.imageViewCover.setVisibility(View.GONE);
        }

        holder.buttonFavorite.setOnClickListener(v -> {
            currentNovel.setFavorite(!currentNovel.isFavorite());
        });

        holder.buttonReview.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ReviewActivity.class);
            intent.putExtra("EXTRA_NOVEL_ID", currentNovel.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return novelList.size();
    }

    public class NovelHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewAuthor;
        private ImageView imageViewCover;
        private Button buttonFavorite;
        private Button buttonReview;

        public NovelHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAuthor = itemView.findViewById(R.id.text_view_author);
            imageViewCover = itemView.findViewById(R.id.image_view_cover);
            buttonFavorite = itemView.findViewById(R.id.button_favorite);
            buttonReview = itemView.findViewById(R.id.button_review);
        }
    }
}
