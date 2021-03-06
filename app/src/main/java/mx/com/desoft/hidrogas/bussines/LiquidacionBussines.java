package mx.com.desoft.hidrogas.bussines;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import mx.com.desoft.hidrogas.LoginActivity;
import mx.com.desoft.hidrogas.to.LiquidacionesTO;
import mx.com.desoft.hidrogas.to.ViajesTO;
import mx.com.desoft.hidrogas.utils.Utils;

public class LiquidacionBussines {
    private Utils utils = new Utils();

    public Long guardarLiquidacion(LiquidacionesTO liquidacionesTO, List<ViajesTO> listaViajes, Bundle bundle) {
        Long idLiquidacion;
        ContentValues registro = new ContentValues();
        registro.put("nominaChofer", liquidacionesTO.getNominaChofer());
        registro.put("nominaAyudante", liquidacionesTO.getNominaAyudante());
        registro.put("idPipa", liquidacionesTO.getNoPipa());
        registro.put("variacion", liquidacionesTO.getVariacion());
        registro.put("alerta", liquidacionesTO.getAlerta());
        registro.put("fechaRegistro", liquidacionesTO.getFechaRegistro().toString());
        registro.put("nominaRegistro", liquidacionesTO.getNominaRegistro());
        registro.put("porcentajeVariacion", liquidacionesTO.getPorcentajeVariacion());
        registro.put("economico", liquidacionesTO.getEconomico());
        registro.put("autoconsumo", liquidacionesTO.getAutoconsumo());
        registro.put("medidores", liquidacionesTO.getMedidores());
        registro.put("traspasosRecibidos", liquidacionesTO.getTraspasosRecibidos());
        registro.put("traspasosRealizados", liquidacionesTO.getTraspasosRealizados());
        if (liquidacionesTO.getIdLiquidacion() != null){
            registro.put("idLiquidacion", liquidacionesTO.getIdLiquidacion());
            LoginActivity.conexion.update("liquidacion", registro, "idLiquidacion = " + liquidacionesTO.getIdLiquidacion(), null);
            idLiquidacion = liquidacionesTO.getIdLiquidacion().longValue();
        }else{
            idLiquidacion = LoginActivity.conexion.insert("liquidacion", null, registro);
        }


        for (ViajesTO viajes : listaViajes) {
            ContentValues registroViajes = new ContentValues();
            registroViajes.put("idLiquidacion", idLiquidacion.intValue());
            registroViajes.put("porcentajeInicial", viajes.getPorcentajeInicial());
            registroViajes.put("porcentajeFinal", viajes.getPorcentajeFinal());
            registroViajes.put("totalizadorInicial", viajes.getTotalizadorInicial());
            registroViajes.put("totalizadorFinal", viajes.getTotalizadorFinal());

            if (liquidacionesTO.getIdLiquidacion() != null ){
                LoginActivity.conexion.update("viajes", registroViajes, "idLiquidacion = " + idLiquidacion + " AND idViaje = " + viajes.getIdViaje(), null);
            }else{
                LoginActivity.conexion.insert("viajes", null, registroViajes);
            }
        }
        return idLiquidacion;
    }

