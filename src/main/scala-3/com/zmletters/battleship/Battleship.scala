package com.zmletters.battleship

import com.zmletters.battleship.controller.{GameplayController, PlayerShipPlacementController, SettingsController}
import com.zmletters.battleship.game.GameState
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.scene as sfxs
import javafx.scene as jfxs
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.Includes.*
import scalafx.scene.Scene
import scalafx.scene.layout.{GridPane, VBox}
import scalafx.scene.control.Button
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.stage.Modality.ApplicationModal
import scalafx.stage.{Screen, Stage}
import scalafx.scene.transform.Scale

object Battleship extends JFXApp3:

  var roots: Option[sfxs.layout.BorderPane] = None
  var mediaPlayer: MediaPlayer = null

  override def start(): Unit =
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    roots = Option(loader.getRoot[jfxs.layout.BorderPane])
    val backgroundMusicPlayer = playBackgroundMusic()

    stage = new PrimaryStage():
      title = "Battleship"
      resizable = false
      //fullScreen = true
      scene = new Scene():
        root = roots.get

    showMenu()
    // play music


  def generateBoard(): Unit =
    val playerGrid = new GridPane
    for (x <- 0 until 10; y <- 0 until 10) {
      val btn = new Button {
        text = "-"
        minHeight = 40
        minWidth = 40
      }
      playerGrid.add(btn, x, y)
    }
    val btn = playerGrid.getChildren
    this.roots.get.center = playerGrid


  def showMenu(): Unit =
    val resource = getClass.getResource("view/MenuLayout.fxml")
    val loader = new FXMLLoader(resource)
    val menuRoot = loader.load[jfxs.layout.AnchorPane]

    this.roots.get.center = menuRoot

  def showSettings(): Unit =
    val resource = getClass.getResource("view/SettingsLayout.fxml")
    val loader = new FXMLLoader(resource)
    val _root = loader.load[jfxs.layout.AnchorPane]
    val controller = loader.getController[SettingsController]

    // Pass the initialized MediaPlayer to the SettingsController
    controller.setMediaPlayer(mediaPlayer)

    this.roots.get.center = _root

  def showDifficultySelection(): Unit =
    val resource = getClass.getResource("view/DifficultySelectionLayout.fxml")
    val loader = new FXMLLoader(resource)
    val _root = loader.load[jfxs.layout.AnchorPane]

    this.roots.get.center = _root

  def showBoard(): Unit =
    val resource = getClass.getResource("view/BoardLayout.fxml")
    val loader = new FXMLLoader(resource)
    val boardRoot = loader.load[jfxs.layout.AnchorPane]

    this.roots.get.center = boardRoot

  def showPlayerShipPlacement(): Unit =
    val resource = getClass.getResource("view/PlayerShipPlacementLayout.fxml")
    val loader = new FXMLLoader(resource)
    val _root = loader.load[jfxs.layout.AnchorPane]
    val controller = loader.getController[PlayerShipPlacementController]
    //controller.initialize()
    this.roots.get.center = _root

  def showGameplay(): Unit =
    val resource = getClass.getResource("view/GameplayLayout.fxml")
    val loader = new FXMLLoader(resource)
    val _root = loader.load[jfxs.layout.AnchorPane]
    val controller = loader.getController[GameplayController]
    this.roots.get.center = _root

  def showGameOver(): Unit =
    val resource = getClass.getResource("view/GameOverLayout.fxml")
    val loader = new FXMLLoader(resource)
    val _root = loader.load[jfxs.layout.AnchorPane]
    this.roots.get.center = _root

  def playBackgroundMusic(): MediaPlayer =
    val musicPath = getClass.getResource("sound/backgroundMusic_1.mp3").toExternalForm
    val media = new Media(musicPath)
    val player = new MediaPlayer(media)

    player.setCycleCount(MediaPlayer.Indefinite) // Loop the music
    player.setVolume(GameState.globalVolume)
    player.play()

    mediaPlayer = player // Store the MediaPlayer instance in the Battleship object
    mediaPlayer
