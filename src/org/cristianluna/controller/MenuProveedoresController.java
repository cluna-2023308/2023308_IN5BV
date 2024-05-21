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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.cristianluna.system.Principal;
import org.cristianluna.bean.Proveedores;
import org.cristianluna.db.Conexion;

public class MenuProveedoresController implements Initializable{
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 3/05/2024
    
    Fecha de modificacion: --/--/----
    */
    
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Proveedores> listaProveedores;
    @FXML private Button btnRegresar;
    @FXML private TextField txtNombreP;
    @FXML private TextField txtCodigoP;
    @FXML private TextField txtApellidoP;
    @FXML private TextField txtDireccionP;
    @FXML private TextField txtRazonSocialP;
    @FXML private TextField txtContactoP;
    @FXML private TextField txtPaginaWebP;
    @FXML private TextField txtNITP;
    @FXML private TableView tblProveedores;
    @FXML private TableColumn colCodigoP;
    @FXML private TableColumn colNITP;   
    @FXML private TableColumn colNombreP;
    @FXML private TableColumn colApellidoP;
    @FXML private TableColumn colDireccionP;
    @FXML private TableColumn colRazonSocialP;
    @FXML private TableColumn colContactoP;
    @FXML private TableColumn colPaginaWebP;
    @FXML private Button btnAgregarProveedor;
    @FXML private Button btnEditarProveedor;
    @FXML private Button btnEliminarProveedor;
    @FXML private Button btnReportesProveedor;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    @FXML MenuItem btnMenuEmailProveedor;
    @FXML MenuItem btnMenuTelefonoProveedor;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    } 
    
    public void cargarDatos(){
        tblProveedores.setItems(getProveedores());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNITP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITproveedor"));
        colNombreP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidoProveedor"));
        colDireccionP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonSocialP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial") );
        colContactoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPaginaWebP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }
    
    public void seleccionarElemento(){
        txtCodigoP.setText(String.valueOf(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNITP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNITproveedor()));
        txtNombreP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor()));
        txtApellidoP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getApellidoProveedor()));
        txtDireccionP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor()));
        txtRazonSocialP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial()));
        txtContactoP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal()));
        txtPaginaWebP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb()));
    }
    
    public ObservableList<Proveedores> getProveedores(){
        ArrayList<Proveedores> lista = new ArrayList<>(); 
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarProveedores()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedores (resultado.getInt("codigoProveedor"), 
                                        resultado.getString("NITproveedor"),
                                        resultado.getString("nombreProveedor"),
                                        resultado.getString("apellidoProveedor"),
                                        resultado.getString("direccionProveedor"),
                                        resultado.getString("razonSocial"),
                                        resultado.getString("contactoPrincipal"),
                                        resultado.getString("paginaWeb")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableArrayList(lista);
    }
    
    public void agregar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregarProveedor.setText("Guardar");
                btnEliminarProveedor.setText("Cancelar");
                btnEditarProveedor.setDisable(true);
                btnReportesProveedor.setDisable(true);
                imgAgregar.setImage(new Image("org/cristianluna/images/Guardar.png"));
                imgEliminar.setImage(new Image("org/cristianluna/images/Eliminar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarProveedor.setText("Agregar");
                btnEliminarProveedor.setText("Eliminar");
                btnEditarProveedor.setDisable(false);
                btnReportesProveedor.setDisable(false);
                imgAgregar.setImage(new Image("org/cristianluna/images/IconAgregarCliente.png"));
                imgEliminar.setImage(new Image("org/cristianluna/images/IconEliminarCliente.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    
        public void guardar() {
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
        registro.setNITproveedor(txtNITP.getText());
        registro.setNombreProveedor(txtNombreP.getText());
        registro.setApellidoProveedor(txtApellidoP.getText());
        registro.setDireccionProveedor(txtDireccionP.getText());
        registro.setRazonSocial(txtRazonSocialP.getText());
        registro.setContactoPrincipal(txtContactoP.getText());
        registro.setPaginaWeb(txtPaginaWebP.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedores(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
            listaProveedores.add(registro);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
        
    public void eliminar (){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarProveedor.setText("Agregar");
                btnEliminarProveedor.setText("Eliminar");
                btnEditarProveedor.setDisable(false);
                btnReportesProveedor.setDisable(false);
                imgAgregar.setImage(new Image("org/cristianluna/images/IconAgregarCliente.png"));
                imgEliminar.setImage(new Image("org/cristianluna/images/IconEliminarCliente.png"));                
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrimar si elimina el registro", "Eliminar Proveedor", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                            procedimiento.setInt(1, ((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
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
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblProveedores.getSelectionModel().getSelectedItem()!= null){
                    btnEditarProveedor.setText("Actualizar");
                    btnReportesProveedor.setText("Cancelar");
                    btnAgregarProveedor.setDisable(true);
                    btnEliminarProveedor.setDisable(true);
                    activarControles();
                    imgEditar.setImage(new Image("org/cristianluna/images/Guardar.png"));
                    imgReportes.setImage(new Image("org/cristianluna/images/Eliminar.png"));
                    txtCodigoP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun Elemento");
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarProveedor.setText("Editar");
                btnReportesProveedor.setText("Reportes");
                btnAgregarProveedor.setDisable(false);
                btnEliminarProveedor.setDisable(false);
                imgEditar.setImage(new Image("org/cristianluna/images/IconEditarCliente.png"));
                imgReportes.setImage(new Image("org/cristianluna/images/IconReportesCliente.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                limpiarControles();
                break;
        }
    }
    
    public void reporte() {
        switch (tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarProveedor.setText("Editar");
                btnReportesProveedor.setText("Reporte");
                btnAgregarProveedor.setDisable(false);
                btnEliminarProveedor.setDisable(false);
                imgEditar.setImage(new Image("org/cristianluna/images/IconEditarCliente.png"));
                imgReportes.setImage(new Image("org/cristianluna/images/IconReportesCliente.png"));                
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void actualizar (){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registro = (Proveedores)tblProveedores.getSelectionModel().getSelectedItem();
            registro.setNITproveedor(txtNITP.getText());
            registro.setNombreProveedor(txtNombreP.getText());
            registro.setApellidoProveedor(txtApellidoP.getText());
            registro.setDireccionProveedor(txtDireccionP.getText());
            registro.setRazonSocial(txtRazonSocialP.getText());
            registro.setContactoPrincipal(txtContactoP.getText());
            registro.setPaginaWeb(txtPaginaWebP.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
        
    public void desactivarControles(){
        txtCodigoP.setEditable(false);
        txtNITP.setEditable(false);
        txtNombreP.setEditable(false);
        txtApellidoP.setEditable(false);
        txtDireccionP.setEditable(false);
        txtRazonSocialP.setEditable(false);
        txtContactoP.setEditable(false);
        txtPaginaWebP.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoP.setEditable(true);
        txtNITP.setEditable(true);
        txtNombreP.setEditable(true);
        txtApellidoP.setEditable(true);
        txtDireccionP.setEditable(true);
        txtRazonSocialP.setEditable(true);
        txtContactoP.setEditable(true);
        txtPaginaWebP.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoP.clear();
        txtNITP.clear();
        txtNombreP.clear();
        txtApellidoP.clear();
        txtDireccionP.clear();
        txtRazonSocialP.clear();
        txtContactoP.clear();
        txtPaginaWebP.clear();
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
    public void clickEmailProveedor(ActionEvent event){
        if(event.getSource() == btnMenuEmailProveedor){
            escenarioPrincipal.menuEmailProveedorV();
        }
    }
    
    @FXML
    public void clickTelefonoProveedor(ActionEvent event){
        if(event.getSource() == btnMenuTelefonoProveedor){
            escenarioPrincipal.menuTelefonoProveedorV();
        }
    }
}
