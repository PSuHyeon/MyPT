const express = require('express');
const app = express();
const bodyParser = require('body-parser')
const mysql = require('mysql');
const user_ids = mysql.createConnection({
    host : 'localhost',
    user : 'root',
    password : 'jj3212617',
    database : "login_info" 
});
const exer_info = mysql.createConnection({
    host : 'localhost',
    user : 'root',
    password : 'jj3212617',
    database : "exercise_info" 
});
const chat_info = mysql.createConnection({
    host : 'localhost',
    user : 'root',
    password : 'jj3212617',
    database : "chat_info" 
});
const PORT = 80;


//user_ids.connect();

app.use(bodyParser.urlencoded({extended: false}))
app.use(bodyParser.json())
app.listen(PORT, () => {
    console.log(`Start Server : localhost:${PORT}`);
    
});
app.get('/get_trainee', async (req, res) => {
    console.log("cameame")
    user_ids.query(`select * from users where position = "TRAINEE"`, (error, rows, field) => {
        console.log(rows)
        var temp = "";
        for (let i = 0; i < rows.length; i++){
            temp += "," + rows[i].name;
        }
        res.send(temp);
    });
})
app.post('/get_by_date', async (req, res) => {
    const date = req.body.date;
    exer_info.query(`select * from exercise where date = "${date}"`, (error, rows, field) => {
        console.log(rows);
        res.send(rows);
    })
});
app.post('/get_by_name', async (req, res) => {
    const name = req.body.name;
    exer_info.query(`select * from exercise where name = "${name}"`, (error, rows, field) => {
        console.log(rows);
        res.send(rows);
    })
});
app.post('/get_by_name', async (req, res) => {
    const name = req.body.name;
    exer_info.query(`select * from exercise where name = "${name}"`, (error, rows, field) => {
        console.log(rows);
        res.send(rows);
    })
});
app.post('/get_exercise', async (req, res) => {

    const date = req.body.date;
    const id = req.body.keyid;
    console.log(date);
    console.log(id);
    exer_info.query(`select * from exercise where id = ${id} and date = "${date}"`, (error, rows, field)  => {
        console.log(rows);
        if (rows != undefined){
            res.send(rows);
        }
    
    });
});

app.post('/delete', async (req, res) => {

    const body = req.body;
    exer_info.query(`delete from exercise where id = "${body.id}" and name = "${body.name}" and date =  "${body.date}" and type = "${body.type}" and exercise = "${body.exercise}" and time = "${body.time}" and number = "${body.number}" and sett = "${body.sett}" and weight = "${body.weight}" `)
});


app.get('/checkid/:id', async (req, res) => {
    let {
        id
    } = req.params;

    user_ids.query(`Select * from users where id = "${id}"`, (error, rows, field) => {
        if (rows.length == 0){
            res.send("ok");
        }
        else{
            const data = rows[0].keyid
            const data1 = rows[0].name
            const data2 = rows[0].position
            res.send(`${data},${data1},${data2}`);
        }
    });
});

app.get('/login/:id', async (req, res) => {
    let {
        id
    } = req.params;

    const ids = id.split(",");


    user_ids.query(`SELECT * from users`, (error, rows, fields) => {
        var success = 0;
        if (error) throw error;
        for (i = 0; i<rows.length; i++){
            if (rows[i].id == ids[0] && rows[i].password == ids[1]){
                res.send(`${rows[i].keyid},${rows[i].name},${rows[i].position}`);
                success = 1;
                console.log("success")
            }
        }

        if (success == 0){
            res.send("login failed");
            console.log("failed")
        }
      });
      

});

app.post('/moo', (req, res) => {
    const body = req.body;
    console.log(body);
    exer_info.query(`insert into exercise values("${body.id}", "${body.name}", "${body.date}", "${body.type}", "${body.exercise}", "${body.time}", "${body.number}", "${body.sett}", "${body.weight}", "${body.current}")`)
});

