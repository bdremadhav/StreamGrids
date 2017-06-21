<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="security"
	   uri="http://www.springframework.org/security/tags" %>
	   <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
        <html id="ng-app">

        <head>
            <title>Streaming Analytics Platform</title>
	<script>
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	  //Please replace with your own analytics id
	  ga('create', 'UA-72345517-1', 'auto');
	  ga('send', 'pageview');
	</script>

	<!-- Jquery Core Js -->
        <script src="../MaterialDesign_css/jquery.min.js"></script>

        <script src="../js/bootstrap.js"></script>

        <!-- Slimscroll Plugin Js -->
        <script src="../MaterialDesign_css/jquery.slimscroll.js"></script>

        <!-- Waves Effect Plugin Js -->
        <script src="../MaterialDesign_css/waves.js"></script>

        <!-- Jquery CountTo Plugin Js -->
        <script src="../MaterialDesign_css/jquery.countTo.js"></script>

        <!-- Morris Plugin Js -->
        <script src="../MaterialDesign_css/raphael.min.js"></script>
        <script src="../MaterialDesign_css/morris.js"></script>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link rel="stylesheet" href="../css/css/bootstrap.min.css" />
            <link rel="stylesheet" href="../css/bootstrap.custom.css" />
            <!--<link rel="stylesheet" href="../css/css/materialstyle.css" />-->
            <link rel="stylesheet" href="../css/submenu.css" />
            <script src="../js/angular.min.js"></script>
            <link href="../StreamAnalytix_files/materialdesignicons.min.css" media="all" rel="stylesheet" type="text/css">
            <link href="../StreamAnalytix_files/bootstrap.min.css" rel="stylesheet">
            <link href="../StreamAnalytix_files/bootstrap-material-design.min.css" rel="stylesheet">
            <link href="../StreamAnalytix_files/ripples.min.css" rel="stylesheet">
            <link href="../StreamAnalytix_files/sax-fonts.css" class="include" rel="stylesheet" type="text/css">
            <link href="../StreamAnalytix_files/toastr.min.css" rel="stylesheet">
            <link href="../StreamAnalytix_files/datatables.min.css" rel="stylesheet">
            <link href="../StreamAnalytix_files/theme.css" rel="stylesheet" type="text/css">
            <link href="../StreamAnalytix_files/style.css" rel="stylesheet" type="text/css">
            <link href="../StreamAnalytix_files/select2.4.0.css" rel="stylesheet">
            <link href="../StreamAnalytix_files/select2-bootstrap.css" rel="stylesheet">

                <!-- Favicon-->
                <link rel="icon" href="favicon.ico" type="image/x-icon">

                <!-- Google Fonts -->
                <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext" rel="stylesheet" type="text/css">
                <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">




            <style>
            .side-container{
                   padding-left:75px;
                }
				body {
					overflow: visible;
				}
				
				#footer {
					background: #f5f5f5;
					border-top: 1px solid #EDE4BF;
					bottom: 0;
					left: 0;
					position: fixed;
					right: 0;
				}
				
				.activs, .activs:hover {
					border-left: 3px solid #f91;
					font-weight: bold;
					padding-left: 17px;
					color: black;
					margin-left: 0;
				}
				
				.activ, .activ:hover {
					/* border-left: 3px solid #f91; */
					font-weight: bold;
					padding-left: 17px;
					color: black;
					margin-left: 0;
				}
				.navbar-inverse .navbar-nav>li>a {
                    color: #FFFFFF;
                    font-weight
                }
				
				#foot {
					background: #2F4F4F;
				}
				
				.navbar-default .navbar-nav>.open>a, .navbar-default .navbar-nav>.open>a:hover,
					.navbar-default .navbar-nav>.open>a:focus {
					background-color: LightSalmon;
					font-weight: bold;
				}
				
				.level1, .level2, .level3 {
					font-style: normal;
				}
				
				.B1, .B2 {
					font-weight: bold;
					border-left: 3px solid #f91;
				}
				
				.sideheight {
					height: 63%;
				}
				
				.sideimg {
					width: 11px;
					left: 23px;
					top: 40%;
					height: 30px;
					padding: 0;
				}
				
				.left {
					height: 97%;
				}
				
				.headerbor {
					border-bottom: 1px solid #EDEDED;
				}
				
				::-webkit-scrollbar {
					width: 8px;
				}
				
				::-webkit-scrollbar-track {
					-webkit-border-radius: 5px;
					border-radius: 5px;
					background: rgba(0, 0, 0, 0.02);
				}
				
				::-webkit-scrollbar-thumb {
					-webkit-border-radius: 5px;
					border-radius: 5px;
					background: rgba(0, 0, 0, 0.02);
				}
				
				::-webkit-scrollbar-thumb:hover {
					background: rgba(0, 0, 0, 0.4);
				}
				
				::-webkit-scrollbar-thumb:window-inactive {
					background: rgba(0, 0, 0, 0.0);
				}
				
				.col-bdre-collapsed {
					width: 2px;
					position: relative;
					min-height: 1px;
					padding-right: 15px;
					padding-left: 15px;
					float: left;
				}
				
				.bdre-full-body {
					width: 100% !important;
				}
				
				/* HEADER and NAV-BAR*/
				.input-sm {
					width: 250px !important;
				}
				
				.usericon {
					display: block;
					width: 30px;
					height: 30px;
					border-radius: 80px;
					background: #FFF no-repeat center;
					background-image: url("../css/images/user_icon.png");
					background-size: 65% 65%;
				}
				
				.bdretextlogo {
					color: #FFFFFF;
					position: relative;
					font-size: 2em;
					top: 11px;
					right: 10px;
				}
				
				.dropdown-toggle {
					padding-top: 9px !important;
				}
				.dropdown-menu {
				position:initial;
				}
				body.container-fluid {
                    padding-left: 0px;
                    padding-right: 0px;
                }


                .list-group.list-group-root {
                    padding: 0;
                    overflow: hidden;
                }

                .list-group.list-group-root .list-group {
                    margin-bottom: 0;
                }

                .list-group.list-group-root .list-group-item {
                    border-radius: 0;
                    border-width: 1px 0 0 0;
                    font-size: 18px;
                }

                .list-group.list-group-root > .list-group-item:first-child {
                    border-top-width: 0;
                }

                .navbar.navbar-inverse {
                    height: 60px;
                }


			</style>
		</head>

        <body ng-app="myApp" ng-controller="myCtrl">
         <div class="sidebar-wrapper">

                             <div class="logo-wrap">
                                 <a id="" href="content.page">
                                     <img src="../StreamAnalytix_files/logo.png" border="0" width="30px">
                                     <span><img src="../StreamAnalytix_files/sax.png" border="0" width="200px"></span>
                                 </a>
                             </div>

                             <ul>
                                 <li>
                                    <a  href="connections.page?type=source"><i class="mdi mdi-24px mdi-login"></i><span>Source Configuration</span></a>
                                 </li>

                                 <li>
                                    <a href="connections.page?type=persistance"><i class="mdi mdi-24px mdi-logout"></i><span>Sink Configuration</span></a>
                                 </li>

                                  <li>
                                    <a  href="premessageconfig.page"><i class="mdi mdi-24px mdi-forum"></i><span>Messages</span></a>
                                 </li>

                                 <li>
                                   <a href="wfdesigner2.page"><i class="mdi mdi-24px mdi-steam"></i><span>Workflow Creator</span></a>
                                 </li>

                                 <li>
                                    <a  href="process.page"><i class="mdi mdi-24px mdi-webhook"></i><span>Manage Workflows</span></a>
                                 </li>

                                 <li>
                                    <a  href="users.page"><i class="mdi mdi-24px mdi-account-settings-variant"></i><span>Security</span></a>
                                 </li>

                                  <li>
                                     <a  href="sessions.page"><i class="mdi mdi-24px mdi-forum"></i><span>Sessions</span></a>
                                  </li>

                                  <li>
                                     <a  href="settings.page"><i class="mdi mdi-24px mdi-tune"></i><span>Settings</span></a>
                                  </li>

                           </ul>
                         </div>
                        <div class="side-container">
                                 <nav class="navbar navbar-inverse">
                                     <div class="container-fluid">
                                         <!-- Brand and toggle get grouped for better mobile display -->
                                         <div class="navbar-header">
                                             <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                                                 <span class="sr-only">Toggle navigation</span>
                                                 <span class="icon-bar"></span>
                                                 <span class="icon-bar"></span>
                                                 <span class="icon-bar"></span>
                                             </button>

                     						<span class="bdretextlogo"></span>
                                         </div>

                                             <ul class="nav navbar-nav navbar-right" >
                                                 <li class="dropdown user-icon-style"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><span class="usericon"></span></a>
                                                     <ul class="dropdown-menu" role="menu">
                                                         <li><a href="/auth/bdre/security/logout">Logout <security:authentication property="principal.username"/></a></li>
                                                     </ul>
                                                 </li>
                                             </ul>
                                         </div>
                                         <!-- /.navbar-collapse -->

                                     <!-- /.container-fluid -->
                                 </nav>
                </div>
                <!-- /.container-fluid -->
            </nav>


            <div>
                <iframe id="dframe" src="welcome.page" scrolling="yes" style="overflow-y:visible;width:100%; height: 800px; border: none;"></iframe>

            </div>

     <!--
	<div id="foot" class="row">
	    div id="footer" class="footer navbar-fixed-bottom"
		<p class="text-center"><ul id="listf" class="list-inline"><li ><a class="btn btn-xs" href="">Terms of use</a> </li><li> <a class="btn btn-xs" href="">Liceelnse</a> </li><li> <a class="btn btn-xs" href="">About BDRE</a> </li><li> <a class="btn btn-xs" href="">Help</a> </li><li><a class="btn btn-xs" href="">Site Map</a> </li><li> <a  class="btn btn-xs" href="">About Wipro</a></li>
		    </p>
	    </div>
	</div>
	 -->
            <script>
                var app = angular.module("myApp", []);
                app.controller("myCtrl", function ($scope) {
                            $scope.left = 0;
                            $scope.down = 1;
                            $scope.linearMenu = [];
                            $scope.createLinearMenu = function (arg) { //arg structure would be [{},{}...]
                                if (arg.length == null) {
                                    $scope.createLinearMenu([arg]);
                                } else {
                                    for (var i = 0; i < arg.length; i++) {
                                        if (arg[i].children.length == 0) {
                                            console.log(arg[i].label);
                                            $scope.linearMenu.push({
                                                label: arg[i].label,
                                                url: arg[i].url
                                            })
                                        } else {
                                            for (var j = 0; j < arg[i].children.length; j++) {
                                                $scope.createLinearMenu(arg[i].children[j]);
                                            }
                                        }
                                    }
                                }
                            }
                            $scope.menu = [{
                                                            label: "Configuration Management",
                                                            collapse: "1",
                                                            children: [{
                                                               label: "Source Configuration",
                                                               collapse: "1",
                                                               url: "connections.page?type=source",
                                                               children: []
                                        }, {
                                                               label: "Emitter Configuration",
                                                               collapse: "1",
                                                               url: "connections.page?type=emitter",
                                                               children: []
                                        }, {
                                                               label: "Persistence Configuration",
                                                               collapse: "1",
                                                              url: "connections.page?type=persistance",
                                                               children: []
                                        }]
                                        },
                                           {

                                                            label: "Messages",
                           									collapse: "1",
                           									children: [
                           									 {
                           									 label: "View Messages",
                                                             collapse: "1",
                                                             url: "streamingmessage.page",
                                                             children: []
                                                             },
                           									{
                                                         label: "Create Messages",
                                                         collapse: "1",
                                                         url: "premessageconfig.page",
                                                         children: []
                                                         }

                           									]
                           				},{
                           								   label: "Workflow Creator",
                           									collapse: "1",
                           									url: "wfdesigner2.page",
                           									children: []
                           				},{
                                                            label: "Manage Workflows",
                           									collapse: "1",
                           									url: "process.page",
                           									children: []
                           				},

                           				                                <security:authorize access = "hasRole('ROLE_ADMIN')"> {
                                                                                label: "Administration",
                                                                                collapse: "1",
                                                                                children: [{
                                                                                        label: "Security",
                                                                                        collapse: "1",
                                                                                        url: "users.page",
                                                                                        children: []
                                        					}, {
                                                                                        label: "Sessions",
                                                                                        collapse: "1",
                                                                                        url: "sessions.page",
                                                                                        children: []
                                        					}, {
                                                                                        label: "Settings",
                                                                                        collapse: "1",
                                                                                        url: "settings.page",
                                                                                        children: []
                                        					}
                                        					]
                                                                            },
                                                                            </security:authorize>
                                            ];
            $scope.createLinearMenu($scope.menu); //For creating linear menu
			$scope.openlink = function (event, url) {
                                    $(".activ").removeClass("activ");
                                    $(".B1").removeClass("B1");
                                    $(".B2").removeClass("B2");
                                    console.log($(event.target).parent().parent().parent(), $(event.target).hasClass("level1"));
                                    if ($(event.target).hasClass("level1")) {
                                        console.log("level1");
                                        $(event.target).addClass("activ");
                                    } else if ($(event.target).hasClass("level2")) {
                                        console.log("level2");
                                        $(event.target).parent().parent().parent().addClass("activ");
                                        $(event.target).addClass("B1");
                                        console.log($(event.target));
                                    } else if ($(event.target).hasClass("level3")) {
                                        console.log("level3");
                                        $(event.target).addClass("B2");
                                        var elem = $(event.target).parent().parent().parent();
                                        elem.addClass("B1");
                                        elem.parent().parent().addClass("activ");
                                    }
                                    if (url != "") {
                                        $("#dframe").attr('src', url);
                                        console.log(url, url != "");
                                    } else
                                        alert("TBD")
			};
			$scope.reset = function (target, index) {
                                    //console.log(target,index);
                                    if (index.children.length == 0) {
                                        $(".activ").removeClass("activ");
                                        $(target).addClass("activ");
                                        if (index.url != "") {
                                            var n = index.url.indexOf("/");
                                            if (n == 0) {
                                                document.location = index.url;
                                            } else {
                                                $("#dframe").attr('src', index.url);
                                                console.log(index.url, index.url != "");
                                            }
                                        } else
                                            alert("TBD")
                                    }
			};
                            });
            </script>
        </div>
        </body>

        </html>
