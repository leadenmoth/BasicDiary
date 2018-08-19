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

        TextView text = (TextView) listItem.findViewById(R.id.textView_text);
        String description = currentDiary.getDescription();
        if (description.length() > 20) description = description.substring(0, 20) + "...";
        text.setText(description);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");
        TextView dateCreated = (TextView) listItem.findViewById(R.id.textView_date_created);
        dateCreated.setText(currentDiary.getDateCreated());

        TextView dateModified = (TextView) listItem.findViewById(R.id.textView_date_modified);
        dateModified.setText(currentDiary.getDateModified());


        return listItem;
    }
}