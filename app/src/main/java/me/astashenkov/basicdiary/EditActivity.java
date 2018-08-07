package me.astashenkov.basicdiary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView titleView = (TextView) findViewById(R.id.title_view);
        final TextView entryView = (TextView) findViewById(R.id.entry_view);
        final EditText titleEdit = (EditText) findViewById(R.id.title_edit);
        final EditText entryEdit = (EditText) findViewById(R.id.entry_edit);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            Diary diary = (Diary) extras.getSerializable("diary");
            titleView.setText(diary.getTitle());
            entryView.setText(diary.getDescription());
            titleEdit.setText(diary.getTitle());
            entryEdit.setText(diary.getDescription());
        }else{
            titleView.setText("");
            entryView.setText("");
        }

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleView.setVisibility(View.GONE);
                entryView.setVisibility(View.GONE);
                titleEdit.setVisibility(View.VISIBLE);
                entryEdit.setVisibility(View.VISIBLE);
                findViewById(R.id.fab_edit).setVisibility(View.GONE);
                findViewById(R.id.fab_save).setVisibility(View.VISIBLE);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fabSave = (FloatingActionButton) findViewById(R.id.fab_save);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleEdit.setVisibility(View.GONE);
                entryEdit.setVisibility(View.GONE);
                titleView.setVisibility(View.VISIBLE);
                entryView.setVisibility(View.VISIBLE);
                findViewById(R.id.fab_save).setVisibility(View.GONE);
                findViewById(R.id.fab_edit).setVisibility(View.VISIBLE);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

    }

}
