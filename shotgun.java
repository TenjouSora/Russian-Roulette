import java.util.*;
public class shotgun {                     // ↓為了延遲所需
    public static void main(String[] args) throws InterruptedException{
        String[] item_name = {"生命水","啤酒","透視眼","轉換器","特殊彈藥"};
        Player user = new Player(6);
        Player pc = new Player(6);
        int[] gun = {};
        int[] gun2 = {0,0};
        int[] gun3 = {0,0,0};
        int[] gun4 = {0,0,0,0};
        int[] gun5 = {0,0,0,0,0};
        int[] gun6 = {0,0,0,0,0,0};
        int[] gun7 = {0,0,0,0,0,0,0};
        int[] gun8 = {0,0,0,0,0,0,0,0};
        boolean user_turn = true;//true表示是玩家回合，false表示PC回合
        boolean gameover = false;//true表示遊戲結束
        int bullet_index = 0;//彈匣裡子彈的指針，當一輪彈匣射完重製成0
        int real_bullet = 0;//彈匣裡的實彈數
        int fake_bullet = 0;//彈匣裡的空包彈數
        int damage = 1;//道具用的子彈傷害倍率
        int max_life = user.life;//設定初始生命值為最大生命值
        String winner = "PC";//贏家預設為PC
        Scanner input = new Scanner(System.in);//建立Scanner物件
        Random randomNumber = new Random();//建立RANDOM物件


        Tool.clean_monitor();
        System.out.println("歡迎來到俄羅斯輪盤，若打自己且該發子彈為空包彈，下一回合還會是自己的回合。");
        Tool.clean_monitor(5000);

        System.out.print("請輸入你的名字: ");
        String user_name = input.nextLine();
        Tool.clean_monitor();

        System.out.println("每位玩家初始生命值為" + user.life);
        Tool.clean_monitor(5000);
        while(gameover==false){//當遊戲尚未結束
            int bullet_num = randomNumber.nextInt(7) + 2;//隨機選定該回合子彈數 0~6，把結果+2
            switch(bullet_num){
                case 2:
                    gun = gun2;
                    break;
                case 3:
                    gun = gun3;
                    break;
                case 4:
                    gun = gun4;
                    break;
                case 5:
                    gun = gun5;
                    break;
                case 6:
                    gun = gun6;
                    break;
                case 7:
                    gun = gun7;
                    break;
                case 8:
                    gun = gun8;
                    break;
            }

            bullet_index = 0;
            real_bullet = Tool.shuffle(gun);
            fake_bullet = gun.length - real_bullet;
            user_turn = true;

            for(int i=0;i<user.get_item.length;i++){//將記錄玩家該回合獲得道具數量的陣列全部歸0
                user.get_item[i] = 0;
                pc.get_item[i] = 0;
            }
            for(int i=0;i<2;i++){//每輪PC跟玩家各隨機獲得2個道具
                int item_result = randomNumber.nextInt(5);//隨機選取道具陣列0~4，選取到的道具數+1
                user.item[item_result]++;
                user.get_item[item_result]++;
                item_result = randomNumber.nextInt(5);
                pc.item[item_result]++;
                pc.get_item[item_result]++;
            }

            System.out.print("此輪" + user_name + "所獲得道具:  ");

            for(int i=0;i<user.get_item.length;i++){//列出玩家該回合所獲得道具
                if(user.get_item[i]>0)
                    System.out.print(item_name[i] + "x" + user.get_item[i] + "   ");
            }

            Tool.clean_monitor(5000);
            System.out.print("此輪PC所獲得道具:  ");

            for(int i=0;i<pc.get_item.length;i++){//列出PC該回合所獲得道具
                if(pc.get_item[i]>0)
                    System.out.print(item_name[i] + "x" + pc.get_item[i]+ "   ");
            }


            Tool.clean_monitor(5000);
            System.out.println("此輪總彈數: "  + gun.length + "\n實彈數: " + real_bullet + " 空包彈數: " + fake_bullet);
            Tool.clean_monitor(5000);
            
            //當該輪子彈尚未擊發完畢且遊戲尚未結束
            while(bullet_index<=gun.length-1 && gameover==false){
                Tool.clean_monitor();
                System.out.print("PC血量: ");
                Tool.printlife(pc.life);
                System.out.print("         " + user_name + "的血量: ");
                Tool.printlife(user.life);
                System.out.println("             剩餘實彈數: " + real_bullet + "         剩餘空包彈數: " + fake_bullet);
                if(user_turn){//玩家回合
                    System.out.print("\n" + user_name + "所持道具: ");
                    for(int i=0;i<user.item.length;i++){
                        if(user.item[i]>0)
                            System.out.print(item_name[i] + "x" + user.item[i] + "   ");
                    }

                    System.out.print("          PC所持道具: ");
                    for(int i=0;i<pc.item.length;i++){
                        if(pc.item[i]>0)
                            System.out.print(item_name[i] + "x" + pc.item[i] + "   ");
                    }
                    System.out.println("\n\n\n\n\n" + user_name + "的回合! 請選擇你要射擊哪一方: 1)自己 2)對方 3)使用道具");
                    

                    int choice = input.nextInt();
                    Tool.clean_monitor();
                    if(choice<1 || choice>3){//若使用者輸入在1~3之範圍外
                        System.out.println("請輸入1~3的選項!");
                        Tool.clean_monitor(3000);
                        continue;
                    }
                    
                    if(choice!=3){//玩家選擇擊發子彈
                        if(gun[bullet_index]==1){
                            System.out.println("BANG!!!");
                            if(choice==1){//打自己
                                System.out.println(user_name + "被打中了! 生命值-" + damage);
                                user.life -= damage;
                            }
                            else{//打對方
                                System.out.println("PC被打中了! 生命值-" + damage);
                                pc.life -= damage;
                            }
                            real_bullet--;
                            user.use_bullet_real++;
                            user_turn = !user_turn;                    
                        }
    
                        else if(gun[bullet_index]==0){
                            System.out.println("是空包彈!");
                            if(choice==2)//打對方
                                user_turn = !user_turn;
                            fake_bullet--;
                            user.use_bullet_fake++;
                        }
                        bullet_index++;
                        user.use_bullet_total++;
                        damage = 1;//將子彈倍率調回1
                        Tool.clean_monitor(4000);
                    }
                    
                    else if(choice==3){//玩家選擇使用道具
                        int item_choice=0;
                        while(item_choice!=6){//當玩家道具選擇不為6，持續顯示道具說明選單
                            System.out.print("PC血量: ");
                            Tool.printlife(pc.life);
                            System.out.print("          " + user_name + "的血量: ");
                            Tool.printlife(user.life);
                            System.out.println("            剩餘實彈數: " + real_bullet + "         剩餘空包彈數: " + fake_bullet + "\n");
                            Tool.show_item(user.item);
                            item_choice = input.nextInt();

                            if(item_choice>=1 && item_choice<6){//若玩家選擇的道具並未持有，回到道具說明選單(選項範圍1~6)
                                if(user.item[item_choice-1]<=0){
                                    Tool.clean_monitor();
                                    System.out.println("現在並未持有" + item_name[item_choice-1] + "!");
                                    Tool.clean_monitor(3000);
                                    continue;
                                }
                            }

                            Tool.clean_monitor();
                            switch(item_choice){
                                case 1: //喝生命水
                                    if(user.life==max_life){
                                        System.out.println(user_name + "目前生命值已為最大! 無法服用生命水");
                                        break;
                                    }
                                    System.out.println(user_name + "喝了一瓶生命水! 生命值+1");
                                    user.life++;
                                    user.item[0]--;
                                    user.use_item[0]++;
                                    break;
                                case 2://喝啤酒
                                    System.out.println(user_name + "喝了一罐啤酒! 濾掉一顆當前的子彈");
                                    Tool.clean_monitor(3000);
                                    System.out.print("濾掉的子彈是...");
                                    if(gun[bullet_index]==0){
                                        System.out.println("空包彈!");
                                        fake_bullet--;
                                    }
                                    else{
                                        System.out.println("實彈!");
                                        real_bullet--;
                                    }
                                    bullet_index++;
                                    user.item[1]--;
                                    user.use_item[1]++;

                                    if(bullet_index==gun.length)//若使用玩啤酒將當前彈匣裡的子彈全部濾完，回到while判斷式獲得新彈匣
                                        item_choice = 6;
                                    break;
                                case 3://透視眼
                                    System.out.println(user_name + "獲得了透視眼! 窺視當前的一顆子彈");
                                    Tool.clean_monitor(3000);
                                    System.out.print("當前的這發子彈是...");
                                    if(gun[bullet_index]==0)
                                        System.out.println("空包彈!"); 
                                    else
                                        System.out.println("實彈!");
                                    user.item[2]--;
                                    user.use_item[2]++;
                                    break;
                                case 4://使用轉換器
                                    System.out.println(user_name + "使用了轉換器! 將當前一發子彈的屬性反轉");
                                    Tool.clean_monitor(3000);
                                    if(gun[bullet_index]==0){
                                        gun[bullet_index] = 1;
                                        real_bullet++;
                                        fake_bullet--;
                                    }
                                    else if(gun[bullet_index]==1){
                                        gun[bullet_index] = 0;
                                        real_bullet--;
                                        fake_bullet++;
                                    }
                                    System.out.println("...轉換成功!");
                                    user.item[3]--;
                                    user.use_item[3]++;
                                    break;
                                case 5://使用特殊彈藥
                                    System.out.println(user_name + "使用了特殊彈藥! 此發子彈將造成2倍傷害");
                                    Tool.clean_monitor(3000);
                                    damage = 2;
                                    System.out.println("...特殊彈藥裝填成功!");
                                    user.item[4]--;
                                    user.use_item[4]++;
                                    break;
                                case 6://回上一頁
                                    continue;
                                default://玩家輸入範圍在1~6之外
                                    System.out.println("請輸入選項1~6!");
                                    break;    
                            }
                            Tool.clean_monitor(3000);
                        }
                    }
                }

                else if(user_turn==false){//pc回合
                    System.out.print("\nPC的回合!  PC所持道具: ");
                    for(int i=0;i<user.item.length;i++){
                        if(pc.item[i]>0)
                            System.out.print(item_name[i] + "x" + pc.item[i] + "   ");
                    }
                    Tool.clean_monitor(5000);

                    //PC使用道具決策
                    while(pc.item[0]>0 && pc.life<max_life){//當PC持有生命水且生命值<最大生命值，持續飲用生命水
                        System.out.println("PC喝了一瓶生命水! 生命值+1");
                        pc.life++;
                        pc.item[0]--;
                        Tool.clean_monitor(3000);
                    }

                    while(pc.item[1]>0 && bullet_index<gun.length-1){//當PC持有啤酒且當前彈匣尚未射完，把啤酒全部用完
                        System.out.println("PC乾了一罐啤酒! 濾掉當前一發子彈");
                        Tool.clean_monitor(3000);
                        System.out.print("濾掉的子彈是...");
                        if(gun[bullet_index]==1){
                            System.out.println("實彈!");
                            real_bullet--;
                        }
                        else{
                            System.out.println("空包彈!");
                            fake_bullet--;
                        }
                        bullet_index++;
                        pc.item[1]--;
                        Tool.clean_monitor(3000);
                    }

                    if(bullet_index==gun.length)//若使用完啤酒已把當前彈匣濾完，回到while判斷式獲得下一輪彈匣
                        continue;

                    boolean pc_see_bullet = false;//預設PC並沒有窺視當前子彈屬性
                    boolean pc_know_bullet = false;//當PC窺視子彈後用這個變數紀錄子彈屬性，實彈為true空包彈為false
                    if(pc.item[2]>0){//當PC持有透視眼立即使用    
                        System.out.println("PC使用了透視眼! 窺視當前的一顆子彈");
                        if(gun[bullet_index]==1)
                            pc_know_bullet = true;
                        pc_see_bullet = true;
                        pc.item[2]--;
                        Tool.clean_monitor(3000);
                        System.out.println("...窺視完成!");
                        Tool.clean_monitor(3000);
                    }

                    if(pc.item[3]>0){//當PC持有轉換器
                        boolean use_inverter = false;//紀錄PC是否使用轉換器
                        if(pc_see_bullet){//如果PC此回合窺視了子彈
                            if(pc_know_bullet==false){//若當前是空包彈，使用轉換器
                                use_inverter = true;
                                pc_know_bullet = true;
                            }
                        }
                        else//若PC沒窺視子彈，隨機選擇是否使用
                            use_inverter = randomNumber.nextBoolean();
                        
                        if(use_inverter){//使用轉換器
                            System.out.println("PC使用了轉換器! 將當前一發子彈的屬性反轉");
                            if(gun[bullet_index]==1){//當前子彈為實彈，轉為空包彈
                                gun[bullet_index] = 0;
                                real_bullet--;
                                fake_bullet++;
                            }
                            else{//當前子彈為空包彈，轉為實彈
                                gun[bullet_index] = 1;
                                real_bullet++;
                                fake_bullet--;
                            }
                            pc.item[3]--;
                            Tool.clean_monitor(3000);
                            System.out.println("...轉換成功!");
                            Tool.clean_monitor(3000);
                        }
                    }

                    if(pc.item[4]>0){//當PC持有特殊彈藥
                        boolean use_bouns_damage = false;//紀錄是否使用特殊彈藥
                        if(pc_see_bullet){//如果該回合有窺視子彈
                            if(pc_know_bullet)//且最後子彈是實彈，使用特殊彈藥
                                use_bouns_damage = true;
                        }
                        else{//若該回合沒窺視子彈，隨機選擇是否使用特殊彈藥
                            if(real_bullet!=0)//若該彈匣內還有實彈就隨機選擇，若已沒有實彈則為預設(不使用)
                                use_bouns_damage = randomNumber.nextBoolean();
                        }    
                        if(use_bouns_damage){//PC選擇使用特殊彈藥
                            System.out.println("PC使用了特殊彈藥! 此發子彈將造成2倍傷害");
                            damage = 2;
                            Tool.clean_monitor(3000);
                            System.out.println("...裝填完成!");
                            pc.item[4]--;
                            Tool.clean_monitor(3000);
                        }
                    }
                    
                    boolean pc_choice = randomNumber.nextBoolean();//PC選擇採隨機布林值
                    
                    if(pc_see_bullet && pc_know_bullet)//若PC該回合有窺視子彈且最後子彈為實彈，強制打玩家
                        pc_choice = true;
                    else if(pc_see_bullet && pc_know_bullet==false)//若PC該回合有窺視子彈且最後子彈為空包彈，強制打自己
                        pc_choice = false;

                    if(damage==2)//若該回合PC使用了特殊彈藥，強制打玩家
                        pc_choice = true;

                    if(real_bullet==0)//若彈匣裡只剩空包彈，強制打自己
                        pc_choice = false;
                    else if(fake_bullet==0)//若彈匣裡只剩實彈，強制打玩家
                        pc_choice = true;

                    
                    if(pc_choice)//true就打玩家
                        System.out.println("PC選擇打你!");
                    else//false打自己
                        System.out.println("PC選擇打自己!");

                    Tool.clean_monitor(3000);

                    if(gun[bullet_index]==1){
                        System.out.println("BANG!!!");
                        real_bullet--;
                        if(pc_choice){//打玩家
                            System.out.println(user_name + "被打中了! 生命值-" + damage);
                            user.life -= damage;
                        }
                        else{//打自己
                            System.out.println("PC被打中了! 生命值-" + damage);
                            pc.life -= damage;
                        }
                        user_turn = !user_turn;
                    }

                    else if(gun[bullet_index]==0){
                        System.out.println("是空包彈!");
                        fake_bullet--;
                        if(pc_choice)//PC打玩家，換玩家的回合
                           user_turn = !user_turn;  
                    }
                    bullet_index++;
                    damage = 1;
                    Tool.clean_monitor(4000);
                }
                 if(user.life<=0 || pc.life<=0){//當某一方生命值<=0，結束遊戲
                    gameover = true;
                    if(user.life>0)//贏家預設為PC，若玩家最後生命值>0，贏家為玩家
                        winner = user_name;
                 }
                        
            }

        }
        System.out.println("Game Over! " + winner + "是贏家!");
        System.out.println(user_name + "在本次遊戲所使用的各項道具明細如下...\n\n");
        Tool.total_item_used(user.use_item, user.use_bullet_total, user.use_bullet_real, user.use_bullet_fake);//秀出最終玩家的擊發子彈數及使用道具數
        Tool.end_the_terminal();
        input.close();//關閉Scanner物件
    }
}

