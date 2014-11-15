package com.vielengames.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vielengames.R;
import com.vielengames.VielenGamesPrefs;
import com.vielengames.utils.ViewUtils;

import javax.inject.Inject;

public final class AboutActivity extends BaseActivity {

    @Inject
    VielenGamesPrefs prefs;

    private int developerModeClickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        final AboutListAdapter adapter = new AboutListAdapter(this);
        ListView listView = ViewUtils.findView(this, R.id.about_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String url = (String) adapter.getItem(position);
                if (url != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    updateDeveloperMode();
                }
            }
        });
    }

    private void updateDeveloperMode() {
        developerModeClickCount++;
        if (developerModeClickCount == 5 && !prefs.getDeveloperMode()) {
            prefs.setDeveloperMode(true);
            Toast.makeText(this, "Developer mode on", Toast.LENGTH_SHORT).show();
        }
    }
}
