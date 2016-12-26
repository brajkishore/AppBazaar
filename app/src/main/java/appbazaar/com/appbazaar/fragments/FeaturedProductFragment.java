package appbazaar.com.appbazaar.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import appbazaar.com.appbazaar.R;
import appbazaar.com.appbazaar.activities.AppFeaturedActivity;
import appbazaar.com.appbazaar.activities.AppInstallActivity;
import appbazaar.com.appbazaar.adapters.OfferAdapter;
import appbazaar.com.appbazaar.listners.RecyclerItemClickListener;
import appbazaar.com.appbazaar.model.AppOffer;
import appbazaar.com.appbazaar.model.DownloadInstructions;
import appbazaar.com.appbazaar.model.Instruction;
import appbazaar.com.appbazaar.model.TnCInstructions;
import appbazaar.com.appbazaar.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeaturedProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeaturedProductFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private OfferAdapter mAdapter;
    private Context context;



    public static FeaturedProductFragment newInstance() {
        FeaturedProductFragment fragment = new FeaturedProductFragment();
        return fragment;
    }

    public FeaturedProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.fragment_featured_product, container, false);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mainScreen);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter=new OfferAdapter(new String[]{"braj","kishore","sagar","braj","kishore","sagar","braj","kishore","sagar"});
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                System.out.println("clieced " + position);
                Toast.makeText(context, "clicked on " + position, Toast.LENGTH_SHORT).show();
                TextView textView=(TextView)(view.findViewById(R.id.promoName_tv));
                String s=String.valueOf(textView.getText());
                Toast.makeText(context,"clicked on "+s,Toast.LENGTH_SHORT).show();
                System.out.println("clieced " + position + " ss " + s);


                AppOffer appOffer=new AppOffer();
                appOffer.setUnique_promo_id("1");
                appOffer.setPromo_name("Quick");
                appOffer.setPromo_price("5");
                appOffer.setPromo_price("5");
                appOffer.setPromo_type("Install");
                appOffer.setPromo_short_info("Download app and get Rs.5 ");
                appOffer.setPromo_detail("sndjdngkdnkbdjnvdldvdfsbvjhvsbvfdjksghitsrjgisr,vdfgjs");
                appOffer.setRating("4.2");
                DownloadInstructions downloadInstructions=new DownloadInstructions();
                downloadInstructions.setHeader("Instructions to get offer");
                Instruction instruction=new Instruction();
                instruction.setMessage("Download from below link and get Rs.3");
                List<Instruction> i=new ArrayList<Instruction>();
                i.add(instruction);
                instruction.setMessage("Keep this app for 10 day and get Rs.2");
                i.add(instruction);
                downloadInstructions.setInstructions(i);
                appOffer.setDownloadInstructions(downloadInstructions);

                TnCInstructions tnCInstructions=new TnCInstructions();
                tnCInstructions.setHeader("Get Rs.100 bonus");

                appOffer.setPromo_action_text("Register");
                appOffer.setPromo_type(Constants.PROMO_INSTALL);
                i=new ArrayList<Instruction>();
                instruction=new Instruction();
                instruction.setMessage("1. Meet your professional");
                i.add(instruction);

                instruction=new Instruction();
                instruction.setMessage("2. Stay and get insttant help !!");
                i.add(instruction);


                instruction=new Instruction();
                instruction.setMessage("2. Stay and get insttant help !!");
                i.add(instruction);

                instruction=new Instruction();
                instruction.setMessage("2. Stay and get insttant help !!");
                i.add(instruction);

                instruction=new Instruction();
                instruction.setMessage("2. Stay and get insttant help !!");
                i.add(instruction);

                instruction=new Instruction();
                instruction.setMessage("2. Stay and get insttant help !!");
                i.add(instruction);

                instruction=new Instruction();
                instruction.setMessage("2. Stay and get insttant help !!");
                i.add(instruction);
                instruction=new Instruction();
                instruction.setMessage("2. Stay and get insttant help !!");
                i.add(instruction);

                instruction=new Instruction();
                instruction.setMessage("2. Stay and get insttant help !!");
                i.add(instruction);


                tnCInstructions.setInstructions(i);
                appOffer.setTncInstructions(tnCInstructions);

                Intent intent=new Intent(getActivity(), AppFeaturedActivity.class);
                intent.putExtra(Constants.USER_SELECTED_OFFER,appOffer);
                getActivity().startActivity(intent);


            }
        }
        ));

        return rootView;

    }


}
