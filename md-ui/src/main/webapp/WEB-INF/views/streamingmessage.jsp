<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	   uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>

          div.jtable-main-container>table.jtable>tbody>tr.jtable-data-row>td:nth-child(2){
                color: #F75C17;
                font-size: 24px;
                font-weight: 500;
            }
            div.jtable-main-container>table.jtable>tbody>tr.jtable-data-row>td img {
                width: 15px;
                height: 15px;
            }

        .label-icons {
                    margin: 0 auto;
                    width: 15px;
                    height: 15px;
                    background-size: 100% !important;
                    display: block;
                    background-repeat: no-repeat !important;
                    background-position: center !important;
                }
                .label-properties {
                    background: url('../css/images/subprocess-rarrow.png') no-repeat center;
                }


        </style>

        <script src="../js/jquery.steps.min.js" type="text/javascript"></script>
		<script src="../js/jquery.min.js" type="text/javascript" ></script>
		<script src="../js/bootstrap.js" type="text/javascript"></script>
        <script src="../js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
        <script src="../js/jquery.jtable.js" type="text/javascript"></script>
        <script src="../js/angular.min.js" type="text/javascript"></script>

		<link href="../css/jtables-bdre.css" rel="stylesheet" type="text/css" />
		<link href="../css/jquery-ui-1.10.3.custom.css" rel="stylesheet">
		<link href="../css/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="../css/jquery.steps.css" />
        <link rel="stylesheet" href="../css/jquery.steps.custom.css" />
        <link href="../css/bootstrap.custom.css" rel="stylesheet" />







 <script type="text/javascript">
     		    $(document).ready(function () {
     	    $('#Container').jtable({
     	    title: 'Message List',
     		    paging: true,
     		    pageSize: 10,
     		    sorting: true,
     		    actions: {
     		    listAction: function (postData, jtParams) {
     		    console.log(postData);
     			    return $.Deferred(function ($dfd) {
     			    $.ajax({
     			    url: '/mdrest/message?page=' + jtParams.jtStartIndex + '&size='+jtParams.jtPageSize,
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
     			    }
     		    },
     		    fields: {
     		    messagename: {
     		        key : true,
     			    list: true,
     			    create:true,
     			    edit: false,
     			    title: 'Message Name'
     		    },
     		     format:{
                     key : true,
                     list: true,
                     create:true,
                     edit: false,
                     title: 'File Format'
                 },

                   connectionName: {
                     key : true,
                     list: true,
                     create:true,
                     edit: false,
                     title: 'Connection Name'
                 },

     			    Properties: {
                     title: 'Schema',
                     width: '5%',
                     sorting: false,
                     edit: false,
                     create: false,
                     listClass: 'bdre-jtable-button',
                     display: function(item) { //Create an image that will be used to open child table

                         var $img = $('<span class="label-icons label-properties"></span>'); //Open child table when user clicks the image

                         $img.click(function() {
                             $('#Container').jtable('openChildTable',
                                 $img.closest('tr'), {
                                     title: ' <spring:message code="process.page.title_properties_of"/>'+' ' + item.record.messagename,
                                     paging: true,
                                     pageSize: 10,
                                     actions: {
                                         listAction: function(postData,jtParams) {
                                             return $.Deferred(function($dfd) {
                                                 console.log(item);
                                                 $.ajax({
                                                     url: '/mdrest/message/' + item.record.messagename+'?page=' + jtParams.jtStartIndex + '&size='+jtParams.jtPageSize,
                                                     type: 'GET',
                                                     data: item,
                                                     dataType: 'json',
                                                     success: function(data) {
                                                        if(data.Result == "OK") {

                                                            $dfd.resolve(data);

                                                        }
                                                        else
                                                        {
                                                         $dfd.resolve(data);
                                                        }
                                                    },
                                                     error: function() {
                                                         $dfd.reject();
                                                     }
                                                 }); ;
                                             });
                                         }
                                     },
                                     fields: {

                                         columnName: {
                                             key: true,
                                             list: true,
                                             create: false,
                                             edit: true,
                                             title: 'Column',
                                             defaultValue: item.record.columnName,
                                         },
                                         dataType: {
                                                 key: true,
                                                 list: true,
                                                 create: false,
                                                 edit: true,
                                                 title: 'Type',
                                                 defaultValue: item.record.dataType,
                                             }

                                     }
                                 },
                                 function(data) { //opened handler

                                     data.childTable.jtable('load');
                                 });
                         }); //Return image to show on the person row

                         return $img;
                     }
                 }
     		    }
     	    });
     		    $('#Container').jtable('load');
     	    });
     	</script>



	</head>
<body>

 <div class="page-header">Message List</div>
<div class='col-md-12' id="messageDetails" >
    <section style="width:100%;text-align:center;">
	    <div id="Container"></div>
    </section>
	<div style="display:none" id="div-dialog-warning">
			<p><span class="ui-icon ui-icon-alert" style="float:left;"></span></p>
	</div>

 </div>
	</body>

</html>
