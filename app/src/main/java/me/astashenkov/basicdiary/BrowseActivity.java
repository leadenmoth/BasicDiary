package me.astashenkov.basicdiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BrowseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        //region Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Enable back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //endregion

        final Diary diary = BrowseAdapter(getIntent());

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowseActivity.this, EditActivity.class);
                intent.putExtra("diary", diary);
                startActivityForResult(intent, 1);
            }
        });
    }

    //Handle back arrow press as navigation up
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                BrowseAdapter(data);
            }
        }
    }

    protected Diary BrowseAdapter(Intent intent) {

        final TextView entryView = (TextView) findViewById(R.id.entry_view);

        Bundle extras = intent.getExtras();
        final Diary diary;
        if(extras != null){
            diary = (Diary) extras.getSerializable("diary");
        }else{
            diary = new Diary(-1, "", "", null, null);
        }

        getSupportActionBar().setTitle(diary.getTitle());

        entryView.setText(diary.getDescription());

        return diary;
    }
}
