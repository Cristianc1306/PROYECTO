package rs.aplicacion;

import java.util.List;
import rs.logica.Calculo;
import rs.modelo.Relacion;
import rs.modelo.Usuario;
import rs.datos.Dato;
import rs.gui.*;

public class Coordinador {

	private Calculo calculo;
	private Dato dato;
	private DesktopFrame desktopFrame;
	private UsuariosForm usuariosForm;
	private UsuariosList usuariosList;
	private RelacionesForm relacionesForm;
	private RelacionesList relacionesList;
	private MetodosList metodosList;
	private UsuariosMetodoForm usuariosMetodoForm;

	public Calculo getCalculo() {
		return calculo;
	}

	public void setCalculo(Calculo calculo) {
		this.calculo = calculo;
	}

	public Dato getDato() {
		return dato;
	}

	public void setDato(Dato dato) {
		this.dato = dato;
	}

	public DesktopFrame getDesktopFrame() {
		return desktopFrame;
	}

	public void setDesktopFrame(DesktopFrame desktopFrame) {
		this.desktopFrame = desktopFrame;
	}

	public UsuariosForm getUsuariosForm() {
		return usuariosForm;
	}

	public void setUsuariosForm(UsuariosForm usuariosForm) {
		this.usuariosForm = usuariosForm;
	}

	public UsuariosList getUsuariosList() {
		return usuariosList;
	}

	public void setUsuariosList(UsuariosList usuariosList) {
		this.usuariosList = usuariosList;
	}

	public RelacionesForm getRelacionesForm() {
		return relacionesForm;
	}

	public void setRelacionesForm(RelacionesForm relacionesForm) {
		this.relacionesForm = relacionesForm;
	}

	public RelacionesList getRelacionesList() {
		return relacionesList;
	}

	public void setRelacionesList(RelacionesList relacionesList) {
		this.relacionesList = relacionesList;
	}

	public MetodosList getMetodosList() {
		return metodosList;
	}

	public void setMetodosList(MetodosList metodosList) {
		this.metodosList = metodosList;
	}

	public UsuariosMetodoForm getUsuariosMetodoForm() {
		return usuariosMetodoForm;
	}

	public void setUsuariosMetodoForm(UsuariosMetodoForm usuariosMetodoForm) {
		this.usuariosMetodoForm = usuariosMetodoForm;
	}

	public void mostrarInsertarUsuario() {
		usuariosForm.accion(Constantes.INSERTAR);
		usuariosForm.setVisible(true);
	}

	public void insertarUsuario(Usuario usuario) {
		dato.escribirUsuarios(usuario);
		calculo.agregarUsuario(usuario);
		usuariosForm.setVisible(false);
		usuariosList.addRow(usuario);

	}

	public void verUsuarios() {
		usuariosList.loadTable();
		usuariosList.setVisible(true);
	}

	public void mostrarInsertarRelacion() {
		relacionesForm.accion(Constantes.INSERTAR);
		relacionesForm.setVisible(true);
	}

	public void insertarRelacion(Relacion relacion) {
		dato.escribirRelaciones(relacion);
		calculo.agregarRelacion(relacion);
		relacionesForm.setVisible(false);
		relacionesList.addRow(relacion);

	}

	public void verRelaciones() {
		relacionesList.loadTable();
		relacionesList.setVisible(true);
	}

	public void cancelarUsuario() {
		usuariosForm.setVisible(false);
	}

	public Usuario buscarUsuario(String id) {
		return calculo.buscarVertice(id);
	}

	public void cancelarRelacion() {
		relacionesForm.setVisible(false);
	}

	public List<Usuario> listaUsuarios() {
		return calculo.getUsuarios();
	}

	public List<Relacion> listaRelacion() {
		return calculo.getArcos();
	}

	public double mostrarGradoPromedio() {
		return calculo.gradoPromedio();
	}

	public void losMetodosList(Usuario u, String id1, String id2, int opcion) {
		metodosList.loadTable(u, id1, id2, opcion);
		metodosList.setVisible(true);
	}

	public List<Usuario> listaInfluyentes() {
		return calculo.masInfluyentes();
	}

	public List<Usuario> listaCaminoMasNuevo(String id1, String id2) {
		return calculo.caminoMasNuevo(id1, id2);
	}

	public int mostrarTiempoAmistad(Usuario u1, Usuario u2) {
		return calculo.tiempoDeAmistad(u1, u2);
	}

	public List<Usuario> listaDeAmigos(Usuario u) {
		return calculo.amigosDe(u);
	}

	public int mostrarCantidadAmigos(Usuario u) {
		return calculo.cantidadAmigos(u);
	}

	public Usuario mostrarMasInfluyente() {
		return calculo.masInfluyente();
	}

	public void mostrarCaminoUsuarios(int opcion) {
		usuariosMetodoForm.accion(opcion);
		usuariosMetodoForm.setVisible(true);
	}

	public void cancelarCaminoUsuarios() {
		usuariosMetodoForm.setVisible(false);
	}
}
