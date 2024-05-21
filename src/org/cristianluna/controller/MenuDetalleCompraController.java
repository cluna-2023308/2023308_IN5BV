package org.cristianluna.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.cristianluna.system.Principal;

public class MenuDetalleCompraController implements Initializable{
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 20/05/2024
    
    Fecha de modificacion: --/--/----
    */
    
    
    private Principal escenarioPrincipal;
    @FXML private Button btnRegresarC;
    
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
    public void clickRegresarC(ActionEvent event){
        if(event.getSource() == btnRegresarC){
            escenarioPrincipal.menuComprasV();
        }
    }
}
