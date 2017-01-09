
// Run with mongo --quiet botnet init.js

db.users.drop();
db.posts.drop();

// creates users
db.users.insert({
	"id" : "user1",
	"password" : "user1",
	"name" : "test user 1",
	"img" : "http://lorempixel.com/100/100/people/1/",
	"email" : "user1@gmail.com"
});

db.users.insert({
	"id" : "user2",
	"password" : "user2",
	"name" : "test user 2",
	"img" : "http://lorempixel.com/100/100/people/2/",
	"email" : "user2@gmail.com"
});

db.users.insert({
	"id" : "admin",
	"password" : "admin",
	"name" : "admin user",
	"img" : "http://lorempixel.com/100/100/people/3/",
	"email" : "admin@gmail.com"
});

// Creates some random public content

db.posts.insert ({
	ts : new Date(2012, 2, 15, 12, 0, 0, 0),
	author: "user1",
	img : "http://lorempixel.com/100/100/people/1/",
	type: "public",
	title: "Title sample 1",
	content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec faucibus mi a pharetra faucibus. Maecenas ultricies, odio non facilisis porta, lectus mauris fringilla leo, luctus egestas enim justo nec lacus. Nunc sit amet neque eu ipsum ultrices luctus. Praesent ac mattis ipsum. Sed ac mi lacus. Donec venenatis nisl non nulla dictum vehicula. Cras at gravida ante. Donec in leo quis odio viverra tincidunt laoreet non dui. Morbi non fringilla neque, eget egestas erat. Etiam tempor, nisl ut laoreet tempor, tortor metus condimentum mauris, lacinia ultricies orci dui nec ligula. Vestibulum diam elit, gravida in viverra ut, rutrum sit amet leo. Sed tincidunt tincidunt felis. Sed massa lacus, porta ullamcorper rhoncus vel, volutpat id tellus."
});
db.posts.insert ({
	ts : new Date(2012, 2, 16, 12, 0, 0, 0),
	author: "user2",
	img : "http://lorempixel.com/100/100/people/2/",
	type: "public",
	title: "Title sample 2",
	content: "Cras justo turpis, ullamcorper non arcu in, pharetra tempus augue. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur erat massa, vehicula sit amet mattis id, aliquam id purus. Integer eu suscipit est. Nulla at turpis tristique, dignissim dui ut, vestibulum urna. Sed iaculis est et purus accumsan facilisis. Praesent dictum mi quam, non tincidunt nulla cursus nec. Quisque nec augue felis."
});
db.posts.insert ({
	ts : new Date(2012, 2, 17, 12, 0, 0, 0),
	author: "user1",
	img : "http://lorempixel.com/100/100/people/1/",
	type: "public",
	title: "Title sample 3",
	content: "Aenean consequat blandit lacus. Fusce vitae consectetur nisi. Donec vel odio ut quam laoreet vehicula. Suspendisse lacinia erat nec massa tincidunt, id fermentum nisi iaculis. Morbi sodales fringilla purus, blandit feugiat purus tempor sed. Praesent ultrices augue ut eros convallis, sed tincidunt tellus aliquam. Curabitur lacus erat, facilisis ut consequat ut, posuere ut elit."
});
db.posts.insert ({
	ts : new Date(2012, 2, 18, 12, 0, 0, 0),
	author: "user1",
	img : "http://lorempixel.com/100/100/people/1/",
	type: "public",
	title: "Title sample 4",
	content : "Fusce urna risus, auctor id sollicitudin ac, aliquam ac est. Pellentesque elit neque, tincidunt ac sem sit amet, pretium cursus ante. Aliquam sit amet varius est. Proin vel facilisis nunc. Phasellus porttitor venenatis imperdiet. Nulla in laoreet orci. Morbi iaculis nunc dui. Etiam quam nisl, dictum quis purus quis, interdum gravida sapien. Morbi porta magna a vulputate rutrum. Morbi accumsan tempor tincidunt. Sed sit amet neque vel arcu tristique feugiat. Nulla id libero id enim rhoncus lacinia. Duis tempus, orci a hendrerit eleifend, augue leo dictum ipsum, eu fringilla ipsum tellus vel nunc. Fusce ac tincidunt nunc. Sed nec porta nisl, in molestie purus. Aliquam eu urna sit amet augue imperdiet varius in eget metus."
});
db.posts.insert ({
	ts : new Date(2012, 2, 19, 12, 0, 0, 0),
	author: "user2",
	img : "http://lorempixel.com/100/100/people/2/",
	type: "public",
	title : "Title sample 5",
	content: "Vestibulum laoreet, lectus vel condimentum tincidunt, arcu erat dictum leo, sit amet tristique nisl urna non mauris. Fusce congue, dui vitae elementum tincidunt, massa nunc facilisis arcu, vitae pulvinar tortor nisi suscipit nunc. Proin dapibus faucibus massa, sed tempor dolor rhoncus ultricies. Mauris quis interdum eros, nec ullamcorper lectus. Mauris elementum est nibh, id sodales lacus scelerisque pulvinar. Fusce nulla metus, eleifend vel lectus at, rhoncus auctor lectus. Nunc id eros eu magna posuere molestie id sed mauris. Donec porta mi dolor. Maecenas malesuada ligula at urna consequat sollicitudin. Aenean fermentum sed nulla vitae porttitor. Etiam sagittis mattis lacus quis mollis. Quisque a dui molestie, varius erat vel, ultrices dui. Donec mollis est quam, at adipiscing sapien faucibus et."
});
db.posts.insert ({
	ts : new Date(2012, 2, 20, 12, 0, 0, 0),
	author: "admin",
	img : "http://lorempixel.com/100/100/people/3/",
	type: "public",
	title : "Title sample 6",
	content: "Aliquam lacus mi, porta sed ipsum ut, venenatis pharetra risus. Phasellus interdum posuere placerat. Aenean nibh tortor, dictum ac faucibus ut, interdum et justo. In porttitor iaculis iaculis. In euismod ut neque id sodales. Ut viverra ultricies dolor, nec laoreet enim laoreet vitae. Nam felis sem, convallis vitae luctus eget, imperdiet vitae dui. Praesent dapibus eros sit amet erat pulvinar consequat. In hac habitasse platea dictumst."
});
db.posts.insert ({
	ts : new Date(2012, 2, 21, 12, 0, 0, 0),
	author: "user2",
	img : "http://lorempixel.com/100/100/people/2/",
	type: "public",
	title : "Title sample 7",
	content: "Etiam hendrerit pretium vestibulum. Vivamus pharetra augue sed turpis dapibus vulputate. Pellentesque malesuada iaculis fringilla. Nam mattis scelerisque iaculis. Maecenas tincidunt tellus lacus, eu sagittis orci mattis sed. Maecenas lacinia nunc ante, nec tincidunt diam iaculis eu. Morbi bibendum tortor vel leo lobortis elementum non vel nibh. Morbi lorem enim, tincidunt in fermentum a, cursus ut ante. Phasellus euismod consequat nulla ac commodo. Morbi sit amet metus convallis, blandit magna sed, euismod ipsum. Morbi ultrices velit libero, eget laoreet tortor sagittis sit amet."
});

