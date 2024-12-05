/*
 * Name: Kaci Craycraft
 * South Hills Username: kcraycraft45
 */

import java.util.Random;
import java.util.Scanner;

public class Main
{
    public static Scanner input = new Scanner(System.in);
    public static Random rand = new Random();

    public static final int ONE_SECOND = 1000;
    public static final String[] CATEGORY_NAMES = {"one", "two", "three", "four", "five", "six", "yahtzee", "chance"};

    public static int[] roll;//global because roll doesn't need returned, but does need remembered
    public static String category;//must be global, used in multiple methods
    public static boolean[] categoriesUsed;

    public static void main(String[] args)
    {
        int finalScore;//initialize local variables

        System.out.println("Welcome!!");
        wait(ONE_SECOND);
        while(playAgain())//repeat as long as user wants to play again
        {
            enableAllCategories();//Starts off all selected categories at zero if user wants to play again
            finalScore = 0;//Resets score if user wants to play again
            for(int i=0;i<CATEGORY_NAMES.length;i++) //repeat for 6 rounds
            {
                rollTheDie();//roll is an array of 5 die
                int i2 = 0;
                while(i2<2 && !scoreThisRoll())//User only has two more rolls
                //if in this while loop, the roll has not been scored
                {//Roll 5 die again
                    rollTheDie();
                    i2++;
                }
                //out of the while loop, user is/must be scoring the roll
                categoryChoice();
                while(hasCatBeenChosen())//what category would they like to score? Has that category been chosen already?
                {
                    wait(ONE_SECOND/2);
                    System.out.println("\nThat category has been chosen! pick a different category.");
                    categoryChoice();
                }
                finalScore += score();//add up each round's score
                wait(ONE_SECOND);
                System.out.println("\nYour current point total is " + finalScore + "\n");
                if (i!=7)//just for some niceties, if all categories have not been picked, another round must occur.
                {
                    System.out.println("Okay, let's roll again...");
                    wait(ONE_SECOND);
                }
            }
            input.close();
            System.out.println("Your final score is " + finalScore + ".  Good job!!\n");
        }
        System.out.println("~Bye Bye~");
    }

    public static void wait(int ms)//delay
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static boolean playAgain() //returns if user wants to play again - done
    {
        String playAgainChoice;//Initialize local variables

        System.out.print("Would you like to play a game? ");
        playAgainChoice = input.nextLine();
        //input validation
        while(!playAgainChoice.equalsIgnoreCase("yes") && !playAgainChoice.equalsIgnoreCase("no"))
        {
            System.out.println("\nInvalid Input, please choose yes or no only... ");
            wait(ONE_SECOND);
            System.out.print("Would you like to play again? (yes or no): ");
            playAgainChoice = input.nextLine();
        }
        //return boolean stating whether user wants to play
        //the whole game again or
        //end the program
        return playAgainChoice.equalsIgnoreCase("yes");
    }

    public static void enableAllCategories()
    {//This is only called at the beginning of each game
        //It allows all categories to be marked as unused.
        categoriesUsed = new boolean[CATEGORY_NAMES.length];
        for(int i = 0; i<CATEGORY_NAMES.length; i++)
        {
            categoriesUsed[i] = false;
        }
    }

    public static void rollTheDie() //returns roll as an array
    {
        int HOW_MANY_DIE = 5; //roll is an array of 5 integers

        roll = new int[HOW_MANY_DIE];

        System.out.println("\nRoll those dice!");
        wait(ONE_SECOND);
        System.out.println("...");
        wait(ONE_SECOND);
        System.out.print("Your roll was as follows:\t");
        for(int i=0;i<HOW_MANY_DIE;i++)
        {
            roll[i] = rand.nextInt(1,7);
            wait(ONE_SECOND/3);
            System.out.print(roll[i] + "...");
        }
    }

    public static boolean scoreThisRoll()
    {
        System.out.print("\n\nWould you like to score this roll? ");
        String scoreThisRoleChoice = input.nextLine();
        //input validation
        while(!scoreThisRoleChoice.equalsIgnoreCase("yes") && !scoreThisRoleChoice.equalsIgnoreCase("no"))
        {
            System.out.println("\nInvalid Input, please choose yes or no only... ");
            wait(ONE_SECOND);
            System.out.print("Would you like to score this roll? (yes or no): ");
            scoreThisRoleChoice = input.nextLine();
        }
        //returns a boolean indicating whether user wants to score this roll or roll again
        return scoreThisRoleChoice.equalsIgnoreCase("yes");
    }

    public static void categoryChoice()
    {
        System.out.print("\nWhat category would you like to assign your roll to? Your choices are one, two, three, four, five, six, yahtzee, or chance: ");
        category = input.nextLine();
        while(!category.equalsIgnoreCase("one") && !category.equalsIgnoreCase("two") && !category.equalsIgnoreCase("three") && !category.equalsIgnoreCase("four") && !category.equalsIgnoreCase("five") && !category.equalsIgnoreCase("six") && !category.equalsIgnoreCase("yahtzee") && !category.equalsIgnoreCase("chance"))//input validation)
        {
            System.out.println("Invalid Input, please choose one, two, three, four, five, six, yahtzee, or chance.");
            wait(ONE_SECOND);
            System.out.print("What category would you like to assign your roll to? ");
            category = input.nextLine();//category needs to be global because it needs to be remembered for several methods
        }
    }

    public static boolean hasCatBeenChosen()
    {
        boolean local = false;
        for(int i = 0; i<CATEGORY_NAMES.length; i++)//the problem lies here
        {
            if(CATEGORY_NAMES[i].equalsIgnoreCase(category) && !categoriesUsed[i])
            {
                categoriesUsed[i] = true;
            }
            else if(CATEGORY_NAMES[i].equalsIgnoreCase(category))
            {
               local = true;
            }
        }
        return local;
    }

    public static int score()//Goes through each element of the array and compares it to the category choice
    {
        int totalScore = 0;//Initialize local variables
        int categoryInteger;
        int yahtzeeQuestionMark = 0;

        categoryInteger = switch(category.toLowerCase())
        {
            case "one" -> 1;
            case "two" -> 2;
            case "three" -> 3;
            case "four" -> 4;
            case "five" -> 5;
            case "six" -> 6;
            default -> 0;
        };
        for(int j : roll)
        {
            if(categoryInteger != 0)
            {
                if(j == categoryInteger)
                {
                    totalScore += j;//If the dice is the same as the category, it adds the dice value to the total score.
                }
            }
            else
            {
                if(category.equalsIgnoreCase("yahtzee"))
                {//yahtzee
                    if(j == roll[0])
                    {
                        yahtzeeQuestionMark++;
                    }
                    if(yahtzeeQuestionMark == 4 && j==4)
                    {
                        totalScore += 50;
                        System.out.println("\nSince you made yahtzee, you get 50 points!!");
                    }
                    else if(j==4)
                    {
                        System.out.println("\nYou did not have a yahtzee.  You will not earn any points for this turn.");
                    }
                }
                else
                {//chance
                    totalScore += j;
                }
            }
        }
        return totalScore;
    }
}
