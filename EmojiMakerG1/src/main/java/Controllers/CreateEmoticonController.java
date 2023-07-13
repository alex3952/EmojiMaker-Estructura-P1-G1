/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Enums.EmojiComponentType;
import Modelos.Emoticon;
import TDAS.CircularArray;
import TDAS.CircularList;
import TDAS.ListaCircularDoble;
import com.pooespol.emojimakerg1.App;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.fxml.FXML;

/**
 *
 * @author infrative
 */
public class CreateEmoticonController {
    final private int CANTIDAD_COMPONENTES_MOSTRADOS = 3;
    
    private App app;
    
    @FXML
    private VBox container;
    
    @FXML
    private HBox componentsHBox;
    
    @FXML
    private ImageView rostroImageView;
    
    @FXML
    private ImageView miradaImageView;
    
    @FXML
    private ImageView mouthImageView;
    
    private EmojiComponentType currentComponentType;
    private CircularList<String> componentes;
    private CircularArray<String> componentesMostrados;
    private Emoticon emoticon;
    
    public void initialize() {
        emoticon = new Emoticon();
    }
    
    public void onMostrarRostros() {
        currentComponentType = EmojiComponentType.FACE;
        
        componentes = app.getProfile().getComponentes().get(currentComponentType);
        cambiarComponentesMostrados();
        actualizarComponentsHBox();
    }
    
    public void onMostrarMiradas() {
        currentComponentType = EmojiComponentType.MIRADA;
        
        componentes = app.getProfile().getComponentes().get(currentComponentType);
        cambiarComponentesMostrados();
        actualizarComponentsHBox();
    }
    
    public void onMostrarMouths() {
        currentComponentType = EmojiComponentType.MOUTH;
        
        componentes = app.getProfile().getComponentes().get(currentComponentType);
        cambiarComponentesMostrados();
        actualizarComponentsHBox();
    }
    
    public void onMoverComponentesALaDerecha() {
        componentesMostrados.addLast(
                componentes.next(componentesMostrados.getLast())
        );
        
        actualizarComponentsHBox();
    }
    
    public void onMoverComponentesALaIzquierda() {
        componentesMostrados.addFirst(
                componentes.prev(componentesMostrados.getFirst())
        );
        
        actualizarComponentsHBox();
    }
    
    public void onGuardar() {
        
    }
    
    public void onCancelar() {
        app.switchToMenuPrincipal();
    }
    
    public void setApp(App app) {
        this.app = app;
    }
    
    private void cambiarComponentesMostrados() {
        CircularArray<String> componentesParaMostrar = new CircularArray(CANTIDAD_COMPONENTES_MOSTRADOS);
        
        for (int i = 0; i < CANTIDAD_COMPONENTES_MOSTRADOS; i++) {
            componentesParaMostrar.addLast(componentes.get(i));
        }
        
        componentesMostrados = componentesParaMostrar;
    }
    
    private void actualizarComponentsHBox() {
        componentsHBox.getChildren().clear();
        
        for (int i = 0; i < CANTIDAD_COMPONENTES_MOSTRADOS; i++) {
            ImageView imageView = new ImageView(new Image(componentesMostrados.get(i)));
            
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            
            imageView.setOnMouseClicked((e) -> {
                //updateEmoticon(i);
            });
            
            componentsHBox.getChildren().add(imageView);
        }
    }
    
    private void updateEmoticon(int indiceComponenteMostrado) {
        
    }
}

