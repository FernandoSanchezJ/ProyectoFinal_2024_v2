package com.example.pyoyectofinal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsuarioFragment extends Fragment {
    private RecyclerView reciclerView;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);

        reciclerView = view.findViewById(R.id.post_reciclerView);
        reciclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadPosts();

        return view;
    }

    private void loadPosts() {
        String postsApiUrl = "http://192.168.137.1/ApiRentaLink/public/posts";
        requestQueue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET, postsApiUrl, null,
                postResponse -> {
                    ArrayList<HashMap<String, String>> postsList = new ArrayList<>();
                    try {
                        for (int i = 0; i < postResponse.length(); i++) {
                            JSONObject postObject = postResponse.getJSONObject(i);

                            HashMap<String, String> postMap = new HashMap<>();
                            postMap.put("titulo", postObject.getString("titulo"));
                            postMap.put("descripcion", postObject.getString("descripcion"));
                            postMap.put("ubicacion", postObject.getString("ubicacion"));
                            postMap.put("precio", postObject.getString("precio"));
                            postMap.put("fecha_post", postObject.getString("fecha_post"));
                            postMap.put("municipio", postObject.getString("municipio"));
                            postMap.put("ciudad", postObject.getString("ciudad"));
                            postMap.put("CP", postObject.getString("CP"));
                            postMap.put("calificacion", postObject.getString("calificacion"));
                            postMap.put("dimenciones", postObject.getString("dimenciones"));

                            // Agrega URLs de imágenes si están disponibles
                            if (postObject.has("imagen1")) {
                                postMap.put("imagen1", postObject.getString("imagen1"));
                            }
                            if (postObject.has("imagen2")) {
                                postMap.put("imagen2", postObject.getString("imagen2"));
                            }

                            postsList.add(postMap);
                        }

                        processPosts(postsList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error al procesar los datos de publicaciones", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("API_ERROR", "Error al cargar las publicaciones: " + error.getMessage());
                    Toast.makeText(getActivity(), "Error al cargar las publicaciones", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(postRequest);
    }

    private void processPosts(ArrayList<HashMap<String, String>> postsList) {
        ArrayList<Post> postObjects = new ArrayList<>();
        for (HashMap<String, String> postMap : postsList) {
            // Extraer datos del mapa
            String titulo = postMap.getOrDefault("titulo", "Sin título");
            String CP = postMap.getOrDefault("CP", "00000");
            String ubicacion = postMap.getOrDefault("ubicacion", "Sin ubicación");
            String municipio = postMap.getOrDefault("municipio", "Desconocido");

            // Crear el objeto Post
            Post post = new Post(
                    titulo,
                    CP,
                    ubicacion,
                    municipio,
                    new ArrayList<>()
            );
            postObjects.add(post);
        }

        // Configurar el adaptador
        requireActivity().runOnUiThread(() -> {
            PostAdapter adapter = new PostAdapter(getContext(), postObjects, requestQueue);
            reciclerView.setAdapter(adapter); // Asigna el adaptador al RecyclerView
        });
    }
}
