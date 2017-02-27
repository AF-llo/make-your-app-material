package com.example.xyzreader.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by lars on 15.02.17.
 */

public class BindingRecyclerAdapter<TItem, V extends ViewDataBinding> extends RecyclerView.Adapter<BindingRecyclerAdapter.MVPViewHolder> {

    private static final String TAG = BindingRecyclerAdapter.class.getSimpleName();

    private int mItemId;
    private int mLayoutId;
    private ObservableArrayList<TItem> mItems;

    private IRecyclerViewItemClickListener<TItem, V> mRecyclerItemClickListener;

    private IRecyclerViewItemLongClickListener<TItem, V> mRecyclerItemLongClickListener;

    public BindingRecyclerAdapter() {
        mItemId = -1;
        mLayoutId = -1;
    }

    protected BindingRecyclerAdapter(int itemId, int layoutId) {
        mItemId = itemId;
        mLayoutId = layoutId;
    }

    public void setItems(ObservableArrayList<TItem> items) {
        if(items == null) {
            throw new IllegalArgumentException(getClass().getName() + ": items must not be null.");
        }

        this.mItems = items;
        mItems.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<TItem>>() {
            @Override
            public void onChanged(ObservableList<TItem> tItems) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<TItem> tItems, final int i, final int i1) {
                BindingRecyclerAdapter.this.notifyItemRangeChanged(i, i1);
            }

            @Override
            public void onItemRangeInserted(ObservableList<TItem> tItems, final int i, final int i1) {
                BindingRecyclerAdapter.this.notifyItemRangeInserted(i, i1);
            }

            @Override
            public void onItemRangeMoved(ObservableList<TItem> tItems, final int i, final int i1, int i2) {
                BindingRecyclerAdapter.this.notifyItemMoved(i, i1);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<TItem> tItems, final int i, final int i1) {
                BindingRecyclerAdapter.this.notifyItemRangeRemoved(i, i1);
            }
        });
    }

    public void setOnItemClickListener(IRecyclerViewItemClickListener<TItem, V> itemClickListener) {
        mRecyclerItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(IRecyclerViewItemLongClickListener<TItem, V> itemLongClickListener) {
        mRecyclerItemLongClickListener = itemLongClickListener;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerItemClickListener = null;
        mRecyclerItemLongClickListener = null;
    }

    @Override
    public MVPViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mItemId == -1 || mLayoutId == -1) {
            throw new RuntimeException(
                    "You used the wrong Constructor of "
                            + getClass().getSimpleName()
                            + " or you forgot to override method 'onCreateViewHolder' to create custom ViewHolder."
            );
        }
        return new MVPViewHolder<>(LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false), mItemId, this);
    }

    @Override
    public void onBindViewHolder(MVPViewHolder holder, int position) {
        final TItem item = mItems.get(position);
        holder.mBinding.setVariable(holder.mItemId, item);
        holder.mBinding.executePendingBindings();
        if(isClickable(item)){
            setClickListenerToItem(holder);
        } else {
            holder.itemView.setOnClickListener(null);
            holder.itemView.setOnLongClickListener(null);
            holder.itemView.setClickable(false);
        }
        try {
            onItemBound(item, (V) holder.mBinding, position);
        } catch (Exception e) {
            Log.w(TAG, "Incompatiple ViewDataBinding for ViewHolder");
        }
    }

    public void onItemBound(TItem item, V binding, int position) {

    }

    private boolean isClickable(TItem o) {
        return !(o instanceof IClickableListItem) || ((IClickableListItem) o).isClickable();
    }

    private void setClickListenerToItem(MVPViewHolder holder){
        holder.itemView.setOnClickListener(holder);
        holder.itemView.setOnLongClickListener(holder);
    }

    @Override
    public void onViewRecycled(MVPViewHolder holder) {
        super.onViewRecycled(holder);
        holder.itemView.setOnClickListener(null);
        holder.itemView.setOnLongClickListener(null);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public static class MVPViewHolder<T, V extends ViewDataBinding> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        int mItemId;
        public V mBinding;
        private BindingRecyclerAdapter<T, V> mRecyclerAdapter;

        public MVPViewHolder(View itemView, int itemId, BindingRecyclerAdapter<T, V> recyclerAdapter) {
            super(itemView);
            mItemId = itemId;
            mBinding = DataBindingUtil.bind(itemView);
            mRecyclerAdapter = recyclerAdapter;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (mRecyclerAdapter.mRecyclerItemClickListener != null && position > -1) {
                mRecyclerAdapter.mRecyclerItemClickListener
                        .onItemClick(mRecyclerAdapter.mItems.get(position), mBinding, position, mRecyclerAdapter);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();

            return position > -1 && mRecyclerAdapter.mRecyclerItemLongClickListener != null &&
                    mRecyclerAdapter.mRecyclerItemLongClickListener
                            .onItemLongClick(mRecyclerAdapter.mItems.get(position), itemView, position, mRecyclerAdapter);
        }
    }

    public ArrayList<TItem> getItems() {
        return mItems;
    }

}
