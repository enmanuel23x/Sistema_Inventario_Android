package unknown.Sistema_Inventario_Android.TABLAS;

public class tab_client {
    //Constantes campos tabla cliente
    public static final String TABLA_CLIENTE="CLIENTES";
    public static final String ID_CLIENTES="ID";
    public static final String CAMPO_NOMBRE="NOMBRE";
    public static final String CAMPO_RIF="RIF";
    public static final String CAMPO_DIRECCION="DIRECCION";
    //CREAR TABLA CLIENTES
    public static final String CREAR_TABLA_CLIENTE="CREATE TABLE " +
            ""+TABLA_CLIENTE+" ("+ID_CLIENTES+" " +
            "TEXT, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_RIF+" TEXT,"+CAMPO_DIRECCION+" TEXT)";


}
