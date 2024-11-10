// NovelListFragment.java
package com.example.jaime_lopez_novelas_con_fragmentos_widgets.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaime_lopez_novelas_con_fragmentos_widgets.R;
import com.example.jaime_lopez_novelas_con_fragmentos_widgets.activity.AddReviewActivity;
import com.example.jaime_lopez_novelas_con_fragmentos_widgets.domain.Novel;
import com.example.jaime_lopez_novelas_con_fragmentos_widgets.ui.mainNovel.NovelAdapter;
import com.example.jaime_lopez_novelas_con_fragmentos_widgets.ui.mainNovel.NovelViewModel;

import java.util.List;

public class NovelListFragment extends Fragment implements NovelAdapter.OnNovelClickListener {

    private TextView titleTextView, authorTextView, synopsisTextView;
    private Button favoriteButton, reviewButton;
    private NovelViewModel novelViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel_detail, container, false);

        titleTextView = view.findViewById(R.id.text_view_title);
        authorTextView = view.findViewById(R.id.text_view_author);
        synopsisTextView = view.findViewById(R.id.text_view_synopsis);
        favoriteButton = view.findViewById(R.id.favorite_button);
        reviewButton = view.findViewById(R.id.review_button);

        novelViewModel = new ViewModelProvider(this).get(NovelViewModel.class);

        String novelId = getArguments() != null ? getArguments().getString("novelId") : null;

        if (novelId != null) {
            novelViewModel.getNovelById(novelId).observe(getViewLifecycleOwner(), novel -> {
                if (novel != null) {
                    displayNovelDetails(novel);

                    // Configuración del botón de favoritos
                    favoriteButton.setText(novel.isFavorite() ? "Eliminar de Favoritos" : "Añadir a Favoritos");
                    favoriteButton.setOnClickListener(v -> {
                        novel.setFavorite(!novel.isFavorite());
                        novelViewModel.updateFavoriteStatus(novel);  // Actualiza el favorito en la base de datos
                        favoriteButton.setText(novel.isFavorite() ? "Eliminar de Favoritos" : "Añadir a Favoritos");
                    });

                    // Configuración del botón de reseña
                    reviewButton.setOnClickListener(v -> {
                        Intent intent = new Intent(getActivity(), AddReviewActivity.class);
                        intent.putExtra("EXTRA_NOVEL_ID", novel.getId());
                        intent.putExtra("EXTRA_NOVEL_NAME", novel.getTitle());
                        startActivity(intent);
                    });
                }
            });
        }

        return view;
    }

    private void displayNovelDetails(Novel novel) {
        titleTextView.setText(novel.getTitle());
        authorTextView.setText("Autor: " + novel.getAuthor());
        synopsisTextView.setText(novel.getSynopsis());
    }

    @Override
    public void onNovelClick(Novel novel) {
        // Navegar al detalle de la novela al hacer clic en el título
        if (getActivity() instanceof OnNovelSelectedListener) {
            ((OnNovelSelectedListener) getActivity()).onNovelSelected(novel);
        }
    }

    @Override
    public void onFavoriteClick(Novel novel) {
        // Manejar la acción de favorito
        novel.setFavorite(!novel.isFavorite());
        novelViewModel.updateFavoriteStatus(novel);
    }

    @Override
    public void onReviewClick(Novel novel) {
        // Navegar a la actividad de agregar reseña
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), AddReviewActivity.class);
            intent.putExtra("EXTRA_NOVEL_ID", novel.getId());
            intent.putExtra("EXTRA_NOVEL_NAME", novel.getTitle());
            startActivity(intent);
        }
    }

    // Interfaz para manejar la selección de novelas
    public interface OnNovelSelectedListener {
        void onNovelSelected(Novel novel);
    }
}
