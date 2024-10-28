package org.example.ejercicioi.app;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.ejercicioi.dao.PersonaDao;
import org.example.ejercicioi.model.Persona;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;


/**
 * Controlador para la tabla de personas en la interfaz gráfica.
 * Maneja las operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * y la interacción con la tabla de personas.
 */
public class tablaController {

	@FXML
	private Button btAgregar; ///< Botón para agregar una nueva persona.
	@FXML
	private Button btEliminar; ///< Botón para eliminar una persona seleccionada.
	@FXML
	private Button btModificar; ///< Botón para modificar una persona seleccionada.
	@FXML
	private TextField txtBuscar; ///< Campo de texto para buscar personas.
	@FXML
	private TableColumn<Persona, String> columnaApellidos; ///< Columna para los apellidos de las personas.
	@FXML
	private TableColumn<Persona, Integer> columnaEdad; ///< Columna para la edad de las personas.
	@FXML
	private TableColumn<Persona, String> columnaNombre; ///< Columna para los nombres de las personas.
	@FXML
	private TableView<Persona> tablaVista; ///< Tabla que muestra la lista de personas.
	@FXML
	private Label lblBuscador;
	private  ContextMenu contextMenu;

	private ObservableList<Persona> personas = FXCollections.observableArrayList(); ///< Lista observable de personas.
	private FilteredList<Persona> filtro; ///< Lista filtrada para la búsqueda de personas.

