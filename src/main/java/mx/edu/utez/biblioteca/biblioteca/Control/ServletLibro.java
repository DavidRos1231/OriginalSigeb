package mx.edu.utez.biblioteca.biblioteca.Control;

import mx.edu.utez.biblioteca.biblioteca.model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletLibro", urlPatterns = {
        "/list-libros",             //get
        "/prestar-libro",           //get
        "/agregar-libro",           //get
        "/regresar-libro",          //get
        "/eliminar-libro",          //get
        "/list-salas",              //get
        "/pedir-sala",              //post
        "/agregar-sala",            //get
        "/eliminar-sala",           //get
        "/crear-usuario-alumno",    //get
        "/crear-usuario-docente",   //get
        "/list-usuarios",           //get
        "/guardar-libro",           //post
        "/guardar-sala",            //post
        "/guardar-usuario",         //post
        "/editar-usuario",
        "/actualizar-usuario",
        "/eliminar-usuario",
        "/menu",
        "/list-ejemplar",
        "/solicitar-ejemplar",
        "/perfil",
        "/list-pedidos",
        "/aceptar-prestamo",
        "/rechazar-prestamo",
        "/regresar-prestamo",
        "/devolucion",
        "/devolver-sala",
        "/agregar-ejemplar",
        "/guardar-ejemplar",
        "/editar-libro",
        "/actualizar-libro",
        "/entregar-ejemplar"
})
@MultipartConfig(
        fileSizeThreshold = 1024*1024,
        maxFileSize = 1024*1024*5,
        maxRequestSize = 1024*1024*5*5
)
public class ServletLibro extends HttpServlet {
    private final String UPLOAD_DIRECTORY="C:\\DirectorioDeCarga";
    /*private final String UPLOAD_DIRECTORY=File.separator+"home"+File.separator+"ec2-user"+File.separator+"imagenes";*/
    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String option=request.getServletPath();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        ServiceLibro serviceLibro=new ServiceLibro();
        switch (option){
            case "/list-libros":
                List<BeanLibro> listLibros=new ArrayList<>();
                request.setAttribute("role",session.getAttribute("role"));
                String nombre=request.getParameter("nombre")!=null?(request.getParameter("nombre")):"";
                String autor=request.getParameter("autor")!=null?(request.getParameter("autor")):"";
                String categoria=request.getParameter("categoria")!=null?(request.getParameter("categoria")):"";
                if(request.getParameter("nombre")==null&&request.getParameter("autor")==null&&request.getParameter("categoria")==null){
                    listLibros = serviceLibro.listLibros();
                }else{
                    listLibros = serviceLibro.listLibros(nombre,autor,categoria);
                }

                request.setAttribute("list",listLibros);
                request.getRequestDispatcher("WEB-INF/view/list-libros.jsp").forward(request,response);
                break;
            case "/agregar-libro":
                request.getRequestDispatcher("/WEB-INF/view/agregar-libro.jsp").forward(request,response);
                break;
            case "/list-salas":
                request.setAttribute("role",session.getAttribute("role"));
                ServiceSala serviceSala=new ServiceSala();
                List<BeanSala> listSalas=serviceSala.listSalas();
                request.setAttribute("list",listSalas);
                request.getRequestDispatcher("WEB-INF/view/list-salas.jsp").forward(request,response);
                break;
            case "/agregar-sala":
                request.getRequestDispatcher("WEB-INF/view/agregar-sala.jsp").forward(request,response);
                break;
            case "/crear-usuario-alumno":
                request.getRequestDispatcher("WEB-INF/view/crear-usuario-alumno.jsp").forward(request,response);
                break;
            case "/crear-usuario-docente":
                request.getRequestDispatcher("WEB-INF/view/crear-usuario-docente.jsp").forward(request,response);
                break;
            case "/list-usuarios":
                String mat=request.getParameter("mat")!=null?(request.getParameter("mat")):"";
                List<BeanUsuario> listUsuarios=serviceLibro.listUsuarios(mat);
                request.setAttribute("list",listUsuarios);
                request.getRequestDispatcher("WEB-INF/view/list-usuarios.jsp").forward(request,response);
                break;
            case "/editar-usuario":
                String idString = request.getParameter("id") != null ? request.getParameter("id") : "0";
               try {
                   BeanUsuario usuario=serviceLibro.editarUsuario(idString);
                   request.setAttribute("usuario",usuario);
                   request.getRequestDispatcher("WEB-INF/view/editar-usuario.jsp").forward(request,response);
               }catch (Exception e){
                   e.printStackTrace();
                   response.sendRedirect("list-usuarios");
               }
                break;
            case "/menu":
                if(session.getAttribute("role").equals(2)){
                    response.sendRedirect("list-libros");
                }else{
                response.sendRedirect("list-libros");
                }
                break;
            case "/list-ejemplar":
                    String idString1 = request.getParameter("id") !=null ? request.getParameter("id"):"0";
                    int id1=Integer.parseInt(idString1);
                    int control = 3;
                    if (session.getAttribute("role").equals(0)){
                        control=3;
                    }
                    if (session.getAttribute("role").equals(1)){
                    control=5;
                    }
                    System.out.println(control);
                    try {
                        List<BeanEjemplar> listEjemplar=serviceLibro.listejemplar(id1);
                        request.setAttribute("control",control );
                        request.setAttribute("list",listEjemplar);
                        request.getRequestDispatcher("WEB-INF/view/list-ejemplar.jsp").forward(request,response);
                    }catch (Exception e){
                        e.printStackTrace();
                        response.sendRedirect("list-libros");
                    }
                break;
            case "/perfil":
                    try{
                        String userid=session.getAttribute("name")+"";
                        BeanUsuario usuario=serviceLibro.editarUsuario(userid);
                        List<BeanPrestamoLibro> listPrestamo=serviceLibro.listPrestamos(userid);
                        request.setAttribute("usuario",usuario);
                        request.setAttribute("list",listPrestamo);
                        request.getRequestDispatcher("WEB-INF/view/perfil.jsp").forward(request,response);
                    }catch (Exception e){
                        e.printStackTrace();

                        response.sendRedirect("menu");
                    }

                break;
            case "/list-pedidos":
                String status = request.getParameter("status") != null ? request.getParameter("status") : "0";
                int intstatus=Integer.parseInt(status);
                try {
                    List<BeanPrestamoLibro> listPrestamo=serviceLibro.listPrestamosadmin(intstatus);
                    request.setAttribute("list",listPrestamo);
                    request.setAttribute("estado",status);
                    request.getRequestDispatcher("WEB-INF/view/list-pedidos.jsp").forward(request,response);
                }catch(Exception e){
                    e.printStackTrace();
                    response.sendRedirect("menu");
                }
                break;
            case "/regresar-libro":
                String matricula = request.getParameter("id") !=null? request.getParameter("id"):"";
                try{
                    List<BeanPrestamoLibro> listPrestamo=serviceLibro.listPrestamos(matricula);
                    request.setAttribute("list",listPrestamo);
                    request.getRequestDispatcher("WEB-INF/view/regresar-libro.jsp").forward(request,response);
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("menu");
                }
                break;
            case "/agregar-ejemplar":
                try{
                int id=Integer.parseInt(request.getParameter("id")!=null? (request.getParameter("id")):"0");
                String name=request.getParameter("name")!=null?(request.getParameter("name")):"0";
                request.setAttribute("id",id);
                request.setAttribute("name",name);
                request.getRequestDispatcher("WEB-INF/view/agregar-ejemplar.jsp").forward(request,response);
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-libros");
                }
                break;
            case "/editar-libro":
                int id=Integer.parseInt(request.getParameter("id")!=null? (request.getParameter("id")):"0");
                BeanLibro libro=new BeanLibro();
                libro=serviceLibro.editarLibro(id);
                request.setAttribute("libro",libro);
                request.getRequestDispatcher("WEB-INF/view/editar-libro.jsp").forward(request,response);
                break;
            case "/entregar-ejemplar":
                String idejemplar=request.getParameter("idejemplar")!=null?(request.getParameter("idejemplar")):"0";
                String idprestamo=request.getParameter("idprestamo")!=null?(request.getParameter("idprestamo")):"0";
                String descripcion=request.getParameter("descripcion")!=null?(request.getParameter("descripcion")):"0";
                int idejemplarint=Integer.parseInt(idejemplar);
                int idprestamoint=Integer.parseInt(idprestamo);
                request.setAttribute("idejemplar",idejemplarint);
                request.setAttribute("idprestamo",idprestamoint);
                request.setAttribute("descripcion",descripcion);
                request.getRequestDispatcher("WEB-INF/view/entregar-ejemplar.jsp").forward(request,response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String option =request.getServletPath();
        switch (option){
            case "/guardar-libro":
                try {
                    int idGenerated =0;
                    String name= request.getParameter("name")!=null ? (request.getParameter("name")):"0";
                    String autor= request.getParameter("autor")!=null ? (request.getParameter("autor")):"0";
                    String categoria= request.getParameter("categoria")!=null ? (request.getParameter("categoria")):"0";
                    Part fileRequest=request.getPart("photo");
                    if(fileRequest!=null){
                        BeanLibro libro =new BeanLibro();
                        String file_name=fileRequest.getSubmittedFileName();
                        try{
                            libro.setName(name);
                            libro.setAutor(autor);
                            libro.setCategoria(categoria);
                            libro.setFile_name(file_name);
                            ServiceLibro serviceLibro=new ServiceLibro();
                            idGenerated=serviceLibro.saveLibro(libro);
                            if(idGenerated>0){
                                String uploadPath=UPLOAD_DIRECTORY+File.separator+idGenerated;
                                File directory=new File(uploadPath);
                                if(!directory.exists()){
                                    directory.mkdir();
                                }
                                fileRequest.write(uploadPath+File.separator+file_name);
                            }else{
                                response.sendRedirect("list-libros");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            response.sendRedirect("menu");
                        }

                        response.sendRedirect("list-libros?result-save="+(idGenerated!=0?"ok":"error"));
                    }else{
                        response.sendRedirect("list-libros?result-save=error");
                    }

                }catch (Exception e){
                    response.sendRedirect("list-libros?result-save=error");
                    e.printStackTrace();
                }
                break;
            case "/guardar-sala":
                try {
                    String lugares= request.getParameter("lugares")!=null ? (request.getParameter("lugares")):"0";
                    String descripcion= request.getParameter("descripcion")!=null ? (request.getParameter("descripcion")):"0";

                    int intLugares = Integer.parseInt(lugares);
                    ServiceSala serviceSala=new ServiceSala();
                    BeanSala sala =new BeanSala();

                    sala.setLugares(intLugares);
                    sala.setDescripcion(descripcion);
                    boolean result=serviceSala.saveSala(sala);
                    response.sendRedirect("list-salas?result-save="+(result?"ok":"error"));
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-salas");
                }
                break;
            case "/guardar-usuario":
                try {
                    String matricula=request.getParameter("matricula")!=null ? (request.getParameter("matricula")):"0";
                    String name=request.getParameter("name")!=null ? (request.getParameter("name")):"0";
                    String midname=request.getParameter("midname")!=null ? (request.getParameter("midname")):"0";
                    String lastname=request.getParameter("lastname")!=null ? (request.getParameter("lastname")):"0";
                    String correo=request.getParameter("correo")!=null ? (request.getParameter("correo")):"0";
                    String password=request.getParameter("password")!=null ? (request.getParameter("password")):"0";
                    String tipo= request.getParameter("tipo")!=null ? (request.getParameter("tipo")):"0";
                    int  inttipo=Integer.parseInt(tipo);
                    ServiceLibro serviceLibro=new ServiceLibro();
                    BeanUsuario usuario=new BeanUsuario(matricula,name,midname,lastname,correo,password,inttipo);
                    boolean result=serviceLibro.saveUsuario(usuario);
                    response.sendRedirect("list-usuarios?result-save="+(result?"ok":"error"));
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-usuarios");
                }
                break;
            case "/actualizar-usuario":
                try {
                    String matricula=request.getParameter("matricula")!=null ? (request.getParameter("matricula")):"0";
                    String name=request.getParameter("name")!=null ? (request.getParameter("name")):"0";
                    String midname=request.getParameter("midname")!=null ? (request.getParameter("midname")):"0";
                    String lastname=request.getParameter("lastname")!=null ? (request.getParameter("lastname")):"0";
                    String correo=request.getParameter("correo")!=null ? (request.getParameter("correo")):"0";
                    String password=request.getParameter("password")!=null ? (request.getParameter("password")):"0";
                    String tipo= request.getParameter("tipo")!=null ? (request.getParameter("tipo")):"0";
                    int  inttipo=Integer.parseInt(tipo);
                    ServiceLibro serviceLibro=new ServiceLibro();
                    BeanUsuario usuario=new BeanUsuario(matricula,name,midname,lastname,correo,password,inttipo);
                    boolean result=serviceLibro.actualizarUsuario(usuario);
                    response.sendRedirect("list-usuarios?result-save="+(result?"ok":"error"));
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-usuarios");
                }
                break;
            case "/eliminar-usuario":
                try{
                    String id=request.getParameter("id")!=null ? (request.getParameter("id")):"0";
                    ServiceLibro serviceLibro=new ServiceLibro();
                    boolean result=serviceLibro.eliminarUsuario(id);
                    response.sendRedirect("list-usuarios?result-save="+(result?"ok":"error"));
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-usuarios");
                }
                break;
            case "/eliminar-libro":
                try{
                    String id=request.getParameter("id")!=null ? (request.getParameter("id")):"0";
                    int idInt = Integer.parseInt(id);
                    ServiceLibro serviceLibro=new ServiceLibro();
                    boolean result=serviceLibro.eliminarLibro(idInt);
                    response.sendRedirect("list-libros?result-elim="+(result?"ok":"error"));
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-libros");
                }
                break;
            case "/solicitar-ejemplar":
                try{
                    String idejemplar=request.getParameter("id")!=null? (request.getParameter("id")):"0";
                    HttpSession sesion = request.getSession(true);

                    String idusuario=request.getParameter("name")!=null? (request.getParameter("name")):"0";
                    int idejemint=Integer.parseInt(idejemplar);
                    int control = 3;
                    if (sesion.getAttribute("role").equals(0)){
                        control=3;
                    }
                    if (sesion.getAttribute("role").equals(1)){
                        control=5;
                    }
                    if (Integer.parseInt(sesion.getAttribute("noprestamos")+"")>=control){
                        response.sendRedirect("list-libros?max=1");
                    }else{ServiceLibro serviceLibro = new ServiceLibro();
                        boolean result=serviceLibro.solicitarEjemplar(idejemint,idusuario);
                        if (result){
                            sesion.setAttribute("noprestamos",Integer.parseInt(sesion.getAttribute("noprestamos")+"")+1);
                        }
                        response.sendRedirect("list-libros?solicitud-save="+(result?"ok":"error"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-libros");
                }
                break;
            case "/aceptar-prestamo":
                try {
                    String idpedido = request.getParameter("id") != null ? (request.getParameter("id")) : "0";
                    String idejemplar = request.getParameter("idejemplar") != null ? (request.getParameter("idejemplar")) : "0";
                    String idusuario = request.getParameter("idusuario")!= null ? (request.getParameter("idusuario")) : "0";
                    int idpedidoint = Integer.parseInt(idpedido);
                    int idejemplarint = Integer.parseInt(idejemplar);
                    ServiceLibro serviceLibro = new ServiceLibro();
                    boolean result = serviceLibro.aceptarPrestamo(idpedidoint, idejemplarint,idusuario);
                    response.sendRedirect("list-pedidos?result-save=" + (result ? "ok" : "error"));
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-pedidos");
                }
                break;
            case "/rechazar-prestamo":
                try{
                    String idpedido=request.getParameter("id") != null ? (request.getParameter("id")) : "0";
                    int idpedidoint=Integer.parseInt(idpedido);
                    ServiceLibro serviceLibro=new ServiceLibro();
                    boolean result=serviceLibro.rechazarPrestamo(idpedidoint);
                    HttpSession session= request.getSession(false);
                    session.setAttribute("noprestamos",Integer.parseInt(session.getAttribute("noprestamos")+"")-1);
                    if(session.getAttribute("role").equals(2)){
                    response.sendRedirect("list-pedidos?result-save=" + (result ? "ok" : "error"));
                    }else{
                        response.sendRedirect("perfil?result-save=" + (result ? "ok" : "error"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-pedidos");
                }
                break;
            case "/devolucion":
                try{
                    String idprestamo=request.getParameter("idprestamo")!=null?(request.getParameter("idprestamo")):"0";
                    String idejemplar=request.getParameter("idejemplar")!=null?(request.getParameter("idejemplar")):"0";
                    String descripcion=request.getParameter("desc")!=null?(request.getParameter("desc")):"0";
                    System.out.println("que??????"+descripcion);
                    int idpedidoint=Integer.parseInt(idprestamo);
                    int idejemplarint=Integer.parseInt(idejemplar);
                    ServiceLibro serviceLibro=new ServiceLibro();
                    boolean result=serviceLibro.regresarPrestamo(idpedidoint,idejemplarint,descripcion);
                    response.sendRedirect("regresar-libro?result-save="+(result?"ok":"error"));
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("regresar-libro");
                }
                break;
            case "/pedir-sala":
                try{
                    HttpSession session= request.getSession(false);

                    String salita = session.getAttribute("salaprestada")+"";
                    if(salita.equals("true")){
                    String tiempo=request.getParameter("tiempo")!=null?(request.getParameter("tiempo")):"0";
                    String id=request.getParameter("id")!=null?(request.getParameter("id")):"0";
                    int intiempo=Integer.parseInt(tiempo);
                    int intid=Integer.parseInt(id);
                    if (session.getAttribute("role").equals(2)){
                        session.setAttribute("salaprestada",true);
                    }else{
                        session.setAttribute("salaprestada",false);
                    }
                    String iduser=request.getParameter("idu")!=null?(request.getParameter("idu")):"0";;
                    ServiceSala serviceSala=new ServiceSala();
                    boolean result=serviceSala.pedirSala(intiempo,intid,iduser);
                    response.sendRedirect("list-salas?result-save="+(result?"ok":"error"));
                    }else{
                        response.sendRedirect("list-salas?result-save=error");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-salas?result-save=error");
                }
                break;
            case "/devolver-sala":
                try {
                    String idsala = request.getParameter("id") != null ? (request.getParameter("id")) : "0";
                    int idsalaint = Integer.parseInt(idsala);
                    ServiceSala serviceSala = new ServiceSala();
                    boolean result = serviceSala.devolverSala(idsalaint);
                    response.sendRedirect("list-salas?result-save=" + (result ? "ok" : "error"));
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-salas?result-save=error");
                }
                    break;
            case"/guardar-ejemplar":
                try{
                String descripcion=request.getParameter("descripcion")!=null?(request.getParameter("descripcion")):"0";
                String editorial=request.getParameter("editorial")!=null?(request.getParameter("editorial")):"0";
                String cantidad=request.getParameter("cantidad")!=null?(request.getParameter("cantidad")):"0";
                int cant=Integer.parseInt(cantidad);
                String id=request.getParameter("id")!=null?(request.getParameter("id")):"0";
                int idint=Integer.parseInt(id);
                BeanEjemplar ejemplar=new BeanEjemplar();
                ejemplar.setIdlibro(idint);
                ejemplar.setDescripcion(descripcion);
                ejemplar.setEditorial(editorial);
                ServiceLibro serviceLibro=new ServiceLibro();
                if (cant>0 && cant<11) {
                    boolean result = serviceLibro.guardarEjemplar(ejemplar, cant);
                    response.sendRedirect("list-libros?result-save=" + (result ? "ok" : "error"));
                }else{
                    response.sendRedirect("list-libros?cantidad=max=1");
                }
                }catch (Exception e){
                    e.printStackTrace();
                    response.sendRedirect("list-libros");
                }
                break;
            case "/actualizar-libro":
                try {
                    String id= request.getParameter("id")!=null ? (request.getParameter("id")):"0";
                    String name= request.getParameter("name")!=null ? (request.getParameter("name")):"0";
                    String autor= request.getParameter("autor")!=null ? (request.getParameter("autor")):"0";
                    String categoria= request.getParameter("categoria")!=null ? (request.getParameter("categoria")):"0";
                    int idint=Integer.parseInt(id);
                    BeanLibro libro=new BeanLibro();
                    libro.setId(idint);
                    libro.setName(name);
                    libro.setAutor(autor);
                    libro.setCategoria(categoria);
                    ServiceLibro serviceLibro=new ServiceLibro();
                    boolean result=serviceLibro.updateLibro(libro);
                    response.sendRedirect("list-libros?result-save=" + (result ? "ok" : "error"));
                    }catch (Exception e){
                        e.printStackTrace();
                        response.sendRedirect("list-libros");
                    }
                break;
        }

    }
}
