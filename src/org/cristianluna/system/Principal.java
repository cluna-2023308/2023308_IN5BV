package org.cristianluna.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.cristianluna.controller.MenuCargoEmpleadoController;
import org.cristianluna.controller.MenuClientesController;
import org.cristianluna.controller.MenuComprasController;
import org.cristianluna.controller.MenuDetalleCompraController;
import org.cristianluna.controller.MenuDetalleFacturaController;
import org.cristianluna.controller.MenuEmailProveedorController;
import org.cristianluna.controller.MenuEmpleadosController;
import org.cristianluna.controller.MenuFacturaController;
import org.cristianluna.controller.MenuPrincipalController;
import org.cristianluna.controller.MenuProductosController;
import org.cristianluna.controller.MenuProgramadorController;
import org.cristianluna.controller.MenuProveedoresController;
import org.cristianluna.controller.MenuTelefonoProveedorController;
import org.cristianluna.controller.MenuTipoProductoController;
        

public class Principal extends Application {
  /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 24/04/2024
    
    Fecha de modificacion: 26/04/2024
  */
    
  private Stage escenarioPrincipal;
  private Scene escena;
  private final String URLVIEW = "/org/cristianluna/view/";
   
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
       this.escenarioPrincipal = escenarioPrincipal;
       this.escenarioPrincipal.setTitle("Bodeguita");
       menuPrincipalV();
      //Parent root = FXMLLoader.load(getClass().getResource("/org/luishernandez/view/MenuPrincipalView.fxml"));
      // Scene escena = new Scene(root);
      // escenarioPrincipal.setScene(escena);
       escenarioPrincipal.show();
    }
    
    
    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception{
         Initializable resultado;
         FXMLLoader loader = new FXMLLoader();
         
         InputStream file = Principal.class.getResourceAsStream(URLVIEW + fxmlName);
         loader.setBuilderFactory(new JavaFXBuilderFactory());
         loader.setLocation(Principal.class.getResource(URLVIEW + fxmlName));
         
         escena = new Scene ((AnchorPane)loader.load(file), width, heigth);
         escenarioPrincipal.setScene(escena);
         escenarioPrincipal.sizeToScene();
         
         resultado = (Initializable)loader.getController();
         
        return resultado;
    }
   
    public void menuPrincipalV (){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 656, 404);
            menuPrincipalView.setEscenarioPrincipal(this);  
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuClienteV (){
        try{
            MenuClientesController menuClientesView = (MenuClientesController)cambiarEscena("MenuClientesView.fxml", 734, 400);
            menuClientesView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuProgramadorV (){
        try{
            MenuProgramadorController menuProgramadorView = (MenuProgramadorController)cambiarEscena("MenuProgramadorView.fxml", 763, 429);
            menuProgramadorView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuProveedorV (){
        try{
            MenuProveedoresController menuProveedoresView = (MenuProveedoresController)cambiarEscena("MenuProveedorView.fxml", 961, 544);
            menuProveedoresView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuCargoEmpleadoV (){
        try{
            MenuCargoEmpleadoController menuCargoEmpleadoView = (MenuCargoEmpleadoController)cambiarEscena("MenuCargoEmpleadoView.fxml", 854, 482);
            menuCargoEmpleadoView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuTipoProductoV (){
        try{
            MenuTipoProductoController menuTipoProductoView = (MenuTipoProductoController)cambiarEscena("MenuTipoProductoView.fxml", 863, 487);
            menuTipoProductoView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuComprasV (){
        try{
            MenuComprasController menuComprasView = (MenuComprasController)cambiarEscena("MenuComprasView.fxml", 989, 557);
            menuComprasView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuProductosV (){
        try{
            MenuProductosController menuProductosView = (MenuProductosController)cambiarEscena("MenuProductosView.fxml", 883, 498);
            menuProductosView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuEmailProveedorV() {
        try{
            MenuEmailProveedorController menuEmailProveedorView = (MenuEmailProveedorController)cambiarEscena("MenuEmailProveedorView.fxml", 772, 435);
            menuEmailProveedorView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuTelefonoProveedorV(){
        try{
            MenuTelefonoProveedorController menuTelefonoProveedorView = (MenuTelefonoProveedorController)cambiarEscena("MenuTelefonoProveedorView.fxml", 782, 442);
            menuTelefonoProveedorView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuDetalleCompraV(){
        try{
            MenuDetalleCompraController menuDetalleCompraView = (MenuDetalleCompraController)cambiarEscena("MenuDetalleCompraView.fxml", 797, 449);
            menuDetalleCompraView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuEmpleadosV(){
        try{
            MenuEmpleadosController menuEmpleadosView = (MenuEmpleadosController)cambiarEscena("MenuEmpleadosView.fxml", 789, 445);
            menuEmpleadosView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuFacturaV(){
        try{
            MenuFacturaController menuFacturaView = (MenuFacturaController)cambiarEscena("MenuFacturaView.fxml", 789, 471);
            menuFacturaView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuDetalleFacturaV(){
        try{
            MenuDetalleFacturaController menuDetalleFacturaView = (MenuDetalleFacturaController)cambiarEscena("MenuDetalleFacturaView.fxml", 751, 422);
            menuDetalleFacturaView.setEscenarioPrincipal(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
           
    public static void main(String[] args) {
        launch(args);  
    }
}