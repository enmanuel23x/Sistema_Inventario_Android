package unknown.Sistema_Inventario_Android.ENTIDADES_TABLAS;

public class lvend {
    private String id;
    private String nombre;
    private String rif;
    private String direccion;
    private String telefono;
    private String email;

    public lvend(String id, String nombre, String rif, String direccion, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.rif = rif;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
