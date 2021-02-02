package com.example.kidoslearning;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class WordsListAdapter extends ArrayAdapter<Words>
{
    private static final String TAG= "WordsListAdapter";
    private Context mcontext;
    private int mColorid;
    int mResource;
    static class ViewHolder
    {
        TextView eng;
        TextView hindi;
        ImageView image;
    }

    WordsListAdapter(Context context, int resource, List<Words> objects,int Colorid)
    {
        super(context,resource,objects);
        mcontext = context;
        mResource= resource;
        mColorid=Colorid;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        String eng =getItem(position).getEnglish_num();
        String hindi =getItem(position).getHindi_num();
        int image=getItem(position).getImage();
        Words words=new Words(eng,hindi, image);
        ViewHolder holder;
        if(convertView==null)
        {
            LayoutInflater inflater=LayoutInflater.from(mcontext);
            convertView=inflater.inflate(mResource,parent,false);
            holder =new ViewHolder();
            holder.eng=(TextView) convertView.findViewById(R.id.textView1);
            holder.hindi=(TextView) convertView.findViewById(R.id.textView2);
            holder.image=(ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder) convertView.getTag();
        }
        holder.eng.setText(words.getEnglish_num());
        holder.hindi.setText(words.hindi_num);
        holder.image.setImageResource(words.getImage());
        if (image==0)
        {
            holder.image.setVisibility(View.GONE);
        }
        else
        {
          holder.image.setVisibility(View.VISIBLE);
        }
        View TextContaner =convertView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mColorid);
        TextContaner.setBackgroundColor(color);

        return convertView;

    }
}

