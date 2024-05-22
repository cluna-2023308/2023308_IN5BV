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
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaDatos();
        cmbCodigoCargoE.setItems(getEmpleados());
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleados(?)}");
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleCompra()}");
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
