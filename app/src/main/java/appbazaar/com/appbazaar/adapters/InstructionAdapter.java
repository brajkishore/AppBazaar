package appbazaar.com.appbazaar.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import appbazaar.com.appbazaar.R;
import appbazaar.com.appbazaar.model.Instruction;

/**
 * Created by braj.kishore on 4/28/2016.
 */
public class InstructionAdapter extends ArrayAdapter<Instruction> {

    Context mContext;
    int layoutResourceId;
    List<Instruction> data;

    public InstructionAdapter(Context mContext, int layoutResourceId, List<Instruction> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }


        // object item based on the position
        String  s = data.get(position).getMessage();
        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(s);


        return convertView;

    }

}
