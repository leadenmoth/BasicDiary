package me.astashenkov.basicdiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //region Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Enable back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //endregion

        final EditText titleEdit = (EditText) findViewById(R.id.title_edit);
        final EditText entryEdit = (EditText) findViewById(R.id.entry_edit);

        Bundle extras = getIntent().getExtras();
        final Diary diary;
        if(extras != null){
            diary = (Diary) extras.getSerializable("diary");
            getSupportActionBar().setTitle("Edit diary");
        }else{
            diary = new Diary(-1, "", "", null, null);
            getSupportActionBar().setTitle("New diary");
        }

        titleEdit.setText(diary.getTitle());
        entryEdit.setText(diary.getDescription());

        FloatingActionButton fabSave = (FloatingActionButton) findViewById(R.id.fab_save);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diary.setTitle(titleEdit.getText().toString());
                diary.setDescription(entryEdit.getText().toString());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("diary",diary);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }

    //Handle back arrow press as navigation up
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
