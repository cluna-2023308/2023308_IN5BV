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
import org.cristianluna.bean.EmailProveedor;
import org.cristianluna.bean.Proveedores;
import org.cristianluna.db.Conexion;
import org.cristianluna.system.Principal;

public class MenuEmailProveedorController implements Initializable{
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 19/05/2024
    
    Fecha de modificacion: --/--/----
    */
    
    private Principal escenarioPrincipal;
    private ObservableList <EmailProveedor> listaEmailP;
    private ObservableList <Proveedores> listaProveedores;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    @FXML private Button btnRegresarP;
    @FXML private TableView tblEmailP;
    @FXML private TableColumn colCodEmailP;
    @FXML private TableColumn colEmailP;
    @FXML private TableColumn colDescripEmail;
    @FXML private TableColumn colCodProv;
    @FXML private TextField txtCodigoEmailP;
    @FXML private TextField txtEmailP;
    @FXML private TextField txtDescripcionE;
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
        cmbCodigoProv.setItems(getProveedores());
    }
    
    public void cargaDatos(){
        tblEmailP.setItems(getEmailProveedor());
        colCodEmailP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoEmailProveedor"));
        colEmailP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("emailProveedor"));
        colDescripEmail.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("descripcion"));
        colCodProv.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoProveedor"));
    }
    
    public void selecionarElementos(){
       txtCodigoEmailP.setText(String.valueOf(((EmailProveedor)tblEmailP.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
       txtEmailP.setText(((EmailProveedor)tblEmailP.getSelectionModel().getSelectedItem()).getEmailProveedor());
       txtDescripcionE.setText(((EmailProveedor)tblEmailP.getSelectionModel().getSelectedItem()).getDescripcion());
       cmbCodigoProv.getSelectionModel().select(buscarProveedores(((EmailProveedor)tblEmailP.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
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
    
    public ObservableList<EmailProveedor> getEmailProveedor(){
        ArrayList<EmailProveedor> lista = new ArrayList<EmailProveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmailProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new EmailProveedor (resultado.getInt("codigoEmailProveedor"),
                                        resultado.getString("emailProveedor"),
                                        resultado.getString("descripcion"),
                                        resultado.getInt("codigoProveedor")            
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaEmailP = FXCollections.observableArrayList(lista); 
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
         EmailProveedor registro = new EmailProveedor();
         registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEmailP.getText()));
         registro.setCodigoProveedor(((Proveedores)cmbCodigoProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
         registro.setEmailProveedor(txtEmailP.getText());
         registro.setDescripcion(txtDescripcionE.getText());
         try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall
            ("{call sp_agregarEmailProveedor(?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getCodigoEmailProveedor());
                procedimiento.setString(2, registro.getEmailProveedor());
                procedimiento.setString(3, registro.getDescripcion());
                procedimiento.setInt(4, registro.getCodigoProveedor());
                procedimiento.execute();
            listaEmailP.add(registro);
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
                if(tblEmailP.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrimar si elimina el registro", "Eliminar Email Proveedor", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmailProveedor(?)}");
                            procedimiento.setInt(1, ((EmailProveedor)tblEmailP.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
                            procedimiento.execute();
                            listaEmailP.remove(tblEmailP.getSelectionModel().getSelectedItem());
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
                if(tblEmailP.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    imgEditar.setImage(new Image("org/cristianluna/images/Guardar.png"));
                    imgReportes.setImage(new Image("org/cristianluna/images/Eliminar.png"));                    
                    txtCodigoEmailP.setEditable(false);
                    cmbCodigoProv.setDisable(true);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmailProveedor(?, ?, ?, ?)}");
            EmailProveedor registro = (EmailProveedor)tblEmailP.getSelectionModel().getSelectedItem();
            registro.setEmailProveedor(txtEmailP.getText());
            registro.setDescripcion(txtDescripcionE.getText());
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtCodigoEmailP.setEditable(false);
        txtEmailP.setEditable(false);
        txtDescripcionE.setEditable(false);
        cmbCodigoProv.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoEmailP.setEditable(true);
        txtEmailP.setEditable(true);
        txtDescripcionE.setEditable(true);
        cmbCodigoProv.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoEmailP.clear();
        txtEmailP.clear();
        txtDescripcionE.clear();
        cmbCodigoProv.getSelectionModel().getSelectedItem();
    }
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clickRegresarP(ActionEvent event){
        if(event.getSource() == btnRegresarP){
            escenarioPrincipal.menuProveedorV();
        }
    }
}
