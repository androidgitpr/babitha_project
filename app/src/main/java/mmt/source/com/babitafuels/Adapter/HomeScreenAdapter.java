package mmt.source.com.babitafuels.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mmt.source.com.babitafuels.Model.HomeIcons;
import mmt.source.com.babitafuels.R;

public class HomeScreenAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<HomeIcons> myPages;
    public HomeScreenAdapter(Context context, ArrayList<HomeIcons> myPages) {
        mContext = context;
        this.myPages = myPages;

    }

    @Override
    public int getCount() {
        return myPages.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.home_screen_item, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
            HomeIcons item = myPages.get(i);
            textViewAndroid.setText(item.getFunctionName());
            imageViewAndroid.setImageResource(item.getResPath());
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}
