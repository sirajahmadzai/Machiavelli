# **Machiavelli is an Italian Variation of Rummy**

**To Build this Project**
1. `git clone https://github.com/sirajahmadzai/Machiavelli-Synchronous.git`
2. `cd Machiavelli-Synchronous`
3. `mvn compile`
4. `mvn package`
5. `mvn package -DstipTests`

**This will generate a jar file that is executable by double clicking it if your OS supports running jar files or by using the following command:**  

`java -jar <path_to_jar>`

**For Maven help, visit:**  
https://maven.apache.org/guides/getting-started/

**Importing and Running with Intellij**  
1. From the main menu, choose VCS | Checkout from Version Control | Git, or, if no project is currently opened, choose Checkout from Version Control | Git on the Welcome screen.

2. In the Clone Repository dialog, specify the URL of the remote repository you want to clone (you can click Test to make sure that connection to the remote can be established).

3. In the Directory field, specify the path where the folder for your local Git repository will be created into which the remote repository will be cloned.

4. Click Clone. If you want to create a IntelliJ IDEA project based on the sources you have cloned, click Yes in the confirmation dialog. Git root mapping will be automatically set to the project root directory.

5. Once the project is cloned, Intellij will detect the pom.xml file and ask if you would like to add the project as a maven project, click yes  

**Set the SDK if it's not already set**
1. Then,From the main menu, select File | Project Structure (Ctrl+Shift+Alt+S).
        
2. Under the Project Settings section, select Project.
        
3. From the Project SDK list, select 1.8 (java version "1.8.0_191").
        
4. If the necessary SDK is not defined in IntelliJ IDEA yet, click New and specify its home directory.

5. Also specify the out folder if it is not specified

**Set src as source**
1. Go to File | Project Structure, or press Ctrl+Shift+Alt+S.

2. Select Modules under the Project Settings section.

3. Select the necessary module, and then open the Sources tab in the right-hand part of the dialog.

4. Select src folder and mark it as Sources

**Run the Project**

1. Navigate to src->main->java->client->App.java

2. Right click App.java and select "Run 'App.main()'"

**Importing and Running with Eclipse**

1. Download Machiavelli-Synchronous as a zip file from the GitHub repo or github clone      

2. `File --> Import --> Maven --> Import existing maven projects --> Machiavelli-Synchronous --> pom.xml selected.

3. From there, you can do whatever. Select the main class (App) from src/main/java/client and run (as application).

**How to Play the Game**

1. Run App from src/main/java/client

2. Either start the game (if you are the first player and a game isn't already running) or join the game (if a server is already running and you are not the first player) 

3. Once all players have joined, wait for your turn


**How to Win**

1. Get rid of all the cards in your hand by playing valid sets to the table
