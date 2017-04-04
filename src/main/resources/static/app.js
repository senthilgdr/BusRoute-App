var app = angular.module('myApp',["ngRoute","ui.bootstrap"]);
app.constant("config", {
	apiUrl : "http://localhost:5000",
	baseUrl: '/'
});
app.config(function($routeProvider) {
    $routeProvider    
    .when("/routes", {
        templateUrl : "_partials/routes.html",
        controller : "RouteController"
    })
    .when("/boarding_points", {
        templateUrl : "_partials/boarding_points.html",
        controller : "RouteBoardingPointController"
    })
     .when("/routes_stats", {
        templateUrl : "_partials/routes_stats.html",
        controller : "RouteStatsController"
    })
     .when("/route_capacity", {
        templateUrl : "_partials/route_capacity.html",
        controller : "RouteCapacityController"
    })
    .when("/students", {
        templateUrl : "_partials/students.html",
        controller : "StudentController"
    })
    .when("/student_routes", {
        templateUrl : "_partials/students_routes.html",
        controller : "StudentRouteController"
    })
    .when("/login", {
        templateUrl : "_partials/login.html"        
    })
    .when("/register", {
        templateUrl : "_partials/register.html"        
    }).
    otherwise({
    	templateUrl : "_partials/login.html"
    });
    
});
    
app.controller('MainController' , function($scope){
	console.log("MainController");
	
	
});

app.controller('HeaderController' , function($rootScope, $scope, $location){
	console.log("HeaderController");
	
	if ( $rootScope.loggedInUser == null ) {
		var user = localStorage.getItem("LOGGED_IN_USER");
		if (user!=null ) {
			console.log("Set LocalStorage LoggedInUser details in rootScope");
			$rootScope.loggedInUser = JSON.parse(user);
		}
	}	

	$scope.logout = function() {
		console.log("Logout");
		localStorage.clear();
		$rootScope.loggedInUser = null;
		$location.path("/");
	}
	
	
});
app.controller('AuthController' , function($rootScope, $scope , $http , config, $location){
	console.log("AuthController");
		
	$scope.login = function() {
		var user = $scope.user;
		$scope.errorMessage = null;		
		$http.post(config.apiUrl + "/auth/login", JSON.stringify(user) ).then ( function ( response){
			console.log("Login Response:" + JSON.stringify(response.data));			
			if ( response.data != null && response.data != ""){
				var loggedInUser = response.data;
				$rootScope.loggedInUser = loggedInUser;
				localStorage.setItem("LOGGED_IN_USER", JSON.stringify(loggedInUser));
				$scope.user = null;
				$location.path("/student_routes");
			}
			else
			{
				$scope.errorMessage = "Error - Invalid Email/Password !!!";
				console.log("Error - Unable to login !!!");
			}
		});
		
	}
	
	$scope.register = function() {
		var user = $scope.user;
		console.log("REgister user: " + JSON.stringify(user));
		$scope.errorMessage = null;
		
		$http.post(config.apiUrl + "/auth/register", user ).then ( function ( response){
			console.log("register Response:" + JSON.stringify(response) );			
			if ( response.status == 201  ){ // 201 refers CREATED status
				$scope.user= null;
				$location.path("/login");
			}
			else
			{
				$scope.errorMessage = "Error - Unable to register !!!";
				console.log("Error - Unable to register !!!");
			}
		}).catch(function(){
			$scope.errorMessage = "Error - Unable to register !!!";
			console.log("catch block - Error - Unable to register !!!");
		});
		
	}
	
});

app.controller("RouteController", function( $scope, config, $http){	

	$scope.init = function() {
		
		getAllRouteStats();
	}
	
function getAllRouteStats(){
		
		$http.get(config.apiUrl + "/boardingdetails/routestats").then(function(response){
			$scope.routedetails = response.data;
			localStorage.setItem("ROUTE_DETAILS", JSON.stringify(response.data));
			//console.log(JSON.stringify(response));
		});
	}
	
	function getAllRoutes(){
		
		$http.get(config.apiUrl + "/routes").then(function(response){
			$scope.routedetails = response.data;
			localStorage.setItem("ROUTE_DETAILS", JSON.stringify(response.data));
			//console.log(JSON.stringify(response));
		});
	}
	
	$scope.loadRoutes = function(){
		console.log("LoadRoutes:" + $scope.route_no);
		var route_no = $scope.route_no;
		if ( route_no != null ) {
			$scope.routedetails = _.where( $scope.routedetails , {"route_no": route_no});
		}
		else
		{
			$scope.routedetails  = JSON.parse(localStorage.getItem("ROUTE_DETAILS"));
		}
	}
	
	$scope.getNoOfStudents = function(route_id) {
		
		var totalStudents = 0;
		/*$http.get("json/students_routes.json").then(function(response){
			var students = response.data;
			totalStudents = _.where( students , {"route_id" : route_id}).length;
			console.log("Total Students for route: " + route_id + ", count= " + totalStudents );
			//console.log(JSON.stringify(response));
		});*/
		
		return totalStudents;
		
	}
	
});



