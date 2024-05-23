package org.cristianluna.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.cristianluna.bean.Clientes;
import org.cristianluna.bean.Empleados;
import org.cristianluna.bean.Factura;
import org.cristianluna.db.Conexion;
import org.cristianluna.system.Principal;

public class MenuFacturaController implements Initializable{
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 20/05/2024
    
    Fecha de modificacion: --/--/----
    */

    private Principal escenarioPrincipal;
    private ObservableList <Factura> listaFactura;
    private ObservableList <Clientes> listaClientes;
    private ObservableList <Empleados> listaEmpleados;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    @FXML private Button btnRegresar;
    @FXML private MenuItem btnMenuDetalleFactura;
    @FXML private TableView tblFactura;
    @FXML private TableColumn colNumFac;
    @FXML private TableColumn colEstado;
    @FXML private TableColumn colTotalF;
    @FXML private TableColumn colFechaF;
    @FXML private TableColumn colCodCliente;
    @FXML private TableColumn colCodEmpleado;
    @FXML private TextField txtNumeroF;
    @FXML private TextField txtEstado;
    @FXML private TextField txtTotalF;
    @FXML private DatePicker datePickerFechaFactura;    
    @FXML private ComboBox cmbCodigoCli;
    @FXML private ComboBox cmbCodigoEmp;
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
        cmbCodigoCli.setItems(getClientes());
        cmbCodigoEmp.setItems(getEmpleados());
    } 
    
    public void cargaDatos(){
        tblFactura.setItems(getFactura());
        colNumFac.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
        colTotalF.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
        colFechaF.setCellValueFactory(new PropertyValueFactory<Factura, String>("fechaFactura"));
        colCodCliente.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoCliente"));
        colCodEmpleado.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoEmpleado"));
    }
    
    public void selecionarElementos(){
       txtNumeroF.setText(String.valueOf(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
       txtEstado.setText(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getEstado());
       txtTotalF.setText(String.valueOf(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getTotalFactura()));
       cmbCodigoCli.getSelectionModel().select(buscarClientes(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getCodigoCliente()));
       cmbCodigoEmp.getSelectionModel().select(buscarEmpleados(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));  
    }
    
    public Clientes buscarClientes (int codigoCliente ){
        Clientes resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarClientes(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Clientes(registro.getInt("codigoCliente"),
                                            registro.getString("NITCliente"),
                                            registro.getString("nombreCliente"),
                                            registro.getString("apellidoCliente"),
                                            registro.getString("direccionCliente"),
                                            registro.getString("telefonoCliente"),
                                            registro.getString("correoCliente")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }    
        return resultado;
    }
    
    public Empleados buscarEmpleados (int codigoEmpleado ){
        Empleados resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleados(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                                            registro.getString("nombresEmpleado"),
                                            registro.getString("apellidosEmpleado"),
                                            registro.getDouble("sueldo"),
                                            registro.getString("direccion"),
                                            registro.getString("turno"),
                                            registro.getInt("codigoCargoEmpleado")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }    
        return resultado;
    }
    
    public ObservableList<Factura> getFactura(){
        ArrayList<Factura> lista = new ArrayList<Factura>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                java.sql.Date fecha = resultado.getDate("fechaFactura");
                LocalDate fechaFac = fecha.toLocalDate();
                lista.add(new Factura (resultado.getInt("numeroFactura"),
                                        resultado.getString("estado"),
                                        resultado.getDouble("totalFactura"),
                                        fechaFac,
                                        resultado.getInt("codigoCliente"),
                                        resultado.getInt("codigoEmpleado")            
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaFactura = FXCollections.observableArrayList(lista); 
    }
    
    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> listaClie = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaClie.add(new Clientes(resultado.getInt("codigoCliente"),
                                        resultado.getString("NITCliente"),
                                        resultado.getString("nombreCliente"),
                                        resultado.getString("apellidoCliente"),
                                        resultado.getString("direccionCliente"),
                                        resultado.getString("telefonoCliente"),
                                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableList(listaClie);
    }
    
    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> listaEmp = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaEmp.add(new Empleados(resultado.getInt("codigoEmpleado"),
                                            resultado.getString("nombresEmpleado"),
                                            resultado.getString("apellidosEmpleado"),
                                            resultado.getDouble("sueldo"),
                                            resultado.getString("direccion"),
                                            resultado.getString("turno"),
                                            resultado.getInt("codigoCargoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableList(listaEmp);
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
                datePickerFechaFactura.setValue(null);
                break;
         }
     }
     
    public void guardar (){
         Factura registro = new Factura();
         registro.setNumeroFactura(Integer.parseInt(txtNumeroF.getText()));
         registro.setCodigoCliente(((Clientes)cmbCodigoCli.getSelectionModel().getSelectedItem()).getCodigoCliente());
         registro.setCodigoEmpleado(((Empleados)cmbCodigoEmp.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
         registro.setEstado(txtEstado.getText());
         registro.setTotalFactura(Double.parseDouble(txtTotalF.getText()));
         LocalDate fechaDoc = datePickerFechaFactura.getValue();
         Date fechaFactura = Date.valueOf(fechaDoc);
         try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall
            ("{call sp_agregarFactura(?, ?, ?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getNumeroFactura());
                procedimiento.setString(2, registro.getEstado());
                procedimiento.setDouble(3, registro.getTotalFactura());
                procedimiento.setDate(4, fechaFactura);
                procedimiento.setInt(5, registro.getCodigoCliente());
                procedimiento.setInt(6, registro.getCodigoEmpleado());
                procedimiento.execute();
            listaFactura.add(registro);
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
                if(tblFactura.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrimar si elimina el registro", "Eliminar Factura", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarFactura(?)}");
                            procedimiento.setInt(1, ((Factura)tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
                            procedimiento.execute();
                            listaFactura.remove(tblFactura.getSelectionModel().getSelectedItem());
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
                if(tblFactura.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    imgEditar.setImage(new Image("org/cristianluna/images/Guardar.png"));
                    imgReportes.setImage(new Image("org/cristianluna/images/Eliminar.png"));                    
                    txtNumeroF.setEditable(false);
                    cmbCodigoCli.setDisable(true);
                    cmbCodigoEmp.setDisable(true);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarFactura(?, ?, ?, ?, ?, ?)}");
            Factura registro = (Factura)tblFactura.getSelectionModel().getSelectedItem();
            registro.setEstado(txtEstado.getText());
            registro.setTotalFactura(Double.parseDouble(txtTotalF.getText()));
            LocalDate fechaFac = datePickerFechaFactura.getValue();
            Date fechaFactura = Date.valueOf(fechaFac);
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setDate(4, fechaFactura);
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getNumeroFactura());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtNumeroF.setEditable(false);
        txtEstado.setEditable(false);
        txtTotalF.setEditable(false);
        datePickerFechaFactura.setEditable(false);
        cmbCodigoCli.setDisable(true);
        cmbCodigoEmp.setDisable(true);
    }
    
    public void activarControles(){
        txtNumeroF.setEditable(true);
        txtEstado.setEditable(true);
        txtTotalF.setEditable(true);
        datePickerFechaFactura.setEditable(true);
        cmbCodigoCli.setDisable(false);
        cmbCodigoEmp.setDisable(false);
    }
    
    public void limpiarControles(){
        txtNumeroF.clear();
        txtEstado.clear();
        txtTotalF.clear();
        tblFactura.getSelectionModel().getSelectedItem();
        cmbCodigoCli.getSelectionModel().getSelectedItem();
        cmbCodigoEmp.getSelectionModel().getSelectedItem();
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
    
    @FXML
    public void clickDetalleFactura(ActionEvent event){
        if(event.getSource() == btnMenuDetalleFactura){
            escenarioPrincipal.menuDetalleFacturaV();
        }
    }
}
