package mx.edu.utez.biblioteca.biblioteca.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        filterName = "FilterAuthorize",
        urlPatterns = {
                "/*"
        }
)
public class FilterAuthorize implements Filter {

    String[] whiteList;
    String[] adminList;
    String[] userList;
//    String[] test = new String ["1as"];

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Filtro iniciado");
        whiteList = new String[8];
        whiteList[0] = "/login";
        whiteList[1] = "/index.jsp";
        whiteList[2] = "/crear-usuario-docente";
        whiteList[3] = "/crear-usuario-alumno";
        whiteList[4] = "/crear-usuario-docente.jsp";
        whiteList[5] = "/crear-usuario-alumno.jsp";
        whiteList[6] = "/guardar-usuario";
        whiteList[7] = "/ServletImages";
      /*  whiteList[6] = "/guardar-usuario";*/
        adminList = new String[23];
        adminList[0]="/agregar-sala";
        adminList[1]="/agregar-libro";
        adminList[2]="/agregar-sala.jsp";
        adminList[3]="/agregar-libro.jsp";
        adminList[4]="/list-usuarios";
        adminList[5]="/list-usuarios.jsp";
        adminList[6]="/editar-usuario";
        adminList[7]="/editar-usuario.jsp";
        adminList[8]="/list-pedidos";
        adminList[9]="/list-pedidos.jsp";
        adminList[10]="/aceptar-prestamo.jsp";
        adminList[11]="/regresar-libro";
        adminList[12]="/regresar-libro.jsp";
        adminList[13]="/agregar-ejemplar";
        adminList[14]="/agregar-ejemplar.jsp";
        adminList[15]="/guardar-ejemplar";
        adminList[16]="/devolver-sala";
        adminList[17]="/devolucion";
        adminList[18]="/guardar-libro;";
        adminList[19]="/aceptar-prestamp";
        adminList[20]="/eliminar-libro";
        adminList[21]="/eliminar-usuario";
        adminList[22]="/guardar-sala";

        userList = new String[3];
        userList[0] ="/perfil";
        userList[1] ="/perfil.jsp";
        userList[2] ="/solicitar-ejemplar";



    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getServletPath();
        //Saber si valido la sesion o no ?
        boolean isPublic = false;
        boolean isAdmin = false;
        boolean isUser=false;
        for (String item:whiteList){
            if (item.equals(path)) {
                isPublic=true;
                break;
            }
        }

        if (isPublic) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = req.getSession(false);
            if (session != null) {
                for (String item:adminList){
                    if(item.equals(path)){
                        isAdmin=true;
                        break;
                    }
                }
                if (isAdmin){
                    if(session.getAttribute("role")==null){
                        ((HttpServletResponse) response).sendRedirect("index.jsp");
                    }else{
                    System.out.println(session.getAttribute("role"));
                    if (session.getAttribute("role").equals(2)){
                        chain.doFilter(request, response);
                    }else{
                        ((HttpServletResponse) response).sendRedirect("menu");
                }}
                }else{
                    for(String item:userList){
                            if(item.equals(path)){
                                isUser=true;
                                break;
                            }
                        }
                        if(isUser){
                            if(session.getAttribute("role")==null){
                                ((HttpServletResponse) response).sendRedirect("index.jsp");
                            }else{
                            if (session.getAttribute("role").equals(1)||session.getAttribute("role").equals(0)){
                                chain.doFilter(request,response);
                            }else{
                                ((HttpServletResponse) response).sendRedirect("menu");
                            }}
                        }else{
                            System.out.println("Hay session");
                System.out.println(session);
                if (session.getAttribute("name")!=null){
                    System.out.println(session.getAttribute("name"));
                    System.out.println("Ya inicio sesión");
                    chain.doFilter(request, response);
                } else {
                    System.out.println("No ha iniciado sesion");
                    ((HttpServletResponse) response).sendRedirect("index.jsp");
                }


                }}
            } else {
                System.out.println("No hay session");
                ((HttpServletResponse) response).sendRedirect("index.jsp");
            }
        }




    }
}
