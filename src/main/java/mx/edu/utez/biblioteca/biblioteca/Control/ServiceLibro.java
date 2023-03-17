package mx.edu.utez.biblioteca.biblioteca.Control;

import mx.edu.utez.biblioteca.biblioteca.model.*;

import java.util.List;

public class ServiceLibro {
    public BeanUsuario editarUsuario(String id){
        DaoLibro daoLibro=new DaoLibro();
        BeanUsuario usuario=daoLibro.editarUsuario(id);
        return usuario;
    }
    public BeanLibro editarLibro(int id){
        DaoLibro daoLibro=new DaoLibro();
        BeanLibro libro=daoLibro.editarLibro(id);
        return libro;
    }
    public List<BeanUsuario> listUsuarios(String mat){
        DaoLibro daoLibro=new DaoLibro();
        List<BeanUsuario> listUsuarios=daoLibro.listUsuarios(mat);
        return listUsuarios;
    }
    public List<BeanEjemplar> listejemplar(int id){
        DaoLibro daoLibro=new DaoLibro();
        List<BeanEjemplar> listejemplar=daoLibro.listEjemplar(id);
        return listejemplar;
    }
    public List<BeanLibro> listLibros(){
        DaoLibro daoLibro = new DaoLibro();
        List<BeanLibro> listLibros = daoLibro.listLibros();
        return listLibros;
    }
    public List<BeanLibro> listLibros(String nombre,String autor,String categoria){
        DaoLibro daoLibro = new DaoLibro();
        List<BeanLibro> listLibros = daoLibro.listLibros(nombre,autor,categoria);
        return listLibros;
    }
    public int saveLibro(BeanLibro libro){
        DaoLibro daoLibro=new DaoLibro();
        return daoLibro.saveLibro(libro);
    }
    public boolean updateLibro(BeanLibro libro){
        DaoLibro daoLibro=new DaoLibro();
        return daoLibro.updateLibro(libro);
    }
    public boolean saveUsuario(BeanUsuario usuario){return  new DaoLibro().saveUsuario(usuario);}
    public boolean actualizarUsuario(BeanUsuario usuario){return  new DaoLibro().actualizarUsuario(usuario);}
    public boolean eliminarUsuario(String id){return  new DaoLibro().eliminarUsuario(id);}
    public boolean eliminarLibro(int id){return  new DaoLibro().eliminarLibro(id);}
    public boolean solicitarEjemplar(int id, String userid){
        boolean result=false;
        DaoLibro daoLibro=new DaoLibro();
        result=daoLibro.solicitarEjemplar(id,userid);
        return result;
    }
    public List<BeanPrestamoLibro> listPrestamos(String userid){
        DaoLibro daoLibro=new DaoLibro();
        List<BeanPrestamoLibro> listarPrestamos=daoLibro.listarPrestamos(userid);
        return listarPrestamos;
    }
    public List<BeanPrestamoLibro> listPrestamosadmin(int status){
        DaoLibro daoLibro=new DaoLibro();
        List<BeanPrestamoLibro> listarPrestamos=daoLibro.listarPrestamosadmin(status);
        return listarPrestamos;
    }
    public boolean aceptarPrestamo(int idpedido,int idejemplar,String idusuario){
        boolean result =false;
        DaoLibro daoLibro=new DaoLibro();
        result=daoLibro.aceptarPrestamo(idpedido,idejemplar,idusuario);
        return result;
    }
    public boolean rechazarPrestamo(int idpedido){
        boolean result =false;
        DaoLibro daoLibro=new DaoLibro();
        result=daoLibro.rechazarPrestamo(idpedido);
        return result;
    }
    public boolean regresarPrestamo(int idpedido, int idejemplar, String descripcion){
        boolean result=false;
        DaoLibro daoLibro=new DaoLibro();
        result=daoLibro.regresarPrestamo(idpedido,idejemplar,descripcion);
        return  result;
    }
    public boolean guardarEjemplar(BeanEjemplar ejemplar,int cantidad){
        boolean result=false;
        DaoLibro daoLibro=new DaoLibro();
        result=daoLibro.guardarEjemplar(ejemplar,cantidad);
        return result;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
