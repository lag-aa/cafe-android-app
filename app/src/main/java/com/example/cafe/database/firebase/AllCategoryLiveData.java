package com.example.cafe.database.firebase;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.cafe.models.Category;
import com.example.cafe.utilits.constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AllCategoryLiveData extends MutableLiveData<List<Category>> {
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private FirebaseDatabase mInstance;


    public AllCategoryLiveData() {
        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();
        mInstance = FirebaseDatabase.getInstance();

    }

    final Object listener = new ValueEventListener() {
        LinkedList category = new LinkedList();
//        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            category.clear();
            try {
                for (Map.Entry<String, String> item : ((HashMap<String, String>) snapshot.child(constants.NODE_CATEGORY).getValue(Object.class)).entrySet()) {
//                    Получение данных из таблицы НовостиПользователи
//                    if (((HashMap) ((Object) item.getValue())).get(constants.USER_ID).equals(UID)) {
//                        Object id = ((HashMap) ((Object) item.getValue())).get(constants.NEWS_ID);
//                        if (id == null && !(id instanceof String))
//                            continue;
//                        for (Map.Entry<String, String> item_news : ((HashMap<String, String>) snapshot.child(constants.NODE_NEWS).getValue(Object.class)).entrySet()) {
//                            HashMap<String, String> data = ((HashMap) ((Object) item_news.getValue()));
//                            if ((data != null || !data.isEmpty()) && item_news.getKey().equals(id.toString())) {
//
//                                String header = (data.get(constants.NEWS_HEADER) instanceof String && data.get(constants.NEWS_HEADER) != null) ? data.get(constants.NEWS_HEADER) : "";
//                                String date = (data.get(constants.NEWS_DATA) != null && constants.NEWS_DATA instanceof String) ? data.get(constants.NEWS_DATA) : "";
//                                String media = (data.get(constants.NEWS_MEDIA) != null && constants.NEWS_MEDIA instanceof String) ? data.get(constants.NEWS_MEDIA) : "";
//                                String desc = (data.get(constants.NEWS_DESC) != null && constants.NEWS_DESC instanceof String) ? data.get(constants.NEWS_DESC) : "";
//                                news.add(new News(header, date, media, desc));
//                            }
//                            //TODO сделать валидацию
//                        }
//                    }
                    HashMap<String, String> data = ((HashMap) ((Object) item.getValue()));
                    String category_id = data.get(constants.CATEGORY_ID);
                    String category_logo = data.get(constants.CATEGORY_LOGO);
                    String category_name = data.get(constants.CATEGORY_NAME);

                    category.add(new Category(category_name, category_logo, category_id));

                }
                if (!category.isEmpty())
                    postValue(category);
            } catch (Exception e) {
                Log.d(constants.TAG, e.getMessage());
            }
        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {
            Log.d(constants.TAG, error.getMessage());
        }

    };

    @Override
    protected void onActive() {
        super.onActive();
        mReference.addValueEventListener((ValueEventListener) listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        mReference.removeEventListener((ValueEventListener) listener);
    }
}