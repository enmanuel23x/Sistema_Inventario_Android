package unknown.Sistema_Inventario_Android.TABLAS;

public class tab_vendedor {
    //Constantes campos tabla vendedores
    public static final String TABLA_VENDEDORES="VENDEDORES";
    public static final String ID_VENDEDORES="ID";
    public static final String CAMPO_NOMBRE="NOMBRES";
    public static final String CAMPO_RIF="RIF";
    public static final String CAMPO_DIRECCION="DIRECCION";
    public static final String CAMPO_TELEFONO="TELEFONO";
    public static final String CAMPO_EMAIL="EMAIL";
    //CREAR TABLA VENDEDORES
    public static final String CREAR_TABLA_VENDEDORES="CREATE TABLE " +
            ""+TABLA_VENDEDORES+" ("+ID_VENDEDORES+" " +
            "TEXT, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_RIF+" TEXT, "
            +CAMPO_DIRECCION+" TEXT, "+CAMPO_TELEFONO+" TEXT, "+CAMPO_EMAIL+" TEXT)";
}
