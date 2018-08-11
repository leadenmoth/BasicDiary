package me.astashenkov.basicdiary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends ArrayAdapter<Diary> {
    private Context diaryContext;
    private List<Diary> diaryList = new ArrayList<>();

    public DiaryAdapter(@NonNull Context context, @NonNull ArrayList<Diary> list) {
        super(context, 0, list);
        this.diaryContext = context;
        this.diaryList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(diaryContext).inflate(R.layout.list_item,parent,false);

        Diary currentDiary = diaryList.get(position);

        TextView title = (TextView) listItem.findViewById(R.id.textView_title);
        title.setText(currentDiary.getTitle());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");
        TextView date = (TextView) listItem.findViewById(R.id.textView_date);
        date.setText(currentDiary.getDateCreated());

        return listItem;
    }
}