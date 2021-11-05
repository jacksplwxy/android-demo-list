package com.jacksplwxy.start;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class AnimeAdapter extends BaseAdapter {
    private static final String TAG = "AnimeAdapter";
    private LayoutInflater mLayoutInflater;//布局加载器对象
    private List<AnimeBean> mAnimeBeans;//数据源

    //构造方法，包含了数据源和上下文。将数据和适配器关联起来了。
    public AnimeAdapter(Context context, List<AnimeBean> animeBeans) {
        mLayoutInflater =  LayoutInflater.from(context);
        mAnimeBeans = animeBeans;
    }

    //item的数目，即适配器要显示的数据项个数
    @Override
    public int getCount() {
        return mAnimeBeans != null ? mAnimeBeans.size() : 0;
    }

    //获取指定位置的数据项
    @Override
    public Object getItem(int position) {
        return mAnimeBeans != null ? mAnimeBeans.get(position) : null;
    }

    //获取指定位置的数据项id
    @Override
    public long getItemId(int position) {
        return position;
    }

    //获取每一项显示内容-view
    /**
     *方法3:
     * 这种方法改进了方法1和方法2的弊端，避免了每次都创建新的View对象和通过findViewById查找组件 而造成的耗时 耗资源的问题。
     * 创建View对象通过判空避免了:if (convertView == null)
     * findViewById()通过ViewHolder缓存下来了，通过setTag设置到View了，需要时可以直接获取到。
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //convertView是显示项的视图。为null时即表示未被实例化过，GridView缓存池中没有缓存
        Log.d( TAG, "getView: position=" + position +";convertView="+convertView );
        if (convertView == null) {
            convertView = mLayoutInflater.inflate( R.layout.grid_item, null );
            //创建ViewHolder对象，并赋值
            viewHolder = new ViewHolder();
            viewHolder.animeNameTxt = convertView.findViewById(R.id.anime_name_txt);
            viewHolder.animeAuthorTxt = convertView.findViewById(R.id.anime_author_txt);
            viewHolder.animeCoverImg = convertView.findViewById(R.id.anime_cover_img);
            //通过setTag，设置与convertView关联的标签ViewHolder，将convertView与ViewHolder关联
            convertView.setTag(viewHolder);
        } else {
            //从缓存中返回convertView设置的标签：ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AnimeBean animeBean = mAnimeBeans.get(position);
        viewHolder.animeNameTxt.setText(animeBean.getmAnimeName());
        viewHolder.animeAuthorTxt.setText(animeBean.getmAnimeAuthor());
        viewHolder.animeCoverImg.setImageResource(animeBean.getmAnimeCoverImg());
        return convertView;
    }

    //缓存控件
    private class ViewHolder {
        public TextView animeNameTxt;
        public TextView animeAuthorTxt;
        public ImageView animeCoverImg;
    }

    /**
     * 方法1:
     * 这种方法的弊端在于:很明显，每次都会创建新的convertView对象，并通过findViewById查找对应组件。
     * 当数据项很大且比较复杂时问题很明显，耗时 耗资源
     * 没有使用GridView ListView的缓存机制。
     */
   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate( R.layout.base_adapter_grid_item, null );
        TextView animeNameTxt = convertView.findViewById(R.id.anime_name_txt);
        TextView animeAuthorTxt = convertView.findViewById(R.id.anime_author_txt);
        ImageView animeCoverImg = convertView.findViewById(R.id.anime_cover_img);

        AnimeBean animeBean = mAnimeBeans.get(position);
        animeNameTxt.setText(animeBean.getmAnimeName());
        animeAuthorTxt.setText(animeBean.getmAnimeAuthor());
        animeCoverImg.setImageResource(animeBean.getmAnimeCoverImg());
        return convertView;
    }*/

    /**
     * 方法2:
     * 该方法时方法1的改进，使用了缓存机制。
     * 这种方法的弊端在于:每次都会通过findViewById（）去遍历视图树，当布局很复杂时就会很耗时
     */
    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate( R.layout.base_adapter_grid_item, null );
        }
        TextView animeNameTxt = convertView.findViewById(R.id.anime_name_txt);
        TextView animeAuthorTxt = convertView.findViewById(R.id.anime_author_txt);
        ImageView animeCoverImg = convertView.findViewById(R.id.anime_cover_img);

        AnimeBean animeBean = mAnimeBeans.get(position);
        animeNameTxt.setText(animeBean.getmAnimeName());
        animeAuthorTxt.setText(animeBean.getmAnimeAuthor());
        animeCoverImg.setImageResource(animeBean.getmAnimeCoverImg());
        return convertView;
    }*/

}