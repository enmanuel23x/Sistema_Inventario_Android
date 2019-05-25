package unknown.Sistema_Inventario_Android.ENTIDADES_TABLAS;

public class user {
    private String id;
    private String usuario;
    private String contraseña;
    private String grado;

    public user(String id, String usuario, String contraseña, String grado) {
        this.id = id;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.grado = grado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }
}
