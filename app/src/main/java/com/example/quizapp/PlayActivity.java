package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class PlayActivity extends AppCompatActivity {
    ArrayList<Question> questionList = new ArrayList<>();
    ArrayList<Question> selectedQuestions = new ArrayList<>();

    TextView cpt_question, text_question;
    Button btn_choose1, btn_choose2, btn_choose3, btn_choose4, btn_next;

    int currentQuestion = 0;
    int scorePlayer = 0;
    boolean isclickBtn = false;
    String valueChoose = "";
    Button btn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        cpt_question = findViewById(R.id.cpt_question);
        text_question = findViewById(R.id.text_question);
        btn_choose1 = findViewById(R.id.btn_choose1);
        btn_choose2 = findViewById(R.id.btn_choose2);
        btn_choose3 = findViewById(R.id.btn_choose3);
        btn_choose4 = findViewById(R.id.btn_choose4);
        btn_next = findViewById(R.id.btn_next);

        findViewById(R.id.image_back).setOnClickListener(a -> finish());

        populateQuestions();
        Collections.shuffle(questionList);
        selectedQuestions.addAll(questionList.subList(0, 10)); // Chọn 10 câu hỏi ngẫu nhiên

        loadQuestion();

        btn_next.setOnClickListener(view -> {
            if (isclickBtn) {
                isclickBtn = false;

                if (!valueChoose.equals(selectedQuestions.get(currentQuestion).getCorrectAnswer())) {
                    Toast.makeText(PlayActivity.this, "Wrong", Toast.LENGTH_LONG).show();
                    btn_click.setBackgroundResource(R.drawable.background_btn_erreur);
                } else {
                    Toast.makeText(PlayActivity.this, "Correct", Toast.LENGTH_LONG).show();
                    btn_click.setBackgroundResource(R.drawable.background_btn_correct);
                    scorePlayer++;
                }

                new Handler().postDelayed(() -> {
                    if (currentQuestion < 9) {
                        currentQuestion++;
                        loadQuestion();
                        resetButtonColors();
                    } else {
                        Intent intent = new Intent(PlayActivity.this, ResulteActivity.class);
                        intent.putExtra("Result", scorePlayer);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            } else {
                Toast.makeText(PlayActivity.this, "Continue", Toast.LENGTH_LONG).show();
            }
        });
    }

    void populateQuestions() {
        questionList.add(new Question("1 + 1 = ?", "1", "2", "3", "4", "2"));
        questionList.add(new Question("Bài hát 'Enchanted' là của ai?", "Taylor Swift", "Ed Sheeran", "Adele", "Justin Bieber", "Taylor Swift"));
        questionList.add(new Question("Cristiano Ronaldo giành bao nhiêu Champions League tại Real Madrid?", "1", "2", "4", "5", "4"));
        questionList.add(new Question("Thủ đô của Pháp là gì?", "Berlin", "Madrid", "Paris", "Rome", "Paris"));
        questionList.add(new Question("3 x 3 = ?", "6", "9", "12", "15", "9"));
        questionList.add(new Question("Ai là tác giả của 'Harry Potter'?", "J.K. Rowling", "Stephen King", "Agatha Christie", "George R.R. Martin", "J.K. Rowling"));
        questionList.add(new Question("Nước nào có dân số đông nhất thế giới?", "Ấn Độ", "Mỹ", "Trung Quốc", "Nga", "Trung Quốc"));
        questionList.add(new Question("Màu nào có bước sóng dài nhất?", "Đỏ", "Xanh", "Tím", "Vàng", "Đỏ"));
        questionList.add(new Question("Quốc gia nào có diện tích lớn nhất thế giới?", "Mỹ", "Canada", "Nga", "Trung Quốc", "Nga"));
        questionList.add(new Question("Nguyên tố hóa học nào có ký hiệu 'O'?", "Oxy", "Vàng", "Bạc", "Đồng", "Oxy"));
        questionList.add(new Question("Ai sáng lập ra Microsoft?", "Steve Jobs", "Elon Musk", "Bill Gates", "Mark Zuckerberg", "Bill Gates"));
        questionList.add(new Question("Bộ phim hoạt hình nào có nhân vật chính là chuột Mickey?", "Tom & Jerry", "Mickey Mouse Clubhouse", "Looney Tunes", "SpongeBob", "Mickey Mouse Clubhouse"));
        questionList.add(new Question("Nguyên tố nào là kim loại nhẹ nhất?", "Sắt", "Nhôm", "Liti", "Magie", "Liti"));
        questionList.add(new Question("Năm nào thế chiến thứ hai kết thúc?", "1943", "1945", "1950", "1939", "1945"));
    }

    void loadQuestion() {
        cpt_question.setText((currentQuestion + 1) + "/10");
        text_question.setText(selectedQuestions.get(currentQuestion).getQuestion());

        btn_choose1.setText(selectedQuestions.get(currentQuestion).getOption1());
        btn_choose2.setText(selectedQuestions.get(currentQuestion).getOption2());
        btn_choose3.setText(selectedQuestions.get(currentQuestion).getOption3());
        btn_choose4.setText(selectedQuestions.get(currentQuestion).getOption4());
    }

    public void ClickChoose(View view) {
        btn_click = (Button) view;
        resetButtonColors();
        btn_click.setBackgroundResource(R.drawable.background_btn_choose_color);
        isclickBtn = true;
        valueChoose = btn_click.getText().toString();
    }

    void resetButtonColors() {
        btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
        btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
        btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
        btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);
    }
}

class Question {
    private final String question, option1, option2, option3, option4, correctAnswer;

    public Question(String question, String option1, String option2, String option3, String option4, String correctAnswer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() { return question; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public String getOption3() { return option3; }
    public String getOption4() { return option4; }
    public String getCorrectAnswer() { return correctAnswer; }
}
