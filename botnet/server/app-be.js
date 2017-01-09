//==================================================================
// Backend source main 
//==================================================================
var express = require('express');
var http = require('http');
var path = require('path');
var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;

// Load local modules
var mongoClient = require('./js/mongoClient');
var config = require('./js/config');

//==================================================================
// Define the strategy to be used by PassportJS
passport.use(new LocalStrategy(function(username, password, done) {
    mongoClient.isUserValid(username,password,
        function (valid) {
            if (valid) return done(null, {name:username});
            else return done(null, false, { message: 'Incorrect username/password.' });
        }
    );
}));

// Serialized and deserialized methods when got from session
passport.serializeUser(function(user, done) {
    done(null, user);
});

passport.deserializeUser(function(user, done) {
    done(null, user);
});

// Define a middleware function to be used for every secured routes
var auth = function(req, res, next){
    if (!req.isAuthenticated()) res.send(401);
    else next();
};
//==================================================================

// Start express application
var app = express();

// all environments
app.set('port', process.env.PORT || config.express_port);
// Route to load nodejs views
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.cookieParser()); 
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(express.session({ secret: 'securedsession' }));
app.use(passport.initialize()); // Add passport initialization
app.use(passport.session());    // Add passport initialization
app.use(app.router);
// Route to load angularjs
app.use(express.static(path.join(__dirname, '../client')));

// development only
if ('development' == app.get('env')) {
    app.use(express.errorHandler());
}

//==================================================================
// routes
//==================================================================

var renderMainPage = function (req, res) {
    if (req.isAuthenticated()) console.log('Serving default to an authenticated user!');
    res.render('index', { title: 'BotNet' });
};
// Gets public messages, unsecured access ..........................
var sendPublicMessages = function (req, res) {
    console.log('Sending public messages');
    var limit = config.pageLimit; // 10 messages per page
    var offset = (req.params.page) ? (req.params.page-1)*limit : 0;
    var types = ['public']; // only query for public messages
    mongoClient.getMessages(offset, limit, ['public'], function (data) { res.send(data); });
};

// Gets public and registered messages, secured access .............
var sendPublicAndRegisteredMessages = function (req, res) {
    console.log('Sending public messages');
    var limit = config.pageLimit; // 10 messages per page
    var offset = (req.params.page) ? (req.params.page-1)*limit : 0;
    var types = ['public']; // only query for public messages
    mongoClient.getMessages(offset, limit, ['public','registered'], function (data) { res.send(data); });
};

var uploadMessage = function (req, res) {
    console.log("User upload request from " + req.user.name + " with data:\n" + req.body);
    var callback = function (isOk) {
        if (isOk) res.json(200, {'result':'ok'});
        else res.json(400, {'result':'err'});
    };
    mongoClient.storeUserMessage(req.user.name, req.body, callback);
};

var registerUser = function (req, res) {
    console.log("Register request received");
    var callback = function (isOk, msg) {
        if (!isOk) res.send(400, {'result': msg});
        else res.send(200, {'result':''});
    };
    mongoClient.registerUser(req.body, callback);
};

var removePost = function (req, res) {
    var id = req.params.id;
    if (req.user.name == 'admin') {
        console.log('Removing id : ' + id);
        mongoClient.removePost(id, function (result) {
            if (result >= 1) res.json(200, {'result':'ok'});
            else res.json(400, {'result':'error'});
        });
    }
    else {
        console.log("User " + req.user.name + " can not erase posts!.");
    }
};

var getPostById = function (req, res) {
    var id = req.params.id;
    mongoClient.getPostById(id, function (err, result) {
        if (err) res.json(500, {'result':'db error'});
        else {
            var post = result[0];
            if (req.user.name == 'admin' || req.user.name == post.author) {
                res.json(200,post);
            }
            else {
                console.log('Wrong user/not authorized');
            }
        }
    });
};

var updatePost = function (req, res) {
    console.log("Received update request");
    var id = req.body.id;
    // check if the user as access to the post message
    mongoClient.getPostById(id, function (err, result) {
        if (err) res.json(500, {'result':'db error'});
        else if (req.user.name == 'admin' || req.user.name == result[0].author) {
            // user can modify the post
            mongoClient.updatePost(req.body, function (err) {
                if (err) res.json(500, {'result':'db error'});
                else res.json(200, {'result':'updated'});
            });
        }
        else res.json(401, {'response':'unauthorized'});
    });
};

app.get('/data/public/:page', sendPublicMessages);
app.get('/data/private/:page', auth, sendPublicAndRegisteredMessages);
app.get('/', renderMainPage);
app.post('/upload', auth, uploadMessage);
app.post('/register', registerUser);
app.delete('/remove/:id', auth, removePost);
app.get('/post/:id', auth, getPostById);
app.post('/update/post', auth, updatePost);

// just to avoid error message on FE when tries to load an image before angular ng-repeat
app.get('/%7B%7Bitem.img%7D%7D', function (req, res) { res.json(200, {'result':'ok'}); });
//==================================================================


//==================================================================
// route to test if the user is logged in or not
app.get('/loggedin', function(req, res) {
    res.send(req.isAuthenticated() ? req.user : '0');
});

// route to log in
app.post('/login', passport.authenticate('local'), function(req, res) {
    res.send(req.user);
});

// route to log out
app.post('/logout', function(req, res){
    req.logOut();
    res.send(200);
});
//==================================================================

http.createServer(app).listen(app.get('port'), function(){
    console.log('Express server listening on port ' + app.get('port'));
});