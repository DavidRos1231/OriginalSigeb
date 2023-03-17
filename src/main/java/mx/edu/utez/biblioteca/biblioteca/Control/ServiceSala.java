package mx.edu.utez.biblioteca.biblioteca.Control;
import mx.edu.utez.biblioteca.biblioteca.model.BeanLibro;
import mx.edu.utez.biblioteca.biblioteca.model.DaoLibro;
import mx.edu.utez.biblioteca.biblioteca.model.DaoSala;
import mx.edu.utez.biblioteca.biblioteca.model.BeanSala;
import java.util.List;
public class ServiceSala {
    public List<BeanSala> listSalas(){
        DaoSala daoSala= new DaoSala();
        List<BeanSala> listSalas=daoSala.listSalas();
        return listSalas;
    }
    public boolean saveSala(BeanSala sala){
        return new DaoSala().saveSala(sala);
    }
    public boolean pedirSala(int tiempo,int idsala,String idusuario){
        boolean result=false;
        DaoSala daoSala=new DaoSala();
        result=daoSala.pedirSala(tiempo,idsala,idusuario);
        return result;
    }
    public boolean devolverSala(int idsala){
        boolean result=false;
        DaoSala daoSala=new DaoSala();
        result=daoSala.devolverSala(idsala);
        return result;
    }
}
