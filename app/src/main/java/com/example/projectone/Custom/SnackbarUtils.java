package com.example.projectone.Custom;





import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorRes;

import com.example.projectone.R;
import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtils {


    /**
     * Muestra un Snackbar con un mensaje corto.
     *
     * @param view      La vista en la que se mostrará el Snackbar.
     * @param message   El ID del recurso de cadena del mensaje a mostrar.
     * @param color   El color en la que se mostrara el Snackbar.
     */
    @SuppressLint("ResourceType")
    public static void showShortSnackbar(View view, String message, @ColorRes int color) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(color);
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();

    }
    /**
     * Muestra un Snackbar con un mensaje largo.
     *
     * @param view      La vista en la que se mostrará el Snackbar.
     * @param message   El ID del recurso de cadena del mensaje a mostrar.
     * @param color   El color en la que se mostrara el Snackbar.
     */

    @SuppressLint("ResourceType")
    public static void showLongSnackbar(View view, String message, @ColorRes int color) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(color);
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();

    }

}
