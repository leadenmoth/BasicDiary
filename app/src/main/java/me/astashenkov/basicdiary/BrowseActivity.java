package me.astashenkov.basicdiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class BrowseActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Diary diary;
    private TextView entryView;

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

        db = new DatabaseHelper(this);

        diary = BrowseAdapter(getIntent());

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowseActivity.this, EditActivity.class);
                intent.putExtra("diary", diary);
                startActivityForResult(intent, 2);
            }
        });

        Snackbar.make(entryView, "Diary created on " + diary.getDateCreated()
                + "\nDiary modified on " + diary.getDateModified(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    //Handle back arrow press as navigation up
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                BrowseAdapter(data);
                entryView = (TextView) findViewById(R.id.entry_view);
                Snackbar.make(entryView, "Diary saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    protected Diary BrowseAdapter(Intent intent) {

        entryView = (TextView) findViewById(R.id.entry_view);
        entryView.setMovementMethod(new ScrollingMovementMethod());

        Bundle extras = intent.getExtras();
        if(extras != null){
            diary = (Diary) extras.getSerializable("diary");
        }else{
            diary = new Diary(-1, "", "", null, null);
        }

        if (diary.getId() == -1) {
            db.insertDiary(diary);
        } else {
            db.updateDiary(diary);
        }

        getSupportActionBar().setTitle(diary.getTitle());

        entryView.setText(diary.getDescription());

        return diary;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_sort).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            db.deleteDiary(diary);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
