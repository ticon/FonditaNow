package bethinktech.mx.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ricki on 19/1/2017.
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    //Variable que contendrea la sentencia SQL de creacion de la tabla
    String  sqlCreacion = "CREATE TABLE Cliente(idCliente INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "usuario TEXT NOT NULL,"
            + "contrasena TEXT NOT NULL,"
            + "correo TEXT,"
            + "tipo INTEGER NOT NULL,"
            + "nombre TEXT NOT NULL,"
            + "apellido TEXT,"
            + "direccion TEXT NOT NULL)";

    //Constructor
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreacion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Cliente");
        db.execSQL(sqlCreacion);
    }
}
