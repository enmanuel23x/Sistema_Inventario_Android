package unknown.Sistema_Inventario_Android.ENTIDADES_TABLAS;

public class client {
    private String id;
    private String nombre;
    private String rif;
    private String direccion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public client(String id, String nombre, String rif, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.rif = rif;
        this.direccion = direccion;
    }
}
