// Templates for dynamic use

var templateTextLite = '<div>This template just has some text, and no controller</div>';


var templateLibrary = [
	{
		"name" : "templateText",
		"longname" : "Template with a simple text",
		"html" : "<div><p>(LIB) :: This template just has some text, and no controller</p></div>"
	},
	{
		"name" : "templateImage",
		"longname" : "Template with an image",
		"html" : "<div><p>(LIB) :: Image below</p><img src='http://i.imgur.com/rZ9KzEG.gif'></img></div>"	
	},
	{
		"name" : "templateLink",
		"html" : "<div><a href='http://www.google.es'>Go to Google</a></div>"
	},
	{
		"name" : "compareModelAndBind",
		"longname": "ng-model vs ng-bind",
		"html" : "<input ng-model='nombre'></input><p ng-bind='nombre'></p>" +
				 "<button ng-click='enviar(nombre)'>Nombre: {{nombre}}</button>"
	},
	{
		"name" : "localJsonLoad",
		"longname" : "Load Json locally",
		"controller" : "jsonLoadController",
		"html" : "<div>"
			+ "<input ng-model='path'></input>"
			+ "<button ng-click='load(path)'>Load {{path}}</button>"
			+ "<p>{{data}}</p>"
			+ "<div ng-repeat='item in data'>"
			+ "<button ng-click='loadIndex(path, $index)'>Load {{$index}}</button>"
			+ "</div>"
			+ "<div> HTML content"
			+ "<p ng-bind-html='firstDataHtml'></p>"
			+ "</div>"
			+ "</div>"
	}
];