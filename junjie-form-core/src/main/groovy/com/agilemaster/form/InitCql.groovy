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
inputAttrs  map<varchar,varchar>,
otherInfo map<varchar,varchar>,
inputType varchar,
labelAfter text,
labelBefore text,
listShow boolean,
saasId text,
selectValues list<varchar>,
rightAnswers list<varchar>,
sequence int,
showToUser boolean,
dateCreated timestamp,
lastUpdated timestamp);""",
"""CREATE INDEX input_formId_index ON  ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_HTML_INPUT} ( formId );""",
"""
CREATE TABLE IF NOT EXISTS ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_HTML_FORM}  
(id text PRIMARY KEY,
afterText text,
beforeText text,
customInfo  map<varchar,varchar>,
description text,
name text,
jsonContent text,
saasId text,
finish boolean,
answerCount int,
expectCount int,
inputCount int,
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
attributes map<varchar,varchar>);"""
,"""
CREATE TABLE IF NOT EXISTS ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_INPUT_VALUE}  
(id varchar PRIMARY KEY,
dateValue timestamp,
formId varchar,
htmlInputId varchar,
label text,
listValue list<varchar>,
numberValue bigint,
strValue text,
dateCreated timestamp,
anwserId varchar,
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
"""CREATE INDEX accessKey_index ON  ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_FORM_SAAS} ( accessKey );""",
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
