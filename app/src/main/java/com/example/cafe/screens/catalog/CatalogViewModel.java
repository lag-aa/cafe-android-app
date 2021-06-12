package com.example.cafe.screens.catalog;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cafe.database.firebase.AllProductLiveData;
import com.example.cafe.database.firebase.AppRepository;
import com.example.cafe.models.Category;
import com.example.cafe.models.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CatalogViewModel extends AndroidViewModel {
    private AppRepository repository;
    private MutableLiveData<List<Category>> allCategory;
    private MutableLiveData<List<Product>> allProduct;
//    private

    public CatalogViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new AppRepository();
        allCategory = repository.getAllCategory();
        allProduct = repository.getAllProduct();
    }

    public MutableLiveData<List<Product>> getAllProduct(String categoryId) {
        ((AllProductLiveData)allProduct).setCategoryId(categoryId);
        return (MutableLiveData)allProduct;
    }

    public MutableLiveData<List<Category>> getAllCategory() {
        return allCategory;
    }

    public void insertProduct(Product product){
        repository.insertProduct(product);
    }
}