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
import org.cristianluna.bean.Productos;
import org.cristianluna.bean.Proveedores;
import org.cristianluna.bean.TipoProducto;
import org.cristianluna.db.Conexion;
import org.cristianluna.system.Principal;


public class MenuProductosController implements Initializable{
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 19/05/2024
    
    Fecha de modificacion: --/--/----
    */
    
    private Principal escenarioPrincipal;
    private ObservableList <Productos> listaProductos;
    private ObservableList <Proveedores> listaProveedores;
    private ObservableList <TipoProducto> listaTipoProducto;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    @FXML private Button btnRegresar;    
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodProd;
    @FXML private TableColumn colDescProd;
    @FXML private TableColumn colPrecioU;
    @FXML private TableColumn colPrecioD;
    @FXML private TableColumn colPrecioM;
    @FXML private TableColumn colImageP;
    @FXML private TableColumn colExistencia;
    @FXML private TableColumn colCodTipoProd;
    @FXML private TableColumn colCodProv;
    @FXML private TextField txtCodigoProd;
    @FXML private TextField txtDescPro;
    @FXML private TextField txtPrecioU;
    @FXML private TextField txtPrecioD;
    @FXML private TextField txtPrecioM;
    @FXML private TextField txtImageP;
    @FXML private TextField txtExistencia;
    @FXML private ComboBox cmbCodigoTipoP;
    @FXML private ComboBox cmbCodigoProv;
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
        cmbCodigoTipoP.setItems(getTipoProducto());
        cmbCodigoProv.setItems(getProveedores());
    }
    
    public void cargaDatos(){
    tblProductos.setItems(getProducto());
    colCodProd.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProducto"));
    colDescProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcion"));
    colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
    colPrecioD.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
    colPrecioM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
    colImageP.setCellValueFactory(new PropertyValueFactory<Productos, String>("imagenProducto"));
    colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
    colCodTipoProd.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoTipoProducto"));
    colCodProv.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
    }
    
    public void selecionarElementos(){
       txtCodigoProd.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
       txtDescPro.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getDescripcion());
       txtPrecioU.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
       txtPrecioD.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
       txtPrecioM.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
       txtImageP.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getImagenProducto());
       txtExistencia.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
       cmbCodigoTipoP.getSelectionModel().select(buscarTipoProducto(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
       cmbCodigoProv.getSelectionModel().select(buscarProveedores(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }
    
    public TipoProducto buscarTipoProducto (int codigoTipoProducto ){
        TipoProducto resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                                            registro.getString("descripcion")
             
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }    
        return resultado;
    }
    
    public Proveedores buscarProveedores (int codigoProveedor ){
        Proveedores resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                                            registro.getString("NITproveedor"),
                                            registro.getString("nombreProveedor"),
                                            registro.getString("apellidoProveedor"),
                                            registro.getString("direccionProveedor"),
                                            registro.getString("razonSocial"),
                                            registro.getString("contactoPrincipal"),
                                            registro.getString("paginaWeb")
             
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }    
        return resultado;
    }
    
    public ObservableList<Productos> getProducto(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Productos (resultado.getInt("codigoProducto"),
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableArrayList(lista); 
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
    }
    
     public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoProducto = FXCollections.observableList(lista);
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
         Productos registro = new Productos();
         registro.setCodigoProducto(Integer.parseInt(txtCodigoProd.getText()));
         registro.setCodigoProveedor(((Proveedores)cmbCodigoProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
         registro.setCodigoTipoProducto(((TipoProducto)cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
         registro.setDescripcion(txtDescPro.getText());
         registro.setPrecioDocena(Double.parseDouble(txtPrecioD.getText()));
         registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
         registro.setPrecioMayor(Double.parseDouble(txtPrecioM.getText()));
         registro.setImagenProducto(txtImageP.getText());
         registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
         try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall
            ("{call sp_agregarProductos(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getCodigoProducto());
                procedimiento.setString(2, registro.getDescripcion());
                procedimiento.setDouble(3, registro.getPrecioUnitario());
                procedimiento.setDouble(4, registro.getPrecioDocena());
                procedimiento.setDouble(5, registro.getPrecioMayor());
                procedimiento.setString(6, registro.getImagenProducto());
                procedimiento.setInt(7, registro.getExistencia());
                procedimiento.setInt(8, registro.getCodigoProveedor());
                procedimiento.setInt(9, registro.getCodigoTipoProducto());
                procedimiento.execute();
            listaProductos.add(registro);
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
                if(tblProductos.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrimar si elimina el registro", "Eliminar Producto", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProductos(?)}");
                            procedimiento.setInt(1, ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            procedimiento.execute();
                            listaProductos.remove(tblProductos.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else 
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Elemento");
        }
    }
    
    public void desactivarControles(){
        txtCodigoProd.setEditable(false);
        txtDescPro.setEditable(false);
        txtPrecioU.setEditable(false);
        txtPrecioD.setEditable(false);
        txtPrecioM.setEditable(false);
        txtImageP.setEditable(false);
        txtExistencia.setEditable(false);
        cmbCodigoTipoP.setDisable(true);
        cmbCodigoProv.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoProd.setEditable(true);
        txtDescPro.setEditable(true);
        txtPrecioU.setEditable(true);
        txtPrecioD.setEditable(true);
        txtPrecioM.setEditable(true);
        txtImageP.setEditable(true);
        txtExistencia.setEditable(true);
        cmbCodigoTipoP.setDisable(false);
        cmbCodigoProv.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoProd.clear();
        txtDescPro.clear();
        txtPrecioU.clear();
        txtPrecioD.clear();
        txtPrecioM.clear();
        txtImageP.clear();
        txtExistencia.clear();
        tblProductos.getSelectionModel().getSelectedItem();
        cmbCodigoTipoP.getSelectionModel().getSelectedItem();
        cmbCodigoProv.getSelectionModel().getSelectedItem();
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
