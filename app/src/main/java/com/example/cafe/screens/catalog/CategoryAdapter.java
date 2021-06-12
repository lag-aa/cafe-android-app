package com.example.cafe.screens.catalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.cafe.R;
import com.example.cafe.models.Category;
import com.example.cafe.utilits.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private OnItemClickListener OnItemClickListener;
    private Context context;


    public CategoryAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;

    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public Category getCategory(int position){
        return categories.get(position);
    }

    @NonNull
    @NotNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryAdapter.CategoryViewHolder holder, int position) {

        holder.bind(categories.get(position));
        Category model = categories.get(position);
        holder.itemView.setTag(categories);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(model.logo_id)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.logo);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    public void setOnCategoryClickListener(OnItemClickListener onCategoryClickListener) {
        this.OnItemClickListener = onCategoryClickListener;
    }

    final class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        private OnItemClickListener onItemClickListener;
        private final ImageView logo;
        private final TextView name;


        public CategoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            logo = (ImageView) itemView.findViewById(R.id.ci_img_v_logo);
            name = (TextView) itemView.findViewById(R.id.ci_txt_v_category_name);
            itemView.setOnClickListener(this);
        }

        private void bind(Category category){
            name.setText(category.name);
        }

        @Override
        public void onClick(View v) {
            OnItemClickListener.onNewsClick(v, getAbsoluteAdapterPosition());
        }
    }

    interface OnItemClickListener {
        void onNewsClick(View view, int position);
    }
}