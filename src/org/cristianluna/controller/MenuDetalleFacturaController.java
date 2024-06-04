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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.cristianluna.bean.DetalleFactura;
import org.cristianluna.bean.Factura;
import org.cristianluna.bean.Productos;
import org.cristianluna.db.Conexion;
import org.cristianluna.system.Principal;


public class MenuDetalleFacturaController implements Initializable{
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 20/05/2024
    
    Fecha de modificacion: --/--/----
    */

    private Principal escenarioPrincipal;
    private ObservableList <DetalleFactura> listaDetalleF;
    private ObservableList <Factura> listaFactura;
    private ObservableList <Productos> listaProductos;
    @FXML private Button btnRegresarF;
    @FXML private TableView tblDetalleF;
    @FXML private TableColumn colCodDetalleF;
    @FXML private TableColumn colPrecioU;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colNumFac;
    @FXML private TableColumn colCodProd;
    @FXML private TextField txtCodigoDetalleF;
    @FXML private TextField txtPrecioU;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox cmbNumeroF;
    @FXML private ComboBox cmbCodigoP;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaDatos();
        cmbNumeroF.setItems(getFactura());
        cmbCodigoP.setItems(getProductos());
    }
    
    public void cargaDatos(){
        tblDetalleF.setItems(getDetalleFactura());
        colCodDetalleF.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("codigoDetalleFactura"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Double>("precioUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("cantidad"));
        colNumFac.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("numeroFactura"));
        colCodProd.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("codigoProducto"));
    }
    
    public void selecionarElementos(){
       txtCodigoDetalleF.setText(String.valueOf(((DetalleFactura)tblDetalleF.getSelectionModel().getSelectedItem()).getNumeroFactura()));
       txtPrecioU.setText(String.valueOf(((DetalleFactura)tblDetalleF.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
       txtCantidad.setText(String.valueOf(((DetalleFactura)tblDetalleF.getSelectionModel().getSelectedItem()).getCantidad()));
       cmbNumeroF.getSelectionModel().select(buscarFactura(((DetalleFactura)tblDetalleF.getSelectionModel().getSelectedItem()).getNumeroFactura()));
       cmbCodigoP.getSelectionModel().select(buscarProductos(((DetalleFactura)tblDetalleF.getSelectionModel().getSelectedItem()).getCodigoProducto()));  
    }
    
    public Factura buscarFactura (int numeroFactura ){
        Factura resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarFactura(?)}");
            procedimiento.setInt(1, numeroFactura);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                java.sql.Date fecha = registro.getDate("fechaFactura");
                LocalDate fechaFac = fecha.toLocalDate();
                resultado = new Factura(registro.getInt("numeroFactura"),
                                            registro.getString("estado"),
                                            registro.getDouble("totalFactura"),
                                            fechaFac,
                                            registro.getInt("codigoCliente"),
                                            registro.getInt("codigoEmpleado")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }    
        return resultado;
    }
    
    public Productos buscarProductos (int codigoProducto ){
        Productos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProducto(?)}");
            procedimiento.setInt(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Productos(registro.getInt("codigoProducto"),
                                            registro.getString("descripcion"),
                                            registro.getDouble("precioUnitario"),
                                            registro.getDouble("precioDocena"),
                                            registro.getDouble("precioMayor"),
                                            registro.getString("imagenProducto"),
                                            registro.getInt("existencia"),
                                            registro.getInt("codigoTipoProducto"),
                                            registro.getInt("codigoProveedor")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }    
        return resultado;
    }
    
    public ObservableList<DetalleFactura> getDetalleFactura(){
        ArrayList<DetalleFactura> lista = new ArrayList<DetalleFactura>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new DetalleFactura (resultado.getInt("codigoDetalleFactura"),
                                        resultado.getDouble("precioUnitario"),
                                        resultado.getInt("cantidad"),
                                        resultado.getInt("numeroFactura"),
                                        resultado.getInt("codigoProducto")            
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaDetalleF = FXCollections.observableArrayList(lista); 
    }
    
    public ObservableList<Factura> getFactura(){
        ArrayList<Factura> listaF = new ArrayList<Factura>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                java.sql.Date fecha = resultado.getDate("fechaFactura");
                LocalDate fechaFac = fecha.toLocalDate();
                listaF.add(new Factura(resultado.getInt("numeroFactura"),
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
        return listaFactura = FXCollections.observableArrayList(listaF);
    }
    
    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new Productos(resultado.getInt("codigoProducto"),
                                        resultado.getString("descripcion"),
                                        resultado.getDouble("precioUnitario"),
                                        resultado.getDouble("precioDocena"),
                                        resultado.getDouble("precioMayor"),
                                        resultado.getString("imagenProducto"),
                                        resultado.getInt("existencia"),
                                        resultado.getInt("codigoTipoProducto"),
                                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableList(listaP);
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clickRegresarF(ActionEvent event){
        if(event.getSource() == btnRegresarF){
            escenarioPrincipal.menuFacturaV();
        }
    }    
}
