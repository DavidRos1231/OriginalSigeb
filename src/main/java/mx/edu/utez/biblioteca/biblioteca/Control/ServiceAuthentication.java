package mx.edu.utez.biblioteca.biblioteca.Control;

import mx.edu.utez.biblioteca.biblioteca.model.BeanAuthentication;
import mx.edu.utez.biblioteca.biblioteca.model.DaoAuthentication ;

public class ServiceAuthentication {

    public BeanAuthentication login(String nickname, String password) {
        DaoAuthentication daoAuthentication = new DaoAuthentication();

        return daoAuthentication.login(nickname,password);

    }
}
