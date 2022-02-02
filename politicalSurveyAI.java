package survey;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; 
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class politicalSurveyAI {
    public static void main(String[] args) throws IOException {
        
        //questions and answers for display
        
        String q1 ="If you had to choose, would you rather have…\n"
                  +"(1)A smaller government providing fewer services\n(2)A bigger government providing more services\n";
       
        String q2 ="Which of the following statements come closest to your view?\n"
                  +"(1)America’s openness to people from all over the world is essential to who we are as a nation\n(2)If America is too open to people from all over the world, we risk losing our identity as a nation\n";
       
        String q3 ="In general, would you say experts who study a subject for many years are…\n"
                  +"(1)Usually BETTER at making good policy decisions about that subject than other people\n(2)Usually WORSE at making good policy decisions about that subject than other people\n(3)NEITHER BETTER NOR WORSE at making good policy decisions about that subject than other people\n";
       
        String q4 = "Thinking about increased trade of goods and services between the U.S. and other nations in recent decades, would you say that the U.S. has...\n"
                  + "(1)Gained more than it has lost because increased trade has helped lower prices and increased the competitiveness of some U.S. businesses\n(2)Lost more than it has gained because increased trade has cost jobs in manufacturing and other industries and lowered wages for some U.S. workers\n";
       
        String q5 = "How much more, if anything, needs to be done to ensure equal rights for all Americans regardless of their racial or ethnic backgrounds?\n"
                  +"(1)A lot\n(2)A little\n(3)Nothing at all\n";
       
        String q6 = "Which of the following statements comes closest to your view?\n"
                  +"(1)Business corporations make too much profit\n(2)Most corporations make a fair and reasonable amount of profit\n";
       
        String q7 = "How much, if at all, would it bother you to regularly hear people speak a language other than English in public places in your community?\n"
                  +"(1)A lot\n(2)Some\n(3)Not much\n(4)Not at all\n";
       
        String q8 = "Which of these statements best describes your opinion about the United States?\n"
                  +"(1)The U.S. stands above all other countries in the world\n(2)The U.S. is one of the greatest countries in the world, along with some others\n(3)There are other countries that are better than the U.S.\n";
                 
        String q9 = "Which comes closer to your view of candidates for political office, even if neither is exactly right? I usually feel like...\n"
                  +"(1)There is at least one candidate who shares most of my views\n(2)None of the candidates represent my views well\n";
                 
        String q10 = "In general, how much do White people benefit from advantages in society that Black people do not have?\n"
                   +"(1)A great deal\n(2)A fair amount\n(3)Not too much\n(4)Not at all\n";
                   
        String q11 = "Do you think greater social acceptance of people who are transgender (people who identify as a gender that is different from the sex they were assigned at birth) is…\n"
                   +"(1)Very good for society\n(2)Somewhat good for society\n(3)Neither good nor bad for society\n(4)Somewhat bad for society\n(5)Very bad for society\n";
       
        String q12 = "Overall, would you say people who are convicted of crimes in this country serve…\n"
                   +"(1)Too much time in prison\n(2)Too little time in prison\n(3)About the right amount of time in prison\n";
       
        String q13 = "Which of the following statements comes closest to your view?\n"
                   +"(1)Religion should be kept separate from government policies\n(2)Government policies should support religious values and beliefs\n";
       
        String q14 = "In the future, do you think...\n"
                   +"(1)U.S. policies should try to keep it so America is the only military superpower\n(2)It would be acceptable if another country became as militarily powerful as the U.S.\n";
       
        String q15 = "Which party most closely represents your views?\n"
                   +"(a)Democratic Party Party\n(b)Republican Party\n(c)Libertarian Party\n(d)Green Party\n";

        String [] questions = {q1,q2,q3,q4,q5,q6,q7,q8,q9,q10,q11,q12,q13,q14,q15};  //contains all questions and answers for display
    	
        
        int[][] demData = readFileAns("democratnum", counter("democratnum"));	//array of democrat respondents
        int[][] repData = readFileAns("republicannum", counter("republicannum"));	//array of republican respondents
        int[][] liberData = readFileAns("libertariannum", counter("libertariannum"));	//array of libertarian respondents
        int[][] greenData = readFileAns("greennum", counter("greennum"));	//array of green respondents
        
        createFile(); //creates new txt files if they do not yet exist
        
        int [] userAnswers = takeTest(questions);	//contains answers given by respondent
        
        if (userAnswers[14] == 1) { //write files to democrat txt file if user answers democrat
        	 writeToFile(userAnswers, "democratnum");
        }
        
        else if (userAnswers[14] == 2) {	//write files to republican txt file if user answers republican
       	 	writeToFile(userAnswers, "republicannum");
        }
        
        else if (userAnswers[14] == 3) {	//write files to libertarian txt file if user answers libertarian
       	 	writeToFile(userAnswers, "libertariannum");
        }
        
        else {	//write files to green party txt file if user answers green party
       	 	writeToFile(userAnswers, "greennum");
        }

        
        float demProb = checkProb(demData, userAnswers);	//calculates probability of belonging to a party
        float repProb = checkProb(repData, userAnswers);
        float liberProb = checkProb(liberData, userAnswers); 
        float greenProb = checkProb(greenData, userAnswers);
        
        float demPercent = 100*(demProb/(demProb+repProb+liberProb+greenProb));	//turns probability of belonging to a party into a percentage
        float repPercent = 100*(repProb/(demProb+repProb+liberProb+greenProb));
        float liberPercent = 100*(liberProb/(demProb+repProb+liberProb+greenProb));  
        float greenPercent = 100*(greenProb/(demProb+repProb+liberProb+greenProb));
        
        if(demPercent>repPercent && demPercent>liberPercent && demPercent>greenPercent){	//checks if highest probability of being democrat
            System.out.println("You're most likely a democrat!\n");
        }
        
        else if(repPercent>demPercent && repPercent>liberPercent && repPercent>greenPercent){	//checks if highest probability of being republican
            System.out.println("You're most likely a republican!\n");
        }
        
        else if(liberPercent>demPercent && liberPercent>repPercent&& liberPercent>greenPercent){	//checks if highest probability of being libertarian
            System.out.println("You're most likely alibertarian!\n");
        }
        
        else {	//checks if highest probability of being green
            System.out.println("You're most likely a green!\n");
        }

        System.out.println("All probabilities of belonging to a party:\n");	//print probability results
        System.out.printf("%.2f", demPercent);
        System.out.println("% " + "Democrat\n");

        System.out.printf("%.2f", repPercent);
        System.out.println("% " + "Republican\n");
        
        System.out.printf("%.2f", liberPercent);
        System.out.println("% " + "Libertarian\n");
        
        System.out.printf("%.2f", greenPercent);
        System.out.println("% " + "Green\n");
        
    }
    public static int[] takeTest(String [] questions) {
        
        int [] currentAnswers = new int[15]; 
        
        Scanner keyboardInput = new Scanner(System.in); //obtains user answers
        
        for(int i = 0; i < questions.length; i++) {	//parse array and print every question element in array
            
            System.out.println(questions[i]);
            
            String answer = keyboardInput.nextLine();
            currentAnswers[i] = Integer.parseInt(answer); //assign user answers to array
            
        }
        
        return currentAnswers;
}
    public static float checkProb(int[][] previousAnswers, int[] currentAnswers) {
 
        float pro = 1;
        
        for(int y = 0; y < 14; y++) {                               //iterate through every answer
                int count = 0;
                float [] allProbs = new float[14];
                
            for(int i = 0; i < previousAnswers.length; i++) {        //iterate through every user
                if (previousAnswers[i][y] == currentAnswers[y]) {
                    count++;
                }  
            }
            
            float total_votes = previousAnswers.length;             //total number of answers per question per political party
            float answer_votes = count;                             //answer choice count per question per political party
            float prob = answer_votes/total_votes;                  //probability of political party choosing an answer
            
            allProbs[y] = prob;                                     //array with probabilities for each question
            
            pro = pro * allProbs[y];                                 //multiplies the probabilities of every question
        }
        return pro;
    }

  	public static void createFile() {
  		
  		String [] fileNames = {"democratnum", "republicannum", "libertariannum", "greennum"};
  		
  		for(int i = 0; i < fileNames.length; i++) { //iterate through every party name, create file if nonexistent
  		    try {  
  		      File myObj = new File("C:\\Users\\1\\Desktop\\PoliticalSurveyAI\\" + fileNames[i] + ".txt");  
  		      if (myObj.createNewFile()) {  
  		        System.out.println("File created: " + myObj.getName());  
  		        System.out.println("Absolute path: " + myObj.getAbsolutePath());  
  		      } else {  
  		        System.out.println(fileNames[i] + ".txt already exists.\n");  
  		      }  
  		    } catch (IOException e) {
  		      System.out.println("An error occurred.");
  		      e.printStackTrace();  
  		    }
  		}
  	}
  	
	public static String[] readFileQue(String fileName)  throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\1\\Desktop\\PoliticalSurveyAI\\" + fileName + ".txt"));
		String str;

		List<String> list = new ArrayList<String>();
		while((str = in.readLine()) != null){ 				//add every text line in file to list
		    list.add(str);
		}

		String[] stringArr = list.toArray(new String[0]);	//turn list into array
		System.out.println(Arrays.toString(stringArr));
		return stringArr;
	}

	public static int[][] readFileAns(String fileNames, int counter) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new FileReader("C:\\Users\\1\\Desktop\\PoliticalSurveyAI\\" + fileNames + ".txt"));
		
		int[][] intArray = new int [counter][15];
		while(sc.hasNextLine()){
			for(int i = 0; i < intArray.length; i++) {
				String [] line = sc.nextLine().trim().split("," + " ");
				for(int j = 0; j <line.length; j++) {
					intArray[i][j] = Integer.parseInt(line[j]);
				}
			}
		}
		return intArray;
		
	}
	
	private static int counter(String fileName) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\1\\Desktop\\PoliticalSurveyAI\\" + fileName + ".txt"), Charset.defaultCharset());
		
		int noOfLines = lines.size(); //count number of lines in txt file to assign array size
		
		return noOfLines;
	}

	public static void writeToFile(int[] currentAnswers, String fileName) {       //write user answers to txt file
	    try {    
	        String data = Arrays.toString(currentAnswers);    

	        File file =new File("C:\\Users\\1\\Desktop\\PoliticalSurveyAI\\" + fileName + ".txt");    

	        //true = append file    
	            FileWriter fileWritter = new FileWriter(file,true);        
	            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	            bufferWritter.newLine();
	            bufferWritter.write(data.replace("[", "").replace("]",""));
	            bufferWritter.close();
	            fileWritter.close();

	        System.out.println("Done");    

	    }catch(Exception e){    
	        e.printStackTrace();    
	    }    
	}  
}


