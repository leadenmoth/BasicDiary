package me.astashenkov.basicdiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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
    private String sortColumn;

    Diary[] diaryPlaceholders = new Diary[]{
            new Diary(1, "Example entry 1", "Lorum ipsum", null, null),
            new Diary(2, "Example entry 2", "Lorum ipsum", null, null),
            new Diary(3, "Example entry 3", "Lorum ipsum", null, null)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sortColumn = sharedPref.getString("pref_sort", "");

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
                startActivityForResult(intent, 1);
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

        diaryList.addAll(db.getAllDiaries(sortColumn));
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
        registerForContextMenu(listView);
        //endregion
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_delete).setVisible(false);
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
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_sort) {
            showSortPopup(findViewById(id));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        // Inflate the menu from xml
        popup.inflate(R.menu.menu_sort);
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_sort_title:
                        sortColumn = Diary.TITLE;
                        break;
                    case R.id.menu_sort_created:
                        sortColumn = Diary.CREATED;
                        break;
                    case R.id.menu_sort_modified:
                        sortColumn = Diary.MODIFIED;
                        break;
                    default:
                        break;
                }
                diaryList.clear();
                diaryList.addAll(db.getAllDiaries(sortColumn));
                adapter.notifyDataSetChanged();
                return true;

            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if (item.getTitle() == "Delete") {
            db.deleteDiary(diaryList.get(info.position));
            diaryList.clear();
            diaryList.addAll(db.getAllDiaries(sortColumn));
            adapter.notifyDataSetChanged();
        } else { return false; }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = intent.getExtras();
                final Diary diary;
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
                diaryList.clear();
                diaryList.addAll(db.getAllDiaries(sortColumn));
                adapter.notifyDataSetChanged();
                Snackbar.make((ListView) findViewById(R.id.diary_list), "Diary saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        diaryList.clear();
        diaryList.addAll(db.getAllDiaries(sortColumn));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        super.finish();
        db.close();
    }
}
