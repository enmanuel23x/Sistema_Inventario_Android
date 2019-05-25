package unknown.Sistema_Inventario_Android.TABLAS;

public class tab_vent {
    //Constantes campos tabla ventas
    public static final String TABLA_VENTA="VENTAS";
    public static final String ID_VENTAS="ID";
    public static final String CAMPO_COMPRADOR="CLIENTE";
    public static final String CAMPO_PRODUCTOS="PRODUCTOS";
    //CREAR TABLA VENTAS
    public static final String CREAR_TABLA_VENTA="CREATE TABLE " +
            ""+TABLA_VENTA+" ("+ID_VENTAS+" " +
            "TEXT, "+CAMPO_COMPRADOR+" TEXT,"+CAMPO_PRODUCTOS+" TEXT)";


}
