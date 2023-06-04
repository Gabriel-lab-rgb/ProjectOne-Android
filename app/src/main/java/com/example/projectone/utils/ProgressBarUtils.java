package com.example.projectone.utils;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.example.projectone.R;

public class ProgressBarUtils {

    private ProgressBar progressBar;

    public ProgressBarUtils(View view) {
        // Obtener una referencia al ProgressBar en el layout de la actividad
        progressBar = view.findViewById(R.id.progressBar);
    }

    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

   /* ProgressBar progressBar = findViewById(R.id.progressBar);
      progressBar.setIndeterminate(true);
      progressBar.setCancelable(false);*/
}