class Player {
    int life;
    int[] item = {0,0,0,0,0}; // 表示玩家現有道具數  0: 恢復生命1點  1: 濾掉1顆現在的子彈 2: 窺視1顆現在的子彈 3: 將現在的1顆子彈實轉虛,虛轉實 4: 攻擊力兩倍
    int[] get_item = {0,0,0,0,0}; //記錄玩家每輪獲得道具，每回合獲得道具前全部重製成0
    int[] use_item = {0,0,0,0,0};//紀錄玩家最後所使用各道具的個數
    int use_bullet_total = 0;//紀錄玩家最後總擊發的子彈數
    int use_bullet_real = 0;//紀錄玩家最後總擊發的實彈數
    int use_bullet_fake = 0;//紀錄玩家最後總擊發的空包彈數

    Player(int life){
        this.life = life;
    }

}

class Tool{  
    static void show_item(int[] item){
        System.out.print("\n1) 生命水: 喝下生命水,恢復玩家1點生命值     所持數: ");
        System.out.println(item[0]);
        System.out.print("2) 啤酒: 乾下一杯啤酒，濾掉當前這發子彈     所持數: ");
        System.out.println(item[1]);
        System.out.print("3) 透視眼: 暫時性獲得透視眼，能夠看到當前這發子彈的屬性     所持數: ");
        System.out.println(item[2]);
        System.out.print("4) 轉換器: 使用轉換器，將當前這發子彈的屬性顛倒     所持數: ");
        System.out.println(item[3]);
        System.out.print("5) 特殊彈藥: 將當前這發子彈裝上特殊彈藥，造成傷害變為兩倍     所持數: ");
        System.out.println(item[4]);
        System.out.println("6) 返回前一頁");
        System.out.println("\n請輸入你的選擇: ");
    }

