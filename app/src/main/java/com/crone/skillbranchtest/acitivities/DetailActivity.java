package com.crone.skillbranchtest.acitivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.utils.MyConfig;
import com.crone.skillbranchtest.utils.SetIntentParams;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private CollapsingToolbarLayout mCollapsToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mCollapsToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);


        ImageView expandedImage = (ImageView) findViewById(R.id.expanded_image);


        //Make statusbar Transparent when Expanded Appbar
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getAttributes().flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        Intent intent = getIntent();
        switch ((int)intent.getLongExtra("house_id",-1)) {
            case MyConfig.STARK_ID:
                Picasso.with(this).load(R.drawable.starks).fit().into(expandedImage);
                break;
            case MyConfig.LANNISTER_ID:
                Picasso.with(this).load(R.drawable.lannister).fit().into(expandedImage);
                break;
            case MyConfig.TARGARYEN_ID:
                Picasso.with(this).load(R.drawable.targarien).fit().into(expandedImage);
                break;
        }


        TextView words = (TextView) findViewById(R.id.words_text);
        words.setText(intent.getStringExtra("words"));

        TextView alies = (TextView) findViewById(R.id.aliases_text);
        alies.setText(intent.getStringExtra("alies"));


        mCollapsToolbar.setTitle(intent.getStringExtra("name"));

        TextView born = (TextView) findViewById(R.id.born_text);
        born.setText(intent.getStringExtra("born"));

        TextView title = (TextView) findViewById(R.id.titles_text);
        title.setText(intent.getStringExtra("title"));

        if (intent.getStringExtra("died") != null && intent.getStringExtra("died").length() > 0) {
            showSnackbar("He was Dead: " + intent.getStringExtra("died"));
        }


        TextView fatherText = (TextView) findViewById(R.id.father);
        TextView motherText = (TextView) findViewById(R.id.mother);


        Button fatherCheck = (Button) findViewById(R.id.father_check);
        Button motherCheck = (Button) findViewById(R.id.mother_check);

        if (intent.getLongExtra("father",-1) != -1 ) {
            final long id = intent.getLongExtra("father",-1);
            fatherText.setVisibility(View.VISIBLE);
            fatherCheck.setVisibility(View.VISIBLE);
            fatherCheck.setText(intent.getStringExtra("father_name"));
            fatherCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentReOpen = new Intent(DetailActivity.this, DetailActivity.class);
                    SetIntentParams.setParams(intentReOpen,id);
                    startActivity(intentReOpen);
                }
            });
        }

        if (intent.getLongExtra("mother",-1) != -1) {
            final long id = intent.getLongExtra("mother",-1);
            motherText.setVisibility(View.VISIBLE);
            motherCheck.setText(intent.getStringExtra("mother_name"));
            motherCheck.setVisibility(View.VISIBLE);
            motherCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentReOpen = new Intent(DetailActivity.this, DetailActivity.class);
                    SetIntentParams.setParams(intentReOpen,id);
                    startActivity(intentReOpen);
                }
            });
        }


        //Background of Buttons
        fatherCheck.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        motherCheck.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG).show();
    }
}


