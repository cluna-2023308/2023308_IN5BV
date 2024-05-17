package org.cristianluna.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.cristianluna.system.Principal;

public class MenuPrincipalController implements Initializable{
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 24/04/2024
    
    Fecha de modificacion: 26/04/2024
    */
    
    private Principal escenarioPrincipal;
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnMenuProgramador;
    @FXML MenuItem btnMenuProveedores;
    @FXML MenuItem btnMenuCargoEmpleado;
    @FXML MenuItem btnMenuTipoProducto;
    @FXML MenuItem btnMenuCompras;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clickClientes(ActionEvent event){
        if(event.getSource() == btnMenuClientes){
            escenarioPrincipal.menuClienteV();
        }
    }
    
    @FXML
    public void clickProgramador(ActionEvent event){
        if(event.getSource() == btnMenuProgramador){
            escenarioPrincipal.menuProgramadorV();
        }
    }
    
    @FXML
    public void clickProveedores(ActionEvent event){
        if(event.getSource() == btnMenuProveedores){
            escenarioPrincipal.menuProveedorV();
        }
    }
    
    @FXML
    public void clickCargoEmpleados(ActionEvent event){
        if(event.getSource() == btnMenuCargoEmpleado){
            escenarioPrincipal.menuCargoEmpleadoV();
        }
    }
    
    @FXML
    public void clickTipoProducto(ActionEvent event){
        if(event.getSource() == btnMenuTipoProducto){
            escenarioPrincipal.menuTipoProductoV();
        }
    }
    
    @FXML
    public void clickCompras(ActionEvent event){
        if(event.getSource() == btnMenuCompras){
            escenarioPrincipal.menuComprasV();
        }
    }
}