package org.cristianluna.controller;

import java.net.URL;
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
        colFechaF.setCellValueFactory(new PropertyValueFactory<Factura, String>("emailProveedor"));
        colCodCliente.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("descripcion"));
        colCodEmpleado.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoProveedor"));
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
