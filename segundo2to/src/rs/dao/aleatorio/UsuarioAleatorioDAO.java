package rs.dao.aleatorio;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import rs.conexion.AConnection;
import rs.dao.RelacionDAO;
import rs.dao.UsuarioDAO;
import rs.modelo.Gender;
import rs.modelo.Usuario;
import rs.util.FileUtil;

public class UsuarioAleatorioDAO implements UsuarioDAO {
	final static Logger logger = Logger.getLogger(UsuarioAleatorioDAO.class);
	private RandomAccessFile file = null;
	private Hashtable<String, Integer> index;
	private int nDeleted;
	private static final int SIZE_ID = 15;
	private static final int SIZE_NOMBRE = 15;
	private static final int SIZE_GENERO = 10;
	private static final int SIZE_CIUDAD = 20;
	private static final int SIZE_ESTADOCIVIL = 15;
	private static final int SIZE_NIVELACADEMICO = 20;

	private static final int SIZE_RECORD = Character.BYTES + Character.BYTES * SIZE_ID + Character.BYTES * SIZE_NOMBRE
			+ Character.BYTES * SIZE_GENERO + Character.BYTES * SIZE_CIUDAD + FileUtil.SIZE_DATE
			+ Character.BYTES * SIZE_ESTADOCIVIL + Character.BYTES * SIZE_NIVELACADEMICO;

	public UsuarioAleatorioDAO() {
		
		if (file == null) {
			file = AConnection.getInstancia("usuarios");
			index = new Hashtable<String, Integer>();
			nDeleted = 0;
			Usuario usuario;
			int pos = 0;
			char deleted;
			try {
				file.seek(0);
				while (true) {
					deleted = file.readChar();
					usuario = readRecord();
					if (deleted == FileUtil.DELETED) {
						index.put(Integer.toString(nDeleted), pos++);
						nDeleted++;
					} else {
						index.put(usuario.getId(), pos++);

					}

				}
			} catch (EOFException e) {
				logger.info("Se termino de leer el archivo usuarios ");
				return;
			} catch (IOException e) {
				logger.error("Error al leer el archivo usuarios");
			}
		}
	}

	public List<Usuario> buscarTodos() {
		List<Usuario> ret = new ArrayList<Usuario>();
		Usuario usuario;
		char deleted;
		try {
			file.seek(0);
			while (true) {
				deleted = file.readChar();
				usuario = readRecord();
				if (deleted != FileUtil.DELETED) {
					ret.add(usuario);

				}
			}
		} catch (EOFException e) {
			logger.info("Se termino de cargar la lista de usuarios del archivo ");
			return ret;
		} catch (IOException e) {
			logger.error("Error al carga la lista de usuarios del archivo");
			return null;
		}
	}

	@Override
	public void insertar(Usuario usuario) {
		Integer pos = index.get(usuario.getId());
		if (pos != null)
			return;
		int nr = index.size() * SIZE_RECORD;
		
		try {
			file.seek(nr);
			file.writeChar(' ');
			writeRecord(usuario);
			index.put(usuario.getId(), nr / SIZE_RECORD);
		} catch (IOException e) {
			logger.error("No se pudo agregar el "+ usuario.getId()+" al archivo");
		}
	}

	@Override
	public void actualizar(Usuario usuario) {
		Integer pos = index.get(usuario.getId());
		if (pos == null)
			return;
		int nr = pos * SIZE_RECORD;
		try {
			file.seek(nr);
			file.writeChar(' ');
			writeRecord(usuario);
		} catch (IOException e) {
			logger.error("No se pudo modificar el "+ usuario.getId()+" del archivo");
		}

	}

	@Override
	public void borrar(Usuario usuario) {
		Integer pos = index.get(usuario.getId());

		if (pos == null)
			return;
		int nr = pos * SIZE_RECORD;

		try {
			file.seek(nr);
			file.writeChar(FileUtil.DELETED);
            index.remove(usuario.getId());
            index.put(Integer.toString(nDeleted), pos);

			nDeleted++;
		} catch (IOException e) {
			logger.error("No se pudo borrar el "+ usuario.getId()+" del archivo");
		}
	}

	public void pack() throws IOException {
		List<Usuario> usuarios = buscarTodos();
		AConnection.backup("usuarios");
		AConnection.delete("usuarios");
		file = AConnection.getInstancia("usuarios");
		index = new Hashtable<String, Integer>();
		nDeleted = 0;
		for (Usuario ea : usuarios)
			insertar(ea);
	}

	private Usuario readRecord() throws IOException {
		return new Usuario(FileUtil.readString(file, SIZE_ID), FileUtil.readString(file, SIZE_NOMBRE),
				Gender.valueOf(FileUtil.readString(file, SIZE_GENERO)), FileUtil.readString(file, SIZE_CIUDAD),
				FileUtil.readDate1(file), FileUtil.readString(file, SIZE_ESTADOCIVIL),
				FileUtil.readString(file, SIZE_NIVELACADEMICO));

	}

	private void writeRecord(Usuario usuario) throws IOException {
		FileUtil.writeString(file, usuario.getId(), SIZE_ID);
		FileUtil.writeString(file, usuario.getNombre(), SIZE_NOMBRE);
		FileUtil.writeString(file, usuario.getGenero().toString(), SIZE_GENERO);
		FileUtil.writeString(file, usuario.getCiudad(), SIZE_CIUDAD);
		FileUtil.writeDate1(file, usuario.getFechaNacimiento());
		FileUtil.writeString(file, usuario.getEstadoCivil(), SIZE_ESTADOCIVIL);
		FileUtil.writeString(file, usuario.getNivelAcademico(), SIZE_NIVELACADEMICO);

	}
}
