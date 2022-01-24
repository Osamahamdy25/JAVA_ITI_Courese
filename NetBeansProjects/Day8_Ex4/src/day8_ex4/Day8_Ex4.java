/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day8_ex4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Day8_Ex4 extends Application
{
	
	TextArea textArea = new TextArea();
	
	
	Label statusLabel = new Label("Not Started...");

	
	Button startButton = new Button("Start");
	Button exitButton = new Button("Exit");
	
	public static void main(String[] args) 
	{
		Application.launch(args);
	}
	
	@Override
	public void start(final Stage stage) 
	{
		
		startButton.setOnAction(new EventHandler <ActionEvent>() 
		{
            public void handle(ActionEvent event) 
            {
            	startTask();
            }
        });

		exitButton.setOnAction(new EventHandler <ActionEvent>() 
		{
            public void handle(ActionEvent event) 
            {
            	stage.close();
            }
        });
		
			
		HBox buttonBox = new HBox(5, startButton, exitButton);
		
		
		VBox root = new VBox(10, statusLabel, buttonBox, textArea);
	
		
		Scene scene = new Scene(root,400,300);
		
		stage.setScene(scene);
		
		stage.setTitle("A simple Concurrency Example");
		
		stage.show();				
	}
	
	public void startTask() 
	{
		
		Runnable task = new Runnable()
		{
			public void run()
			{
				runTask();
			}
		};

		Thread backgroundThread = new Thread(task);
		
		backgroundThread.setDaemon(true);
	
		backgroundThread.start();
	}	
	
	public void runTask() 
	{
		for(int i = 1; i <= 10; i++) 
		{
			try 
			{
				
				final String status = "Processing " + i + " of " + 10;
					
				Platform.runLater(new Runnable() 
				{
		            @Override 
		            public void run() 
		            {
		            	statusLabel.setText(status);
		            }
                            
		        });
		                          System.out.println(status);
				textArea.appendText(status+"\n");
		
				Thread.sleep(1000);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}	
}