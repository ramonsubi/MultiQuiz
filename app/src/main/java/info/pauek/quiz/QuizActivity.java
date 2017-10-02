package info.pauek.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };

    //Shift + F6 para cambiar nombres de variables
    private int correct_answer;
    private int actual_question;
    private String[] all_questions;
    private boolean[] check_answers;
    private int [] num_answer;
    private TextView text_question;
    private RadioGroup group;
    private Button btn_check, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        text_question = (TextView) findViewById(R.id.text_question);
        group = (RadioGroup) findViewById(R.id.answer_group);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_back = (Button) findViewById(R.id.btn_back);

        all_questions = getResources().getStringArray(R.array.all_questions);
        check_answers = new boolean[all_questions.length];
        num_answer = new int [all_questions.length];

        for (int i = 0; i < all_questions.length; i++){
            num_answer[i] = -1;
        }

        actual_question = 0;
        show_question();



        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_answer();

                if(actual_question < all_questions.length-1) {
                    actual_question++;
                    show_question();
                }
                else{
                    int correct=0, incorrect=0;
                    boolean b = true;

                    // for (boolean b : check_questions)  48:30
                    for (int i = 0; i < check_answers.length; i++) {
                        if(b == check_answers[i]) correct++;
                        else incorrect++;
                    }


                    //Mostrar en un solo String
                    String resultc = String.format("Respuestas correctas: %d", correct);
                    String resulti = String.format("Respuestas incorrectas: %d", incorrect);

                    Toast.makeText(QuizActivity.this, resultc, Toast.LENGTH_LONG).show();
                    Toast.makeText(QuizActivity.this, resulti, Toast.LENGTH_LONG).show();
                    finish(); //Opcional
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_answer();
                if(actual_question > 0){
                    actual_question--;
                    show_question();
                }
            }

        });
    }




    private void save_answer() {
        int id = group.getCheckedRadioButtonId();
        int answer = -1;

        for (int i = 0; i < ids_answers.length; i++) {
            if (ids_answers[i] == id) {
                answer = i;
            }
        }

        check_answers[actual_question] = (answer == correct_answer);
        num_answer[actual_question] = answer;
    }




    private void show_question() {

        String q = all_questions [actual_question];
        String [] parts = q.split(";");//Podem separar els Strings amb comes, punts...

        group.clearCheck();

        text_question.setText(parts[0]);

        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            String answer = parts[i + 1];

            if (answer.charAt(0) == '*') {
                correct_answer = i;
                answer = answer.substring(1);
            }

            rb.setText(answer);
            if (num_answer[actual_question] == i) {
                rb.setChecked(true);
            }

        }

        if(actual_question == 0){
            btn_back.setVisibility(View.GONE);
        }
        else{
            btn_back.setVisibility(View.VISIBLE);
        }

        if(actual_question == all_questions.length-1){
            btn_check.setText(R.string.finish);
        }
        else{
            btn_check.setText(R.string.next);
        }

    }
}
