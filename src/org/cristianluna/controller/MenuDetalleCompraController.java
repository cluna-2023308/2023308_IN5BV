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
import org.cristianluna.bean.Compras;
import org.cristianluna.bean.DetalleCompra;
import org.cristianluna.bean.Productos;
import org.cristianluna.db.Conexion;
import org.cristianluna.system.Principal;

public class MenuDetalleCompraController implements Initializable{
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 20/05/2024
    
    Fecha de modificacion: 22/05/2024
    */
    
    
    private Principal escenarioPrincipal;
    /*private ObservableList <DetalleCompra> listaDetalleC;*/
    @FXML private Button btnRegresarC;
    /*@FXML private TableView tblDetalleC;
    @FXML private TableColumn colCodDetalleC;
    @FXML private TableColumn colCostoU;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colCodProd;
    @FXML private TableColumn colNumDoc;
    @FXML private TextField txtCodigoDetalleC;
    @FXML private TextField txtCostoU;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox cmbCodigoProd;
    @FXML private ComboBox cmbCodigoNumDoc;*/
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    } 
    
    /*public void cargaDatos(){
        tblDetalleC.setItems(getDetalleCompra());
        colCodDetalleC.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
        colCostoU.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
        colCodProd.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoProducto"));
        colNumDoc.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("numeroDocumento"));
    }
    
    public void selecionarElementos(){
       txtCodigoDetalleC.setText(String.valueOf(((DetalleCompra)tblDetalleC.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
       txtCostoU.setText(String.valueOf(((DetalleCompra)tblDetalleC.getSelectionModel().getSelectedItem()).getCostoUnitario()));
       txtCantidad.setText(String.valueOf(((DetalleCompra)tblDetalleC.getSelectionModel().getSelectedItem()).getCantidad())); 
       cmbCodigoProd.getSelectionModel().select(buscarProductos(((DetalleCompra)tblDetalleC.getSelectionModel().getSelectedItem()).getCodigoProducto()));
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
    
    public Compras buscarCompras (int numeroDocumento ){
        Productos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCompras(?)}");
            procedimiento.setInt(1, numeroDocumento);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                java.sql.Date fecha = resultado.getDate("fechaDocumento");
                LocalDate fechaDoc = fecha.toLocalDate();
                resultado = new Compras(registro.getInt("numeroDocumento"),
                                            fechaDoc,
                                            registro.getString("descripcion"),
                                            registro.getString("totalDocumento")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }    
        return resultado;
    }
    
    public ObservableList<DetalleCompra> getDetalleCompra(){
        ArrayList<DetalleCompra> lista = new ArrayList<DetalleCompra>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new DetalleCompra (resultado.getInt("codigoDetalleCompra"),
                                        resultado.getDouble("costoUnitario"),
                                        resultado.getInt("cantidad"),
                                        resultado.getInt("codigoProducto"),
                                        resultado.getInt("numeroDocumento")            
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaDetalleC = FXCollections.observableArrayList(lista); 
    }
    
    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> listaPro = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaPro.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITproveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidoProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(listaPro);
    }*/

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clickRegresarC(ActionEvent event){
        if(event.getSource() == btnRegresarC){
            escenarioPrincipal.menuComprasV();
        }
    }
}
