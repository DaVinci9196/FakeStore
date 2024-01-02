package org.microg.vending.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.vending.R;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MarketDeepLinkHandlerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.hint);
        builder.setMessage(R.string.hint_download_app);
        builder.setNegativeButton(android.R.string.ok, (dialog, which) -> {
            openWebPlayStore();
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(it -> finish());
        dialog.show();
    }

    private void openWebPlayStore() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            Map<String, String> params = getParamsFromUrl(uri.toString());
            String id = params.get("id");
            if (!TextUtils.isEmpty(id)) {
                String url = "https://play.google.com/store/apps/details?id=" + id; // The web page address to be opened
                // Create an Intent object, set Action to ACTION_VIEW, and set Data to the address of the web page to be opened.
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                // Check if any application can handle this intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    // If there is an application that can handle the intent, launch the browser
                    startActivity(intent);
                } else {
                    // If no application can handle this Intent, the corresponding prompt or processing logic will be given.
                    // For example, Toast prompts the user that there is no suitable application to open the web link.
                    Toast.makeText(this, R.string.no_app_to_open_link, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> params = new HashMap<>();
        try {
            URI uri = new URI(url);
            String query = uri.getQuery();

            if (query != null) {
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    int idx = pair.indexOf("=");
                    String key = idx > 0 ? pair.substring(0, idx) : pair;
                    String value = idx > 0 && pair.length() > idx + 1 ? pair.substring(idx + 1) : null;

                    params.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    @Override
    public void finish() {
        setResult(RESULT_CANCELED);
        super.finish();
    }
}