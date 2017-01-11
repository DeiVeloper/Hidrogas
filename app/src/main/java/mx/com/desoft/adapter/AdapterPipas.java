package mx.com.desoft.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import mx.com.desoft.hidrogas.R;
import mx.com.desoft.hidrogas.to.PipasTO;

/**
 * Created by erick.martinez on 08/01/2017.
 */

public class AdapterPipas extends ArrayAdapter<PipasTO> {
    Context context;
    private int layoutResourceId;
    ArrayList<PipasTO> pipasTOs = new ArrayList<PipasTO>();
    PipasTO pipasTO = new PipasTO();
    public AdapterPipas(Context context, int layoutResourceId, ArrayList<PipasTO> pipasTOs) {
        super(context, layoutResourceId, pipasTOs);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.pipasTOs = pipasTOs;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        AdapterPipas.PipasTOWrapper pipasTOWrapper = null;
        if (item == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            item = inflater.inflate(layoutResourceId, parent, false);
            pipasTOWrapper = new AdapterPipas.PipasTOWrapper();
            pipasTOWrapper.pipa = (TextView) item.findViewById(R.id.txtPipa);
            pipasTOWrapper.fechaRegistro = (TextView) item.findViewById(R.id.txtFechaRegistro);
            item.setTag(pipasTOWrapper);
        } else {
            pipasTOWrapper = (AdapterPipas.PipasTOWrapper) item.getTag();
        }
        pipasTO = pipasTOs.get(position);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(pipasTO.getFechaRegistro());
        pipasTOWrapper.pipa.setText(pipasTO.getNoPipa().toString());
        pipasTOWrapper.fechaRegistro.setText(dateText);
        return item;
    }
    static class PipasTOWrapper {
        TextView pipa;
        TextView fechaRegistro;
    }
}