app.post('/checkbox', (req, res) => {

    const body = req.body;
    console.log(body);
    exer_info.query(`update exercise set current = "${body.current}" where id = "${body.id}" and name = "${body.name}" and date =  "${body.date}" and type = "${body.type}" and exercise = "${body.exercise}" and time = "${body.time}" and number = "${body.number}" and sett = "${body.sett}" and weight = "${body.weight}"`)
});
app.post('/sign_up', (req, res) => {
    const id = req.body.id;
    const password = req.body.password;
    const name = req.body.name;
    const position = req.body.position;
    const age = req.body.age;
    const weight = req.body.weight;
    const height = req.body.height;
    const gender = req.body.gender;
    var keyid = 0;
    user_ids.query(`Select Max(keyid) as id from users`, (error, rows, field) => {
        if (rows[0].id == null){
            keyid = 0;
        }
        else {
            keyid = rows[0].id + 1;
        }
        console.log("inserting id value of",id);
        console.log("inserting password value of",password);
        console.log("inserting keyid value of",keyid);
        user_ids.query(`insert into users values("${id}", "${password}", ${keyid}, "${name}", "${position}", "${age}", "${weight}", "${height}", "${gender}" )`);
        var data = new Object();
        data.keyid = keyid;
        res.send(data);
    });
    
    
});
app.get('/get_trainee_info', async (req, res) => {
    user_ids.query('select * from users where position = "TRAINEE"', (error, rows, fields) => {
        console.log(rows);
        res.send(rows);
    })
    
});

app.get('/get_chat_info/:key_id', async (req,res) => {
    let {
        key_id
    } = req.params;

    chat_info.query(`select * from chat_log where room_id = "${key_id}"`, (error, rows, fields) => {
        res.send(rows)
    })
})

// community tab 새 게시물 작성
app.post('/feed', async (req, res) => {
    const id = req.body.id;
    const date = req.body.date;
    const name = req.body.name;
    //const position = req.body.position;
    //const image = req.body.image;
    const contents = req.body.contents;
    const bitmap = req.body.image;

    console.log("id", id);
    console.log("date", date);
    console.log("name",name);
    console.log("contents",contents);

    var keyid = 0;
    
    exer_info.query(`Select * from feed`, (error, rows, field) => {
        console.log(rows);
        if (rows.length == 0){
            keyid = 0
            exer_info.query(`insert into feed values("${date}", "${name}", "null", "${bitmap}", "${contents}", "${keyid}")`);
            var data = new Object();
            data.keyid = keyid;
            res.send(data);
        }
        else{
            exer_info.query(`select Max(id) as id from feed`, (error, rows, field) => {
                console.log("rows[0].id", rows[0].id);
                keyid = parseInt(rows[0].id) + 1
                exer_info.query(`insert into feed values("${date}", "${name}", "null", "${bitmap}", "${contents}", "${keyid}")`);
                var data = new Object();
                data.keyid = keyid;
                res.send(data);
            })
        }
        
    });
    
});

app.get('/getfeed', async (req, res) => {
    let {
        id
    } = req.params;

    exer_info.query(`Select * from feed`, (error, rows, field) => {
    
        res.send(rows);
    });
});

app.post('/getReply/:id', async (req, res) => {
    let {
        id
    } = req.params;
    console.log(id)
    exer_info.query(`select * from reply where feedId = "${id}"`, (error, rows, fields) => {
        console.log(rows);
        res.send(rows)
    })
    
});
app.post('/newReply/:id', async (req, res) => {
    let {
        id
    } = req.params;
    console.log(id);
    const text = req.body.text;
    const name = req.body.name;
    const time = req.body.time;
    const key_id = req.body.feedid;

    exer_info.query(`insert into reply values("${name}", "${time}", "${text}", "${key_id}")`);

    const temp = new Object();
    temp.name = name;
    temp.time = time;
    temp.text = text;
    temp.feed = key_id;
    console.log("here",temp);
    res.send(temp);
});

app.post('/getUser', async (req, res) => {
    const name = req.body.name;
    console.log("here",name);
    user_ids.query(`select * from users where name = "${name}"`, (error, rows, fields) => {
        console.log(rows);
        const ob = rows[0];
        const temp = new Object();
        temp.name = ob.name;
        temp.age = ob.age;
        temp.gender = ob.gender;
        temp.weight = ob.weight;
        temp.height = ob.height;
        res.send(temp);
    })
})
//user_ids.end();