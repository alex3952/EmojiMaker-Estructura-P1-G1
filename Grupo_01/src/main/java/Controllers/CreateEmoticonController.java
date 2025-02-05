/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Enums.EmojiComponentType;
import Modelos.Emoticon;
import TDAS.CircularArray;
import TDAS.CircularList;
import com.app.App;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

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
    private Integer indexEmoticonToEditInProfile = null;
    
    public void initialize() {
        emoticon = new Emoticon();
        String rutaBaseProyecto = System.getProperty("user.dir");
        Image img = new Image("file:" + rutaBaseProyecto + "/src/main/resources/views/fondo_ventanas.jpg");

        double width = 600;
        double height = 413;

        BackgroundSize backgroundSize = new BackgroundSize(width, height, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);

        container.setBackground(background);
    }
    
    public void onDeshacer() {
        emoticon.restoreLastChange(app.getProfile().getComponentes());
        refreshEmoticonImageView();
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
        updateEmoticon(1);
    }
    
    public void onMoverComponentesALaIzquierda() {
        componentesMostrados.addFirst(
                componentes.prev(componentesMostrados.getFirst())
        );
        
        actualizarComponentsHBox();
        updateEmoticon(1);
    }
    
    public void onGuardar() {
        Stage stage = (Stage) container.getScene().getWindow();
        
        if (indexEmoticonToEditInProfile != null && !emoticon.isEmpty()) {
            app.getProfile().getEmoticones().update(indexEmoticonToEditInProfile, emoticon);
        }
        
        if (indexEmoticonToEditInProfile == null && !emoticon.isEmpty()) {
            app.getProfile().getEmoticones().add(emoticon);
        }
        
        app.getConsultarEmojisController().build();
        
        stage.close();
        app.loadCreateEmoticonFxml();
    }
    
    public void onCancelar() {
        Stage stage = (Stage) container.getScene().getWindow();
        
        stage.close();
        app.loadCreateEmoticonFxml();
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
            imageView.setPickOnBounds(true);
            
            final int indiceComponenteSeleccionado = i;
            imageView.setOnMouseClicked((e) -> {
                this.updateEmoticon(indiceComponenteSeleccionado);
            });
            
            componentsHBox.getChildren().add(imageView);
        }
    }
    
    private void updateEmoticon(int indiceComponente) {
        emoticon.updateComponent(currentComponentType, componentesMostrados.get(indiceComponente));
        
        refreshEmoticonImageView();
    }
    
    private void refreshEmoticonImageView() {
        String miradaUrl = emoticon.getComponent(EmojiComponentType.MIRADA);
        String rostroUrl = emoticon.getComponent(EmojiComponentType.FACE);
        String mouthUrl = emoticon.getComponent(EmojiComponentType.MOUTH);
        
        if (miradaUrl != null) {
            miradaImageView.setImage(new Image(miradaUrl));
        }
        
        if (rostroUrl != null) {
            rostroImageView.setImage(new Image(rostroUrl));
        }
        
        if (mouthUrl != null) {
            mouthImageView.setImage(new Image(mouthUrl));
        }
    }

    public void setIndexEmoticonToEditInProfile(int indexEmoticonToEditInProfile) {
        this.indexEmoticonToEditInProfile = indexEmoticonToEditInProfile;
        
        this.emoticon = app.getProfile().getEmoticones().get(indexEmoticonToEditInProfile);
        refreshEmoticonImageView();
    }
}