    @SuppressLint("Recycle")
    public LiquidacionesTO getLiquidacionByIdLiquidacion(Long idLiquidacion){
        LiquidacionesTO liquidacion = null;
        String selectQuery = "    SELECT  liquidacion.*, " +
                "            (SELECT e.nombre||' '||e.apellidoPaterno||' '|| e.apellidoMaterno FROM Empleados e WHERE e.nominaEmpleado = liquidacion.nominaChofer) as chofer," +
                "            (SELECT e.nombre||' '||e.apellidoPaterno||' '|| e.apellidoMaterno FROM Empleados e WHERE e.nominaEmpleado = liquidacion.nominaAyudante) as ayudante" +
                "    FROM    Liquidacion liquidacion " +
                "    WHERE   liquidacion.idLiquidacion = " + idLiquidacion.intValue();
        Cursor cursor = LoginActivity.conexion.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                liquidacion = new LiquidacionesTO();
                liquidacion.setIdLiquidacion(Integer.parseInt(cursor.getString(cursor.getColumnIndex("idLiquidacion"))));
                liquidacion.setNoPipa(cursor.getInt(cursor.getColumnIndex("idPipa")));
                liquidacion.setEconomico(cursor.getString(cursor.getColumnIndex("economico")));
                liquidacion.setNominaChofer(cursor.getString(cursor.getColumnIndex("nominaChofer")));
                liquidacion.setNominaAyudante(cursor.getString(cursor.getColumnIndex("nominaAyudante")));
                liquidacion.setVariacion(cursor.getInt(cursor.getColumnIndex("variacion")));
                liquidacion.setAlerta(cursor.getInt(cursor.getColumnIndex("alerta")));
                liquidacion.setFechaRegistro(cursor.getLong(cursor.getColumnIndex("fechaRegistro")));
                liquidacion.setPorcentajeVariacion(cursor.getFloat(cursor.getColumnIndex("porcentajeVariacion")));
                liquidacion.setChofer(cursor.getString(cursor.getColumnIndex("chofer")));
                liquidacion.setAyudante(cursor.getString(cursor.getColumnIndex("ayudante")));
            } while (cursor.moveToNext());
        }
        return liquidacion;
    }

    @SuppressLint("Recycle")
    public ViajesTO getPorcentajeInicial(String economico){
        ViajesTO viaje = new ViajesTO();
        String selectQuery = "SELECT  max(idViaje) as idViaje, " +
                "        viajes.idLiquidacion, " +
                "        totalizadorFinal " +
                "FROM    Viajes viajes,  " +
                "        Liquidacion liquidacion " +
                "WHERE   viajes.idLiquidacion = liquidacion.idLiquidacion " +
                " AND     liquidacion.economico = " + economico;
        Cursor cursor = LoginActivity.conexion.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                viaje.setIdViaje(cursor.getInt(cursor.getColumnIndex("idViaje")));
                viaje.setIdLiquidacion(cursor.getInt(cursor.getColumnIndex("idLiquidacion")));
                viaje.setTotalizadorFinal(cursor.getInt(cursor.getColumnIndex("totalizadorFinal")));
            } while (cursor.moveToNext());
        }
        return viaje;
    }

    @SuppressLint("Recycle")
    public List<ViajesTO> getViajesByIdLiquidacion(int idLiquidacion){

        List<ViajesTO> lista = new ArrayList<>();
        String selectQuery = "SELECT  * " +
                "FROM    Viajes viajes  " +
                "WHERE   viajes.idLiquidacion = " + idLiquidacion;
        Cursor cursor = LoginActivity.conexion.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ViajesTO viaje = new ViajesTO();
                viaje.setIdViaje(cursor.getInt(cursor.getColumnIndex("idViaje")));
                viaje.setIdLiquidacion(cursor.getInt(cursor.getColumnIndex("idLiquidacion")));
                viaje.setPorcentajeInicial(cursor.getInt(cursor.getColumnIndex("porcentajeInicial")));
                viaje.setPorcentajeFinal(cursor.getInt(cursor.getColumnIndex("porcentajeFinal")));
                viaje.setTotalizadorInicial(cursor.getInt(cursor.getColumnIndex("totalizadorInicial")));
                viaje.setTotalizadorFinal(cursor.getInt(cursor.getColumnIndex("totalizadorFinal")));
                lista.add(viaje);
            } while (cursor.moveToNext());
        }
        return lista;
    }

    @SuppressLint("Recycle")
    public LiquidacionesTO getLiquidacionByIdLiquidacion(Integer idLiquidacion){
        LiquidacionesTO liquidacion = null;
        String selectQuery = "    SELECT  liquidacion.* " +
                "    FROM    Liquidacion liquidacion  " +
                "    WHERE   liquidacion.idLiquidacion = " + idLiquidacion;
        Cursor cursor = LoginActivity.conexion.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                liquidacion = new LiquidacionesTO();
                liquidacion.setIdLiquidacion(cursor.getInt(cursor.getColumnIndex("idLiquidacion")));
                liquidacion.setNominaChofer(cursor.getString(cursor.getColumnIndex("nominaChofer")));
                liquidacion.setNominaAyudante(cursor.getString(cursor.getColumnIndex("nominaAyudante")));
                liquidacion.setNoPipa(cursor.getInt(cursor.getColumnIndex("idPipa")));
                liquidacion.setVariacion(cursor.getInt(cursor.getColumnIndex("variacion")));
                liquidacion.setAlerta(cursor.getInt(cursor.getColumnIndex("alerta")));
                liquidacion.setFechaRegistro(cursor.getLong(cursor.getColumnIndex("fechaRegistro")));
                liquidacion.setNominaRegistro(cursor.getString(cursor.getColumnIndex("nominaRegistro")));
                liquidacion.setPorcentajeVariacion(cursor.getFloat(cursor.getColumnIndex("porcentajeVariacion")));
                liquidacion.setEconomico(cursor.getString(cursor.getColumnIndex("economico")));
                liquidacion.setViajes(getViajesByIdLiquidacion(idLiquidacion));
            } while (cursor.moveToNext());
        }

        return liquidacion;
    }

}
