USE [DBexp]
GO

/****** Object:  Table [dbo].[SoftwareInfo_test]    Script Date: 2024/11/22 ¤U¤È 03:57:54 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SoftwareInfo_test](
	[Ip] [varchar](50) NULL,
	[Name] [varchar](max) NULL,
	[Version] [varchar](max) NULL,
	[Description] [varchar](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


