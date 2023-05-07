package com.example.myshop.catalog.categorycard;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.R;

public class CategoryCardViewHolder extends RecyclerView.ViewHolder {
    private View view;
    public ImageView categoryImage;
    public TextView categoryName;
    public Button btnCategoryDelete;
    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        view=itemView;
        categoryName=itemView.findViewById(R.id.categoryName);
        categoryImage=itemView.findViewById(R.id.categoryImage);
        btnCategoryDelete=itemView.findViewById(R.id.btnCategoryDelete);
    }
    public View getView() {
        return view;
    }
}