	private TextField txtNombre; ///< Campo de texto para el nombre de la persona.
	private TextField txtApellidos; ///< Campo de texto para los apellidos de la persona.
	private TextField txtEdad; ///< Campo de texto para la edad de la persona.
	private Button btnGuardar; ///< Botón para guardar los cambios.
	private Button btnCancelar; ///< Botón para cancelar la operación.
	private Stage modal; ///< Ventana modal para ingresar datos de la persona.
	MenuItem item1;
	MenuItem item2;
	@FXML
	private ResourceBundle resources;
	/**
	 * Inicializa la tabla y configura las columnas.
	 * Carga la lista de personas desde la base de datos.
	 */
	public void initialize() throws FileNotFoundException {
		columnaNombre.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNombre()));
		columnaApellidos.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getApellidos()));
		columnaEdad.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEdad()));
		FontIcon iconoAniadir = new FontIcon(FontAwesomeRegular.PLUS_SQUARE);
		iconoAniadir.setIconColor(javafx.scene.paint.Color.WHITE);
		btAgregar.setGraphic(iconoAniadir);
		FontIcon iconoModificar=new FontIcon(FontAwesomeRegular.CARET_SQUARE_UP);
		iconoModificar.setIconColor(javafx.scene.paint.Color.WHITE);
		btModificar.setGraphic(iconoModificar);
		FontIcon iconoEliminar=new FontIcon(FontAwesomeRegular.MINUS_SQUARE);
		iconoEliminar.setIconColor(javafx.scene.paint.Color.WHITE);
		btEliminar.setGraphic(iconoEliminar);
		personas = PersonaDao.cargarPersonas();
		contextMenu = new ContextMenu();
		item1 = new MenuItem(resources.getString("btModificar"));
		item2 = new MenuItem(resources.getString("btEliminar"));
		item1.setOnAction(event -> modificar(event));
		item2.setOnAction(event -> eliminar(event));
		contextMenu.getItems().addAll(item1,item2);
		filtro = new FilteredList<>(personas);
		tablaVista.setItems(filtro);

		txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> filtrar(null));
	}

	/**
	 * Maneja la acción de agregar una nueva persona.
	 * Muestra la ventana modal para ingresar datos.
	 *
	 * @param event El evento de acción.
	 */
	@FXML
	void agregarPersona(ActionEvent event) {
		mostrarVentanaDatos((Stage) btAgregar.getScene().getWindow(), false);
		btnGuardar.setOnAction(actionEvent -> {
			guardar(false);
			filtro.setPredicate(null);
			tablaVista.getSelectionModel().clearSelection();
		});
		btnCancelar.setOnAction(actionEvent -> cancelar());
	}

	/**
	 * Muestra una ventana modal para ingresar o modificar los datos de una persona.
	 *
	 * @param ventanaPrincipal La ventana principal desde donde se abre el modal.
	 * @param esModif Indica si es una modificación (true) o una nueva entrada (false).
	 */
	public void mostrarVentanaDatos(Stage ventanaPrincipal, boolean esModif) {
		modal = new Stage();
		modal.setResizable(false);
		modal.initOwner(ventanaPrincipal);
		modal.initModality(Modality.WINDOW_MODAL);

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		Label lblNombre = new Label(resources.getString("nombre"));
		txtNombre = new TextField(esModif ? tablaVista.getSelectionModel().getSelectedItem().getNombre() : "");
		gridPane.add(lblNombre, 0, 0);
		gridPane.add(txtNombre, 1, 0);

		Label lblApellidos = new Label(resources.getString("apellidos"));
		txtApellidos = new TextField(esModif ? tablaVista.getSelectionModel().getSelectedItem().getApellidos() : "");
		gridPane.add(lblApellidos, 0, 1);
		gridPane.add(txtApellidos, 1, 1);

		Label lblEdad = new Label(resources.getString("edad"));
		txtEdad = new TextField(esModif ? String.valueOf(tablaVista.getSelectionModel().getSelectedItem().getEdad()) : "");
		gridPane.add(lblEdad, 0, 2);
		gridPane.add(txtEdad, 1, 2);

		btnGuardar = new Button(resources.getString("btGuardar"));
		btnCancelar = new Button(resources.getString("btCancelar"));
		FlowPane flowPane = new FlowPane(btnGuardar, btnCancelar);
		flowPane.setAlignment(Pos.CENTER);
		flowPane.setHgap(20);
		gridPane.add(flowPane, 0, 3, 2, 1);

		Scene scene = new Scene(gridPane, 300, 150);
		modal.setScene(scene);
		modal.setResizable(false);
		modal.setTitle(esModif ? resources.getString("ep") : resources.getString("nvp"));
		modal.show();
	}

	/**
	 * Guarda la nueva persona o modifica una existente.
	 *
	 * @param esModificar Indica si se está modificando (true) o agregando (false).
	 */
	public void guardar(boolean esModificar) {
		if (valido()) {
			String nombre = txtNombre.getText();
			String apellidos = txtApellidos.getText();
			int edad = Integer.parseInt(txtEdad.getText());

			Persona nuevaPersona = new Persona(nombre, apellidos, edad);

			boolean existe = false;
			for (Persona persona : personas) {
				if (persona.equals(nuevaPersona)) {
					if (esModificar && persona.equals(tablaVista.getSelectionModel().getSelectedItem())) {
						continue;
					}
					existe = true;
					break;
				}
			}

			if (existe) {
				ArrayList<String> errores = new ArrayList<>();
				errores.add(resources.getString("existe"));
				mostrarAlertError(errores);
				return;
			}
			if (esModificar) {
				Persona personaSeleccionada = tablaVista.getSelectionModel().getSelectedItem();
				Persona personaModificada = new Persona(personaSeleccionada.getId(), nombre, apellidos, edad);

				if (PersonaDao.modificarPersona(personaSeleccionada, personaModificada)) {
					int index = personas.indexOf(personaSeleccionada);
					if (index >= 0) {
						personas.set(index, personaModificada);
						mostrarVentanaModificado();
					}
				}
			} else {
				int idGenerado = PersonaDao.insertarPersona(nuevaPersona);
				if (idGenerado != -1) {
					nuevaPersona.setId(idGenerado);
					personas.add(nuevaPersona);
					mostrarVentanaAgregado();
				}
			}

			modal.close();
			txtBuscar.setText("");
			filtro.setPredicate(null);
		}
	}

	/**
	 * Valida los campos de entrada antes de guardar.
	 *
	 * @return true si los campos son válidos, false en caso contrario.
	 */
	private boolean valido() {
		boolean error = false;
		ArrayList<String> errores = new ArrayList<>();
		if (txtNombre.getText().equals("")) {
			errores.add(resources.getString("campoN"));
			error = true;
		}
		if (txtApellidos.getText().equals("")) {
			errores.add(resources.getString("campoA"));
			error = true;
		}
		try {
			Integer.parseInt(txtEdad.getText());
		} catch (NumberFormatException e) {
			errores.add(resources.getString("campoE"));
			error = true;
		}
		if (error) {
			mostrarAlertError(errores);
			return false;
		}
		return true;
	}

	/**
	 * Elimina la persona seleccionada de la tabla.
	 *
	 * @param event El evento de acción.
	 */
	@FXML
	void eliminar(ActionEvent event) {
		Persona p = tablaVista.getSelectionModel().getSelectedItem();
		if (p == null) {
			ArrayList<String> lst = new ArrayList<>();
			lst.add((resources.getString("noSelect")));
			mostrarAlertError(lst);
		} else {
			if (PersonaDao.eliminarPersona(p)) {
				personas.remove(p);
				filtro.setPredicate(null);
				mostrarVentanaEliminado();
				tablaVista.getSelectionModel().clearSelection();
				txtBuscar.setText("");
			}
		}
	}

	/**
	 * Modifica la persona seleccionada.
	 *
	 * @param event El evento de acción.
	 */
	@FXML
	void modificar(ActionEvent event) {
		Persona p = tablaVista.getSelectionModel().getSelectedItem();
		if (p == null) {
			ArrayList<String> lst = new ArrayList<>();
			lst.add((resources.getString("noSelect")));
			mostrarAlertError(lst);
		} else {
			mostrarVentanaDatos((Stage) btModificar.getScene().getWindow(), true);
			btnGuardar.setOnAction(actionEvent -> {
				guardar(true);
				tablaVista.getSelectionModel().clearSelection();
			});
			btnCancelar.setOnAction(actionEvent -> cancelar());
		}
	}

	/**
	 * Filtra la lista de personas según el texto ingresado en el campo de búsqueda.
	 *
	 * @param event El evento de acción.
	 */
	@FXML
	void filtrar(ActionEvent event) {
		if (txtBuscar.getText().isEmpty()) {
			tablaVista.setItems(personas);
		} else {
			String textoBusqueda = txtBuscar.getText().toLowerCase();
			filtro.setPredicate(persona ->
					persona.getNombre().toLowerCase().startsWith(textoBusqueda)
			);
			tablaVista.setItems(filtro);
		}
	}

	/**
	 * Muestra un mensaje de error en un cuadro de diálogo.
	 *
	 * @param lst Lista de mensajes de error a mostrar.
	 */
	private void mostrarAlertError(ArrayList<String> lst) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initOwner(btAgregar.getScene().getWindow());
		alert.setHeaderText(null);
		alert.setTitle("Error");
		String error = String.join("\n", lst);
		alert.setContentText(error);
		alert.showAndWait();
	}

	/**
	 * Muestra un mensaje informando que una persona fue agregada correctamente.
	 */
	private void mostrarVentanaAgregado() {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.initOwner(btAgregar.getScene().getWindow());
		alerta.setHeaderText(null);
		alerta.setTitle("Info");
		alerta.setContentText((resources.getString("ok")));
		alerta.showAndWait();
	}

	/**
	 * Muestra un mensaje informando que una persona fue modificada correctamente.
	 */
	private void mostrarVentanaModificado() {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.initOwner(btAgregar.getScene().getWindow());
		alerta.setHeaderText(null);
		alerta.setTitle("Info");
		alerta.setContentText((resources.getString("okM")));
		alerta.showAndWait();
	}

	/**
	 * Muestra un mensaje informando que una persona fue eliminada correctamente.
	 */
	private void mostrarVentanaEliminado() {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.initOwner(btAgregar.getScene().getWindow());
		alerta.setHeaderText(null);
		alerta.setTitle("Info");
		alerta.setContentText((resources.getString("okD")));
		alerta.showAndWait();
	}

	/**
	 * Cierra la ventana modal actual.
	 */
	private void cancelar() {
		modal.close();
	}

	/**
	 * Muestra el menú contextual al hacer clic con el botón derecho del ratón.
	 *
	 * @param mouseEvent El evento del mouse que desencadena la acción.
	 */
	public void mostrarMenuContextual(MouseEvent mouseEvent) {
		if(mouseEvent.getButton()==MouseButton.SECONDARY) {
			contextMenu.show(btAgregar.getScene().getWindow());
		}
	}
	/**
	 * Cambia el idioma de la interfaz según el botón presionado.
	 *
	 * @param event El evento de acción que desencadena el cambio de idioma.
	 */
	@FXML
	void cambiarIdioma(ActionEvent event) {
		String idiomaSeleccionado = ((Button) event.getSource()).getText().toLowerCase();

		if (idiomaSeleccionado.equals("eu")) {
			cambiarIdioma("eu");
		} else if (idiomaSeleccionado.equals("es")) {
			cambiarIdioma("es");
		}else{
			cambiarIdioma("en");
		}
	}
	/**
	 * Cambia el idioma de la interfaz a uno específico.
	 *
	 * @param idioma El código del idioma a cambiar (eu, es, en).
	 */

	private void cambiarIdioma(String idioma) {
		resources = ResourceBundle.getBundle("/org/example/ejercicioi/languages/lang", new Locale(idioma));
		actualizarInterfaz(); // Asegúrate de llamar a este método
	}
	/**
	 * Actualiza la interfaz de usuario con los textos localizados según el idioma seleccionado.
	 */
	private void actualizarInterfaz() {
		// Actualiza los títulos de las columnas
		columnaNombre.setText(resources.getString("nombre"));
		columnaApellidos.setText(resources.getString("apellidos"));
		columnaEdad.setText(resources.getString("edad"));

		// Actualiza los textos de los botones
		btAgregar.setText(resources.getString("btAgregar"));
		btModificar.setText(resources.getString("btModificar"));
		btEliminar.setText(resources.getString("btEliminar"));

		// Actualiza el texto de la etiqueta buscador
		lblBuscador.setText(resources.getString("lblBuscador"));

		item1.setText(resources.getString("btModificar"));
		item2.setText(resources.getString("btEliminar"));
	}

}
