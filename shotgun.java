import java.util.*;
public class shotgun {                     // ↓為了延遲所需
    public static void main(String[] args) throws InterruptedException{
        Player user = new Player(5);
        Player pc = new Player(5);
        int[] gun = {};
        int[] gun2 = {0,0};
        int[] gun3 = {0,0,0};
        int[] gun4 = {0,0,0,0};
        int[] gun5 = {0,0,0,0,0};
        int[] gun6 = {0,0,0,0,0,0};
        int[] gun7 = {0,0,0,0,0,0,0};
        int[] gun8 = {0,0,0,0,0,0,0,0};
        boolean user_turn = true;
        boolean gameover = false;
        int bullet_index = 0;
        int real_bullet = 0;
        int fake_bullet = 0;
        String winner = "PC";
        Scanner input = new Scanner(System.in);//建立輸入物件
        Random randomNumber = new Random();//建立RANDOM物件


        System.out.print("\033[H\033[2J"); //清除螢幕
        System.out.flush();
        System.out.println("歡迎來到俄羅斯輪盤，若用空包彈打中自己下一回合還會是該方的回合。");
        Thread.sleep(2000);//睡5秒
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("請輸入你的名字: ");
        String user_name = input.nextLine();
        System.out.print("\033[H\033[2J"); 
        System.out.flush();

        System.out.println("每位玩家初始生命值為" + user.life);
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
            real_bullet = Player.shuffle(gun);
            fake_bullet = gun.length - real_bullet;
            user_turn = true;

            
            //for(int i=0;i<gun.length;i++)
                //System.out.print(gun[i]);

            System.out.println("此輪總彈數: "  + gun.length + "\n實彈數: " + real_bullet + " 空包彈數: " + fake_bullet);
            Thread.sleep(5000);
            System.out.print("\033[H\033[2J"); 
            System.out.flush();
            
            //當該輪子彈尚未擊發完畢且遊戲尚未結束
            while(bullet_index<=gun.length-1 && gameover==false){
                System.out.print("PC血量: ");
                Player.printlife(pc.life);
                System.out.print("           " + user_name + "的血量: ");
                Player.printlife(user.life);
                System.out.println("                剩餘實彈數: " + real_bullet + "         剩餘空包彈數: " + fake_bullet);
                if(user_turn){//玩家回合
                    System.out.println("\n" + user_name + "的回合! 請選擇你要射擊哪一方: 1)自己 2)對方");
                    int choice = input.nextInt();
                    
                    System.out.print("\033[H\033[2J"); 
                    System.out.flush();

                    if(gun[bullet_index]==1){
                        System.out.println("BANG!!!");
                        real_bullet--;
                        if(choice==1){//打自己
                            System.out.println(user_name + "被打中了! 生命值-1");
                            user.life--;
                        }
                        else{//打對方
                            System.out.println("PC被打中了! 生命值-1");
                            pc.life--;
                        }
                        user_turn = !user_turn;                    
                    }

                    else if(gun[bullet_index]==0){
                        System.out.println("是空包彈!");
                        if(choice==2)//打對方
                            user_turn = !user_turn;
                        fake_bullet--;
                    }
                    bullet_index++;

                    Thread.sleep(4000);
                    System.out.print("\033[H\033[2J"); 
                    System.out.flush();
                }

                else if(user_turn==false){//pc回合
                    System.out.println("PC的回合!");
                    Thread.sleep(3000);
                    System.out.print("\033[H\033[2J"); 
                    System.out.flush();
                    
                    boolean pc_choice = randomNumber.nextBoolean();//PC選擇採隨機布林值
                    
                    if(pc_choice)//true就打玩家
                        System.out.println("PC選擇打你!");
                    else//false打自己
                        System.out.println("PC選擇打自己!");

                    Thread.sleep(3000);
                    System.out.print("\033[H\033[2J"); 
                    System.out.flush();

                    if(gun[bullet_index]==1){
                        System.out.println("BANG!!!");
                        real_bullet--;
                        if(pc_choice){//打玩家
                            System.out.println(user_name + "被打中了! 生命值-1");
                            user.life--;
                        }
                        else{//打自己
                            System.out.println("PC被打中了! 生命值-1");
                            pc.life--;
                        }
                        user_turn = !user_turn;
                    }

                    else if(gun[bullet_index]==0){
                        System.out.println("是空包彈!");
                        fake_bullet--;
                        if(pc_choice)//打玩家
                           user_turn = !user_turn;  
                    }
                    bullet_index++;
        
                    Thread.sleep(4000);
                    System.out.print("\033[H\033[2J"); 
                    System.out.flush();
                }
                 if(user.life<=0 || pc.life<=0){//當某一方生命值<=0，結束遊戲
                    gameover = true;
                    if(user.life>0)//贏家預設為PC，若玩家最後生命值>0，贏家為玩家
                        winner = user_name;
                 }
                        
            }

        }
        System.out.println("Gamve Over! " + winner + "是贏家!");
        input.close();
    }
}

class Player {
    int life;
    Player(int life){
        this.life = life;
    }

    //隨機決定實虛彈，回傳值為實彈個數
    static int shuffle(int[] magazine){
        int count_bullet = 0;
        while(count_bullet==0){//確保不會發生全都是虛彈
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

    //在螢幕上呈現玩家血量
    static void printlife(int life){
        for(int  i=0;i<life;i++)
            System.out.print("I ");
    }
}