package com.example.myshop.catalog;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myshop.BaseActivity;
import com.example.myshop.R;
import com.example.myshop.catalog.categorycard.CategoriesAdapter;
import com.example.myshop.dto.category.CategoryItemDTO;
import com.example.myshop.service.CategoryNetwork;
import com.example.myshop.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogActivity extends BaseActivity {

    CategoriesAdapter categoriesAdapter;
    private RecyclerView rcvCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        rcvCategories = findViewById(R.id.rcvCategories);
        rcvCategories.setHasFixedSize(true);
        rcvCategories.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        requestServer();
    }

    private void requestServer() {
        CommonUtils.showLoading();

        CategoryNetwork
                .getInstance()
                .getJsonApi()
                .list()
                .enqueue(new Callback<List<CategoryItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
                        CommonUtils.hideLoading();
                        List<CategoryItemDTO> data = response.body();
                        //CategoryItemDTO item = data.get(0);
                        categoriesAdapter = new CategoriesAdapter(data,
                                CatalogActivity.this::onClickDelete,
                                CatalogActivity.this::onClickUpdate);
                        rcvCategories.setAdapter(categoriesAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable t) {
                        CommonUtils.hideLoading();

                    }
                });

    }

    void onClickDelete(CategoryItemDTO category) {
        //Toast.makeText(this, "Видаляємо "+ category.getId(), Toast.LENGTH_SHORT).show();
        CommonUtils.showLoading();
        CategoryNetwork.getInstance()
                .getJsonApi()
                .delete(category.getId())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        CommonUtils.hideLoading();
                        Intent intent = new Intent(CatalogActivity.this, CatalogActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        CommonUtils.hideLoading();
                    }
                });
    }

    void onClickUpdate(CategoryItemDTO category) {
        Intent intent = new Intent(CatalogActivity.this, CategoryUpdateActivity.class);
        Bundle b = new Bundle();
        b.putInt("id", category.getId());
        intent.putExtras(b);
        startActivity(intent);
        finish();

    }
}