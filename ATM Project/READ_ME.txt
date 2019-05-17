First Step:
        You will need two external Libraries to run this. They are: Twilio and Jsoup.
        - To download Twilio go to: https://repo1.maven.org/maven2/com/twilio/sdk/twilio/7.36.2/
                And press the twilio-7.36.2-jar-with-dependencies.jar file to download
        - To download Jsoup go to: https://jsoup.org/download
                And press the jsoup-1.11.3.jar  file to download
        Save these files somewhere on your computer.
        Twilio is used for texting and phone call functionality.
        Jsoup is used for webscraping functionality.

Second Step:
        Add these files to the Project Structure
        Go to project structure. Go to the libraries tab. and press the plus symbol at the top.
        Find the location you downloaded the Twilio and Jsoup jar file to and add them to the project structure.
        Select to add them to phase 2.

Third Step:
        The Project is run via the main method in the ATM class. located in the ATM_Elements Folder.
        WITHOUT A GROUP MEMBER PRESENT, YOU CANNOT USE THE FUNCTIONALITY OF CREATING A REQUEST FOR A NEW USER FROM THE INITIAL
        LOG IN SCREEN. PLEASE LOG IN AS THE BANK MANAGER TO CREATE A NEW USER IF YOU WANT A NEW ONE.
        PLEASE SEE ALERT 2 BELOW IN THE NOTICE SECTION FOR AN EXPLANATION AS TO WHY.

Fourth Step:
        There are a few pre-existing Persons (1 Bank Manager, 1 Bank Assistant Manager and 2 Users):
            BankManager
                username: bm
                password: 123

            AssistantBankManager:
                username: abm
                password: 123

            user1:
                username: user1
                password: abc

            user2:
                username: user2
                password: 123

Fifth Step:
        You can now use these users to do any tasks you like. They each have their own home screen with processes you can select from.
        Each user starts with one of each account available.
                Some requests require a BankWorker to process them before they go through/pass:
                    - creation of a new user upon log in
                    - creation of a request for a new account or joint account
                These things must be processed by a BankWorker before they begin to exist.

NOTICE:
        ALERT ALERT ALERT - 1:
            IF THE PROGRAM FAILS TO RUN DUE TO THE INABILITY TO LOCATE THE NECESSARY ALERT TEXT FILE...
            THIS MIGHT BE DUE TO A CHANGE IN THE PATH UPON DOWNLOAD TO A NEW COMPUTER. PLEASE FIX THE PATH IN THE CODE MANUALLY
                - THE ALERT FILE PATH IS USED IN THE ATM CLASS' WRITEFILE METHOD AND THE BANKMANAGER'S FILLATM METHOD

        ALERT ALERT ALERT - 2:
            FUNCTIONALITY OF TWILIO TEXT MESSAGING AND PHONE CALLING IS DEPENDANT OF HAVING A PRE-EXISTING VERIFIED PHONE NUMBER.
            AS OF RIGHT NOW, ONLY ETHAN HEIMLICH HAS HIS PHONE NUMBER VERIFIED. FOR THIS REASON IT WILL FAIL TO WORK FOR NEW USERS.
                - THE PROGRAM TEXTS YOU IF YOU TRY TO MAKE A NEW USER FROM THE LOGIN SCREEN. IT ASKS FOR A 2-FACTOR AUTHENTICATION.
                    YOU WILL NOT BE ABLE TO GET THIS INFORMATION UNLESS ETHAN IS THERE. SO TO CREATE A NEW USER, PLEASE LOG IN AS BANK MANAGER
                    AND DO IT FROM THEIR MAIN MENU.
                - THE PROGRAM CALLS YOU IF YOU TRY TO ENTER AN INCORRECT PASSWORD TO AN ACCOUNT 3 TIMES. IF YOU DO THIS IT WILL LOCK YOU OUT OF THE
                ACCOUNT UNTIL YOU LOG IN AS THE BANK MANAGER TO UN-LOCK THE ACCOUNT. YOU WILL NOT RECEIVE A PHONE CALL... ETHAN WILL.

        ALERT ALERT ALERT - 3:
            FUNCTIONALITY OF THE WEBSCRAPER DEPENDS ON THE JSOUP LIBRARY. FURTHERMORE, WEBSCRAPER USES THE WEBPAGE OF FINANCE.YAHOO TO GET STOCK
            INFORMATION. IF THE WEBPAGE'S HTML CODE CHANGES UPON AN UPDATE TO THE WEBSITE (working as of March 31st) THEN THERE MIGHT BE AN ERROR
            AS THE WEBSCRAPER ATTEMPTS TO SEARCH THROUGH THE HTML CODE TO GET THE NECESSARY STOCK INFO.


        ALERT ALERT ALERT - 4:
            THERE ARE TWO WAYS TO CREATE A JOINT ACCOUNT, YOU CAN EITHER MAKE AN EXISTING ACCOUNT JOINT, WHICH
            WILL REQUIRE THE USER YOU SENT IT TO TO PROCESS THESE REQUEST, AN OPTION IN THEIR HOME SCREEN OR
            YOU CAN REQUEST TO CREATE A NEW ACCOUNT WHICH WILL BE JOINT, WHICH REQUIRES A BANKWORKER(EITHER KIND
            WILL WORK) TO PROCESS THE NEW JOINT ACCOUNT REQUEST, AN OPTION IN THEIR HOMESCREEN.