db.posts.insert ({
	ts : new Date(2012, 2, 28, 9, 50, 0, 0),
	author: "admin",
	img : "http://lorempixel.com/100/100/people/3/",
	type: "public",
	title : "Admin notification, long message, read all!",
	content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam varius nisi et lectus tempus congue. Mauris vel neque tortor. In dignissim lorem eget arcu gravida tincidunt iaculis quis est. Donec sit amet justo nisl. Etiam eget ultricies tortor, sit amet porta velit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin facilisis arcu vestibulum, dictum velit vel, ultricies neque.<br/><br/>Pellentesque sed venenatis massa, sed pellentesque lorem. Suspendisse et semper diam. Sed massa leo, fringilla id egestas id, gravida tincidunt libero. Morbi mollis neque ac aliquet hendrerit. Donec ac sem et neque pharetra blandit non in ipsum. Mauris consequat pharetra ultrices. Duis porttitor diam vel bibendum dapibus. Mauris dapibus orci sapien, et pharetra justo venenatis nec. Nam mollis ornare enim eu consectetur. Aliquam et tortor tellus. Nam et suscipit purus.<br/><br/>Nam ac elit eu dolor euismod fermentum sed id dui. In hac habitasse platea dictumst. Nullam laoreet ac orci id pellentesque. Pellentesque eget auctor libero. Suspendisse tempor nisl eget nulla sagittis iaculis id eget est. Pellentesque aliquam eget dui sed auctor. Pellentesque vitae mauris faucibus metus gravida ullamcorper ac eget urna. In commodo nibh et pulvinar euismod. Phasellus odio leo, scelerisque vel malesuada at, tincidunt eu neque. Aliquam pellentesque lorem ac leo blandit, in posuere tortor dictum. Etiam ut consequat orci. Vestibulum volutpat eros velit, nec viverra mauris viverra dapibus. In hac habitasse platea dictumst.<br/><br/>Nunc eu neque eget ligula placerat tincidunt. Nulla volutpat non massa sed tristique. Sed sed dui id elit imperdiet condimentum id eget mi. Nullam id justo a ipsum ullamcorper porta eget vel nulla. Duis est purus, semper et tortor sit amet, convallis placerat tortor. Nulla vestibulum diam sed quam rutrum lobortis. Ut vitae euismod purus, vitae mollis orci. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Quisque pharetra orci semper ipsum pharetra laoreet.<br/><br/>Nullam varius malesuada ligula, sed cursus risus varius quis. Donec a dolor vitae nibh consequat semper. Suspendisse gravida ultrices ipsum, ac tincidunt est. Nulla facilisis molestie interdum. Aliquam ut quam placerat, faucibus lorem at, scelerisque elit. Morbi condimentum pulvinar est a scelerisque. Vestibulum molestie fermentum nisl, id convallis ligula pretium quis. Aenean aliquet tempor libero sit amet aliquam. Maecenas tristique viverra mauris. Suspendisse ac nisi ac mi fermentum feugiat et non lectus. In ut consectetur mauris. Morbi et tempor augue."
});

