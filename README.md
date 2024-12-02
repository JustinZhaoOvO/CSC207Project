## CSC207 Final Project : Chess Game Program

### Contributors:

- Boquan Zhao

- Yihan Wang

- Ruike Hou

- Katelynn Ruoxi Bai



[Github Repo Link](https://github.com/JustinZhaoOvO/CSC207Project)



## Table Of Contents

1. <a  href  ="#Project's Purpose"  title="Project's Purpose">Project's Purpose</a>
2. <a  href  ="#Features"  title="Features">Features</a>
3. <a  href  ="#Installation Instructions"  title="Installation Instructions">Installation Instructions</a>
4. <a  href  ="#Usage Guide"  title="Usage Guide">Usage Guide</a>
5. <a  href  ="#License"  title="License">License</a>
6. <a  href  ="#Feedback"  title="Feedback">Feedback</a>
7. <a  href  ="#make a contribution"  title="make a contribution">Make A Contribution</a>

---
<div  id="Project's Purpose">

### Project's Purpose

This project is a chess game program that simulates a chess match on a computer. It provides a visual chess game based on Java Swing that implements the standard rules of chess. The project was created to provide a convenient way to play chess, offering features like highlighting effects for mouse interaction events, countdown timer, customizable user profiles, and moves recording.
</div>

---
<div id="Features">

### Features
-   **Intuitive User Interface:** A user-friendly interface with clear visual cues and intuitive controls.
- -   **Following Standard Chess Rules:** The game strictly adheres to the official rules of chess.
-   **User Authentication:** A sign-up and login system.

<img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/Signup.png?raw=true?" alt="image" width="290"/>
<img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/Login.png?raw=true?" alt="image" width="350"/>

-   **Highlighting Effects:** Dynamic highlighting of potential moves and selected pieces to guide gameplay.
    <img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/HightLight.png?raw=true?" alt="image" width="300"/>
-   **Countdown Timer:** A built-in timer to the game.
- <img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/Timer.png?raw=true?" alt="image" width="300"/>
-   **User Customization:** The ability to create and customize user profiles with unique usernames and avatars.
-   **Move Recording:** A feature to record and review past moves.
</div>

---

<div id="Installation Instructions">

### Installation Instructions

#### To run the program:

- **Way 1**:
  Download and unzip the folder, use a java IDE(Intellij) to open the folder as a Maven Project with `JDK 21.0.5`. Make sure the following dependencies are in the pom.xml file
```  
  <dependency>
     <groupId>io.github.tors42</groupId>
     <artifactId>chariot</artifactId>
     <version>0.1.10</version>
  </dependency>
  <dependency>  
     <groupId>junit</groupId>  
	 <artifactId>junit</artifactId>  
	 <version>4.13.1</version>  
	 <scope>test</scope>  
  </dependency>
  <dependency>  
    <groupId>org.json</groupId>  
    <artifactId>json</artifactId>  
    <version>20240303</version>  
  </dependency>
``` 
Run the main class of `CSC207Project\src\main\java\app\Main.java`.
<img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/mainfile.png?raw=true" alt="image" width="300"/>

- **Way 2**:
  Download and unzip the folder, use a java IDE(Intellij) to open the folder with `JDK 21.0.5`, download the required chariot AP jar file from [link](https://repo1.maven.org/maven2/io/github/tors42/chariot/0.1.10/chariot-0.1.10.jar) and add it to libraries. Install `junit 4.13.1` and `org.json 20240303` in the IDE, then run the main class of `CSC207Project\src\main\java\app\Main.java`


#### Common issues in the installation process
**package does not exist**
Ensure that all of the following packages are downloaded and have the correct versions
```
chariot 4.13.1
junit 4.13.1
org,json 20240303
```
**Java Version is incorrect**
Make sure the program is running on JDK 21.0.5. Download from [here](https://www.oracle.com/ca-en/java/technologies/downloads/#java21)

</div>

---

<div id="Usage Guide">

### Usage Guide
Make sure follow the Installation Instructions to install all the required packages, then go to CSC207Project\src\main\java\app\Main.java file, run the main function. After success, the sign up window will pop up.

<img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/signupwindow.png?raw=true" alt="image" width="400" height="300"/>

There are buttons to switch to the login and to sign up an account. You have to sign up and login two accounts. The first one is Player1, white in Chess. The second is Player2, which is Black in Chess. After logging into both accounts, you can enter the Chess Game.

<img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/chessgamewindow.png?raw=true" alt="image" width="400" height="300"/>

Click on Continues to start the game.

<img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/gamestart.png?raw=true" alt="image" width="400" height="300"/>

The timer highlights which side's turn it is. Use the mouse to click on a piece to select it.

<img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/mousehover.png?raw=true" alt="image" width="400" height="300"/>

The board highlights the selected piece with a yellow background. All spaces that the selected piece can move to are shown with red dots, and all pieces that can be captured are highlighted with a red background.

<img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/selected.png?raw=true" alt="image" width="400" height="300"/>

You can also control the countdown timer using the pause and resume buttons below the countdown timer.

<img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/paused.png?raw=true" alt="image" width="400" height="300"/>

On your turn, click the restart button to forfeit.

<img src="https://github.com/JustinZhaoOvO/CSC207Project/blob/zhaoboqu/src/main/resources/READMEresources/forfeit.png?raw=true" alt="image" width="400" height="300"/>

The game will end according to standard chess rules. This includes Checkmate, Draw by Stalemate, Draw by three fold repetition, Draw by fifty move rule.


**Have Fun !!**

</div>

---

<div id="License">

### License
[Using a MIT license](https://github.com/JustinZhaoOvO/CSC207Project?tab=License-1-ov-file)
</div>

---

<div id="Feedback">

### Feedback
We value your input and want to hear your thoughts on this project! Here's how you can provide feedback:

-   **Email:** Send your feedback directly to boquan.zhao@mail.utoronto.ca with an email title starts with ChessGameFeedback.
-
**We are looking  for feedback about**

-   **Bugs and Issues:** If you encounter any bugs or unexpected behavior, please provide a detailed description of the issue, including steps to reproduce it if possible.
-   **Suggestions and Improvements:** We welcome any suggestions you have for improving the project's functionality or user experience.
-   **General Feedback:** Let us know what you like or dislike about the project.

**We will do our best to respond to all feedback as soon as possible.**
</div>

<div id="make a contribution">

### Make a contribution
We welcome contributions to this project. Here's how you can contribute:

**1. Fork the Repository:**

-   Create a fork of this repository on GitHub.
-   Clone your forked repository to your local.

**2. Make Changes:**

-   Create a new branch for your feature or bug fix.
-   Make your changes and commit them to your branch.
-   Push your branch to your forked repository.

**3. Create a Pull Request:**

-   On GitHub, create a pull request from your branch to the main branch of this repository.
-   Clearly describe the changes you've made in the pull request description.

**Guidelines for Good Merge Pull Requests:**

-   **Write clear commit messages:** Use concise and descriptive commit messages.
-   **Test your changes thoroughly:** Ensure your changes don't introduce new bugs or regressions.

**Review and Merge Process:**

-   Pull requests will be reviewed by other contributors.
-   Reviewers will provide feedback and suggestions for improvement.
-   Once a pull request is approved, it will be merged into the main branch.

By following these guidelines, you can contribute to the project and help make it even better!
</div>
