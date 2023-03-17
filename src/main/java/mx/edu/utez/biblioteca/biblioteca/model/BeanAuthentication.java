package mx.edu.utez.biblioteca.biblioteca.model;

public class BeanAuthentication {

    private String nickname;
    private int rol;

    private int noprestamos;
    private boolean salaprestada;

    public BeanAuthentication() {
    }

    public BeanAuthentication(long userId, String nickname, int rol,int noprestamos,boolean salaprestada) {
        this.nickname = nickname;
        this.rol = rol;
        this.noprestamos =noprestamos;
        this.salaprestada=salaprestada;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getNoprestamos() {
        return noprestamos;
    }

    public boolean isSalaprestada() {
        return salaprestada;
    }

    public void setSalaprestada(boolean salaprestada) {
        this.salaprestada = salaprestada;
    }

    public void setNoprestamos(int noprestamos) {
        this.noprestamos = noprestamos;
    }
}
