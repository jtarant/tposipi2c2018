package servlet;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.AdminTurnos;
import controlador.TurnoView;

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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		String idOfuscado = request.getParameter("id");
		TurnoView turno;
		try 
		{
			turno = AdminTurnos.getInstancia().obtenerTurno(idOfuscado);
			
			request.setAttribute("paciente", turno.getPaciente().getNombre());
			request.setAttribute("fechaHora", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(turno.getFechaHoraInicio()));
			request.setAttribute("profesional", turno.getProfesional().getNombre());
			request.setAttribute("id", idOfuscado);
		    request.getRequestDispatcher("/WEB-INF/Confirmacion.jsp").forward(request, response);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
