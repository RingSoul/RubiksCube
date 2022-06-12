import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GamePlayManager {
    private Scanner inputReader;
    private File file;
    private Player player;
    private RubiksCube rubiksCube;
    private String data; // that would be saved into the file at the end of the program


    public GamePlayManager()
    {
        data = "";
        inputReader = new Scanner(System.in);
        initiatePlayer(); // player and file initiated
        constructRubiksCube(); // rubiksCube initiated
    }

    public void playGame()
    {
        if (rubiksCube != null)
        {
            System.out.println();
            System.out.println("是时候来解开一个魔方了~~~");
            boolean isWon = rubiksCube.checkIfWon();
            player.incrementGameCount();
            System.out.println("这是你第" + player.getGameCount() + "局游戏");
            data += "游戏#" + player.getGameCount() + "/尺寸:" + rubiksCube.getSize() + "/";
            Record record = new Record(rubiksCube.getSize());
            while (!isWon) {
                System.out.println("这是你当前的正面视图：");
                rubiksCube.displayFrontView();
                System.out.println("可用的指令：");
                System.out.println("1. 改变视角 --- 不计入行动数");
                System.out.println("2. 以行旋转 --- 计入行动数");
                System.out.println("3. 以列旋转 --- 计入行动数");
                System.out.println("4. 退出游戏\n");
                System.out.print("输入你的指令（1/2/3/4）：");
                String input = inputReader.nextLine();
                if (input.equals("4")) {
                    break;
                }
                processChoiceAndUpdateRecord(input, record);
                isWon = rubiksCube.checkIfWon();
            }
            endGameAndSaveData(record);
        }
        else
        {
            System.out.println("奇怪的事情发生了 =( 游戏结束");
        }
    }

    private void initiatePlayer()
    {
        System.out.print("输入你的用户名：");
        String username = inputReader.nextLine();
        try {
            // creating, if applicable
            file = new File(username + ".data");
            file.createNewFile();
            // reading
            int gameCount = 0;
            boolean alreadyExist = false;
            Scanner fileReader = new Scanner(file);
            String copy = ""; // in case user played before, this variable saves the line of data before the line is manipulated by String methods
            while (fileReader.hasNextLine())
            {
                String line = fileReader.nextLine();
                if (line.indexOf(username) != -1)
                {
                    copy = line;
                    alreadyExist = true;
                    int index = line.indexOf("|");
                    line = line.substring(index + 1); // the first part is always the userName
                    index = line.indexOf("|");
                    while (index != -1)
                    {
                        gameCount++;
                        line = line.substring(index + 1);
                        index = line.indexOf("|");
                    }
                    break;
                }
            }
            fileReader.close();
            if (alreadyExist)
            {
                System.out.println("欢迎回来，" + username + "。");
                data += copy;
                player = new Player(username, gameCount, data);
                presentOptionsForReturningPlayers();
            }
            else
            {
                System.out.println("你的用户名我第一次见，你好。");
                data += "用户名：" + username + "|";
                player = new Player(username, 0, data);
            }
        } catch (IOException e) {
            System.out.println("初始化玩家时出错。");
        }
    }

    private void constructRubiksCube()
    {
        if (player != null)
        {
            String sizeAsStr = "";
            int sizeAsInt = 0;
            while (!(isNumber(sizeAsStr) && isWithinRange(sizeAsInt, 2, 30))) {
                System.out.println("你的魔方尺寸将会是 \"尺寸 * 尺寸 * 尺寸\".");
                System.out.print("输入尺寸的数值（2~30）：");
                sizeAsStr = inputReader.nextLine();
                if (isNumber(sizeAsStr) == true) {
                    sizeAsInt = Integer.parseInt(sizeAsStr);
                    if (isWithinRange(sizeAsInt, 2, 30) == true)
                    {
                        rubiksCube = new RubiksCube(sizeAsInt, player);
                    }
                    else
                    {
                        System.out.println(TextFormat.RED + "错误的，确保尺寸的数值在2-30之间。" + TextFormat.RESET);
                        sizeAsInt = 0;
                    }
                } else {
                    System.out.println(TextFormat.RED + "错误的，确保你输入的是有效的数字。" + TextFormat.RESET);
                }
            }
        }
    }


    private void processChoiceAndUpdateRecord(String choice, Record record)
    {
        if (choice.equals("1"))
        {
            System.out.println("你想要魔方以哪个方向旋转，以改变当前的正面视图？：");
            System.out.println("1. 向左转 --> 正面视图变为当前的右边一面");
            System.out.println("2. 向右转 --> 正面视图变为当前的左边一面");
            System.out.println("3. 向上转 --> 正面视图变为当前的下边一面");
            System.out.println("4. 向下转 --> 正面视图变为当前的上边一面");
            System.out.print("输入你的指令（1/2/3/4）：");
            String command = inputReader.nextLine();
            switch (command) {
                case "1" -> rubiksCube.rotatePerspectiveLeftward();
                case "2" -> rubiksCube.rotatePerspectiveRightward();
                case "3" -> rubiksCube.rotatePerspectiveUpward();
                case "4" -> rubiksCube.rotatePerspectiveDownward();
                default -> System.out.println(TextFormat.RED + "你想什么呢，现在我们要重新来一遍了 =(" + TextFormat.RESET);
            }
        }
        else if (choice.equals("2"))
        {
            String rowNumAsStr = "";
            int rowNum = 0;
            while (!isWithinRange(rowNum, 1, rubiksCube.getSize())) {
                System.out.print("（输入一个介于1和" + rubiksCube.getSize() + "之间的数字）你想要旋转第#行：");
                rowNumAsStr = inputReader.nextLine(); // minus 1 later
                if (isNumber(rowNumAsStr))
                {
                    rowNum = Integer.parseInt(rowNumAsStr);
                    if (!isWithinRange(rowNum, 1, rubiksCube.getSize()))
                    {
                        System.out.println(TextFormat.RED + "不在指定范围内。" + TextFormat.RESET);
                    }
                }
                else
                {
                    System.out.println(TextFormat.RED + "输入数字好不好 =(" + TextFormat.RESET);
                }
            }
            System.out.print("(L)向左 还是 (R)向右？：");
            String direction = inputReader.nextLine();
            if (direction.toUpperCase().equals("L"))
            {
                rubiksCube.rowRotationLeft(rowNum - 1);
                record.incrementActionCount();
            }
            else if (direction.toUpperCase().equals("R"))
            {
                rubiksCube.rowRotationRight(rowNum - 1);
                record.incrementActionCount();
            }
            else
            {
                System.out.println(TextFormat.RED + "干得不错，但不有效，现在我们重来一次 =(" + TextFormat.RESET);
            }
        }
        else if (choice.equals("3"))
        {
            String colNumAsStr = "";
            int colNum = 0;
            while (!isWithinRange(colNum, 1, rubiksCube.getSize())) {
                System.out.print("（输入一个介于1和" + rubiksCube.getSize() + "之间的数字）你想要旋转第#列：");
                colNumAsStr = inputReader.nextLine(); // minus 1 later
                if (isNumber(colNumAsStr))
                {
                    colNum = Integer.parseInt(colNumAsStr);
                    if (!isWithinRange(colNum, 1, rubiksCube.getSize()))
                    {
                        System.out.println(TextFormat.RED + "不在指定范围内。" + TextFormat.RESET);
                    }
                }
                else
                {
                    System.out.println(TextFormat.RED + "输入数字好不好 =(" + TextFormat.RESET);
                }
            }
            System.out.print("(U)向上 还是 (D)向下？：");
            String direction = inputReader.nextLine();
            if (direction.toUpperCase().equals("U"))
            {
                rubiksCube.colRotationUp(colNum - 1);
                record.incrementActionCount();
            }
            else if (direction.toUpperCase().equals("D"))
            {
                rubiksCube.colRotationDown(colNum - 1);
                record.incrementActionCount();
            }
            else
            {
                System.out.println(TextFormat.RED + "干得不错，但不有效，现在我们重来一次 =(" + TextFormat.RESET);
            }
        }
        else
        {
            System.out.println(TextFormat.RED + "无效的指令。" + TextFormat.RESET);
        }
    }

    // end the game and save the data into the file
    private void endGameAndSaveData(Record record)
    {
        data += "行动数：" + record.getActionCount() + "/";
        data += "是否被解开：" + rubiksCube.checkIfWon() + "|";
        if (rubiksCube.checkIfWon())
        {
            System.out.println(TextFormat.GREEN + "可喜可贺，你解决了一个尺寸为" + rubiksCube.getSize() + "的魔方！" + TextFormat.RESET);
            record.changeToSolved();
        }
        else
        {
            System.out.println(TextFormat.BLUE + "不幸的，你没能解决这个魔方……" + TextFormat.RESET);
        }
        player.getRecords().add(record);
        // writing into file
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.close();
            System.out.println("本局游戏已被记录，常来玩啊~");
        } catch(IOException e) {
            System.out.println("保存数据时出错。");
        }
    }

    // if the user is a returning player, allow them to check their record
    private void presentOptionsForReturningPlayers()
    {
        if (player != null) {
            System.out.println("在你游玩之前，你想要做什么？");
            System.out.println("1. 查看我行动数最少的一局游戏！");
            System.out.println("2. 查看我试过的最大的魔方！");
            System.out.println("3. 查看所有的记录！");
            System.out.print("输入你的指令（1/2/3）：");
            String command = inputReader.nextLine();
            System.out.println();
            if (command.equals("1"))
            {
                player.reportBestRecordByActionCount();
            }
            else if (command.equals("2"))
            {
                player.reportLargestSize();
            }
            else if (command.equals("3"))
            {
                player.presentAllRecords();
            }
            else
            {
                System.out.println(TextFormat.GREEN + "看来你并不想查看以前的记录，那就让我们直接进入名为折磨的游戏中吧。" + TextFormat.RESET);
            }
            System.out.println();
        }
    }


    // input checker methods
    private boolean isNumber(String str)
    {
        if (str.equals(""))
        {
            return false;
        }
        String[] nums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < str.length(); i++) // for every "digit" in sizeAsStr
        {
            boolean isValidDigit = false;
            String current = str.substring(i, i + 1);
            for (String num : nums) // for every num in nums
            {
                if (current.equals(num))
                {
                    isValidDigit = true;
                    break;
                }
            }
            if (isValidDigit == false)
            {
                return false;
            }
        }
        return true;
    }

    private boolean isWithinRange(int input, int lowerBound, int upperBound)
    {
        if (input >= lowerBound && input <= upperBound)
        {
            return true;
        }
        return false;
    }
}
