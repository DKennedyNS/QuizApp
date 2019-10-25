package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Main2Activity extends AppCompatActivity {

    //Declare all the controls and data structures
    Button btnTopLeft, btnTopRight, btnBottomLeft, btnBottomRight;
    TextView txtTerm;

    ArrayList<String> definitionsList = new ArrayList<String>(); //All possible answers
    ArrayList<String> displayedList = new ArrayList<String>(); //Answers being displayed

    //With a maximum number of 10 entries, and a default initial capacity of 16,
    // the default load factor or 0.75 means a rehash will never occur.
    //https://developer.android.com/reference/java/util/HashMap
    Map<String, String> answerMap = new HashMap<>(); //Correct question/answer pairs

    int currentQuestion = 0;
    String correctAnswer;
    public static int score = 0; //Current quiz grade
    int questionWeight = 10; //how much each question is worth.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Point all the controls at their resources
        txtTerm = findViewById(R.id.txtTerm);
        btnTopLeft = findViewById(R.id.btnTopLeft);
        btnTopRight = findViewById(R.id.btnTopRight);
        btnBottomLeft = findViewById(R.id.btnBottomLeft);
        btnBottomRight = findViewById(R.id.btnBottomRight);

        //Set the text for the controls from the quiz file.
        buildArrays();
        loadQuestion(this.getCurrentFocus());

        //onClick for the buttons.
        // Check if the button had the right answer, then call for the next.
        btnTopLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnTopLeft.getText() == correctAnswer){
                    btnTopLeft.setBackgroundColor(Color.GREEN);
                    score =+ questionWeight;
                }else{
                    btnTopLeft.setBackgroundColor(Color.RED);
                }
                currentQuestion++;
                loadQuestion(v);
            }
        });
        btnTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnTopRight.getText() == correctAnswer){
                    btnTopRight.setBackgroundColor(Color.GREEN);
                    score =+ questionWeight;
                }else{
                    btnTopRight.setBackgroundColor(Color.RED);
                }
                currentQuestion++;
                loadQuestion(v);
            }
        });
        btnBottomLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnBottomLeft.getText() == correctAnswer){
                    btnBottomLeft.setBackgroundColor(Color.GREEN);
                    score =+ questionWeight;
                }else{
                    btnBottomLeft.setBackgroundColor(Color.RED);
                }
                currentQuestion++;
                loadQuestion(v);
            }
        });
        btnBottomRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnBottomRight.getText() == correctAnswer){
                    btnBottomRight.setBackgroundColor(Color.GREEN);
                    score =+ questionWeight;
                }else{
                    btnBottomRight.setBackgroundColor(Color.RED);
                }
                currentQuestion++;
                loadQuestion(v);
            }
        });
    }//End on Create

    private void buildArrays(){
        //Populate all the data structures from the quiz file.
        try{
            String line; //Temp string to be split
            String [] separatedLine; //String array to hold the separated values

            //File quizFile = getFileStreamPath("android.resource://com.example.quizapp/raw/quiz.txt");
            FileReader reader = new FileReader("android.resource://com.example.quizapp/raw/quiz.txt");
            BufferedReader buffer = new BufferedReader(reader);
            //While the reader is ready, keep reading lines
            while (reader.ready()){
                line = buffer.readLine();
                separatedLine = line.split(",");
                answerMap.put(separatedLine[1], separatedLine[0]); //uses definitions as keys
                definitionsList.add(separatedLine[1]);
                System.out.println(definitionsList);
            }
            //When it's done, close everything
            reader.close();
            buffer.close();

        }catch(FileNotFoundException e){
            Log.println(Log.ERROR,"Error:", "File not found");
        }catch(IOException e){
            Log.println(Log.ERROR,"Error:", "IO Exception ");
        }

    }

    private void loadQuestion(View v){
        //Load next question, if one remains. If not, finish quiz.
        if(currentQuestion < 10){
            //For each question, get the term and display it, then get the correct answer and add it
            //to the list of options for the buttons.
            txtTerm.setText(answerMap.remove(definitionsList.get(currentQuestion)));
            correctAnswer = definitionsList.get(currentQuestion);
            displayedList.add(correctAnswer);

            //Grab three other random answers and add those to the button options.
            int rand;
            while(displayedList.size()<4){
                //high end of next int is exclusive, so should never be out of bounds
                rand = new Random().nextInt(definitionsList.size());
                String def = definitionsList.get(rand);

                //Check to make sure nothing is duplicated
                if(!displayedList.contains(def)){
                    displayedList.add(def);
                }
            }
            //Shuffle the options, then add each to a button.
            Collections.shuffle(displayedList);
            btnTopLeft.setText(displayedList.remove(0));
            btnTopRight.setText(displayedList.remove(0));
            btnBottomLeft.setText(displayedList.remove(0));
            btnBottomRight.setText(displayedList.remove(0));
            displayedList.clear();
        }else{
            Intent intent = new Intent(v.getContext(), Main3Activity.class);
            startActivity(intent);
        }
    }
}//End Main2Activity
