# Installation Steps

General Prerequisites

Cloudera Quickstart 5.5.0
Git 1.9 and up
Maven 3 and up
Oracle JDK 8(and up) or OpenJDK
MySQL Server 5.1 and up
Google Chrome browser

# Steps:
1)Download and install VirtualBox from https://www.virtualbox.org/⏎

2)Download Cloudera Quickstart VM 5.5.0 from https://www.cloudera.com/downloads/cdh/5-5-0.html⏎

3)Setup a 'Host-Only Adapter' for network to enable communication between Host and Guest OS.⏎

4)Login to Cloudera Quickstart VM⏎

5)Download Maven from a mirror, unpack and add to the PATH.⏎

[cloudera@quickstart ~]# wget http://www.us.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip⏎
[cloudera@quickstart ~]# unzip apache-maven-3.3.9-bin.zip⏎
[cloudera@quickstart ~]# export PATH=$PATH:/home/cloudera/apache-maven-3.3.9/bin⏎

# Building StreamGrids from source

Obtain the source code⏎
cd to the home directory of cloudera.⏎

[cloudera@quickstart ~]# cd ~
Pull StreamGrids source from this git repository. To find out your repository link navigate to the repository in this website and copy the https repo URL.⏎

[cloudera@quickstart ~]# git clone https://github.com/bdremadhav/StreamGrids.git
cd to the cloned source dir ⏎

[cloudera@quickstart ~]# cd StreamGrids⏎

-------------------------
# Database Setup

Execute the dbsetup.sh script without any parameters as shown below. In this example, we are going to use MySQL as StreamGrids backend as it's already available in the Cloudera VM.⏎
[cloudera@quickstart ~/StreamGrids]# sh dbsetup.sh⏎
Supported DB⏎
1) Embedded ⏎
2) Oracle⏎
3) MySQL⏎
4) PostgreSQL⏎

Select Database Type(Enter 1, 2, 3 , 4 or leave empty and press empty to select the default DB):3⏎

Enter DB username (Type username or leave it blank for default 'root'):root⏎

Enter DB password (Type password or leave it blank for default '<blank>'):cloudera⏎
  
Enter DB hostname (Type db hostname or leave it blank for default 'localhost'):localhost⏎

Enter DB port (Type db port or leave it blank for default '3306'):3306⏎

Enter DB name (Type db name or leave it blank for default 'bdre'):platmd⏎

Enter DB schema (Type schema or leave it blank for default 'bdre'):platmd⏎

Please confirm:

Database Type: mysql⏎

JDBC Driver Class: com.mysql.jdbc.Driver⏎

JDBC Connection URL: jdbc:mysql://localhost:3306/platmd⏎

Database Username: root⏎

Database Password: cloudera⏎

Hibernate Dialect: org.hibernate.dialect.MySQLDialect⏎

Database Schema: platmd⏎

Are those correct? (type y or n - default y):y⏎

Database configuration written to ./md-dao/src/main/resources/db.properties⏎

Will create DB and tables⏎

Tables created successfully in MySQL platmd DB⏎

----------------
# Building

Now build StreamGrids using (note StreamGrids may not compile if the settings.xml is not passed from the commandline so be sure to use the -s option. When building for the first time, it might take a while as maven resolves and downloads the jar libraries from diffrent repositories.

mvn -s settings.xml clean install -P cdh52⏎

-----------------
# Installing StreamGrids⏎

After building StreamGrids successfully run

sh install-scripts.sh local

It'll install the StreamGrids scripts and artifacts in /home/cloudera/bdre

# Using StreamGrids

After a successful build, start the StreamGrids UI service

[cloudera@quickstart ~/StreamGrids]$ sh quick-run.sh⏎

Use Google Chrome browser from the host machine and open http://VM_IP:28850/mdui/pages/content.page⏎

Login using admin/zaq1xsw2⏎
