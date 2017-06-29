package com.example.dell.shopingdemo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.shopingdemo.R;
import com.example.dell.shopingdemo.bean.IndexBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by muhanxi on 17/6/20.
 */

public class IndexFragmentRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final String base_image = "http://image3.suning.cn";

    private final int singleType = 0;
    private final int horizontalType = 1;
    private final int tuigouType = 2;


    private final int defaultType = 3;

    private Context context;

    public List<IndexBean.DataBean> list;

    public IndexFragmentRecycleViewAdapter(Context context) {
        this.context = context;
    }


    public void setData(List<IndexBean.DataBean> data) {
        if (list == null) {
            this.list = new ArrayList<IndexBean.DataBean>();
        }
        this.list.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {

            case singleType:
                view = LayoutInflater.from(context).inflate(R.layout.singlepic_item, parent, false);
                viewHolder = new SinglePicViewHolder(view);

                break;
            case horizontalType:

                view = LayoutInflater.from(context).inflate(R.layout.horizontalscroll_item, parent, false);
                viewHolder = new HorizontalScrollViewViewHolder(view);
                break;
            case tuigouType:
                view = LayoutInflater.from(context).inflate(R.layout.tuangou_item, parent, false);
                viewHolder = new TuanGouViewHolder(view);
                break;
            default:

                view = LayoutInflater.from(context).inflate(R.layout.index_default_item, parent, false);

                viewHolder = new DefaultViewHodler(view);

                break;


        }
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (list == null || list.size() == 0) {
            return;
        }



        if (holder instanceof SinglePicViewHolder) {


            SinglePicViewHolder holder1 = (SinglePicViewHolder) holder;

            String pic = list.get(position).getTag().get(0).getPicUrl();
            Glide.with(context).load(base_image + pic).into(holder1.imageView);

        } else if (holder instanceof HorizontalScrollViewViewHolder) {
            HorizontalScrollViewViewHolder horizontalScrollViewViewHolder = (HorizontalScrollViewViewHolder) holder;
            String pic = list.get(position).getTag().get(0).getPicUrl();

            Glide.with(context).load(base_image + pic).into(horizontalScrollViewViewHolder.topImageView);


            int size = list.get(position).getTag().size();

            for (int i = 1; i < size; i++) {
                //图片对应的路径
                String item_pic = list.get(position).getTag().get(i).getPicUrl();


                System.out.println("item_pic = " + base_image + item_pic);

                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setPadding(10, 10, 10, 0);


                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));

                Glide.with(context).load(base_image + item_pic).into(imageView);


                TextView textView = new TextView(context);
                textView.setText("95");
                textView.setTextColor(Color.RED);
                textView.setTextSize(20f);
                textView.setGravity(Gravity.CENTER);


                linearLayout.addView(imageView);
                linearLayout.addView(textView);


                //
                horizontalScrollViewViewHolder.linearLayout.addView(linearLayout);


            }


        } else if(holder instanceof TuanGouViewHolder){
            TuanGouViewHolder tuanGouViewHolder = (TuanGouViewHolder) holder;

            String item_pic = list.get(position).getTag().get(0).getPicUrl();
            Glide.with(context).load(base_image+item_pic).into(tuanGouViewHolder.tuangouTop);

            String item_pic1 = list.get(position+1).getTag().get(0).getPicUrl();
            Glide.with(context).load(base_image+item_pic1).into(tuanGouViewHolder.tuangouBigimage);

            tuanGouViewHolder.tuangouTitle.setText(list.get(position+1).getTag().get(0).getElementName());
            tuanGouViewHolder.tuangouDes.setText(list.get(position+1).getTag().get(0).getElementDesc());


        } else if(holder instanceof DefaultViewHodler){
            DefaultViewHodler defaultViewHodler = (DefaultViewHodler) holder;
            defaultViewHodler.textView.setVisibility(View.GONE);

        }


    }


    @Override
    public int getItemViewType(int position) {


        System.out.println("position = " + position);

        int type = 0 ;
        switch (position){
            case 0:
                type = singleType ;
                break;
            case 1:
                type = horizontalType ;
                break;
            case 2:
                type = tuigouType;
                break;
            default:
                type = defaultType ;
                break;
        }
        return type;



    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    // 单张图片
    class SinglePicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.singleitem_imageview)
        ImageView imageView;

        public SinglePicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    //水平滚动的item
    class HorizontalScrollViewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.horizontal_linearlayout)
        LinearLayout linearLayout;
        @BindView(R.id.horizontals_imageview)
        ImageView topImageView;

        public HorizontalScrollViewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class DefaultViewHodler extends RecyclerView.ViewHolder {

        @BindView(R.id.indexfragment_textview)
        TextView textView;

        public DefaultViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    //团购
     class TuanGouViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tuangou_top)
        ImageView tuangouTop;
        @BindView(R.id.tuangou_bigimage)
        ImageView tuangouBigimage;
        @BindView(R.id.tuangou_title)
        TextView tuangouTitle;
        @BindView(R.id.tuangou_des)
        TextView tuangouDes;
        @BindView(R.id.tuangou_price)
        TextView tuangouPrice;

        @BindView(R.id.tuangou_buttom)
        LinearLayout linearLayoutButtom;

        public TuanGouViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

