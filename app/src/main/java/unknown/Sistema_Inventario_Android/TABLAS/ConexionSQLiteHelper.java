package unknown.Sistema_Inventario_Android.TABLAS;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tab_user.CREAR_TABLA_USUARIOS);
        db.execSQL(tab_client.CREAR_TABLA_CLIENTE);
        db.execSQL(tab_inventario.CREAR_TABLA_INVENTARIO);
        db.execSQL(tab_vent.CREAR_TABLA_VENTA);
        db.execSQL(tab_vendedor.CREAR_TABLA_VENDEDORES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS "+ tab_inventario.TABLA_INVENTARIO);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ tab_client.TABLA_CLIENTE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ tab_vent.TABLA_VENTA);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ tab_vendedor.TABLA_VENDEDORES);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ tab_user.TABLA_USUARIOS);
        onCreate(db);
    }
}