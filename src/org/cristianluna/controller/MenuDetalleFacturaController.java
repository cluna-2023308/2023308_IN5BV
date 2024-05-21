package org.cristianluna.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.cristianluna.system.Principal;


public class MenuDetalleFacturaController implements Initializable{
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 20/05/2024
    
    Fecha de modificacion: --/--/----
    */

    private Principal escenarioPrincipal;
    @FXML private Button btnRegresarF;
    
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
    public void clickRegresarF(ActionEvent event){
        if(event.getSource() == btnRegresarF){
            escenarioPrincipal.menuFacturaV();
        }
    }    
}