// Creates some private data for the users
db.posts.insert ({
	author: "user1",
	img : "http://lorempixel.com/100/100/people/1/",
	ts : new Date(2012, 2, 21, 20, 0, 0, 0),
	type: "registered",
	title: "Ukrainian president ousted, US church prays for peace",
	content: "St. Petersburg, Florida residents with ties to Ukraine are hopeful that the ousting of President Viktor Yanukovych will bring peace to the country.",
	comments: []
});

db.posts.insert ({
	author: "user1",
	img : "http://lorempixel.com/100/100/people/1/",
	ts : new Date(2012, 2, 21, 10, 0, 0, 0),
	type: "registered",
	title: "Egyptian cabinet resigns, paving way for military chief to run for president",
	content: "CAIRO - Egyptian Prime Minister Hazem el-Beblawi announced Monday that his cabinet was resigning, marking yet another abrupt shift in a nation that has been wracked by insurgency and political and economic uncertainty.",
	comments: []
});

db.posts.insert ({
	author: "user2",
	img : "http://lorempixel.com/100/100/people/2/",
	ts : new Date(2012, 2, 21, 12, 30, 0, 0),
	type: "registered",
	title: "WhatsApp to go big on voice communications in 2nd quarter",
	content: "The text-messaging startup, which Facebook is buying for $19 billion, will introduce voice communications to its 465 million users, in its quest to exceed a billion users.",
	comments: []
});

db.posts.insert ({
	author: "admin",
	img : "http://lorempixel.com/100/100/people/3/",
	ts : new Date(2012, 2, 21, 12, 0, 0, 0),
	type: "registered",
	title: "BlackBerry to offer BBM for Windows, Nokia X",
	content: "BARCELONA, SPAIN—BlackBerry Ltd (TSX:BB) says it will make its BBM text messaging system available to two more types of smartphones, those running the Windows operating system and the Nokia X platform.",
	comments: []
});

	