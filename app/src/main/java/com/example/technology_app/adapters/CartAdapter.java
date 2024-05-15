package com.example.technology_app.adapters;
import android.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.technology_app.R;
import com.example.technology_app.interfaces.IImageClickListener;
import com.example.technology_app.models.CartModel;
import com.example.technology_app.models.EventBus.CaculatorSumEvent;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    Context context;
    List<CartModel.Item> carts;

    public CartAdapter(Context context, List<CartModel.Item> carts) {
        this.context = context;
        this.carts = carts;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<CartModel.Item> getCarts() {
        return carts;
    }

    public void setCarts(List<CartModel.Item> carts) {
        this.carts = carts;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        CartModel.Item cart = carts.get(position);
        holder.item_cart_productName.setText(cart.getProduct().getName());
        holder.item_cart_quantity.setText(cart.getQuantity() + " ");
        Glide.with(context).load(cart.getProduct().getImages().get(0)).into(holder.item_cart_image);
        //DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_cart_price.setText("Giá: "+ cart.getProduct().getSale_price());
        int gia = cart.getQuantity() * Integer.parseInt(cart.getProduct().getSale_price().replaceAll("[^\\d]", ""));
        holder.item_cart_price2.setText(String.valueOf(gia));
        holder.setiImageClickListener(new IImageClickListener() {
            @Override
            public void onImageClicked(View view, int pos, int value) {
                String userId = Paper.book().read("userId");
                String accessToken = Paper.book().read("accessToken");
                Log.d("TAG", "onImageClick" + pos + "..." + value);
                if(value == 1){
                    if(carts.get(pos).getQuantity() > 1){
                        int newQuantity = carts.get(pos).getQuantity() - 1;
                        updateCartQuantity(userId, accessToken, carts.get(pos).getProduct().get_id(), newQuantity);
                        carts.get(pos).setQuantity(newQuantity);
                    }else if(carts.get(pos).getQuantity() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng không?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Utils.mangGioHang.remove(pos);
                                deleteItemFromCart(userId, accessToken, carts.get(pos).getProduct().get_id(), pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new CaculatorSumEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }else if(value == 2){
                    if(carts.get(pos).getQuantity() < 11){
                        int newQuantity = carts.get(pos).getQuantity() + 1;
                        updateCartQuantity(userId, accessToken, carts.get(pos).getProduct().get_id(), newQuantity);
                        carts.get(pos).setQuantity(newQuantity);
                    }
                }
                holder.item_cart_quantity.setText(carts.get(pos).getQuantity() + " ");
                int gia = Integer.parseInt(carts.get(pos).getProduct().getSale_price().replaceAll("[^\\d]", "")) * carts.get(pos).getQuantity();
                holder.item_cart_price2.setText(String.valueOf(gia));
                EventBus.getDefault().postSticky(new CaculatorSumEvent());
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    GlobalVariable.listCartBuy.add(cart);
                    EventBus.getDefault().postSticky(new CaculatorSumEvent());
                }else{
                    for(int i = 0; i < GlobalVariable.listCartBuy.size(); i++){
                        if(Objects.equals(GlobalVariable.listCartBuy.get(i).getProduct().get_id(), cart.getProduct().get_id())){
                            GlobalVariable.listCartBuy.remove(i);
                            EventBus.getDefault().postSticky(new CaculatorSumEvent());
                        }
                    }
                }
            }
        });
    }

    private void updateCartQuantity(String userId, String accessToken, String productId, int quantity){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Api api;
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        compositeDisposable.add(api.updateCartQuantity(userId, accessToken, productId, quantity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        updateCartModel -> {
                            if (updateCartModel.getStatus() == 200) {
                                Log.d("TAG", "Cart updated successfully");
                            } else {
                                Log.d("Fail", "update cart Fail");
                            }
                        },
                        throwable -> {
                            Log.d("Fail", Objects.requireNonNull(throwable.getMessage()));
                        }
                )
        );
    }

    private void deleteItemFromCart(String userId, String accessToken, String productId, int position){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Api api;
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        compositeDisposable.add(api.removeItemInCart(userId, accessToken, productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        updateCartModel -> {
                            if (updateCartModel.getStatus() == 200) {
                                Log.d("TAG", "Item deleted successfully");
                                carts.remove(position); // Remove item from the list
                                notifyItemRemoved(position); // Notify RecyclerView of item removal
                                EventBus.getDefault().postSticky(new CaculatorSumEvent()); // Update sum event
                            } else {
                                Log.d("Fail", "item delete Fail");
                            }
                        },
                        throwable -> {
                            Log.d("Fail", Objects.requireNonNull(throwable.getMessage()));
                        }
                )
        );
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        IImageClickListener iImageClickListener;

        public void setiImageClickListener(IImageClickListener iImageClickListener) {
            this.iImageClickListener = iImageClickListener;
        }

        ImageView item_cart_image, item_cart_remove, item_cart_add;
        TextView item_cart_productName, item_cart_price, item_cart_quantity, item_cart_price2;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_cart_image = itemView.findViewById(R.id.item_cart_image);
            item_cart_productName = itemView.findViewById(R.id.item_cart_productName);
            item_cart_price = itemView.findViewById(R.id.item_cart_price);
            item_cart_price2 = itemView.findViewById(R.id.item_cart_price2);
            item_cart_quantity = itemView.findViewById(R.id.item_cart_quantity);
            item_cart_remove = itemView.findViewById(R.id.item_cart_remove);
            item_cart_add = itemView.findViewById(R.id.item_cart_add);
            checkBox = itemView.findViewById(R.id.item_cart_check);

            //event click
            item_cart_remove.setOnClickListener(this);
            item_cart_add.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v == item_cart_remove){
                iImageClickListener.onImageClicked(v, getAdapterPosition(), 1);
            }else if(v == item_cart_add){
                iImageClickListener.onImageClicked(v, getAdapterPosition(), 2);
            }
        }
    }
}
