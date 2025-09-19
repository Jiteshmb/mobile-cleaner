package com.hivas.mobilecleaner.onboarding;

import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hivas.mobilecleaner.R;
import java.util.List;

public class JunkCategoryAdapter extends RecyclerView.Adapter<JunkCategoryAdapter.ViewHolder> {

    private List<JunkCategory> categories;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(JunkCategory category);
    }

    public JunkCategoryAdapter(List<JunkCategory> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_junk_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JunkCategory category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName, tvCategorySize;
        ImageView ivIcon, ivLock;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            tvCategorySize = itemView.findViewById(R.id.tv_category_size);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            ivLock = itemView.findViewById(R.id.iv_lock);
        }

        void bind(JunkCategory category) {
            tvCategoryName.setText(category.getName());

            String sizeText = category.getSize() > 0
                    ? Formatter.formatFileSize(itemView.getContext(), category.getSize())
                    : "0.00 B";
            tvCategorySize.setText(sizeText);

            ivIcon.setImageResource(category.getIconResId());
            ivLock.setVisibility(category.isUnlocked() ? View.GONE : View.VISIBLE);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCategoryClick(category);
                }
            });
        }
    }
}
