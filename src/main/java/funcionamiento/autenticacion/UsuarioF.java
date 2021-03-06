package funcionamiento.autenticacion;

import funcionamiento.Funcionamiento;
import comunes.ConversorJson;
import comunes.FormatearJson;
import comunes.LeerRequest;
import dao.autenticacion.UsuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.autenticacion.UsuarioM;

public class UsuarioF extends Funcionamiento{

    public UsuarioF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       super(request, response);
    }

    @Override
    public void distribuirPost() {
        String accion = this.request.getParameter("accion");
        try {
            switch (accion) {
                case "crear":
                    crearUsuario();
                    break;
                case "autenticar":
                    autenticarUsuario();
                    break;
                default:
                    System.out.println("NO EXSITE ESTA OPCION POST usuario");
            }
        } catch (IOException ex) {
            System.err.println("OCURRIO UN ERROR EN POST USUARIO");
            ex.printStackTrace(System.out);
        }
    }

    private void crearUsuario() throws IOException {
        String contenido = LeerRequest.obtenerContenido(request.getReader());
        String json = FormatearJson.formatearJson(contenido);
        UsuarioM nuevoUsuario = new ConversorJson<UsuarioM>().convertirJsonAObject(json, UsuarioM.class);
        new UsuarioDAO().insertar(nuevoUsuario);
        response.getWriter().append(json);
    }

    private void autenticarUsuario() throws IOException {
        String contenido = LeerRequest.obtenerContenido(request.getReader());
        UsuarioM usuario = new ConversorJson<UsuarioM>().convertirJsonAObject(FormatearJson.formatearJson(contenido), UsuarioM.class);
        new UsuarioDAO().encontrar(usuario);
        String json = new ConversorJson<UsuarioM>().convertirObjectAJson(usuario, UsuarioM.class);
        response.getWriter().append(json);
    }

    @Override
    public void distribuirGet() {
        String accion = this.request.getParameter("accion");
        try {
            switch (accion) {
                case "validarNombreUsuario":
                    validarNombreUsuario();
                    break;
                case "verificarTienePerfil":
                    verificarSiTienePerfil();
                    break;
                default:
                    System.out.println("NO EXSITE ESTA OPCION GET usuario");
            }
        } catch (IOException ex) {
            System.err.println("OCURRIO UN ERROR EN POST USUARIO");
            ex.printStackTrace(System.out);
        }
    }

    private void validarNombreUsuario() throws IOException {
        String nombre = request.getParameter("nombre");

        boolean existe = new UsuarioDAO().existeNombre(nombre);

        String json;
        if (existe) {
            json = "{\"mensaje\":\"Este usuario ya existe\"}";
        } else {
            json = "[]";
        }
        response.getWriter().append(json);
    }
    
    private void verificarSiTienePerfil() throws IOException{
        String nombre = request.getParameter("nombre");
        
        boolean tiene = new UsuarioDAO().tienePerfil(nombre);
        
        String json;
        if (tiene) {
            json = "{\"mensaje\":\"Tiene\"}";
        } else {
            json = "[]";
        }
        response.getWriter().append(json);
    }
}
