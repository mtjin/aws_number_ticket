var mysql = require('mysql');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

app.use(bodyParser.json({extended: true})); //사용자가 웹사이트로 전달하는 정보들을 검사하는 미들웨어
app.use(bodyParser.urlencoded({extended: true})); //json이 아닌 post형식으로올때 파서

app.listen(3000, function () {
    console.log('서버 실행');
});

//디비연결
var connection = mysql.createConnection({
    host: "..--...",
    user: "",
    database: "",
    password: "!",
    port: 3306
});

//회원가입
app.post('/user/join', function (req, res) {
    var userId = req.body.userId;
    var userPassword = req.body.userPassword;
    var userRestaurant = req.body.userRestaurant;
    var restaurantLocation = req.body.restaurantLocation;
    var restaurantTel = req.body.restaurantTel;
    var fcm = req.body.fcm;

    var sql = 'INSERT INTO Users (userId, userPassword, userRestaurant, restaurantLocation, restaurantTel, fcm ) VALUES (?, ?, ?, ?, ?, ?)';
    var params = [userId, userPassword, userRestaurant, restaurantLocation, restaurantTel, fcm];

    connection.query(sql, params, function (err, result) {
        if (err)
            console.log(err);
        else {
            res.json({
                result: true,
                msg: '회원가입에 성공했습니다.'
            })
        }
    });
});

//아이디 중복확인
app.post('/user/checkId', function (req, res) {
    var userId = req.body.userId;
    var sql = 'select * from Users where userId = ?';
    connection.query(sql, userId, function (err, result) {
        if (err)
            console.log(err);
        else {
            if (result.length === 0) {
                res.json({
                    result: false,
                    msg: '사용가능한 아이디입니다.'
                });
            } else {
                res.json({
                    result: true,
                    msg: '중복된 아이디가 존재합니다'
                });
            }

        }
    })
});


//로그인
app.post('/user/login', function (req, res) {
    var userId = req.body.userId;
    var userPassword = req.body.userPassword;
    var sql = 'select * from Users where userId = ? AND userPassword = ?';
    var params = [userId, userPassword];
    connection.query(sql, params, function (err, result) {
        if (err)
            console.log(err);
        else {
            if (result.length === 0) {
                res.json({
                    result: false,
                    msg: '존재하지 않는 계정입니다!'
                });
            } else if (userPassword !== result[0].userPassword) {
                res.json({
                    result: false,
                    msg: '비밀번호가 틀렸습니다!'
                });
            } else {
                res.json({
                    result: true,
                    msg: '로그인 성공!',
                    id : result[0].id,
                    userId: userId,
                    userPassword: result[0].userPassword,
                    userRestaurant: result[0].userRestaurant,
                    restaurantLocation : result[0].restaurantLocation,
                    restaurantTel : result[0].restaurantTel
                });
            }

        }
    })
});


//레스토랑검색
app.post('/restaurant/search', function (req, res) {
    var userRestaurant = req.body.userRestaurant;
    var sql = "select * from Users where userRestaurant LIKE " +connection.escape('%'+req.body.userRestaurant+'%');

    connection.query(sql, userRestaurant, function (err, result) {
        if (err)
            console.log(err);
        else {
            if (result.length === 0) {

            } else {
                res.json({
                    result
                });
            }
        }
    })
});


//레스토랑 예약
app.post('/restaurant/insert', function (req, res) {
    var restaurant_id = req.body.restaurant_id;
    var restaurant_name = req.body.restaurant_name;
    var apply_id = req.body.apply_id;
    var apply_date = req.body.apply_date;
    var reserve_date = req.body.reserve_date;
    var user_tel = req.body.user_tel;
    var user_pw = req.body.user_pw;
    var accept = req.body.accept;
    var fcm = req.body.fcm;

    var sql = 'INSERT INTO Apply (restaurant_id, restaurant_name, apply_id, apply_date, reserve_date, user_tel,user_pw, accept, fcm ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)';
    var params = [restaurant_id, restaurant_name, apply_id, apply_date, reserve_date, user_tel,user_pw, accept, fcm];

    connection.query(sql, params, function (err, result) {
        if (err)
            console.log(err);
        else {
            res.json({
                result: true,
                msg: '예약이 성공했습니다'
            })
        }
    });
});

