package mx.com.desoft.hidrogas.bussines;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.com.desoft.SQLite.AdminSQLiteOpenHelper;
import mx.com.desoft.hidrogas.to.LiquidacionesTO;
import mx.com.desoft.hidrogas.to.LlenadoTO;

/**
 * Created by David on 04/01/17.
 */

public class ReporteUnidadesBussines {

    private Activity activity = new Activity();
    private static AdminSQLiteOpenHelper baseDatos;
    private SQLiteDatabase sqLiteDatabase;

    public ReporteUnidadesBussines(Context context){
        baseDatos = new AdminSQLiteOpenHelper(context);
        sqLiteDatabase = baseDatos.getWritableDatabase();
    }

    public List<LlenadoTO> getUnidadLlenadoByFecha(Long fechaBusqueda){
        List<LlenadoTO> lista = new ArrayList<>();
        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("    SELECT  noPipa, porcentajeLlenado, fechaRegistro " );
        selectQuery.append("    FROM    Liquidacion liquidacion, ");
        selectQuery.append("            Llenado llenado ");
        selectQuery.append("    WHERE   liquidacion.noPipa = llenado.noPipa" );
        selectQuery.append("    AND     liquidacion.variacion = 1 ");
        selectQuery.append("    AND     liquidacion.fechaRegistro = " + fechaBusqueda);

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery.toString(), null);
        if (cursor.moveToFirst()) {
            do {
                LlenadoTO unidad = new LlenadoTO();
                unidad.setNoPipa(cursor.getInt(0));
                unidad.setPorcentajeLlenado(cursor.getInt(1));
                unidad.setFechaRegistro(cursor.getLong(2));
                lista.add(unidad);
            } while (cursor.moveToNext());
        }

        return lista;
    }
}
