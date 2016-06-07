package example.loloangela.GRR;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lo on 4/1/2016.
 */
public class CustomAdapter extends BaseAdapter {
    String [] result;
    Context context;
    int imgId;
    UserData userData;
    User me;
    StationData stationData;
    public static final String[] stations ={"Europe", "Africa", "North America", "South America", "Asia", "Australia", "Antarctica"};
    Station curStation;
    private static LayoutInflater inflater=null;

    public CustomAdapter(ListFavsActivity mainActivity, String[] prgmNameList , int im) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        userData = new UserData(context);
        me = userData.getLoggedIn();
        stationData = new StationData(context);
        imgId = im;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    public CustomAdapter(Context mainActivity, ArrayList<String> prgmNameList , int im) {
//        // TODO Auto-generated constructor stub
//        result=prgmNameList;
//        context=mainActivity;
//        userData = new UserData(context);
//        me = userData.getLoggedIn();
//        stationData = new StationData(context);
//        imgId = im;
//        inflater = ( LayoutInflater )context.
//                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView = convertView;
        if(convertView == null){
            rowView = inflater.inflate(R.layout.station_item, parent, false);
        }
        String param1 = "", param2 = "";

        new FavsTask(context, 1).execute(param1, param2);

        holder.tv=(TextView) rowView.findViewById(R.id.station_nameTV);
        holder.img =(ImageView) rowView.findViewById(R.id.deleteIV);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete from database
                String[] temp = new String[result.length - 1];
                int index = 0;
                boolean found = false;
                for (int i = 0; i < stations.length; i++) {
                    if (stations[i].equals(result[position])) {
                        new FavsTask(context, 3).execute(String.valueOf(me.getUser_id()), String.valueOf((i + 1)));
                        index = position;
                        found = true;
                    }
                }
                if(found){
                    if((result.length - 1) <= 0 ){
                        result = new String[0];
                    }else{
                        int j =0;
                        for (int i = 0; (i < result.length) && (j < temp.length); i++){
                            if(i != index){
                                temp[j++] = result[i];
                            }else {

                            }
                        }
                        result = temp;
                    }

                    notifyDataSetChanged();
                   // new FavsTask(context, 1).execute(String.valueOf(me.getUser_id()));
                }
                //((ListFavsActivity)context).finish();

                // Remove from ListView
               // Toast.makeText(context, "Here we will delete " + result[position], Toast.LENGTH_SHORT).show();
            }
        });
            rowView.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                // TODO Auto-generated method stub
                // Go to station
                for (int i = 0; i < stations.length; i++) {
                    if (stations[i].equals(result[position])) {
                        if (RadioService.first == false) {
                            if (RadioService.mediaPlayer != null) {
                                if (RadioService.mediaPlayer.isPlaying()) {
                                    Station temp = stationData.getCurrentStation();
                                    if (temp.getStation_id() != i + 1) {
                                        context.stopService(new Intent(context, RadioService.class));
                                    }
                                }

                            }
                        }
                        if((i + 1) == 3){
                            new StationPickerTask(context, "nAmerica", i + 1).execute();
                        }else if((i+1) == 4){
                            new StationPickerTask(context, "sAmerica", i + 1).execute();
                        }else {
                            new StationPickerTask(context, result[position], i + 1).execute();
                        }
                        break;
                    }
                }
                // Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_SHORT).show();
            }
        });
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imgId);
        return rowView;
    }

}
