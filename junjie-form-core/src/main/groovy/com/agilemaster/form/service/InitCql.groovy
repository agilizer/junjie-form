package com.agilemaster.form.service

class  InitCql {
	public static String DROP_CQL="""
drop keySpace junjie_form;
"""
	public  static String INIT_DEV_KEYSPACE="""
CREATE KEYSPACE IF NOT EXISTS junjie_form WITH replication
= {'class':'SimpleStrategy', 'replication_factor':2};
"""
	public static String INIT_PRODUCT_KEYSPACE="""
CREATE KEYSPACE IF NOT EXISTS junjie_form WITH replication
= {'class':'SimpleStrategy', 'replication_factor':2};
"""
	public static List<String> INIT_CQL=["""
CREATE TABLE IF NOT EXISTS junjie_form.HtmlInput 
(id text PRIMARY KEY,
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
CREATE TABLE IF NOT EXISTS junjie_form.HtmlForm 
(id text PRIMARY KEY,
afterText text,
beforeText text,
customInfo  map<text,text>,
description text,
name text,
saasId uuid,
htmlInputs list<text>,
dateCreated timestamp,
lastUpdated timestamp);""","""
CREATE TYPE IF NOT EXISTS junjie_form.FileInfo 
(
id uuid,
authorId text,
dateCreated timestamp,
lastUpdated timestamp,
fileSize int,
fileType varchar,
description text,
originalName varchar,
storeFileName varchar,
storePath varchar,
otherInfo map<text,text>);"""
,"""
CREATE TABLE IF NOT EXISTS junjie_form.InputValue 
(id text PRIMARY KEY,
dateValue timestamp,
doubleValue double,
formId uuid,
htmlInputId uuid,
label text,
listValue list<text>,
numberValue bigint,
strValue text,
userId text,
fileInfoes list<frozen<FileInfo>>);""","""
CREATE TYPE IF NOT EXISTS junjie_form.FormListShow 
(
name text,
answerTime bigint,
dateCreated timestamp,
endTime timestamp,
startTime timestamp,
formId uuid);""","""
CREATE TABLE IF NOT EXISTS junjie_form.FormUser 
(id text PRIMARY KEY,
dateCreated timestamp,
lastUpdated timestamp,
formList list<frozen<FormListShow>>);""","""
CREATE TABLE IF NOT EXISTS junjie_form.FormSaas
(id text PRIMARY KEY,
dateCreated timestamp,
lastUpdated timestamp,
formList list<frozen<FormListShow>>
);
"""
]

}
