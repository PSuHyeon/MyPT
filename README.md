# MyPT Application: </br></br> Real time member management application for trainers

## 1. Trainer

#### Tab 1: 

**Trainee information attrievement by two methods:**

1. By specific date
2. By specific name

**retreive selected trainee's exercise info according to aforementioned method**

exercise information containes:
1. name
2. time
3. weight
4. set
5. number
6. current state (whether one had finished)

#### Tab 2:

**Create private chatting room with every trainee**

#### Tab 3: 

**Community available for all users including trainees and trainers**
1. uploading image with comment
2. reply message for each feed

## 2. Trainee

#### Tab 1:

**Create exercise information for each date in the calender provided**

#### Tab 2: 

**Chatting room with trainer**

#### Tab 3: 

**above mentioned community**

#### Tab 4:

**Timer with number input for the ease of trainees' training**

# Application specification
* Naver Login SDK (네아로);
  * Support login using naver Id; 
<img width="266" alt="image" src="https://user-images.githubusercontent.com/99390261/178462792-8f53417e-d561-46b7-94af-2e8fb208c19c.png">

* Nodejs server with mysql databases:
  * login_information databases;
  * user_information databases;
  * exercise_information for each users databases;
  * Chat room information for each users;
  * Chatting log history for each chat room;
  * feed information for the community;
   * upload feed image by (bitmap -> String) convertion and following (String -> bitmap).
  * Reply message history for each feed;
 
* The followings are set of mysql databases;

  Users
 <img width="503" alt="image" src="https://user-images.githubusercontent.com/99390261/178460860-baf6b734-5fcd-42da-ae64-a0561194ffa4.png">
  Exercises
 <img width="492" alt="image" src="https://user-images.githubusercontent.com/99390261/178461253-c413f7ed-0276-49b3-ba9e-86445d9d0096.png">
  Feed information
 <img width="502" alt="image" src="https://user-images.githubusercontent.com/99390261/178461355-909f9c9a-4df9-4b9e-9af1-1fb1a196ca10.png">
  Feed reply information
 <img width="484" alt="image" src="https://user-images.githubusercontent.com/99390261/178461498-54173382-5fc2-4eb0-a149-2e66c4954107.png">
  Chat_log information
<img width="494" alt="image" src="https://user-images.githubusercontent.com/99390261/178461590-0a2271b1-3c59-483e-91d8-b0a8f22acd83.png">

 
* Socket io connection for real time chat connection for individual trainees;
  * Exploit join to create individaul room for socket system
  * use io.to(room).emit() to send socket event to specific room

* Calender view to store information for each dates;
  * data stored with calender view date information

* Recycler view to maintain and show information dynamically
  * feed information
  * replies for feed
  * exercise information for each dates
  * chat messages
  * trainee lists in trainer tab
 
# Applicatio Design 

* Used Dialog and chips for concise exercise selection.
* Check boxs and Delete icon in each items in exercise list for concise state insertion and deletion.
* Use nine patch image for natural chat box. 
* 
