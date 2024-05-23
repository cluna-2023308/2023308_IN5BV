package org.cristianluna.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.cristianluna.bean.CargoEmpleado;
import org.cristianluna.bean.Empleados;
import org.cristianluna.db.Conexion;
import org.cristianluna.system.Principal;


public class MenuEmpleadosController implements Initializable{
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 20/05/2024
    
    Fecha de modificacion: --/--/----
    */
    
    private Principal escenarioPrincipal;
    private ObservableList <Empleados> listaEmpleados;
    private ObservableList <CargoEmpleado> listaCargoE;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    @FXML private Button btnRegresar;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodEmple;
    @FXML private TableColumn colNombreE;
    @FXML private TableColumn colApellidoE;
    @FXML private TableColumn colSueldo;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTurno;
    @FXML private TableColumn colCodCargoE;
    @FXML private TextField txtCodigoE;
    @FXML private TextField txtNombreE;
    @FXML private TextField txtApellidoE;
    @FXML private TextField txtSueldo;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTurno;
    @FXML private ComboBox cmbCodigoCargoE;
    @FXML private Button btnAgregar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReportes;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaDatos();
        cmbCodigoCargoE.setItems(getCargoEmpleado());
    } 
    
    public void cargaDatos(){
        tblEmpleados.setItems(getEmpleados());
        colCodEmple.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNombreE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
        colApellidoE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurno.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCodCargoE.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoCargoEmpleado"));
    }
    
    public void selecionarElementos(){
       txtCodigoE.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
       txtNombreE.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
       txtApellidoE.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
       txtSueldo.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo())); 
       txtDireccion.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion());
       txtTurno.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTurno());     
       cmbCodigoCargoE.getSelectionModel().select(buscarCargoEmpleado(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
    }
    
    public CargoEmpleado buscarCargoEmpleado (int codigoCargoEmpleado ){
        CargoEmpleado resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargoEmpleado(?)}");
            procedimiento.setInt(1, codigoCargoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new CargoEmpleado(registro.getInt("codigoCargoEmpleado"),
                                            registro.getString("nombreCargo"),
                                            registro.getString("descripcionCargo")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }    
        return resultado;
    }
    
    public ObservableList<Empleados> getEmpleados(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleados (resultado.getInt("codigoEmpleado"),
                                        resultado.getString("nombresEmpleado"),
                                        resultado.getString("apellidosEmpleado"),
                                        resultado.getDouble("sueldo"),
                                        resultado.getString("direccion"),
                                        resultado.getString("turno"),
                                        resultado.getInt("codigoCargoEmpleado")            
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableArrayList(lista); 
    }
    
    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> listaCargo = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaCargo.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCargoE = FXCollections.observableList(listaCargo);
    }
    
    public void agregar (){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("org/cristianluna/images/Guardar.png"));
                imgEliminar.setImage(new Image("org/cristianluna/images/Eliminar.png"));
                tipoDeOperacion = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar ();
                desactivarControles();
                limpiarControles ();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("org/cristianluna/images/IconAgregarCliente.png"));
                imgEliminar.setImage(new Image("org/cristianluna/images/IconEliminarCliente.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargaDatos();
                break;
         }
     }
     
    public void guardar (){
         Empleados registro = new Empleados();
         registro.setCodigoEmpleado(Integer.parseInt(txtCodigoE.getText()));
         registro.setCodigoCargoEmpleado(((CargoEmpleado)cmbCodigoCargoE.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
         registro.setNombresEmpleado(txtNombreE.getText());
         registro.setApellidosEmpleado(txtApellidoE.getText());
         registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
         registro.setDireccion(txtDireccion.getText());
         registro.setTurno(txtTurno.getText());
         try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall
            ("{call sp_agregarEmpleados(?, ?, ?, ?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getCodigoEmpleado());
                procedimiento.setString(2, registro.getNombresEmpleado());
                procedimiento.setString(3, registro.getApellidosEmpleado());
                procedimiento.setDouble(4, registro.getSueldo());
                procedimiento.setString(5, registro.getDireccion());
                procedimiento.setString(6, registro.getTurno());
                procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
                procedimiento.execute();
            listaEmpleados.add(registro);
         }catch (Exception e){
             e.printStackTrace();
         }
     }
    
    public void eliminar (){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("org/cristianluna/images/IconAgregarCliente.png"));
                imgEliminar.setImage(new Image("org/cristianluna/images/IconEliminarCliente.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrimar si elimina el registro", "Eliminar Empleado", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleados(?)}");
                            procedimiento.setInt(1, ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else 
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Elemento");
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblEmpleados.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    imgEditar.setImage(new Image("org/cristianluna/images/Guardar.png"));
                    imgReportes.setImage(new Image("org/cristianluna/images/Eliminar.png"));                    
                    txtCodigoE.setEditable(false);
                    cmbCodigoCargoE.setDisable(true);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun Elemento");
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("org/cristianluna/images/IconEditarCliente.png"));
                imgReportes.setImage(new Image("org/cristianluna/images/IconReportesCliente.png"));
                desactivarControles();
                tipoDeOperacion = operaciones.NINGUNO;
                cargaDatos();
                limpiarControles();
                break;
        }
    }
    
    public void reporte() {
        switch (tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("org/cristianluna/images/IconEditarCliente.png"));
                imgReportes.setImage(new Image("org/cristianluna/images/IconReportesCliente.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void actualizar (){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleados(?, ?, ?, ?, ?, ?, ?)}");
            Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNombresEmpleado(txtNombreE.getText());
            registro.setApellidosEmpleado(txtApellidoE.getText());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDireccion(txtDireccion.getText());
            registro.setTurno(txtTurno.getText());
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtCodigoE.setEditable(false);
        txtNombreE.setEditable(false);
        txtApellidoE.setEditable(false);
        txtSueldo.setEditable(false);
        txtDireccion.setEditable(false);
        txtTurno.setEditable(false);
        cmbCodigoCargoE.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoE.setEditable(true);
        txtNombreE.setEditable(true);
        txtApellidoE.setEditable(true);
        txtSueldo.setEditable(true);
        txtDireccion.setEditable(true);
        txtTurno.setEditable(true);
        cmbCodigoCargoE.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoE.clear();
        txtNombreE.clear();
        txtApellidoE.clear();
        txtSueldo.clear();
        txtDireccion.clear();
        txtTurno.clear();
        tblEmpleados.getSelectionModel().getSelectedItem();
        cmbCodigoCargoE.getSelectionModel().getSelectedItem();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clickRegresar(ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalV();
        }
    }
}
