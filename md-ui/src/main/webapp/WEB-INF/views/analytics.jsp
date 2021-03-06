<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	 pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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

	<link href="../css/pages.css" rel="stylesheet" type="text/css" />




	<!-- Include one of jTable styles. -->

	<link href="../css/metro/brown/jtable.css" rel="stylesheet" type="text/css" />
	<link href="../css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />

	<!-- Include jTable script file. -->
	<script src="../js/jquery.min.js" type="text/javascript"></script>
	<script src="../js/Chart.js" type="text/javascript"></script>
	<script src="../js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
	<script src="../js/jquery.jtable.js" type="text/javascript"></script>
	<script type="text/javascript">

	    GeneratePiChart(1, "chart-daily");
	    GeneratePiChart(7, "chart-weekly");


	    $(document).ready(function () {
		$('#Failure').jtable({
		    title: '<spring:message code="analytics.page.title_failures"/>',
		    paging: false,
		    pageSize: 10,
		    actions: {
			listAction: function (postData) {
			    return $.Deferred(function ($dfd) {
				$.ajax({
				    url: '/mdrest/welcomepage/weeklyfailure',
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
			processId: {
			    key: true,
			    list: true,
			    create: true,
			    edit: false,
			    title: '<spring:message code="analytics.page.title_p_id"/>'
			},
			instanceExecId: {
			    title: '<spring:message code="analytics.page.title_inst_id/>'
			}
		    }
		});
		$('#Failure').jtable('load');
	    })

	    $(document).ready(function () {
		$('#Running').jtable({
		    title: '<spring:message code="analytics.page.title_running"/>',
		    paging: false,
		    pageSize: 10,
		    actions: {
			listAction: function (postData) {
			    console.log(postData);
			    return $.Deferred(function ($dfd) {
				$.ajax({
				    url: '/mdrest/welcomepage/running',
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
			processId: {
			    key: true,
			    list: true,
			    create: true,
			    edit: false,
			    title: '<spring:message code="analytics.page.title_p_id"/>',
			    width: '30%'
			},
			iexecId: {
			    title: '<spring:message code="analytics.page.title_inst_id"/>',
			    list: true,
			    width: '30%'
			},
			pname: {
			    title: '<spring:message code="analytics.page.title_p_name"/>',
			    list: true,
			    width: '30%'
			},
			duration: {
			    title: '<spring:message code="analytics.page.title_duration"/>',
			    width: '30%'
			}
		    }
		});
		$('#Running').jtable('load');
	    })
	    function GeneratePiChart(time, value)
	    {
		$.ajax({
		    url: '/mdrest/welcomepage/successfailure/' + time,
		    type: 'GET',
		    dataType: 'json',
		    success: function (data) {
			var chartData = data;
			var countS = chartData.Records.countSuccess;
			var countF = chartData.Records.countFailure;
			var pieData = [
			    {
				value: countS,
				color: "#EDE4BF",
				highlight: "#EDE4CF",
				label: '<spring:message code="analytics.page.succes"/>'

			    },
			    {
				value: countF,
				color: "#61380a",
				highlight: "#71380a",
				label: '<spring:message code="analytics.page.fail"/>'
			    }

			];

			console.log(data);
			var ctx = document.getElementById(value).getContext("2d");
			window.myPie = new Chart(ctx).Pie(pieData);

		    },
		    error: function () {

		    }
		});


	    }
	</script>

    </head>
    <body>

	<table width="98%"  >
	    <tr>
		<td align="center" colspan="2"><h1><spring:message code="analytics.page.job_status"/></h1></td>
	    </tr>
	    <tr>
		<td align="center"><h2><spring:message code="analytics.page.status_hrs"/></h2></td>
		<td align="center"><h2><spring:message code="analytics.page.status_week"/></h2></td>
	    </tr>

	    <tr>
		<td align="center"><canvas id="chart-daily" width="200" height="200"/></td>
	<td align="center"><canvas id="chart-weekly" width="200" height="200"/></td>
</tr>
<tr>
    <td colspan="2" align="center" background-color="orange">
	<spring:message code="analytics.page.legends"/><font color ="brown"><spring:message code="analytics.page.fail"/></font>,<font color = "#EDE4BF"> <spring:message code="analytics.page.succes"/></font>
    </td>
</tr>
</table>


<center>
    <table>
	<tr>

	    <td valign="top"><div id="Failure"></div></td>
	    <td valign="top"><div id="Running"></div></td>
	</tr>

    </table>
</center>
</body>

</html>