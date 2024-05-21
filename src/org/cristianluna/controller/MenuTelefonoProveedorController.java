package org.cristianluna.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.cristianluna.system.Principal;

public class MenuTelefonoProveedorController implements Initializable{
        /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 19/05/2024
    
    Fecha de modificacion: --/--/----
    */
    
    private Principal escenarioPrincipal;
    @FXML private Button btnRegresarP;
    
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
    public void clickRegresarP(ActionEvent event){
        if(event.getSource() == btnRegresarP){
            escenarioPrincipal.menuProveedorV();
        }
    }
}
