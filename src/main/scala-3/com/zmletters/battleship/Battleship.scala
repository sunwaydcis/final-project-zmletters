package com.zmletters.battleship

import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.scene as sfxs
import javafx.scene as jfxs
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*

object Battleship extends JFXApp3:

  var roots: Option[sfxs.layout.BorderPane] = None

  override def start(): Unit =
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    roots = Option(loader.getRoot[jfxs.layout.BorderPane])

    stage = new PrimaryStage():
      title = "Battleship"
      scene = new Scene():
        root = roots.get

    showBoard()

  def showBoard(): Unit =
    val resource = getClass.getResource("view/BoardLayout.fxml")
    val loader = new FXMLLoader(resource)
    val boardRoot = loader.load[jfxs.layout.GridPane]

    roots match {
      case Some(root) =>
        // Adding the GridPane (board) to the center of the BorderPane
        root.center = boardRoot
      case None =>
        println("Root layout not loaded")
    }
