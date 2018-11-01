package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CancelarTurno
 */
@WebServlet("/CancelarTurno")
public class CancelarTurno extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelarTurno() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//metodo getTurno
		request.setAttribute("paciente", "TARANTINO, JOSE LUIS");
		request.setAttribute("fechaHora", "29/10/2018 9:00hs");
		request.setAttribute("profesional", "ARCE, HUGO");
		request.setAttribute("direccion", "ZELARRAYAN 6065");
		request.setAttribute("telefono", "11 36295079");
		request.setAttribute("consultorio", "APPointment");
		request.setAttribute("mail", "appointment.web@gmail.com");
		request.setAttribute("id", request.getParameter("id"));
	    request.getRequestDispatcher("/WEB-INF/Confirmacion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
