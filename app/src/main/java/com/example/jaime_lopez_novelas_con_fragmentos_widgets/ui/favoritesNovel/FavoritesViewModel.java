package com.example.jaime_lopez_novelas_con_fragmentos_widgets.ui.favoritesNovel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jaime_lopez_novelas_con_fragmentos_widgets.domain.Novel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoritesViewModel extends ViewModel {
    private FirebaseFirestore db;
    private MutableLiveData<List<Novel>> favoriteNovelsLiveData;

    public FavoritesViewModel() {
        db = FirebaseFirestore.getInstance();
        favoriteNovelsLiveData = new MutableLiveData<>();
        loadFavoriteNovels();
    }

    public LiveData<List<Novel>> getFavoriteNovels() {
        return favoriteNovelsLiveData;
    }

    private void loadFavoriteNovels() {
        db.collection("novelas").whereEqualTo("favorite", true).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Novel> favoriteNovels = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Novel novel = document.toObject(Novel.class);
                    novel.setId(document.getId());
                    favoriteNovels.add(novel);
                }
                favoriteNovelsLiveData.setValue(favoriteNovels);
            } else {
                Log.d("FavoritesViewModel", "Error al obtener novelas favoritas: ", task.getException());
            }
        });
    }
}
