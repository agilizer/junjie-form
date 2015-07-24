package com.agilemaster.form

import com.agilemaster.form.constants.JunjieFormConstants

class  InitCql {
	public static String DROP_CQL="""
drop keySpace  IF EXISTS ${JunjieFormConstants.DEFAULT_KEY_SPACE};
"""
	public  static String INIT_DEV_KEYSPACE="""
CREATE KEYSPACE IF NOT EXISTS ${JunjieFormConstants.DEFAULT_KEY_SPACE} WITH replication
= {'class': 'NetworkTopologyStrategy', 'datacenter1': '1'} ;
"""
	public static String INIT_PRODUCT_KEYSPACE="""
CREATE KEYSPACE IF NOT EXISTS ${JunjieFormConstants.DEFAULT_KEY_SPACE} WITH replication
=  {'class': 'NetworkTopologyStrategy', 'datacenter1': '1'} ;
"""
	public static List<String> INIT_CQL=["""
CREATE TABLE IF NOT EXISTS ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_HTML_INPUT} 
(id text PRIMARY KEY,
formId text,
inputAttrs map<text,text>,
inputType varchar,
labelAfter text,
labelBefore text,
listShow boolean,
saasId uuid,
selectInfo map<text,text>,
sequence int,
showToUser boolean,
valueType varchar,
dateCreated timestamp,
lastUpdated timestamp);""","""
CREATE TABLE IF NOT EXISTS ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_HTML_FORM}  
(id text PRIMARY KEY,
afterText text,
beforeText text,
customInfo  map<text,text>,
description text,
name text,
saasId text,
formId text,
finish boolean,
answerCount int,
exceptCount int,
endTime timestamp,
startTime timestamp,
dateCreated timestamp,
lastUpdated timestamp);""","""
CREATE TYPE IF NOT EXISTS ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_FILE_INFO}  
(
id text,
authorId text,
dateCreated timestamp,
lastUpdated timestamp,
fileSize int,
fileType varchar,
description text,
originalName varchar,
storeFileName varchar,
storePath varchar,
attributes map<text,text>);"""
,"""
CREATE TABLE IF NOT EXISTS ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_INPUT_VALUE}  
(id text PRIMARY KEY,
dateValue timestamp,
doubleValue double,
formId text,
formSubmitId text,
htmlInputId text,
label text,
listValue list<text>,
numberValue bigint,
strValue text,
userId text,
fileInfoes list<frozen<${JunjieFormConstants.T_FILE_INFO}  >>);""","""
CREATE TABLE IF NOT EXISTS ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_FORM_SUBMIT}
(id text PRIMARY KEY,
formId text,
userId text,
answerCount int,
exceptCount int,
finish boolean,
name text,
answerTime bigint,
dateCreated timestamp,
endTime timestamp,
startTime timestamp);""",
"""
CREATE TABLE IF NOT EXISTS  ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_FORM_SAAS}  
(id text PRIMARY KEY,
accessKey text,
attributes map<text,text>,
dateCreated timestamp,
lastUpdated timestamp
);
""",
"""
CREATE TABLE IF NOT EXISTS  ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_FORM_SAAS_COUNTER}  
(id text PRIMARY KEY,
counterValue counter
);
""",
"""
CREATE TABLE IF NOT EXISTS  ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_HTML_FORM_COUNTER}  
(id text PRIMARY KEY,
counterValue counter
);
""",
"""
CREATE TABLE IF NOT EXISTS  ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_SAAS_COUNTER}  
(id text PRIMARY KEY,
counterValue counter
);
""",
"""
update  ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_SAAS_COUNTER}  
set counterValue=counterValue+1 where id='${JunjieFormConstants.SAAS_COUNT_ID}';
"""
]
	
}
