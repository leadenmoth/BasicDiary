package me.astashenkov.basicdiary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Diary> diaryList = new ArrayList<>();
    private DiaryAdapter adapter;
    private DatabaseHelper db;

    Diary[] diaryPlaceholders = new Diary[]{
            new Diary(1, "Example entry 1", "Lorum ipsum", null, null),
            new Diary(2, "Example entry 2", "Lorum ipsum", null, null),
            new Diary(3, "Example entry 3", "Lorum ipsum", null, null)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //endregion

        //region FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
        //endregion

        //region List from Cursor
        db = new DatabaseHelper(this);
        //db.open();
        int diary_rows = db.getDiariesCount();
        if (diary_rows == 0) {
            db.setupDiary(diaryPlaceholders);
        }

        diaryList.addAll(db.getAllDiaries());
        adapter = new DiaryAdapter(this, diaryList);
        ListView listView = (ListView) findViewById(R.id.diary_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Diary diary = (Diary) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
                intent.putExtra("diary", diary);
                startActivity(intent);
            }
        });
        //endregion
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        diaryList.clear();
        diaryList.addAll(db.getAllDiaries());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        super.finish();
        db.close();
    }



}
