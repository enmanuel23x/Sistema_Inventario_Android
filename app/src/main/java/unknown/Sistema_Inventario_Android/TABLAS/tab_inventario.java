package unknown.Sistema_Inventario_Android.TABLAS;

public class tab_inventario {

    //Constantes campos tabla inventario
    public static final String TABLA_INVENTARIO="INVENTARIO";
    public static final String ID_INVENTARIO="ID";
    public static final String CAMPO_NOMBRE="NOMBRE";
    public static final String CAMPO_PRECIO="PRECIO";
    public static final String CAMPO_DISPONIBLE="DISPONIBLE";
    //CREAR TABLA INVENTARIO
    public static final String CREAR_TABLA_INVENTARIO="CREATE TABLE " +
            ""+TABLA_INVENTARIO+" ("+ID_INVENTARIO+" " +
            "TEXT, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_PRECIO+" TEXT,"+CAMPO_DISPONIBLE+" TEXT)";


}