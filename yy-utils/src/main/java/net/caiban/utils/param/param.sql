/*==============================================================*/
/* Table: param_type                                            */
/*==============================================================*/
create  table if not exists `param_type` (
  `id` int(20) not null auto_increment ,
  `key` varchar(64) not null ,
  `name` varchar(254) null default null ,
  `gmt_created` datetime not null ,
  `gmt_modified` datetime not null ,
  primary key (`id`) ,
  unique index `key_unique` (`key` asc) )
engine = MyISAM
default character set = utf8;

/*==============================================================*/
/* Table: param                                                 */
/*==============================================================*/
create  table if not exists `param` (
  `id` int(20) not null auto_increment ,
  `types` varchar(64) not null ,
  `name` varchar(254) null default null ,
  `key` varchar(254) not null ,
  `value` varchar(254) not null ,
  `sort` tinyint(4) null default null ,
  `isuse` tinyint(4) not null default 0 ,
  `gmt_created` datetime not null ,
  `gmt_modified` datetime not null ,
  primary key (`id`) )
engine = MyISAM
default character set = utf8;
