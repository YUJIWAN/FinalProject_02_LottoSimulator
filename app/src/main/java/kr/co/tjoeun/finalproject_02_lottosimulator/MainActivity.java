package kr.co.tjoeun.finalproject_02_lottosimulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.co.tjoeun.finalproject_02_lottosimulator.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    long winMoneyAmount = 0;
    long useMoneyAmount = 0;
    int[] myLottoNumArr = {19, 9, 5, 11, 2, 26};
    List<TextView> winNumTxtList = new ArrayList<>();
    int[] winLottoNumArr = new int[6];
    int bounsNum = 0;
    ActivityMainBinding binding = null;

    Handler mHandler = new Handler();
    Runnable buyLottoRunnable = new Runnable() {
        @Override
        public void run() {
            if(useMoneyAmount < 200000000){
                makeWinLottoNum();
                checkLottoRank();
                buyLottoLoop();
            }else{
                Toast.makeText(mContext, "로또 구매를 종료합니다", Toast.LENGTH_SHORT).show();
            }

        }
    };
    void buyLottoLoop(){
        mHandler.post(buyLottoRunnable);

    }

    int firstRankCount = 0;
    int secondRankCount = 0;
    int thirdRankCount = 0;
    int fourthRankCount = 0;
    int fifthRankCount = 0;
    int noRankCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.buyAutoLottoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                사용금액의 총액이 1천만원이 될때까지 반복

//                while (useMoneyAmount < 300000000){
////                    당첨번호를 만들고 => 등수를 카운팅 반복
//                    makeWinLottoNum();
//                    checkLottoRank();
//                }
                  buyLottoLoop();

            }
        });
        binding.buyOneLottoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                당첨번호를 생성하고 => 텍스트뷰에 반영
                makeWinLottoNum();
//                몇등인지 판단하는 함수
                checkLottoRank();



            }
        });
    }

    @Override
    public void setValues() {

        winNumTxtList.add(binding.winLottoNumTxt01);
        winNumTxtList.add(binding.winLottoNumTxt02);
        winNumTxtList.add(binding.winLottoNumTxt03);
        winNumTxtList.add(binding.winLottoNumTxt04);
        winNumTxtList.add(binding.winLottoNumTxt05);
        winNumTxtList.add(binding.winLottoNumTxt06);

    }


    void makeWinLottoNum() {

//        6개의 숫자(배열) + 보너스번호 1개 int변수
//         => 이 함수에서만이 아니라, 다른곳에서도 쓸 예정
//         => 당첨 등수 확인때도 사용. => 멤버변수로 배열 / 변수 생성

//        당첨번호 + 보너스번호를 모두0 으로 초기화.
//        (이미 뽑은 번호가 있다면 모두 날려라)

        for (int i = 0; i < winLottoNumArr.length; i++) {
            winLottoNumArr[i] = 0;
        }
        bounsNum = 0;
//        로또 번호 6개 생성
//         1~45여야함 + 중복 허용 X

        for (int i = 0; i < winLottoNumArr.length; i++) {
//            1~45의 숫자를 랜덤으로 뽑고
//            중복이 아니라면 => 당첨번호로 선정
//            중복이라면 다시 뽑자 => 중복이 아닐때까지 계속 뽑자.

            while (true) {

//                0+1 <= (Math.random()*45)+1 < 1*45+1

//                1~45의 정수를 램덤으로 뽑아서 임시 저장.
//                이 숫자가 중복검사를 통과하면 사용, 아니면 다시.
                int randomNum = (int) (Math.random() * 45 + 1);

//                중보검사? 당첨번호 전부와 randomNum을 비교.
//                하나라도 같은 탈락.

                boolean isDupOk = true; //중복검사 변수
                for (int winNum : winLottoNumArr) {
                    if (winNum == randomNum) {
                        isDupOk = false; //탈락!
                        break;
                    }
                }
                if (isDupOk) {
                    winLottoNumArr[i] = randomNum;
                    Log.i("당첨번호", randomNum + "");
                    break; // 무한반복 탈출
                }
            }
        }

//        6개의 당첨번호를 작은 숫자부터 정렬.
        Arrays.sort(winLottoNumArr);

        for (int i = 0 ; i < winLottoNumArr.length; i++){

            winNumTxtList.get(i).setText(winLottoNumArr[i]+"");

        }

//        보너스번호도 생성하자 => 1~45 당첨번호 중복X

        while(true){
            int randomNum = (int)(Math.random()*45+1);

            boolean isDupOk = true;
            for (int winNum : winLottoNumArr){
                if (winNum == randomNum){
                    isDupOk =false;
                    break;
                }
            }
            if(isDupOk){
                bounsNum = randomNum;
                break;
            }
        }
//        보너스 넘버가 생성됨.
        binding.bonusLottoNumTxt.setText(bounsNum+"");
    }
    void checkLottoRank(){
//        돈을 천원 지불+등수 확인
        useMoneyAmount += 1000;

        binding.useMoneyTxt.setText(String.format("사용 금액 : %,d원",useMoneyAmount));

//        몇등인지?
//        내 번호를 하나 들고 => 당첨번호 6개 돌아볼것임.
//        얻어낼것? 몇개의 숫자를 맞췄는지.

//        맞춘갯수를 담아줄 변수
        int correctCount = 0;

        for(int myNum : myLottoNumArr){
            for(int winNum : winLottoNumArr){

                if(myNum == winNum){
                    correctCount++;
                }

            }
        }

//        correctCount의 값에 따라 등수를 판정.
        if(correctCount == 6){
//            1등
            winMoneyAmount += 1200000000;
            firstRankCount++;
        }else if(correctCount == 5){
//            2등인지 3등인지 재검사 => 보너스번호를 맞췄는지?
//            => 내 번호중에 보너스번호와 같은게 있나?
            boolean hasBonusNUm =false;


            for(int myNum : myLottoNumArr){
               if(myNum == bounsNum) {
                   hasBonusNUm = true;
                   break;
               }
            }
            if(hasBonusNUm) {
//          2등
               winMoneyAmount += 75000000;
               secondRankCount++;
            }else{
//                3등
                winMoneyAmount += 1500000;
                thirdRankCount++;

            }
        }else if(correctCount == 4){
//            4등
            winMoneyAmount += 50000;
            fourthRankCount++;
        }else if (correctCount == 3) {
//            5등
            useMoneyAmount -= 5000;
            fifthRankCount++;
        }else{
//            꽝
            noRankCount++;

        }
//      당첨금액 텍스트에도 반영
        binding.winMoneyTxt.setText(String.format("당첨금액 : %,d원",winMoneyAmount));

//        당첨횟수들도 텍스트뷰에 반영
        binding.firstRankCountTxt.setText(String.format("1등 : %,d회",firstRankCount));
        binding.secondRankCountTxt.setText(String.format("2등 : %,d회",secondRankCount));
        binding.thirdRankCountTxt.setText(String.format("3등 : %,d회",thirdRankCount));
        binding.fourtRankCountTxt.setText(String.format("4등 : %,d회",fourthRankCount));
        binding.fifthRankCountTxt.setText(String.format("5등 : %,d회",fifthRankCount));
        binding.noRankCountTxt.setText(String.format("낙첨 : %,d회",noRankCount));

    }
}
