package unknown.Sistema_Inventario_Android.ENTIDADES_TABLAS;

public class vent {
    private String id;
    private String comprador;
    private String productos [];
    public vent(String n,String c,String [] p){
        this.id = n;
        this.comprador = c;
        this.productos= p;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public String[] getProductos() {
        return productos;
    }

    public void setProductos(String[] productos) {
        this.productos = productos;
    }
}
