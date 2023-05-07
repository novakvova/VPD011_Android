package com.example.myshop.catalog.categorycard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myshop.R;
import com.example.myshop.application.HomeApplication;
import com.example.myshop.contants.Urls;
import com.example.myshop.dto.category.CategoryItemDTO;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryCardViewHolder> {
    private List<CategoryItemDTO> categories;
    private final OnCategoryClickListener onClickDelete;
    private final OnCategoryClickListener onClickUpdate;

    public CategoriesAdapter(List<CategoryItemDTO> categories,
                             OnCategoryClickListener onClickDelete,
                             OnCategoryClickListener onClickUpdate) {
        this.categories = categories;
        this.onClickDelete = onClickDelete;
        this.onClickUpdate = onClickUpdate;
    }
    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_view, parent, false);
        return new CategoryCardViewHolder(layout);
    }
    @Override
    public void onBindViewHolder(@NonNull CategoryCardViewHolder holder, int position) {
        if(categories!=null && position<categories.size()) {
            CategoryItemDTO cat = categories.get(position);
            holder.categoryName.setText(cat.getName());
            String url = Urls.BASE+cat.getImage();
            Glide.with(HomeApplication.getAppContext())
                    .load(url)
                    .apply(new RequestOptions().override(600))
                    .into(holder.categoryImage);
            holder.btnCategoryDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickDelete.onButtonClick(cat);
                }
            });
            holder.btnCategoryUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickUpdate.onButtonClick(cat);
                }
            });

        }
    }
    @Override
    public int getItemCount() {
        return categories.size();
    }
}
