package pers.ervinse;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@Slf4j
@SpringBootTest
//限定抽卡次数测试
public class PoolTest {

    private static ArrayList<Integer> pool;
    private static final int total = 100000000;
    private static int count = 0;

    @Test
    //初始版本
    public void mainTest() {

        creatPool();

        for (int i = 0; i < total; i++) {
            int card1 = getCard();
            int card2 = getCard();
            int card3 = getCard();
            if (pool.get(card1) == 1 || pool.get(card2) == 1 || pool.get(card3) == 1) {
                count++;
            }
        }
        System.out.println("percent : " + (float)count/total + "%");

    }

    @Test
    public void singleCard(){
        //单次抽卡次数
        int getCardCount = 5;
        int[] card = new int[getCardCount];
        creatPool();

        //按顺序测试total次
        for (int i = 0; i < total; i++) {
            boolean isGetCard = false;
            //按顺序抽卡getCardCount次
            for (int k = 0;k < getCardCount;k++){
                card[k] = getCard();
                if (pool.get(card[k]) == 1){
                    isGetCard = true;
                }
            }

            //本次抽卡结束,统计是否抽到限定
            if (isGetCard){
                count++;
            }
        }
        System.out.println("percent : " + (float)count/total + "%");
    }

    public static void creatPool() {
        pool = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            pool.add(0);
        }

        for (int i = 0; i < 35; i++) {
            pool.add(1);
        }

        for (int i = 0; i < 35; i++) {
            pool.add(2);
        }
    }

    public int getCard() {
        return (int) (Math.random() * 100);
    }

}
