package kr.co.tjoeun.finalproject_02_lottosimulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import kr.co.tjoeun.finalproject_02_lottosimulator.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    int[] winLottoNumArr = new int[6];
    int bounsNum = 0;
    ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.buyOneLottoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                당첨번호를 생성하고 => 텍스트뷰에 반영
                makeWinLottoNum();
            }
        });
    }

    @Override
    public void setValues() {

    }



    void makeWinLottoNum(){

//        6개의 숫자(배열) + 보너스번호 1개 int변수
//         => 이 함수에서만이 아니라, 다른곳에서도 쓸 예정
//         => 당첨 등수 확인때도 사용. => 멤버변수로 배열 / 변수 생성

//        당첨번호 + 보너스번호를 모두0 으로 초기화.
//        (이미 뽑은 번호가 있다면 모두 날려라)

        for ( int i = 0; i < winLottoNumArr.length; i++){
            winLottoNumArr[i] = 0;
        }
        bounsNum = 0;
//        로또 번호 6개 생성
//         1~45여야함 + 중복 허용 X

        for(int i = 0; i < winLottoNumArr.length; i++){
//            1~45의 숫자를 랜덤으로 뽑고
//            중복이 아니라면 => 당첨번호로 선정
//            중복이라면 다시 뽑자 => 중복이 아닐때까지 계속 뽑자.

            while(true){

//                0+1 <= (Math.random()*45)+1 < 1*45+1

//                1~45의 정수를 램덤으로 뽑아서 임시 저장.
//                이 숫자가 중복검사를 통과하면 사용, 아니면 다시.
                int randomNum = (int)(Math.random()*45+1);

//                중보검사? 당첨번호 전부와 randomNum을 비교.
//                하나라도 같은 탈락.

                boolean isDupOk = true; //중복검사 변수
                for(int winNum : winLottoNumArr){
                    if(winNum == randomNum){
                        isDupOk = false; //탈락!
                        break;
                    }
                }
                if(isDupOk){
                    winLottoNumArr[i] = randomNum;
                    Log.i("당첨번호",randomNum+"");
                    break; // 무한반복 탈출
                }

            }


        }

    }
}