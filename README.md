# MyPT Application: </br></br> Real time member management application for trainers

## 1. Trainer

#### Tab 1: 

** Trainee information attrievement by two methods: **

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
  
* Nodejs server with mysql databases:
  * login_information databases;
  * user_information databases;
  * exercise_information for each users databases;
  * Chat room information for each users;
  * Chatting log history for each chat room;
  * feed information for the community;
  * Reply message history for each feed;
 
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
 
