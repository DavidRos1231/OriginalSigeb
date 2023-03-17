package mx.edu.utez.biblioteca.biblioteca.Control;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet(name = "ServletImages", value = "/ServletImages")
public class ServletImages extends HttpServlet {
    private final  String UPLOAD_DIRECTORY = "C:\\DirectorioDeCarga";
    /*private final  String UPLOAD_DIRECTORY = File.separator+"home"+File.separator+"ec2-user"+File.separator+"imagenes";*/
    private final int ARBITARY_SIZE = 1048;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/png");
        response.setHeader("Content-disposition","attachment; filename=sample.png");
        String idProduct = request.getParameter("id_producto")!= null ? request.getParameter("id_producto"): "0";

        if (!idProduct.equals("0")){
            File file = new File(UPLOAD_DIRECTORY+File.separator+idProduct);
            if (file.exists()){
                File[] files = file.listFiles();
                response.setHeader("Content-disposition", "attachment; filename="+files[0].getName());
                try(InputStream in = new FileInputStream(files[0]);
                    OutputStream out = response.getOutputStream()){
                    byte[] buffer = new byte[ARBITARY_SIZE];
                    int numBytesRead;
                    while ((numBytesRead = in.read(buffer)) > 0){
                        out.write(buffer, 0, numBytesRead);
                    }
                }

            }
        }
    }
}
