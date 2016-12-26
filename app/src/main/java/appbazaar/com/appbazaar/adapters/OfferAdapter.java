package appbazaar.com.appbazaar.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appbazaar.com.appbazaar.R;

/**
 * Created by braj.kishore on 4/16/2016.
 */
public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {


    private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView =(TextView) v.findViewById(R.id.promoName_tv);
        }
    }


    public OfferAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_promo_grid, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }


    public int getItemCount() {
        return mDataset.length;
    }
}