     //隨機決定實虛彈，回傳值為實彈個數
     static int shuffle(int[] magazine){
        int count_bullet = 0;
        while(count_bullet==0 || count_bullet==magazine.length){//確保不會發生全都是空包彈或全都是實彈
            count_bullet = 0;
            for(int i=0;i<magazine.length;i++){
                Random randomNumber2 = new Random();
                boolean temp = randomNumber2.nextBoolean();
                if(temp){
                    magazine[i] = 1;//若是true設成1 (實彈)
                    count_bullet++;
                }
                else
                    magazine[i] = 0;
            }
        }
        return count_bullet;
    }

     //在螢幕上顯示玩家血量
     static void printlife(int life){
        for(int  i=0;i<life;i++)
            System.out.print("I ");
    }

    //睡...毫秒後清除畫面
    static void clean_monitor(int millisec) throws InterruptedException{
        Thread.sleep(millisec);
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
    }

    //單純清除畫面不延遲
    static void clean_monitor(){
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
    }

    //印出玩家該次遊戲所擊發的總子彈數及使用的各個道具數量
    static void total_item_used(int[] use_item, int total_bullet, int real_bullet, int fake_bullet) throws InterruptedException{
        Thread.sleep(1000);
        System.out.println("擊發出的總子彈數: " + total_bullet);
        Thread.sleep(500);
        System.out.println("擊發出的總實彈數: " + real_bullet);
        Thread.sleep(500);
        System.out.println("擊發出的總空包彈數: " + fake_bullet + "\n");

        Thread.sleep(1000);
        if(use_item[0]>0){
            Thread.sleep(500);
            System.out.println("服用的生命水次數: " + use_item[0]);
        }
        if(use_item[1]>0){
            Thread.sleep(500);
            System.out.println("乾下的啤酒罐數: " + use_item[1]);
        }
        if(use_item[2]>0){
            Thread.sleep(500);
            System.out.println("使用的透視眼次數: " + use_item[2]);
        }
        if(use_item[3]>0){
            Thread.sleep(500);
            System.out.println("使用的轉換器個數: " + use_item[3]);
        }
        if(use_item[4]>0){
            Thread.sleep(500);
            System.out.println("裝填的特殊彈藥次數: " + use_item[4]);
        }
    }

    //執行「按下enter結束程式」
    static void end_the_terminal(){
        System.out.println("\n\n請按下enter結束程式...");
        try{
            System.in.read();
        }
        catch(Exception e){
            System.out.println("ERROR!");
        }
    }
}
