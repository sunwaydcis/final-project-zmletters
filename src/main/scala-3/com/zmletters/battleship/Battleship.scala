package com.zmletters.battleship

import com.zmletters.battleship.controller.{GameplayController, PlayerShipPlacementController}
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.scene as sfxs
import javafx.scene as jfxs
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import scalafx.scene.layout.{GridPane, VBox}
import scalafx.scene.control.Button

object Battleship extends JFXApp3:

  var roots: Option[sfxs.layout.BorderPane] = None

  override def start(): Unit =
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    roots = Option(loader.getRoot[jfxs.layout.BorderPane])

    stage = new PrimaryStage():
      title = "Battleship"
      resizable = false
      scene = new Scene():
        root = roots.get

    showMenu()
    //showBoard()
    //generateBoard()

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