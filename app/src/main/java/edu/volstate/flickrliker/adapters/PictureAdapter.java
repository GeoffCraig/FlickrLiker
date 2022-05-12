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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.volstate.flickrliker.R;
import edu.volstate.flickrliker.controllers.PictureController;
import edu.volstate.flickrliker.models.Favorites;
import edu.volstate.flickrliker.models.FlickrPicture;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FlickrPicture> flickrPictureArrayList;
    private ArrayList<Favorites> favoritesArrayList;
    private RecyclerView recyclerView;

    public PictureAdapter(Context context, ArrayList<FlickrPicture> flickrPictureArrayList, ArrayList<Favorites> favoritesArrayList, RecyclerView recyclerView) {
        this.context = context;
        this.flickrPictureArrayList = flickrPictureArrayList;
        this.favoritesArrayList = favoritesArrayList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public PictureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureAdapter.ViewHolder holder, int position) {
        FlickrPicture flickrPicture = flickrPictureArrayList.get(position);
        holder.tvPictureDescription.setText(flickrPicture.getTitle());
        String imageURL = "https://live.staticflickr.com/" +
                flickrPicture.getServer() + "/" +
                flickrPicture.getId() + "_" +
                flickrPicture.getSecret() + ".jpg";
        Glide
                .with(context)
                .load(imageURL)
                .fitCenter()
                .into(holder.ivPicture);
//        Picasso.get().load(imageURL).fit().centerInside().into(holder.ivPicture);
        if(flickrPicture.isFavorite()) {
            holder.ibFavorite.setTag(R.drawable.favorites);
            holder.ibFavorite.setImageResource(R.drawable.favorites);
        } else {
            holder.ibFavorite.setTag(R.drawable.favorite_border);
        }
        holder.ibFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureController.setFavorite(holder.ibFavorite, favoritesArrayList, flickrPicture, view, recyclerView);
            }
        });
 }

    @Override
    public int getItemCount() {
        return flickrPictureArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPicture;
        public TextView tvPictureDescription;
        public ImageButton ibFavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.iv_Picture);
            tvPictureDescription = itemView.findViewById(R.id.tv_Picture_Description);
            ibFavorite = itemView.findViewById(R.id.ib_Picture_Favorite);
        }
    }
}
