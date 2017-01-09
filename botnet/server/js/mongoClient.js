
var config = require('./config');
var mongo = require('mongoskin');
var db = mongo.db('mongodb://' + config.mongo_ip + ':'+ config.mongo_port + '/' + config.mongo_db, {native_parser:true});

var isUserValid = function (user, pass, callback) {
	db.collection('users').count({'id':user,'password':pass},function (err, count) {
		callback(count==1);
	});
};

var storeUserMessage = function (user, message, callback) {
    message.author = user,
    message.ts = new Date();
    message.comments = [];

    // get user img
    db.collection('users').find({id:user},{img:1}).toArray(function (err, result) {
        message.img = result[0].img;
        db.collection('posts').insert(message, function (err) {
            if (err) callback(false);
            else callback(true);
        });
    });    
};

var registerUser = function (user, callback) {
    db.collection('users').count({id:user.id}, function (err, count) {
        if (err) callback(false, 'DB Insertion error');
        else if (count > 0) callback(false, 'User ' + user.id + " already exists.");
        else db.collection('users').insert(user, function (err) {
            if (err) callback("DB Insertion error (2)");
            else callback(true, '');
        });
    });
};

var getMessages = function (offset, limit, types, callback) {
    var filter = {};
    if (types.length == 1) filter = { 'type' : types[0] };
    else {
        filter = { $or : []};
        for (var i = 0; i < types.length; i++) filter.$or.push({'type' : types[i]});
    }

    // how many results there are
    db.collection('posts').count(filter, function (err, count) {
        db.collection('posts')
            .find(filter)
            .skip(offset)
            .limit(limit)
            .sort({ts:-1})
            .toArray(function(err, result) {
                var response = {
                    pageInfo : {
                        offset : offset,
                        limit : limit,
                        count : count,    
                    },                    
                    data : result
                };
                callback(response);
            });  
        }
    );
};

var removePost = function (id, callback) {
    db.collection('posts').remove({'_id' : mongo.helper.toObjectID(id)}, function (err, result) {
        callback(result);
    });
};

var getPostById = function (id, callback) {
    db.collection('posts').find({'_id' : mongo.helper.toObjectID(id)}).toArray(function (err, result) {
        callback(err,result);
    }); 
};

var updatePost = function (post, callback) {
    db.collection('posts').update(
            {'_id' : mongo.helper.toObjectID(post.id)}, 
            { $set: {type:post.type, title:post.title, content:post.content} },
            callback
        );
};

/* Exported module API */
module.exports = {
    isUserValid : isUserValid,
    storeUserMessage : storeUserMessage,
    registerUser :registerUser,
    getMessages : getMessages,
    removePost : removePost,
    getPostById : getPostById,
    updatePost : updatePost
};

