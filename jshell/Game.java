
package jshell;

class Game extends JShell{
    public void Run(){
        boolean bool = true;
        int points = 0;
        int needclue = 0;
        System.out.println("Welcome to number guess");
        while(bool){
            System.out.println("Generating number");
            int randnum = (int) (Math.random() * 9999);
            System.out.println("The number lies between " + (randnum-60) + " and " + (randnum+60));
            System.out.println("Your points: " + points);
            if(needclue == 7){
                input("-str", "Need Clue?");
                if(strinput.equals("y")){
                    System.out.println("number x2 = " + (randnum*2) + "number / 2 * 4 - 1 = " + (randnum/2*3));
                }
            }
            input("-num", "Guess the number -");
            if(intinput == randnum){
               System.out.println("WellDone You Guessed the correct number!!\nAnd you get one point for that!");
               points++;
               needclue--;
            }else{
                System.out.println("Try again Next Time\nThe number was " + randnum);
                needclue++;
            }
            input("-str", "Continue [Y/N]");
            if(strinput.equals("N")){
                bool = false;
            }else{
                continue;
            }
     
        }
    }

}
