package org.cristianluna.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.cristianluna.system.Principal;
import org.cristianluna.bean.Compras;
import org.cristianluna.db.Conexion;


public class MenuComprasController implements Initializable{
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 09/05/2024
    
    Fecha de modificacion: --/--/----
    */
    
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Compras> listaCompras;
    @FXML private Button btnRegresar;
    @FXML private TextField txtNumeroDocumento;
    @FXML private DatePicker datePickerFechaDocumento;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtTotalDocumento;
    @FXML private TableView tblCompras;
    @FXML private TableColumn colNumDoc;
    @FXML private TableColumn colFechaDoc;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colTotalDoc;
    @FXML private Button btnAgregarCompras;
    @FXML private Button btnEliminarCompras;
    @FXML private Button btnEditarCompras;
    @FXML private Button btnReportesCompras;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    @FXML MenuItem btnMenuDetalleCompra;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    } 

    public void cargarDatos(){
        tblCompras.setItems(getCompras());
        colNumDoc.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaDoc.setCellValueFactory(new PropertyValueFactory<Compras, LocalDate>("fechaDocumento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalDoc.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalDocumento"));
    }
    
    public void seleccionarElemento(){
        txtNumeroDocumento.setText(String.valueOf(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtDescripcion.setText((((Compras)tblCompras.getSelectionModel().getSelectedItem()).getDescripcion()));
        txtTotalDocumento.setText(String.valueOf((((Compras)tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento())));
    }
    
    public ObservableList<Compras> getCompras(){
        ArrayList<Compras> lista = new ArrayList<>(); 
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarCompras()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                java.sql.Date fecha = resultado.getDate("fechaDocumento");
                LocalDate fechaDoc = fecha.toLocalDate();
                lista.add(new Compras (resultado.getInt("numeroDocumento"),
                                        fechaDoc,
                                        resultado.getString("descripcion"),
                                        resultado.getDouble("totalDocumento")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableArrayList(lista);
    }
    
    public void agregar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregarCompras.setText("Guardar");
                btnEliminarCompras.setText("Cancelar");
                btnEditarCompras.setDisable(true);
                btnReportesCompras.setDisable(true);
                imgAgregar.setImage(new Image("org/cristianluna/images/Guardar.png"));
                imgEliminar.setImage(new Image("org/cristianluna/images/Eliminar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarCompras.setText("Agregar");
                btnEliminarCompras.setText("Eliminar");
                btnEditarCompras.setDisable(false);
                btnReportesCompras.setDisable(false);
                imgAgregar.setImage(new Image("org/cristianluna/images/IconAgregarCliente.png"));
                imgEliminar.setImage(new Image("org/cristianluna/images/IconEliminarCliente.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                datePickerFechaDocumento.setValue(null);
                break;
        }
    }
    
    public void guardar() {
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumento.getText()));
        LocalDate fechaDoc = datePickerFechaDocumento.getValue();
        Date fechaDocumento = Date.valueOf(fechaDoc);
        registro.setDescripcion(txtDescripcion.getText());
        registro.setTotalDocumento(Double.parseDouble(txtTotalDocumento.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call Sp_agregarCompras(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setDate(2, fechaDocumento);
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();
            listaCompras.add(registro);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void eliminar (){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarCompras.setText("Agregar");
                btnEliminarCompras.setText("Eliminar");
                btnEditarCompras.setDisable(false);
                btnReportesCompras.setDisable(false);
                imgAgregar.setImage(new Image("org/cristianluna/images/IconAgregarCliente.png"));
                imgEliminar.setImage(new Image("org/cristianluna/images/IconEliminarCliente.png"));               
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if(tblCompras.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrimar si elimina el registro", "Eliminar Compra", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCompras(?)}");
                            procedimiento.setInt(1, ((Compras)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
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
                if(tblCompras.getSelectionModel().getSelectedItem()!= null){
                    btnEditarCompras.setText("Actualizar");
                    btnReportesCompras.setText("Cancelar");
                    btnAgregarCompras.setDisable(true);
                    btnEliminarCompras.setDisable(true);
                    activarControles();
                    imgEditar.setImage(new Image("org/cristianluna/images/Guardar.png"));
                    imgReportes.setImage(new Image("org/cristianluna/images/Eliminar.png"));
                    txtNumeroDocumento.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun Elemento");
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarCompras.setText("Editar");
                btnReportesCompras.setText("Reportes");
                btnAgregarCompras.setDisable(false);
                btnEliminarCompras.setDisable(false);
                imgEditar.setImage(new Image("org/cristianluna/images/IconEditarCliente.png"));
                imgReportes.setImage(new Image("org/cristianluna/images/IconReportesCliente.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                limpiarControles();
                datePickerFechaDocumento.setValue(null);
                break;
        }
    }
    
    public void reporte() {
        switch (tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarCompras.setText("Editar");
                btnReportesCompras.setText("Reporte");
                btnAgregarCompras.setDisable(false);
                btnEliminarCompras.setDisable(false);
                imgEditar.setImage(new Image("org/cristianluna/images/IconEditarCliente.png"));
                imgReportes.setImage(new Image("org/cristianluna/images/IconReportesCliente.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    
    public void actualizar (){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCompras(?, ?, ?, ?)}");
            Compras registro = (Compras)tblCompras.getSelectionModel().getSelectedItem();
            LocalDate fechaDoc = datePickerFechaDocumento.getValue();
            Date fechaDocumento = Date.valueOf(fechaDoc);            
            registro.setDescripcion(txtDescripcion.getText());
            registro.setTotalDocumento(Double.parseDouble(txtTotalDocumento.getText()));
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setDate(2, fechaDocumento);
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtNumeroDocumento.setEditable(false);
        datePickerFechaDocumento.setEditable(false);
        txtDescripcion.setEditable(false);
        txtTotalDocumento.setEditable(false);
    }
    
    public void activarControles(){
        txtNumeroDocumento.setEditable(true);
        datePickerFechaDocumento.setEditable(true);
        txtDescripcion.setEditable(true);
        txtTotalDocumento.setEditable(true);
    }
    
    public void limpiarControles(){
        txtNumeroDocumento.clear();
        txtDescripcion.clear();
        txtTotalDocumento.clear();

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
    public void clickDetalleCompra(ActionEvent event){
        if(event.getSource() == btnMenuDetalleCompra){
            escenarioPrincipal.menuDetalleCompraV();
        }
    }
}