app.controller("RouteBoardingPointController", function( $scope, config, $http){	

	$http.get( config.apiUrl + "/boardingdetails").then(function(response){
		var boardingPoints = response.data;
		
		localStorage.setItem("BOARDING_DETAILS", JSON.stringify(response.data));
		//console.log(JSON.stringify(response));
		$scope.routedetails = boardingPoints;
	});
	
	$scope.loadRoutes = function(){
		console.log("LoadRoutes:" + $scope.route_no);
		var route_no = $scope.route_no;
		if ( route_no != null ) {
			$scope.routedetails = _.where( $scope.routedetails , {"route_no": route_no});
		}
		else
		{
			$scope.routedetails  = JSON.parse(localStorage.getItem("ROUTE_DETAILS"));
		}
	}
	
	$scope.getNoOfStudents = function(route_id) {
		
		var totalStudents = 0;
		/*$http.get("json/students_routes.json").then(function(response){
			var students = response.data;
			totalStudents = _.where( students , {"route_id" : route_id}).length;
			console.log("Total Students for route: " + route_id + ", count= " + totalStudents );
			//console.log(JSON.stringify(response));
		});*/
		
		return totalStudents;
		
	}
	
});

app.controller("RouteStatsController", function( $scope, config, $http){

	$scope.init = function () 
	{
		loadStudentRoutes();
		
	}
	function loadStudentRoutes(){
		
		
		$http.get( config.apiUrl + "/boardingdetails").then(function(response){
			$scope.boardingdetails = response.data;
		});
		
		var boardingDetailsStats ={};
		
		var studentsCountMap = {};
		$http.get( config.apiUrl + "/userboardingdetails/boardingstats").then(function(response){
			var boardingDetailsStats = response.data;
			/*for (var i in userboardingstats) {
				var obj = userboardingstats[i];
				boardingDetailsStats[obj.boarding_id] = obj.no_of_students;
				
			}
			*/
			$scope.boardingDetailsStats = boardingDetailsStats;			
			
			console.log(JSON.stringify(boardingDetailsStats));		
		});
	}	
	
	
	
});


app.controller("RouteCapacityController", function( $scope, config,  $http){

	$scope.init = function () 
	{
		loadStudentRoutes();
		
	}
	function loadStudentRoutes(){
		
		
		$http.get( config.apiUrl + "/routes").then(function(response){
			$scope.routes = response.data;
		});
		
		
		$http.get( config.apiUrl + "/userboardingdetails/routestats").then(function(response){
			var boardingDetailsStats = response.data;			
			$scope.boardingDetailsStats = boardingDetailsStats;
			console.log(JSON.stringify(boardingDetailsStats));		
		});
	}	
		
	
	
});

app.controller("StudentController", function( $scope, config, $http){	

	$http.get(config.apiUrl + "/users").then(function(response){
		$scope.students = response.data;
		console.log(JSON.stringify(response));
		localStorage.setItem("USER_DETAILS", JSON.stringify(response.data));
		//console.log(JSON.stringify(response));
	});
	
	$http.get(config.apiUrl + "/userboardingdetails").then(function(response){
		$scope.student_routes = response.data;
		localStorage.setItem("USER_BOARDING_DETAILS", JSON.stringify(response.data));
		//console.log(JSON.stringify(response));
	});
	
	$scope.getRouteDetails = function(routeId) {
		var routes =  JSON.parse(localStorage.getItem("ROUTE_DETAILS"));
		var route = _.where(routes, {"id" : 1})[0];
		return "Route:" + route.route_no + ", Pickup Point- " + route.name + "( " + route.pickup_time + ")";
		
	}
	
	
	
});

app.controller("StudentRouteController", function( $scope, config, $http){	

	$http.get(config.apiUrl + "/users").then(function(response){
		$scope.students = response.data;
		localStorage.setItem("USER_DETAILS", JSON.stringify(response.data));
		//console.log(JSON.stringify(response));
	});
	
	$http.get(config.apiUrl + "/userboardingdetails").then(function(response){
		var student_routes = response.data;
		localStorage.setItem("USER_BOARDING_DETAILS", JSON.stringify(response.data));
		var uniqueRoutes = _.where( student_routes, {})
		//console.log(JSON.stringify(response));
		
		$scope.student_routes = student_routes;
	});
	
	$scope.getRouteDetails = function(routeId) {
		var routes =  JSON.parse(localStorage.getItem("ROUTE_DETAILS"));
		var route = _.where(routes, {"id" : routeId})[0];
		return "Route:" + route.route_no + ", Pickup Point- " + route.name + "( " + route.pickup_time + ")";
		
	};
	
	$scope.getStudentName = function(id) {
		console.log("getStudentName: " + id);
		var students =  JSON.parse(localStorage.getItem("STUDENT_DETAILS"));		
		console.log("students"  + JSON.stringify(students));
		
		var student = _.where(students, {"id" : id})[0];
		
		return  student !=null ? student.name : "";
		
	};
});
