package com.example.pyoyectofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.List;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;
    private RequestQueue requestQueue;

    public PostAdapter(Context context, List<Post> postList, RequestQueue requestQueue) {
        this.context = context;
        this.postList = postList;
        this.requestQueue = requestQueue;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_list_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.txtTitulo.setText(post.getTitulo());
        holder.txtCP.setText(String.valueOf(post.getCP()));
        holder.txtDireccion.setText(post.getUbicacion());
        holder.txtMunicipio.setText(post.getMunicipio());

        List<CarouselItem> carouselItems = new ArrayList<>();
        for (String imageUrl : post.getImageUrls()) {
            carouselItems.add(new CarouselItem(imageUrl));
        }
        holder.imageCarousel.setData(carouselItems);
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0;
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtCP, txtDireccion, txtMunicipio;
        ImageCarousel imageCarousel;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtCP = itemView.findViewById(R.id.txtCP);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
            txtMunicipio = itemView.findViewById(R.id.txtMunicipio);
            imageCarousel = itemView.findViewById(R.id.imageView);
        }
    }
}


