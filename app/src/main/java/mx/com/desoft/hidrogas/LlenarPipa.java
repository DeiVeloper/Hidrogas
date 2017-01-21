package mx.com.desoft.hidrogas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.com.desoft.hidrogas.bussines.PipasBussines;
import mx.com.desoft.hidrogas.to.PipasTO;

/**
 * Created by erick.martinez on 25/11/2016.
 */

public class LlenarPipa extends Activity {
    private EditText txtPorcentaje;
    private TextView txtNoPipa, txtFecha;
    private Button btnCancelar, btnGuardar;
    private Long fecha;
    private Bundle bundle;
    private PipasTO pipasTO;
    private PipasBussines pipasBussines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llenar_pipa);
        pipasTO = new PipasTO();
        pipasBussines = new PipasBussines();
        bundle = getIntent().getExtras();
        inicializarComponentes();
        cargarEventos();
    }

    private void inicializarComponentes() {
        txtFecha = (TextView)findViewById(R.id.lblFecha);
        txtNoPipa = (TextView)findViewById(R.id.txtEconomico);
        txtPorcentaje = (EditText)findViewById(R.id.txtPorcentaje);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String fechaText = formato.format(new Date());
            Date soloFecha = formato.parse(fechaText);
            fecha = soloFecha.getTime();
            txtFecha.setText(fechaText);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //obtener datos que se pasaron en el Intent para llenar
        if(bundle != null) {
            if (bundle.containsKey("noPipa")) {
                txtNoPipa.setText(bundle.getString("noPipa"));
            }
            if (bundle.containsKey("porcentajeLlenado")) {
                txtPorcentaje.setText(bundle.getString("porcentajeLlenado"));
            }
        }

        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);
    }

    private void cargarEventos() {
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnTab(2);
                //onBackPressed();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean guardado = guardar();
                if (guardado){
                    returnTab(2);
                }
            }
        });
    }

    private boolean guardar() {
        try {
            if (TextUtils.isEmpty(txtPorcentaje.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
            } else {
                if (Integer.parseInt(txtPorcentaje.getText().toString()) > 100) {
                    Toast.makeText(getApplicationContext(), "El porcentaje no puede ser mayor a 100.", Toast.LENGTH_SHORT).show();
                } else {
                    pipasTO.setNoPipa(Integer.parseInt(txtNoPipa.getText().toString()));
                    pipasTO.setPorcentajeLlenado(Integer.parseInt(txtPorcentaje.getText().toString()));
                    pipasTO.setFechaRegistro(fecha);
                    pipasTO.setNominaRegistro("20");
                    pipasBussines.llenar(getApplicationContext(), pipasTO);
                    Toast.makeText(getApplicationContext(), "La pipa número: " + pipasTO.getNoPipa() + " se ha llenado correctamente.", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error al llenar la pipa.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void returnTab(int posicion){
        Intent go = new Intent(getApplicationContext(), MainActivity.class);
        go.putExtra("viewpager_position", posicion);
        startActivity(go);
    }
}
