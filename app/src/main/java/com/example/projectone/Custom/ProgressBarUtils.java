package com.example.projectone.Custom;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.example.projectone.R;

public class ProgressBarUtils {

    private ProgressBar progressBar;

    public ProgressBarUtils(Activity activity) {
        // Obtener una referencia al ProgressBar en el layout de la actividad
        progressBar = activity.findViewById(R.id.progressBar);
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
