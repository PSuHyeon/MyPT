const app = require('express')();
const server = require('http').createServer(app);
const io = require('socket.io')(server)
const mysql = require('mysql');
const PORT = 443
const chat_info = mysql.createConnection({
  host : 'localhost',
  user : 'root',
  password : 'jj3212617',
  database : "chat_info" 
});

//서버연결하기 직전에뜸
console.log("outside io");
 
io.on('connection', socket => {
 
  //로그인하면 이거 밑에 두개뜸
 
  socket.on('connect user', function(user){

    console.log("state : ",socket.adapter.rooms);
    socket.join(user)
    io.emit('connect user', "hihi");
  });
 
 
  //메세지 입력하면 서버 로그에 이거뜸
  socket.on('chat message', function(msg){
    //io.to(msg['roomName']).emit('chat message', msg);
    var temp = Object();
    temp.name = msg.name;
    temp.text = msg.text;
    temp.key_id = msg.key_id;
    temp.time = msg.time;
    chat_info.query(`insert into chat_log values("${msg.name}", "${msg.key_id}", "${msg.text}", "${msg.time}", "${msg.room_id}")`)
    io.to(msg.room_id).emit('chat message', temp);
  });
});
 
//맨처음에 서버 연결하면 몇번포트에 서버 연결되어있는지 ㅇㅇ
server.listen(PORT, () => {
  console.log('Node app is running on port', PORT);
});
