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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
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
    private ObservableList <DetalleCompra> listaDetalleC;
    private ObservableList <Productos> listaProductos;
    private ObservableList <Compras> listaCompras;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    @FXML private Button btnRegresarC;
    @FXML private TableView tblDetalleC;
    @FXML private TableColumn colCodDetalleC;
    @FXML private TableColumn colCostoU;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colCodProd;
    @FXML private TableColumn colNumDoc;
    @FXML private TextField txtCodigoDetalleC;
    @FXML private TextField txtCostoU;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox cmbCodigoProd;
    @FXML private ComboBox cmbCodigoNumDoc;
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
        cmbCodigoProd.setItems(getProductos());
        cmbCodigoNumDoc.setItems(getCompras());
    } 
    
    public void cargaDatos(){
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
       cmbCodigoNumDoc.getSelectionModel().select(buscarCompras(((DetalleCompra)tblDetalleC.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
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
        Compras resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCompras(?)}");
            procedimiento.setInt(1, numeroDocumento);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                java.sql.Date fecha = registro.getDate("fechaDocumento");
                LocalDate fechaDoc = fecha.toLocalDate();
                resultado = new Compras(registro.getInt("numeroDocumento"),
                                        fechaDoc,
                                        registro.getString("descripcion"),
                                        registro.getDouble("totalDocumento")
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
    
    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> listaPro = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaPro.add(new Productos(resultado.getInt("codigoProducto"),
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
        return listaProductos = FXCollections.observableList(listaPro);
    }
    
    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> listaCom = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
               java.sql.Date fecha = resultado.getDate("fechaDocumento");
                LocalDate fechaDoc = fecha.toLocalDate();
                listaCom.add(new Compras(resultado.getInt("numeroDocumento"),
                                        fechaDoc,
                                        resultado.getString("descripcion"),
                                        resultado.getDouble("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableList(listaCom);
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
         DetalleCompra registro = new DetalleCompra();
         registro.setCodigoDetalleCompra(Integer.parseInt(txtCodigoDetalleC.getText()));
         registro.setCodigoProducto(((Productos)cmbCodigoProd.getSelectionModel().getSelectedItem()).getCodigoProveedor());
         registro.setNumeroDocumento(((Compras)cmbCodigoNumDoc.getSelectionModel().getSelectedItem()).getNumeroDocumento());
         registro.setCostoUnitario(Double.parseDouble(txtCostoU.getText()));
         registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
         try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall
            ("{call sp_agregarDetalleCompra(?, ?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getCodigoDetalleCompra());
                procedimiento.setDouble(2, registro.getCostoUnitario());
                procedimiento.setInt(3, registro.getCantidad());
                procedimiento.setInt(4, registro.getCodigoProducto());
                procedimiento.setInt(5, registro.getNumeroDocumento());
                procedimiento.execute();
            listaDetalleC.add(registro);
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
                if(tblDetalleC.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrimar si elimina el registro", "Eliminar Detalle Compra", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleCompra(?)}");
                            procedimiento.setInt(1, ((DetalleCompra)tblDetalleC.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            procedimiento.execute();
                            listaDetalleC.remove(tblDetalleC.getSelectionModel().getSelectedItem());
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
                if(tblDetalleC.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    imgEditar.setImage(new Image("org/cristianluna/images/Guardar.png"));
                    imgReportes.setImage(new Image("org/cristianluna/images/Eliminar.png"));                    
                    txtCodigoDetalleC.setEditable(false);
                    cmbCodigoProd.setDisable(true);
                    cmbCodigoNumDoc.setDisable(true);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDetalleCompra(?, ?, ?, ?, ?)}");
            DetalleCompra registro = (DetalleCompra)tblDetalleC.getSelectionModel().getSelectedItem();
            registro.setCostoUnitario(Double.parseDouble(txtCostoU.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtCodigoDetalleC.setEditable(false);
        txtCostoU.setEditable(false);
        txtCantidad.setEditable(false);
        cmbCodigoProd.setDisable(true);
        cmbCodigoNumDoc.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoDetalleC.setEditable(true);
        txtCostoU.setEditable(true);
        txtCantidad.setEditable(true);
        cmbCodigoProd.setDisable(false);
        cmbCodigoNumDoc.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoDetalleC.clear();
        txtCostoU.clear();
        txtCantidad.clear();
        tblDetalleC.getSelectionModel().getSelectedItem();
        cmbCodigoProd.getSelectionModel().getSelectedItem();
        cmbCodigoNumDoc.getSelectionModel().getSelectedItem();
    }

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
