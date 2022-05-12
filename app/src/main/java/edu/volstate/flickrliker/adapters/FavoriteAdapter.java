package edu.volstate.flickrliker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.volstate.flickrliker.R;
import edu.volstate.flickrliker.controllers.FavoritesController;
import edu.volstate.flickrliker.controllers.PictureController;
import edu.volstate.flickrliker.models.Favorites;
import edu.volstate.flickrliker.models.FlickrPicture;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FlickrPicture> flickrPictureArrayList;
    private ArrayList<Favorites> favoritesArrayList;
    private RecyclerView recyclerView;

    public FavoriteAdapter(Context context, ArrayList<FlickrPicture> flickrPictureArrayList, ArrayList<Favorites> favoritesArrayList, RecyclerView recyclerView) {
        this.context = context;
        this.flickrPictureArrayList = flickrPictureArrayList;
        this.favoritesArrayList = favoritesArrayList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        Favorites favorites = favoritesArrayList.get(position);
        holder.tvFavoritePictureDescription.setText(favorites.getTitle());
        String imageURL = "https://live.staticflickr.com/" +
                favorites.getServer() + "/" +
                favorites.getId() + "_" +
                favorites.getSecret() + ".jpg";
        Glide
                .with(context)
                .load(imageURL)
                .fitCenter()
                .into(holder.ivFavoritePicture);
        holder.ibFavoriteFavorite.setTag(R.drawable.favorites);
        holder.ibFavoriteFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritesController.removeFavorite(holder.ibFavoriteFavorite, favoritesArrayList, favorites, view, recyclerView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFavoritePicture;
        TextView tvFavoritePictureDescription;
        ImageButton ibFavoriteFavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFavoritePicture = itemView.findViewById(R.id.iv_Favorite_Picture);
            tvFavoritePictureDescription = itemView.findViewById(R.id.tv_Favorite_Picture_Description);
            ibFavoriteFavorite = itemView.findViewById(R.id.ib_Favorite_Favorite);
        }
    }
}
