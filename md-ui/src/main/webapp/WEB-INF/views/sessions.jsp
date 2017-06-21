<%@ taglib prefix="security"
	   uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	        					<style>
            					body.container-fluid{
                                    padding-left: 0px;
                                    padding-right: 0px;
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
                                                .navbar.navbar-inverse {
                                                    height:60px;
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
                                                .side-container{
                                                   padding-left:75px;
                                                }
    </style>
	<title><spring:message code="common.page.title_bdre_1"/></title>
	<script>
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	  //Please replace with your own analytics id
	  ga('create', 'UA-72345517-1', 'auto');
	  ga('send', 'pageview');
	</script>

	<!-- Include one of jTable styles. -->

	<link href="../css/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="../css/jtables-bdre.css" rel="stylesheet" type="text/css" />
	<link href="../css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
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

	<!-- Include jTable script file. -->
	<script src="../js/jquery.min.js" type="text/javascript"></script>
	<script src="../js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
	<script src="../js/jquery.jtable.js" type="text/javascript"></script>
    <script src="../js/bootstrap.js"></script>

   <script type="text/javascript">
                var workspace="";
                function findWorkspace() {
                    var location=window.location.href;
                    console.log(window.location.href);
                    var res = location.split("/");
                    for (var i in res) {
                     if(res[i].includes("mdui")==true)
                        {
                         workspace=res[i];
                         console.log(workspace);
                        }
                    }

                  if(workspace!="mdui")
                  $('#logout').append(" from "+workspace.replace("mdui_"+""));
                }
                window.onload = findWorkspace;
                </script>

	<script type="text/javascript">
		    $(document).ready(function () {
	    $('#Container').jtable({
	    title: '<spring:message code="sessions.page.title_list"/>',
		    paging: true,
		    pageSize: 10,
		    sorting: true,
		    actions: {
		    listAction: function (postData, jtParams) {
		    console.log(postData);
			    return $.Deferred(function ($dfd) {
			    $.ajax({
			    url: '../../auth/bdre/security/admin/sessions',
				    type: 'GET',
				    data: postData,
				    dataType: 'json',
				    success: function (data) {
				    $dfd.resolve(data);
				    },
				    error: function () {
				    $dfd.reject();
				    }
			    });
			    });
		    },
		   deleteAction: function (item) {
           			    console.log(item);
           			    return $.Deferred(function ($dfd) {
           				$.ajax({
           				    url: '../../auth/bdre/security/admin/logout/' + item.authToken,
           				    type: 'DELETE',
           				    data: item,
           				    dataType: 'json',
           				    success: function (data) {
           					$dfd.resolve(data);
           				    },
           				    error: function () {
           					$dfd.reject();
           				    }
           				});
           			    });
            		    }

		    },
            fields: {
            username: {
            title: '<spring:message code="sessions.page.title_username"/>',
            display:function(data){
            return data.record.user.username;
            }
            },
			startTime: {
			title: '<spring:message code="sessions.page.title_start_time"/>',
			display:function(data){
			console.log(data);
			return (new Date(data.record.startTime).toLocaleString());
			}
			},
			expiration: {
			title: '<spring:message code="sessions.page.title_exp_time"/>',
			display:function(data){
			return (new Date(data.record.expiration).toLocaleString());
			}
			},
            authToken: {
            key: true,
            title: '<spring:message code="sessions.page.title_id"/>',
            }
            }
            });
		    $('#Container').jtable('load');
	    });
	</script>
    </head>
    <body>
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
                                    <a  href="streamingmessage.page"><i class="mdi mdi-24px mdi-forum"></i><span>View Messages</span></a>
                                 </li>
                                                       <li>
                                                         <a  href="premessageconfig.page"><i class="mdi mdi-24px mdi-forum"></i><span>Create Messages</span></a>
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
                                                          <a  href="sessions.page"><i class="mdi mdi-24px mdi-watch"></i><span>Sessions</span></a>
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
                                                                 <li><a href="/auth/bdre/security/logout" id="logout">Logout <security:authentication property="principal.username"/></a></li>
                                                             </ul>
                                                         </li>
                                                     </ul>
                                                 </div>
                                                 <!-- /.navbar-collapse -->

                                             <!-- /.container-fluid -->
                                         </nav>

    <section style="width:100%;text-align:center;">
	<div id="Container"></div>
    </section>
</div>

</body>
</html>