//특정 레스토랑 전체 예약리스트(전체날짜)
app.post('/restaurant/admin', function (req, res) {
    var restaurant_id = req.body.restaurant_id;
    console.log(restaurant_id, typeof(restaurant_id));
    var sql = "select * from Apply where restaurant_id = ? ORDER BY reserve_date";
    connection.query(sql, restaurant_id, function (err, result) {
        if (err)
            console.log(err);
        else {
            if (result.length === 0) {

            } else {
                res.json({
                    result
                });
            }
        }
    })
});

//특정 레스토랑  예약리스트(특정날짜)
app.post('/restaurant/admin/certain', function (req, res) {
    var restaurant_id = req.body.restaurant_id;
    var reserve_date = req.body.reserve_date;
    var sql = "select * from Apply where restaurant_id = ? AND reserve_date LIKE " + connection.escape('%'+req.body.reserve_date+'%') + "order by reserve_date";
    var params = [restaurant_id, reserve_date];
    connection.query(sql, params, function (err, result) {
        if (err)
            console.log(err);
        else {
            if (result.length === 0) {

            } else {
                res.json({
                    result
                });
            }
        }
    })
});

//예약 수락
app.post('/restaurant/accept', function (req, res) {
    var id = req.body.id;
    var sql = "update Apply set accept = 'yes' where id = ?";

    connection.query(sql, id, function (err, result) {
        if (err)
            console.log(err);
        else {
            if (err)
                console.log(err);
            else {
                res.json({
                    result: true,
                    msg: '예약이 수락되었습니다.'
                })
            }
        }
    })
});

//예약 수락, 거절 알림
app.post('/restaurant/accept/alarm', function (req, res) {
    var id = req.body.id;
    var sql = "select * from Apply where id = ?";

    connection.query(sql, id, function (err, result) {
        if (err)
            console.log(err);
        else {
            if (err)
                console.log(err);
            else {
                res.json({
                    result: true,
                    fcm: result[0].fcm,
                    restaurant_name: result[0].restaurant_name
                })
            }
        }
    })
});

//예약신청 했다고 상대방에게 알림
app.post('/restaurant/reserve/alarm', function (req, res) {
    var id = req.body.id;
    var sql = "select * from Users where id = ?";

    connection.query(sql, id, function (err, result) {
        if (err)
            console.log(err);
        else {
            if (err)
                console.log(err);
            else {
                res.json({
                    result: true,
                    fcm: result[0].fcm,
                    userId: result[0].userId
                })
            }
        }
    })
});

//예약수락한거 취소
app.post('/restaurant/cancel', function (req, res) {
    var id = req.body.id;
    var sql = "update Apply set accept = 'no' where id = ?";

    connection.query(sql, id, function (err, result) {
        if (err)
            console.log(err);
        else {
            if (err)
                console.log(err);
            else {
                res.json({
                    result: true,
                    msg: '예약이 취소되었습니다.'
                })
            }
        }
    })
});

//내가 예약한 레스토랑
app.post('/user/reserve', function (req, res) {
    var apply_id = req.body.apply_id;
    var user_tel = req.body.user_tel;
    var user_pw = req.body.user_pw;
    console.log("내가 예약한 가게검색");
    var sql = "select * from Apply where apply_id = ? AND user_tel = ? AND  user_pw = ? order by reserve_date";
    var params = [apply_id, user_tel, user_pw];
    connection.query(sql, params, function (err, result) {
        if (err)
            console.log(err);
        else {
            if (result.length === 0) {

            } else {
                res.json({
                    result
                });
            }
        }
    })
});

//음식점(유저) fcm 변경
app.post('/user/fcm', function (req, res) {
    var id = req.body.id;
    var fcm = req.body.fcm;
    var sql = "update Users set fcm = ? where id = ?";
    var params = [id, fcm];
    connection.query(sql, params, function (err, result) {
        if (err)
            console.log(err);
        else {
            if (err)
                console.log(err);
            else {
                res.json({
                    result: true,
                    msg: 'FCM 변경 성공'
                })
            }
        }
    })
});