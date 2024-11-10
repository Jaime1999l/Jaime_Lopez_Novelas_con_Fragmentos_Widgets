package com.example.jaime_lopez_novelas_con_fragmentos_widgets.ui.mainNovel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jaime_lopez_novelas_con_fragmentos_widgets.domain.Novel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NovelViewModel extends AndroidViewModel {

    private MutableLiveData<List<Novel>> novelListLiveData = new MutableLiveData<>();
    private FirebaseFirestore db;

    public NovelViewModel(@NonNull Application application) {
        super(application);
        db = FirebaseFirestore.getInstance();
        loadNovels();
    }

    public LiveData<List<Novel>> getAllNovels() {
        return novelListLiveData;
    }

    public LiveData<List<Novel>> getFavoriteNovels() {
        MutableLiveData<List<Novel>> favoriteNovelsLiveData = new MutableLiveData<>();
        db.collection("novelas").whereEqualTo("favorite", true).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Novel> favoriteNovels = task.getResult().toObjects(Novel.class);
                        favoriteNovelsLiveData.setValue(favoriteNovels);
                    }
                });
        return favoriteNovelsLiveData;
    }

    private void loadNovels() {
        db.collection("novelas").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Novel> novelList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Novel novel = document.toObject(Novel.class);
                    novel.setId(document.getId()); // Asigna el ID del documento a la novela
                    novelList.add(novel);
                }
                novelListLiveData.setValue(novelList);
            } else {
                Log.e("Firebase", "Error al obtener novelas: ", task.getException());
            }
        });
    }

}
