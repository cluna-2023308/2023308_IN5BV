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
import org.cristianluna.bean.Proveedores;
import org.cristianluna.bean.TelefonoProveedor;
import org.cristianluna.db.Conexion;
import org.cristianluna.system.Principal;

public class MenuTelefonoProveedorController implements Initializable{
        /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 19/05/2024
    
    Fecha de modificacion: --/--/----
    */
    
    private Principal escenarioPrincipal;
    private ObservableList <TelefonoProveedor> listaTelefonoP;
    private ObservableList <Proveedores> listaProveedores;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    @FXML private Button btnRegresarP;
    @FXML private TableView tblTelefonoP;
    @FXML private TableColumn colCodTelefonoP;
    @FXML private TableColumn colNumeroP;
    @FXML private TableColumn colNumeroS;
    @FXML private TableColumn colObser;
    @FXML private TableColumn colCodProv;
    @FXML private TextField txtCodigoTelefonoP;
    @FXML private TextField txtNumeroPrin;
    @FXML private TextField txtNumeroSecu;
    @FXML private TextField txtObservacion;
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
        tblTelefonoP.setItems(getTelefonoProveedor());
        colCodTelefonoP.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoTelefonoProveedor"));
        colNumeroP.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroPrincipal"));
        colNumeroS.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroSecundario"));
        colObser.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("observaciones"));
        colCodProv.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoProveedor"));
    }
    
    public void selecionarElementos(){
       txtCodigoTelefonoP.setText(String.valueOf(((TelefonoProveedor)tblTelefonoP.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor()));
       txtNumeroPrin.setText(((TelefonoProveedor)tblTelefonoP.getSelectionModel().getSelectedItem()).getNumeroPrincipal());
       txtNumeroSecu.setText((((TelefonoProveedor)tblTelefonoP.getSelectionModel().getSelectedItem()).getNumeroSecundario()));
       txtObservacion.setText(((TelefonoProveedor)tblTelefonoP.getSelectionModel().getSelectedItem()).getObservaciones());
       cmbCodigoProv.getSelectionModel().select(buscarProveedores(((TelefonoProveedor)tblTelefonoP.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
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
    
    public ObservableList<TelefonoProveedor> getTelefonoProveedor(){
        ArrayList<TelefonoProveedor> lista = new ArrayList<TelefonoProveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTelefonoProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TelefonoProveedor (resultado.getInt("codigoTelefonoProveedor"),
                                        resultado.getString("numeroPrincipal"),
                                        resultado.getString("numeroSecundario"),
                                        resultado.getString("observaciones"),
                                        resultado.getInt("codigoProveedor")            
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaTelefonoP = FXCollections.observableArrayList(lista); 
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
         TelefonoProveedor registro = new TelefonoProveedor();
         registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodigoTelefonoP.getText()));
         registro.setCodigoProveedor(((Proveedores)cmbCodigoProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
         registro.setNumeroPrincipal(txtNumeroPrin.getText());
         registro.setNumeroSecundario(txtNumeroSecu.getText());
         registro.setObservaciones(txtObservacion.getText());
         try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall
            ("{call sp_agregarTelefonoProveedor(?, ?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
                procedimiento.setString(2, registro.getNumeroPrincipal());
                procedimiento.setString(3, registro.getNumeroSecundario());
                procedimiento.setString(4, registro.getObservaciones());
                procedimiento.setInt(5, registro.getCodigoProveedor());
                procedimiento.execute();
            listaTelefonoP.add(registro);
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
                if(tblTelefonoP.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrimar si elimina el registro", "Eliminar Email Proveedor", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTelefonoProveedor(?)}");
                            procedimiento.setInt(1, ((TelefonoProveedor)tblTelefonoP.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
                            procedimiento.execute();
                            listaTelefonoP.remove(tblTelefonoP.getSelectionModel().getSelectedItem());
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
        txtCodigoTelefonoP.setEditable(false);
        txtNumeroPrin.setEditable(false);
        txtNumeroSecu.setEditable(false);
        txtObservacion.setEditable(false);
        cmbCodigoProv.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoTelefonoP.setEditable(true);
        txtNumeroPrin.setEditable(true);
        txtNumeroSecu.setEditable(true);
        txtObservacion.setEditable(true);
        cmbCodigoProv.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoTelefonoP.clear();
        txtNumeroPrin.clear();
        txtNumeroSecu.clear();
        txtObservacion.clear();
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
