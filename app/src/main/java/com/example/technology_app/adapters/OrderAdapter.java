package com.example.technology_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technology_app.R;
import com.example.technology_app.models.GetOrder.Order;
import com.example.technology_app.models.GetOrder.products.Products;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private static RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    List<Order> orderList;
    Context context;

    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtIdOrder.setText("Order: " + orderList.get(position).getId());
        LinearLayoutManager manager = new LinearLayoutManager(
                holder.recyclerViewOrder.getContext(),
                LinearLayoutManager.VERTICAL, false);
        manager.setInitialPrefetchItemCount(order.getProducts().size());

        DetailOrderAdapter detailOrderAdapter = new DetailOrderAdapter(order.getProducts(), context, order);
        holder.recyclerViewOrder.setLayoutManager(manager);
        holder.recyclerViewOrder.setAdapter(detailOrderAdapter);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtIdOrder;
        RecyclerView recyclerViewOrder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdOrder = itemView.findViewById(R.id.idItemOrder);
            recyclerViewOrder = itemView.findViewById(R.id.recycleItemOrder);
            recyclerViewOrder.setRecycledViewPool(viewPool);
        }
    }
}
