package controladores;

import funcionamiento.recomendaciones.RecomendacionesF;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ControladorRecomendaciones", urlPatterns = {"/controladorRecomendaciones"})
public class RecomendacionesControl extends Controlador {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       RecomendacionesF funcionamiento = new RecomendacionesF(request, response);
       funcionamiento.distribuirGet();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
}
