
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[hd_user]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[hd_user]
GO

CREATE TABLE [dbo].[hd_user] (
	[user_id] [int] IDENTITY (1, 1) NOT NULL ,
	[login] [nvarchar] (96) NOT NULL ,
	[passw] [nvarchar] (32) NOT NULL ,
	[first_name] [nvarchar] (64) NULL ,
	[last_name] [nvarchar] (128) NULL ,
	[email] [nvarchar] (128) NOT NULL ,
	[phone] [nvarchar] (20) NOT NULL ,
	[mobile] [nvarchar] (20) NULL ,
	[role] [smallint] NOT NULL ,
	[is_active] [bit] NOT NULL 
) ON [PRIMARY]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[hd_bug]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[hd_bug]
GO

CREATE TABLE [dbo].[hd_bug] (
	[bug_id] [int] IDENTITY (1, 1) NOT NULL ,
	[bug_category] [int] NOT NULL ,
	[bug_priority] [int] NOT NULL ,
	[bug_status] [int] NOT NULL ,
	[saviour] [int] NULL ,
	[notifyier] [int] NULL ,
	[inputer] [int] NULL ,
	[create_date] [datetime] NOT NULL ,
	[subject] [nvarchar] (255) NOT NULL ,
	[description] [ntext] NULL ,
	[step_by_step] [ntext] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[hd_bug_category]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[hd_bug_category]
GO

CREATE TABLE [dbo].[hd_bug_category] (
	[category_id] [int] IDENTITY (1, 1) NOT NULL ,
	[parent_category] [int] NULL ,
	[category_name] [nvarchar] (64) NOT NULL ,
	[category_desc] [nvarchar] (255) NULL ,
	[is_active] [bit] NOT NULL 
) ON [PRIMARY]
GO