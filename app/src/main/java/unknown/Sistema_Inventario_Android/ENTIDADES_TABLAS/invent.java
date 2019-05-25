package unknown.Sistema_Inventario_Android.ENTIDADES_TABLAS;

public class invent {
    private String id;
    private String nombre;
    private String precio;
    private String disponible;

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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public invent(String id, String nombre, String precio, String disponible) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = disponible;
    }
}
