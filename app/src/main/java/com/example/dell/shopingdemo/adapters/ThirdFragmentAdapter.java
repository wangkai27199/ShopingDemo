package com.example.dell.shopingdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.shopingdemo.R;
import com.example.dell.shopingdemo.bean.ShopBean;
import com.example.dell.shopingdemo.fragments.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 2017/6/22.
 */

public class ThirdFragmentAdapter extends RecyclerView.Adapter<ThirdFragmentAdapter.IViewHolder>{

    private Context context;
    private List<ShopBean.OrderDataBean.CartlistBean> list;

    public ThirdFragmentAdapter(Context context){
        this.context = context;
    }

    public void setData(List<ShopBean.OrderDataBean.CartlistBean> list){
        if (this.list == null){
           this.list = new ArrayList<ShopBean.OrderDataBean.CartlistBean>();
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.third_fragment_item,parent,false);
        IViewHolder viewHolder = new IViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ThirdFragmentAdapter.IViewHolder holder, final int position) {

        if (position > 0){
            //头
            if (list.get(position).getShopId() == list.get(position-1).getShopId()){
                holder.llShopcartHeader.setVisibility(View.GONE);
            }else {
                holder.llShopcartHeader.setVisibility(View.VISIBLE);
            }
        }else {
            //position = 0
            holder.llShopcartHeader.setVisibility(View.VISIBLE);
        }

        System.out.println("holder = " + list.get(position).getShopName());

        holder.tvItemShopcartClothColor.setText("颜色：" + list.get(position).getColor());
        holder.tvItemShopcartClothSize.setText("尺寸：" + list.get(position).getSize());
        holder.tvItemShopcartClothname.setText(list.get(position).getProductName());
        holder.tvItemShopcartShopname.setText(list.get(position).getShopName());
        holder.tvItemShopcartClothPrice.setText("¥" + list.get(position).getPrice());
        holder.etItemShopcartClothNum.setText(list.get(position).getCount() + "");

        Glide.with(context).load(list.get(position).getDefaultPic()).into(holder.ivItemShopcartClothPic);

        //标记商品是否选中
        if (list.get(position).isSelect()){
            holder.tvItemShopcartClothselect.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_selected));
        }else {
            holder.tvItemShopcartClothselect.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_unselected));
        }
        //标记商铺是否选中
        if (list.get(position).isShopSelect()){
            holder.ivItemShopcartShopselect.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_selected));
        }else {
            holder.ivItemShopcartShopselect.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_unselected));
        }

        //全选按钮的状态
        if (onRefershListener != null){
            boolean isSelect = false;
            for (int i = 0;i<list.size();i++){
                if (!list.get(i).isSelect()){
                    isSelect = false;
                    //有一个商品的状态是未选中 全选按钮的状态就是未选中
                    break;
                }else {
                    isSelect = true;
                }
            }
            onRefershListener.onRefersh(isSelect,list);
        }

        //删除事件回调
        holder.ivItemShopcartClothDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteClickListener != null){
                    onDeleteClickListener.onDeleteClick(v,position,list.get(position).getId());
                }
                list.remove(position);
                //如果删除的是第一条数据 或者是数据带有商户名称的数据 更新数据源 标记该数据 显示商户名称
                ThirdFragment.setFitstState(list);
                notifyDataSetChanged();
            }
        });

        //减少商品数量
        holder.ivItemShopcartClothMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getCount() > 1){
                    int count = list.get(position).getCount() - 1;
                    list.get(position).setCount(count);
                    notifyDataSetChanged();
                    if (onEditListener != null){
                        onEditListener.onEditListener(position,list.get(position).getId(),count);
                    }
                }
            }
        });
        //添加商品数量
        holder.ivItemShopcartClothAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = list.get(position).getCount() + 1;
                list.get(position).setCount(count);
                notifyDataSetChanged();

                if (onEditListener != null){
                    onEditListener.onEditListener(position,list.get(position).getId(),count);
                }
            }
        });

        //商品选中和未选中的状态与商铺选中的状态（关联）
        holder.tvItemShopcartClothselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //标记当前商品的选中状态
                list.get(position).setSelect(!list.get(position).isSelect());
                for (int i = 0;i<list.size();i++){
                    for (int j = 0;j<list.size();j++){
                        //如果是同一家店铺的商品 并且其中一个的商品的选择状态是未选中 商铺的全选状态取消
                        if (list.get(j).getShopId() == list.get(i).getShopId() && !list.get(j).isSelect()){
                            list.get(i).setShopSelect(false);
                            break;
                        }else {
                            //如果说选择的商品是同一家的商品 并且都是选中状态 商铺的全选按钮的状态是选中的
                            list.get(i).setShopSelect(true);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
        //店铺按钮 的 选中与未选中
        holder.ivItemShopcartShopselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getIsFirst() == 1){

                    //店铺未选中 false 商品的状态 false
                    //店铺按钮选中 true 商品1的状态 true 商品2 的状态 true
                    list.get(position).setShopSelect(!list.get(position).isShopSelect());
                    for (int i = 0;i<list.size();i++){
                        if (list.get(i).getShopId() == list.get(position).getShopId()){
                            list.get(i).setSelect(list.get(position).isShopSelect());
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    // 点击事件

    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener ;
    }



    //删除
    public OnDeleteClickListener onDeleteClickListener;
    public interface OnDeleteClickListener {
        void onDeleteClick(View view, int position, int cartid);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener deleteClickListener){
        this.onDeleteClickListener = deleteClickListener;
    }

    public OnEditListener onEditListener;
    //添加 减少
    public interface OnEditListener {
        void onEditListener(int position, int cartid, int count);
    }

    public void setOnEditListener(OnEditListener onEditListener){
        this.onEditListener = onEditListener;
    }


    // 商品 选中状态发生变化

    public OnRefershListener onRefershListener;

    public interface OnRefershListener{
        //isSelect true 表示商品全部选中 false 未全部选中
        void onRefersh(boolean isSelect, List<ShopBean.OrderDataBean.CartlistBean> list);
    }

    public void setOnRefershListener(OnRefershListener listener){
        this.onRefershListener = listener ;
    }





    class IViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.view)
        View view;
        @BindView(R.id.iv_item_shopcart_shopselect)
        ImageView ivItemShopcartShopselect;
        @BindView(R.id.tv_item_shopcart_shopname)
        TextView tvItemShopcartShopname;
        @BindView(R.id.ll_shopcart_header)
        LinearLayout llShopcartHeader;
        @BindView(R.id.tv_item_shopcart_clothname)
        TextView tvItemShopcartClothname;
        @BindView(R.id.tv_item_shopcart_clothselect)
        ImageView tvItemShopcartClothselect;
        @BindView(R.id.iv_item_shopcart_cloth_pic)
        ImageView ivItemShopcartClothPic;
        @BindView(R.id.tv_item_shopcart_cloth_price)
        TextView tvItemShopcartClothPrice;
        @BindView(R.id.tv_item_shopcart_cloth_color)
        TextView tvItemShopcartClothColor;
        @BindView(R.id.tv_item_shopcart_cloth_size)
        TextView tvItemShopcartClothSize;
        @BindView(R.id.iv_item_shopcart_cloth_minus)
        ImageView ivItemShopcartClothMinus;
        @BindView(R.id.et_item_shopcart_cloth_num)
        TextView etItemShopcartClothNum;
        @BindView(R.id.iv_item_shopcart_cloth_add)
        ImageView ivItemShopcartClothAdd;
        @BindView(R.id.iv_item_shopcart_cloth_delete)
        ImageView ivItemShopcartClothDelete;
        public IViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
